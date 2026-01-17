package my.edu.utem.finsafe

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import my.edu.utem.finsafe.data.AppDatabase
import my.edu.utem.finsafe.data.Transaction

class DashboardActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TransactionAdapter
    private lateinit var btnAddTransaction: Button
    private lateinit var btnLowBalanceAlert: Button
    private lateinit var db: AppDatabase
    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        // Toolbar for menu
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        prefs = getSharedPreferences("FinSafePrefs", Context.MODE_PRIVATE)
        db = AppDatabase.getDatabase(this)

        recyclerView = findViewById(R.id.recyclerView)
        btnAddTransaction = findViewById(R.id.btnAdd)
        btnLowBalanceAlert = findViewById(R.id.btnAlert)

        setupUserInterface()
        setupRecyclerView()
        setupNotificationPermission()

        btnAddTransaction.setOnClickListener {
            val amount = if ((0..1).random() == 0) -50.0 else 100.0
            val type = if (amount < 0) "Debit" else "Credit"
            saveTransaction(amount, type)
        }

        btnLowBalanceAlert.setOnClickListener {
            setLowBalanceAlarm()
        }
    }

    // ---------------- MENU ----------------
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                performLogout()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun performLogout() {
        prefs.edit().clear().apply()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }


    private fun setupUserInterface() {
        val userType = prefs.getString("userType", "standard")
        btnLowBalanceAlert.visibility = if (userType == "premium") View.VISIBLE else View.GONE
    }


    private fun setupRecyclerView() {
        adapter = TransactionAdapter { transaction ->
            // Show confirmation dialog before deleting
            androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Delete Transaction")
                .setMessage("Do you really want to delete this transaction?")
                .setPositiveButton("Yes") { _, _ ->
                    // User confirmed deletion
                    lifecycleScope.launch {
                        db.transactionDao().delete(transaction)
                        val data = db.transactionDao().getAllTransactions()
                        adapter.setData(data)
                        Toast.makeText(
                            this@DashboardActivity,
                            "Transaction deleted",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                .setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss() // Do nothing
                }
                .show()
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // Load initial data
        lifecycleScope.launch {
            val data = db.transactionDao().getAllTransactions()
            adapter.setData(data)
        }
    }



    private fun saveTransaction(amount: Double, type: String) {
        lifecycleScope.launch {
            db.transactionDao().insert(Transaction(amount = amount, type = type))
            val data = db.transactionDao().getAllTransactions()
            adapter.setData(data)
        }
    }


    private fun setLowBalanceAlarm() {
        val userType = prefs.getString("userType", "standard")
        if (userType != "premium") {
            Toast.makeText(this, "Low balance alert is only for Premium users", Toast.LENGTH_SHORT)
                .show()
            return
        }

        Toast.makeText(this, "Low balance alert set for 10 seconds", Toast.LENGTH_SHORT).show()

        // Trigger AlarmReceiver after 10 seconds
        Handler(mainLooper).postDelayed({
            val intent = Intent(this, AlarmReceiver::class.java)
            sendBroadcast(intent) // triggers notification
        }, 10_000L) // 10 seconds
    }


    private fun setupNotificationPermission() {
        // Android 13+ notification permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) !=
                android.content.pm.PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 101)
            }
        }
    }
}
