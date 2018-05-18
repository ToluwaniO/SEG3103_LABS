package com.example.oguns.lab1

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.noButton
import org.jetbrains.anko.yesButton

class MainActivity : AppCompatActivity() {

    private val bill = Bill()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tip_percentage_view.addTextChangedListener(ViewWatcher(Type.TIP))
        bill_view.addTextChangedListener(ViewWatcher(Type.BILL))
        people_view.addTextChangedListener(ViewWatcher(Type.PEOPLE))

        total_bill_view.text = "${Util.getCurrency(applicationContext)}0.00"
        tip_percentage_view.setText("${Util.getTipPercentage(applicationContext)}")

        summary.setOnClickListener {
            if(verify()) {
                startActivity(intentFor<SummaryActivity>().putExtras(
                        Bundle().apply {
                            putParcelable("bill", bill)
                        }
                ))
            }
        }

        suggest_tip.setOnClickListener { suggestTip() }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onStart() {
        super.onStart()
        total_bill_view.text = "${Util.getCurrency(applicationContext)}${bill.total}"
        tip_percentage_view.setText("${Util.getTipPercentage(applicationContext)}")
    }

    private fun updateUi() {

    }

    private fun verify(): Boolean {
        var status = true

        if(bill_view.text.isBlank()) {
            bill_view.error = "Bill must be set"
            status = false
        }
        if(tip_percentage_view.text.isBlank()) {
            tip_percentage_view.error = "Tip must be set"
            status = false
        }
        if(people_view.text.isBlank()) {
            people_view.error = "Number of payers must be set"
            status = false
        }
        return status
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId

        when (id) {
            R.id.action_settings -> {
                startActivity(intentFor<SettingsActivity>())
            }
        }
        return true
    }

    fun updateTotal() {
        val total = bill.amount + (bill.tipPercentage/100)*bill.amount
        val roundOff = Math.round(total * 100).toDouble() / 100
        bill.total = roundOff
        total_bill_view.text = "${Util.getCurrency(applicationContext)}$roundOff"

    }

    private fun suggestTip() {
        val rating = ratingBar.rating.toDouble()
        val tip = 10 + rating * 2
        val message = "Would you like to add a tip of $tip%? This was suggested with your restaurant rating."
        alert(message) {
            yesButton {
                tip_percentage_view.setText(tip.toString())
            }
            noButton {
                it.dismiss()
            }
        }.show()
    }

    enum class Type {
        PEOPLE, TIP, BILL
    }

    inner class ViewWatcher(private val type: Type) : TextWatcher {
        override fun afterTextChanged(s: Editable?) {

        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if(!s.isNullOrBlank()) {
                val text = s.toString()
                when(type) {
                    Type.BILL -> bill.amount = text.toDouble()
                    Type.TIP -> bill.tipPercentage = text.toDouble()
                    Type.PEOPLE -> bill.payers = text.toInt()
                }
            }
            updateTotal()
        }

    }

}
