package com.example.dailyexpensetracker.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dailyexpensetracker.R
import com.example.dailyexpensetracker.model.TransactionModel

class TransactionAdapter(val context: Context, var data: ArrayList<TransactionModel>): RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {
    class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tName: TextView = itemView.findViewById(R.id.displayName)
        val tAmount: TextView = itemView.findViewById(R.id.displayAmount)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val itemView: View =
            LayoutInflater.from(context).inflate(R.layout.sample_transaction, parent, false)
        return TransactionViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return data.size;
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.tName.text = data[position].transactionName
        holder.tAmount.text = data[position].amount.toString()


//        holder.editButtom.setOnClickListener{
//            val intent = Intent(context,UpdateProductActivity::class.java)
//            intent.putExtra("productId",data[position].productId)
//            context.startActivity(intent)
//
//        }
    }

    fun updateData(products: List<TransactionModel>) {
        data.clear()
        data.addAll(products)
        notifyDataSetChanged()
    }

    fun getProductId(position: Int): String {
        return data[position].transactionId
    }
}
