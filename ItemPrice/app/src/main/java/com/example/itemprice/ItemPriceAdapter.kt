package com.example.itemprice

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

class ItemPriceAdapter(private val itemListFull: List<ItemPrice>) :
    RecyclerView.Adapter<ItemPriceAdapter.ItemPriceViewHolder>(), Filterable {

    private var itemList: List<ItemPrice> = itemListFull.toList()

    class ItemPriceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemNameTextView: TextView = itemView.findViewById(R.id.itemName)
        val itemPriceTextView: TextView = itemView.findViewById(R.id.itemPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemPriceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_price_row, parent, false)
        return ItemPriceViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemPriceViewHolder, position: Int) {
        val currentItem = itemList[position]
        holder.itemNameTextView.text = currentItem.name

        // Format the price using the formatPrice function
        holder.itemPriceTextView.text = formatPrice(currentItem.price)
    }

    override fun getItemCount(): Int = itemList.size

    // Function to format price
    private fun formatPrice(price: Double): String {
        val numberFormat = NumberFormat.getInstance(Locale("de", "DE")) // Using German locale to get . as separator
        return numberFormat.format(price)
    }

    // Filterable implementation
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredList = if (constraint.isNullOrEmpty()) {
                    itemListFull
                } else {
                    val query = constraint.toString().lowercase()
                    itemListFull.filter { it.name.lowercase().contains(query) }
                }

                return FilterResults().apply { values = filteredList }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                itemList = results?.values as List<ItemPrice>
                notifyDataSetChanged()
            }
        }
    }
}
