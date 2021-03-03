package uz.usoft.test.core

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

open class BaseActivity : AppCompatActivity() {

    fun toastSH(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun toastLN(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}