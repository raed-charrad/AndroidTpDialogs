package com.example.dialogs

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Html
import android.text.TextUtils
import android.util.Patterns
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    lateinit var email:EditText
    lateinit var nom:EditText
    lateinit var prenom:EditText
    lateinit var btn: Button
    lateinit var classe : Spinner
    lateinit var  DoBbtn :Button
    private fun isValidEmail(email: EditText): Boolean {
        return !TextUtils.isEmpty(email.text) && Patterns.EMAIL_ADDRESS.matcher(email.text).matches()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val builderP =AlertDialog.Builder(this)
        val dialog = builderP.create()
        val dialogeView = layoutInflater.inflate(R.layout.progressdialog,null)
        val message = dialogeView.findViewById<TextView>(R.id.text)
        message.text = getString(R.string.messagewait)
        dialog.setView(dialogeView)
        dialog.setCancelable(false)
        dialog.show()
        Handler(Looper.getMainLooper()).postDelayed({dialog.dismiss()},5000)
        btn = findViewById<Button>(R.id.button)
        email = findViewById<EditText>(R.id.EmailAddress)
        prenom = findViewById<EditText>(R.id.FirstName)
        nom = findViewById<EditText>(R.id.LastName)
        classe = findViewById<Spinner>(R.id.Classe)
        DoBbtn = findViewById<Button>(R.id.DoB)
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        var msg = String()
        DoBbtn.setOnClickListener{
            val DoB =DatePickerDialog(this, DatePickerDialog.OnDateSetListener{ view,year,monthOfYear,dayOfMonth ->
                msg = " "+dayOfMonth+ "/"+month +"/"+year
            },year,month,day)
            DoB.show()
        }
        ArrayAdapter.createFromResource(
            this,
            R.array.classee,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            classe.adapter = adapter
        }
        btn.setOnClickListener {
            if (email.text.isBlank()||nom.text.isBlank()||prenom.text.isBlank()||classe.selectedItem == null || msg.length==null ){
                Toast.makeText(this, "Les champs ne doivent pas etre vide !", Toast.LENGTH_LONG).show();
                val snack = Snackbar.make(it,"email no valide ",Snackbar.LENGTH_LONG)
                snack.show()
            }else if (!isValidEmail(this.email)){
                val snack = Snackbar.make(it,"email no valide ",Snackbar.LENGTH_LONG)
                snack.show()
            }
            else{
                val str = email.text.toString()
                val Name = nom.text.toString()
                val LastName = prenom.text.toString()
                val Classe = classe.selectedItem.toString()
                val alertDialogBuilder = AlertDialog.Builder(this);
                alertDialogBuilder.setTitle(Html.fromHtml("<font color='#ff0000'>confirme !</font>"))
                alertDialogBuilder.setMessage("nom : $Name prenom :$LastName email : $str Classe : $Classe date de naissance : $msg")
                alertDialogBuilder.setNegativeButton("OK"){dialog,which ->}
                alertDialogBuilder.setPositiveButton("yes"){dialog,which ->
                    val intent = Intent(applicationContext, MainActivity2::class.java)
                    val text = "nom : $Name prenom :$LastName email : $str Classe : $Classe Date de naissance : $msg"
                    intent.putExtra("message_key", text)
                    startActivity(intent)
                }
                alertDialogBuilder.show();

            }
        }
    }

}