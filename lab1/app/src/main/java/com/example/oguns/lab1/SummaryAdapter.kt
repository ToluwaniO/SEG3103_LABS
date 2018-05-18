package com.example.oguns.lab1

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.summary_item_layout.*

class SummaryAdapter(var bill: Bill, val context: Context): RecyclerView.Adapter<SummaryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.summary_item_layout, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount() = bill.payers

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class ViewHolder(override val containerView: View?) : RecyclerView.ViewHolder(containerView),
            LayoutContainer {

        fun bind(position: Int) {
            val currency = Util.getCurrency(context)
            val total = round(bill.total/bill.payers)
            val amount = round(bill.amount/bill.payers)
            var tip = round(bill.amount * (bill.amount/100) / bill.payers)
            val person = "Person ${position+1}"
            person_name.text = person
            tip_amount.text = "$currency$tip"
            bill_amount.text = "$currency$amount"
            total_amount.text = "$currency$total"
        }

    }

    fun round(value: Double) = Math.round(value * 100).toDouble() / 100

}