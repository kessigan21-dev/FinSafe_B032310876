package my.edu.utem.finsafe

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import my.edu.utem.finsafe.data.AppDatabase
import my.edu.utem.finsafe.data.Transaction

class DashboardActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TransactionAdapter
    private lateinit var btnAddTransaction: Button
    private lateinit var btnLowBalanceAlert: Button // Hidden for standard users
    private lateinit var db: AppDatabase
    private lateinit var transactionViewModel: TransactionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        createNotificationChannel()

        transactionViewModel = ViewModelProvider(this)[TransactionViewModel::class.java]

        db = AppDatabase.getDatabase(this)
        recyclerView = findViewById(R.id.recyclerView)
        btnAddTransaction = findViewById(R.id.btnAdd)
        btnLowBalanceAlert = findViewById(R.id.btnAlert)

        setupUserInterface()
        setupRecyclerView()

        btnAddTransaction.setOnClickListener {
            // Adds a dummy transaction
            val dummyAmount = if ((0..1).random() == 0) -50.0 else 100.0
            val type = if (dummyAmount < 0) "Debit" else "Credit"

            saveTransaction(dummyAmount, type)
        }

        btnLowBalanceAlert.setOnClickListener {
            setLowBalanceAlarm()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // TODO 13: Inflate the menu resource (res/menu/main_menu.xml)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // TODO 14: Handle the Logout menu item click
        return when (item.itemId) {
            R.id.action_logout -> {
                performLogout()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun performLogout() {
        // TODO 15: Clear SharedPreferences (isLoggedIn = false)

        // TODO 16: Navigate back to LoginActivity and finish() this activity

    }

    private fun setupUserInterface() {
        // TODO 5: Retrieve "userType" from SharedPreferences
        // TODO 6: If user is "standard", hide btnLowBalanceAlert (View.GONE).
        // If "premium", make it visible.
    }

    private fun setupRecyclerView() {
        adapter = TransactionAdapter()
        // TODO 7: Set LayoutManager for recyclerView
        recyclerView.adapter = adapter

        // Note: Logic to observe DB data and submit to adapter would typically go here
    }

    private fun saveTransaction(amount: Double, type: String) {
        val transaction = Transaction(amount = amount, type = type)

        // TODO 8: Use Coroutines (lifecycleScope or GlobalScope) to insert 'transaction' into 'db' on a background thread.
    }

    private fun setLowBalanceAlarm() {
        // TODO 9: Implement AlarmManager logic
        // Trigger a broadcast to AlarmReceiver after 10 seconds.
        Toast.makeText(this, "Alarm set for 10 seconds", Toast.LENGTH_SHORT).show()
    }

    private fun createNotificationChannel() {

    }
}