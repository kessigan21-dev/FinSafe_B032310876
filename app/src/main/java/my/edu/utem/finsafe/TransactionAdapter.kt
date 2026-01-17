package my.edu.utem.finsafe

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import my.edu.utem.finsafe.data.Transaction

class TransactionAdapter(
    private val onLongClick: (Transaction) -> Unit
) : RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

    private var transactionList = listOf<Transaction>()

    fun setData(list: List<Transaction>) {
        transactionList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_transaction, parent, false)
        return TransactionViewHolder(view)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val item = transactionList[position]

        holder.tvType.text = item.type
        holder.tvAmount.text = item.amount.toString()

        if (item.amount < 0) {
            holder.tvAmount.setTextColor(Color.RED)
        } else {
            holder.tvAmount.setTextColor(Color.GREEN)
        }

        holder.itemView.setOnLongClickListener {
            onLongClick(item) // Callback to delete
            true
        }
    }

    override fun getItemCount(): Int = transactionList.size

    inner class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvType: TextView = itemView.findViewById(R.id.tvType)
        val tvAmount: TextView = itemView.findViewById(R.id.tvAmount)
    }
}

