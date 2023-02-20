package com.example.tiptime

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.example.tiptime.databinding.ActivityMainBinding
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.calculateButton.setOnClickListener { calculateTip() }

        binding.costOfServiceEditText.setOnKeyListener { view, keyCode, _ -> handleKeyEvent(view, keyCode)
        }
        
    }
    private fun calculateTip(){
        // Get the decimal value from the cost of service text field

        val stringInTextField = binding.costOfServiceEditText.text.toString()
//        Notice the .text on the end. The first part, binding.costOfService,
//        references the UI element for the cost of service.
//        Adding .text on the end says to take that result (an EditText object),
//        and get the text property from it.
//        This is known as chaining, and is a very common pattern in Kotlin.

        val cost = stringInTextField.toDoubleOrNull()
//        toDouble() needs to be called on a String. It turns out that the text attribute of
//        an EditText is an Editable, because it represents text that can be changed. Fortunately,
//        you can convert an Editable to a String by calling toString() on it.

        if (cost == null || cost == 0.0) {
            /*binding.tipResult.text=""*/
            displayTip(0.0)
            return
//            The return instruction means exit the method without executing
//            the rest of the instructions. If the method needed to return a value,
//            you would specify it with a return instruction with an expression.
        }

        //Radio
        //Get the tip percentage
        val tipPercentage = when(binding.tipOptions.checkedRadioButtonId){
            R.id.option_eighteen_percent-> 0.20
            R.id.option_fifteen_percent-> 0.50
            else -> 0.15
        }

        //Calculate the tip and round it up
        var tip = tipPercentage * cost

        //switch
        if (binding.roundUpSwitch.isChecked){
            tip = kotlin.math.ceil(tip)
        }

        displayTip(tip)
    }
    private fun displayTip(tip : Double) {
        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        binding.tipResult.text= getString(R.string.tip_amount, formattedTip)
    }

    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the keyboard
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }

}