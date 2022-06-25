package br.dipievil.appbookstore.adapter

import android.content.ComponentCallbacks
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.dipievil.appbookstore.R
import br.dipievil.appbookstore.model.Book

class ListAdapter(private val books: List<Book>, internal val context: Context,
                    private val callbacks: (Int) -> Unit) :
    RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    //Create the view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
         val view = LayoutInflater.from(context).inflate(R.layout.content_list, parent, false)
         return ViewHolder(view);
    }

    //Receive the values
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
         val book = books[position]
        holder.tvTitle.text = book.title
        holder.tvId.text = book.id.toString()
        holder.tvPages.text = book.pages.toString()
    }

    override fun getItemCount(): Int {
        return this.books.size
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        var tvTitle : TextView = view.findViewById(R.id.tvTitle)
        var tvPages : TextView = view.findViewById(R.id.tvPages)
        var tvId : TextView = view.findViewById(R.id.tvId)
    }
}