package com.example.productivityapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class MemoryPiAdapter(private val fileList: Array<String>) :
    RecyclerView.Adapter<MemoryPiAdapter.ViewHolder>() {

    companion object {
        val EXTRA_FILENAME = "fileName"
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView
        val layout: ConstraintLayout

        init {
            // Define click listener for the ViewHolder's View
            textView = view.findViewById(R.id.textView_memoryPiFileSelect_fileName)
            layout = view.findViewById(R.id.layout_item_file)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.file_item, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        if (fileList.size != 0) {
            viewHolder.textView.text = fileList[position]
        } else {
            viewHolder.textView.text = "Nothing here"
        }

        val context = viewHolder.layout.context

        viewHolder.layout.setOnClickListener {
            val intent = Intent(context, MemoryPi::class.java)
            intent.putExtra(EXTRA_FILENAME, fileList[position].substring(fileList[position].lastIndexOf("/")+1))
            context.startActivity(intent)
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = fileList.size

}
