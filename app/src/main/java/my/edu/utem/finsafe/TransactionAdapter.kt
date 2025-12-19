package my.edu.utem.finsafe

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import my.edu.utem.finsafe.data.Transaction

class TransactionAdapter : RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

    private var transactionList = listOf<Transaction>()

    fun setData(list: List<Transaction>) {
        this.transactionList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_transaction, parent, false)
        return TransactionViewHolder(view)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val currentItem = transactionList[position]

        // TODO 10: Bind data to Views
        // holder.tvType.text = ...
        // holder.tvAmount.text = ...

        // TODO 11: Logic
        // If amount < 0, set tvAmount text color to Color.RED
        // If amount >= 0, set tvAmount text color to Color.GREEN

        // TODO 12: Implement Long Click Listener
        // On long press, show a Toast with "Transaction ID: [id]"
    }

    override fun getItemCount(): Int {
        return transactionList.size
    }

    class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvType: TextView = itemView.findViewById(R.id.tvType)
        val tvAmount: TextView = itemView.findViewById(R.id.tvAmount)
    }
}