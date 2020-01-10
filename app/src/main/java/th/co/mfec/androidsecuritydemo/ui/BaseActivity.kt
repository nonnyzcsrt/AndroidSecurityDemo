package th.co.mfec.androidsecuritydemo.ui

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.scottyab.rootbeer.RootBeer
import kotlinx.android.synthetic.main.activity_main.*
import th.co.mfec.androidsecuritydemo.R
import th.co.mfec.androidsecuritydemo.utils.CommonUtils
import th.co.mfec.androidsecuritydemo.utils.SecurityUtils

abstract class BaseActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun showErrorDialog(title: String, message: String) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setNegativeButton(android.R.string.no, null)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
    }

    fun showSuccessDialog(title: String, message: String) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(
                android.R.string.ok
            ) { dialog, which ->
                dialog.dismiss()
            }
            .show()
    }

    fun showConfirmDialog(title: String, message: String, okClickListener: () -> Unit) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(
                android.R.string.yes
            ) { dialog, which ->
                okClickListener.invoke()
            }
            .setNegativeButton(android.R.string.no, null)
            .setIcon(android.R.drawable.ic_dialog_info)
            .show()
    }
}
