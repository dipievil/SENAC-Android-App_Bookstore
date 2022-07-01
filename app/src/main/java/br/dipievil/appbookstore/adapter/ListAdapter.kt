package br.dipievil.appbookstore.adapter

import android.content.ComponentCallbacks
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.dipievil.appbookstore.R
import br.dipievil.appbookstore.model.Book

/*
class ListAdapter(private val books: List<Book>, internal val context: Context) :
    RecyclerView.Adapter<ListAdapter.ViewHolder>() {
*/

class ListAdapter(private val books: List<Book>, internal val context: Context,
                    private val callbacks: (Int,String) -> Unit) :
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

        if(position%2==0)
            holder.layout.setBackgroundColor(Color.rgb(240,240,240))
        else
            holder.layout.setBackgroundColor(Color.rgb(255,255,255))

        holder.layout.setOnClickListener{
           this.callbacks(position,"editar")
        }

        holder.layout.setOnLongClickListener {
            this.callbacks(position,"deletar")
        }
    }

    override fun getItemCount(): Int {
        return this.books.size
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        var tvTitle : TextView = view.findViewById(R.id.tvTitle)
        var tvPages : TextView = view.findViewById(R.id.tvPages)
        var tvId : TextView = view.findViewById(R.id.tvId)
        var layout: LinearLayout = view.findViewById(R.id.linearLayout)
    }
}