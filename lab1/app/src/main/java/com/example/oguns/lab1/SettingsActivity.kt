package com.example.oguns.lab1

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_settings.*
import org.jetbrains.anko.selector

class SettingsActivity : AppCompatActivity() {

    private val currencies = listOf<String>("Dollar ($)", "Pound (£)", "Euro (€)")
    private val symbols = listOf<String>("$", "£", "€")

    private val watcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if(s != null && s.isNotBlank()) {
                val tip = s.toString()
                Util.updateTipPercentage(applicationContext, tip.toDouble())
            } else {
                Util.updateTipPercentage(applicationContext, 0.0)
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        currency_view.setOnClickListener {
            selector("Select currency", currencies, {dialogInterface, i ->
                currency_view.text = symbols[i]
                Util.updateCurrency(applicationContext, symbols[i])
            })
        }

        tip_percentage.addTextChangedListener(watcher)

        updateView()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId
        when(id) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }

    private fun updateView() {

        currency_view.text = Util.getCurrency(applicationContext)
        tip_percentage.setText(Util.getTipPercentage(applicationContext))

    }
}
