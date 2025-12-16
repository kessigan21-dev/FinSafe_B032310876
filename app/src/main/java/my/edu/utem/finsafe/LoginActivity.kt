package my.edu.utem.finsafe

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LoginActivity : AppCompatActivity() {

    private lateinit var usernameInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        usernameInput = findViewById(R.id.etUsername)
        passwordInput = findViewById(R.id.etPassword)
        loginButton = findViewById(R.id.btnLogin)

        checkLoginStatus()

        loginButton.setOnClickListener {
            val user = usernameInput.text.toString()
            val pass = passwordInput.text.toString()
            performLogin(user, pass)
        }
    }

    private fun checkLoginStatus() {
        // TODO 1: Check SharedPreferences. If "isLoggedIn" is true, navigate directly to DashboardActivity
    }

    private fun performLogin(user: String, pass: String) {
        // TODO 2: Implement logic
        // Standard User -> User: "std", Pass: "1234"
        // Premium User -> User: "prm", Pass: "1234"

        // TODO 3: On success, save "isLoggedIn" boolean and "userType" string to SharedPreferences
        // TODO 4: Navigate to DashboardActivity
    }
}