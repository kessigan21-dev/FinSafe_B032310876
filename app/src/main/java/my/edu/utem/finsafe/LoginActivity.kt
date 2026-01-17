package my.edu.utem.finsafe

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var usernameInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var loginButton: Button
    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        usernameInput = findViewById(R.id.etUsername)
        passwordInput = findViewById(R.id.etPassword)
        loginButton = findViewById(R.id.btnLogin)

        prefs = getSharedPreferences("FinSafePrefs", MODE_PRIVATE)

        checkLoginStatus()

        loginButton.setOnClickListener {
            val user = usernameInput.text.toString()
            val pass = passwordInput.text.toString()
            performLogin(user, pass)
        }
    }

    private fun checkLoginStatus() {
        val isLoggedIn = prefs.getBoolean("isLoggedIn", false)
        if (isLoggedIn) {
            startActivity(Intent(this, DashboardActivity::class.java))
            finish()
        }
    }

    private fun performLogin(user: String, pass: String) {
        when {
            user == "std" && pass == "1234" -> {
                saveLogin("standard")
            }
            user == "prm" && pass == "1234" -> {
                saveLogin("premium")
            }
            else -> {
                Toast.makeText(this, "Invalid login", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveLogin(userType: String) {
        prefs.edit()
            .putBoolean("isLoggedIn", true)
            .putString("userType", userType)
            .apply()

        startActivity(Intent(this, DashboardActivity::class.java))
        finish()
    }
}
