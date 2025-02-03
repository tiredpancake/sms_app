
package com.example.myapplication


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import android.widget.EditText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import com.example.myapplication.ui.theme.MyApplicationTheme
import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Telephony
import android.telephony.SmsManager
import android.telephony.SmsMessage
import android.widget.Button

import android.widget.Toast
import androidx.annotation.Nullable
import androidx.compose.material3.TooltipState
import androidx.compose.ui.res.integerResource

import java.security.Permissions
import java.util.Locale.IsoCountryCode

class MainActivity : ComponentActivity() {

    private val request_code_sms_permission=111


    private lateinit var editTextPhone2:EditText
    private lateinit var editTextTextMultiLine:EditText
    private lateinit var button:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.xyz)
        enableEdgeToEdge()

        editTextPhone2 = findViewById(com.example.myapplication.R.id.editTextPhone2);
        editTextTextMultiLine = findViewById(com.example.myapplication.R.id.editTextPhone2)
        button = findViewById(R.id.button)
        if(ActivityCompat.checkSelfPermission(  this,Manifest.permission.RECEIVE_SMS)!=PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECEIVE_SMS,Manifest.permission.SEND_SMS),request_code_sms_permission)
        }
        else{
            receive_msg()
            button.setOnClickListener{
                val sms : SmsManager=SmsManager.getDefault()
                sms.sendTextMessage(editTextPhone2.text.toString(),null ,editTextTextMultiLine.text.toString(),null,null)
                Toast.makeText(this, "SMS sent.", Toast.LENGTH_SHORT).show()
            }
        }

        /*setContent {
            MaterialTheme {
            }
        }*/
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array< String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == request_code_sms_permission && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            receive_msg()
        }

    }
    private  fun receive_msg()
    {
        var br=object:BroadcastReceiver()
        {
            override fun onReceive(context: Context?, intent: Intent?) {
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT)
                {
                    for(sms:SmsMessage in  Telephony.Sms.Intents.getMessagesFromIntent(intent))
                    {
                        editTextPhone2.setText(sms.originatingAddress)
                        editTextTextMultiLine.setText(sms.displayMessageBody)
                    }

                }
            }
        }
        registerReceiver(br, IntentFilter("android.provider.Telephony.SMS_RECEIVED"))
    }
}


//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//    Text(
//        text = "Hello $name!",
//        modifier = modifier
//    )
//}
//
//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    MyApplicationTheme {
//        Greeting("Android")
//    }
//}