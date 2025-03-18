package mx.edu.itesca.practica11

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    private val userRef = FirebaseDatabase.getInstance().getReference("Users")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var btn_save:Button = findViewById(R.id.btn_save)
        btn_save.setOnClickListener {
            saveMarkFromForm()
        }

        userRef.addChildEventListener(object: ChildEventListener {
            override fun onCancelled(error: DatabaseError){}
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?){}
            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?){}
            override fun onChildRemoved(snapshot: DataSnapshot) {
            }

            override fun onChildAdded(dataSnapshot: DataSnapshot,p1:String?){
                val value =dataSnapshot.getValue()

                if(value is String){
                }else if(value is User){
                    val usuario = value

                    if(usuario != null){
                        writeMark(usuario)
                    }
                }
            }
        })
    }

    private fun saveMarkFromForm(){
        var et_name:EditText=findViewById(R.id.et_name)
        var et_lastName:EditText=findViewById(R.id.et_lastName)
        var et_age:EditText=findViewById(R.id.et_age)

        val usuario = User(et_name.text.toString(),et_lastName.text.toString(),et_age.text.toString())

        userRef.push().setValue(usuario)
    }

    private fun writeMark(mark:User){
        var tv_list:TextView = findViewById(R.id.tv_list)
        val text = tv_list.text.toString()+mark.toString()+"\n"
        tv_list.text=text
    }
}