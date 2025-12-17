package my.edu.utem.finsafe

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import my.edu.utem.finsafe.data.AppDatabase

class DashboardActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TransactionAdapter
    private lateinit var btnAddTransaction: Button
    private lateinit var btnLowBalanceAlert: Button // Hidden for standard users
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

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
        // For this test, you can assume a simple refresh list function is called
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
}