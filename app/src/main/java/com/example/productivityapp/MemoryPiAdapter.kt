package com.example.productivityapp

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import java.io.File


class MemoryPiAdapter(var fileList: MutableList<File>) :
    RecyclerView.Adapter<MemoryPiAdapter.ViewHolder>() {



    companion object {
        val EXTRA_FILENAME = "fileName"
    }


    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val fileName: TextView
        val layout: ConstraintLayout

        init {
            // Define click listener for the ViewHolder's View
            fileName = view.findViewById(R.id.textView_memoryPiFileSelect_fileName)
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

        val fileName = fileList[position].name

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
            viewHolder.fileName.text = fileName

        val context = viewHolder.layout.context

        viewHolder.layout.isLongClickable = true
        viewHolder.layout.setOnLongClickListener {
            val popMenu = PopupMenu(context, viewHolder.fileName)
            popMenu.inflate(R.menu.menu_memory_pi_files_context)
            popMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.menu_memoryPiFiles_delete -> {
                        delete(position)
                        true
                    }
                    else -> true
                }
            }
            popMenu.show()
            true
        }

        viewHolder.layout.setOnClickListener {

//            val intent = Intent(context, MemoryPi::class.java)
//            intent.putExtra(EXTRA_FILENAME, fileName)
//            (context as Activity).startActivityForResult(intent,1)



            val intent = Intent(context, MemoryPi::class.java)
            intent.putExtra(EXTRA_FILENAME, fileName)
            context.startActivity(intent)
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return fileList.size
    }

    
    private fun delete(position: Int) {
        fileList[position].delete()
        fileList.removeAt(position)
        notifyDataSetChanged()
    }

}




