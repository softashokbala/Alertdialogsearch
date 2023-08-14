package com.example.alertwithsearch

import ItemAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import java.util.Collections

val items = listOf(
    Item(1, "Item 1",false),
    Item(2, "Item 2",true),
    Item(3, "Item 3",false),
    // Add more items
)
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val openDialogButton = findViewById<Button>(R.id.openDialogButton)


        openDialogButton.setOnClickListener {
            showMultiSelectDialog()
        }
    }

    private fun showMultiSelectDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_multi_select, null)
        val recyclerView = dialogView.findViewById<RecyclerView>(R.id.itemListView)
        val searchEditText: EditText = dialogView.findViewById(R.id.searchInput)
        val itemAdapter = ItemAdapter(items)
        recyclerView.adapter = itemAdapter


        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                itemAdapter.filter(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        // To get selected items
        //val selectedItems = itemAdapter.getSelectedItems()

        AlertDialog.Builder(this)
            .setTitle("Select Items")
            .setView(dialogView)
            .setPositiveButton("Confirm") { _, _ ->
                val selectedItems = itemAdapter.getSelectedItems()
                Log.e("selectedItems",selectedItems.toString())
                // Sort the selectedItems list based on the id field
                Collections.sort(selectedItems) { item1, item2 ->
                    item1.id.compareTo(item2.id)
                }
                Log.e("selectedItems",selectedItems.toString())

                showSelectedItemsToast(selectedItems)
            }
            .setNegativeButton("Cancel", null)
            .create()
            .show()
    }

    private fun showSelectedItemsToast(selectedItems: List<Item>) {
        if (selectedItems.isEmpty()) {
            Toast.makeText(this, "No items selected", Toast.LENGTH_SHORT).show()
            return
        }

        val selectedNames = selectedItems.joinToString { it.name }
        Log.e("selectedNames",selectedNames.toString())
        val toastMessage = "Selected Count: ${selectedItems.size}"

        Toast.makeText(this, toastMessage, Toast.LENGTH_LONG).show()
    }
}