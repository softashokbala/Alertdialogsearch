import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.alertwithsearch.Item
import com.example.alertwithsearch.R
import java.util.Locale

class ItemAdapter(private val originalItems: List<Item>) :
    RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    private val filteredItems = ArrayList<Item>(originalItems)
    private val selectedItems = ArrayList<Item>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_checkbox, parent, false)
        return ItemViewHolder(itemView)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = filteredItems[position]
        holder.bind(item)

        if (holder.itemCheckbox.isChecked && !selectedItems.contains(item)) {
            selectedItems.add(item)
        }
        holder.itemView.setOnClickListener {
            if(holder.itemCheckbox.isChecked) {
                item.isChecked=false
                holder.itemCheckbox.isChecked =false
                selectedItems.remove(item)
                Log.e("selectedItems3", selectedItems.toString());
            }else
            {
                item.isChecked=true
                holder.itemCheckbox.isChecked =true
                selectedItems.add(item)
                Log.e("selectedItems3", selectedItems.toString());
            }
            notifyDataSetChanged()
        }

//        holder.itemView.setOnClickListener {
//            item.isChecked = !item.isChecked
//            holder.itemCheckbox.isChecked = item.isChecked
//
//            if (item.isChecked) {
//                selectedItems.add(item)
//            } else {
//                selectedItems.remove(item)
//            }
//
//            notifyDataSetChanged()
//            Log.e("selectedItems3", selectedItems.toString())
//        }
      // Notify adapter about the change
    }

    override fun getItemCount(): Int = filteredItems.size

    fun filter(query: String) {
        filteredItems.clear()
        if (query.isEmpty()) {
            filteredItems.addAll(originalItems)
        } else {
            val filterPattern = query.toLowerCase().trim()
            originalItems.forEach {
                if (it.name.toLowerCase(Locale.ROOT).contains(filterPattern)) {
                    filteredItems.add(it)
                }
            }
        }
        notifyDataSetChanged()
    }

    fun getSelectedItems(): ArrayList<Item> {
        return selectedItems
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val itemText: TextView = itemView.findViewById(R.id.itemText)
        val itemCheckbox: CheckBox = itemView.findViewById(R.id.itemCheckbox)

        fun bind(item: Item) {
            itemText.text = item.name
            itemCheckbox.isChecked = item.isChecked
        }
    }
}
