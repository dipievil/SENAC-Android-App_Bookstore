package br.dipievil.appbookstore.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.dipievil.appbookstore.R
import br.dipievil.appbookstore.adapter.ListAdapter
import br.dipievil.appbookstore.databinding.ActivityMainBinding
import br.dipievil.appbookstore.model.Book
import br.dipievil.appbookstore.repository.DbHandler

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var rvBooks : RecyclerView

    var listAdapter : ListAdapter? = null
    var linearLayoutManager : LinearLayoutManager? = null

    var books: List<Book> = ArrayList<Book>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        rvBooks = findViewById(R.id.rvBooks)

/*
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
*/
        binding.fab.setOnClickListener { view ->
            val intent = Intent(this, FormActivity::class.java)
            intent.putExtra("action","inserir")
            startActivity(intent);

        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        val dbHandler = DbHandler(this, null)
        books = dbHandler.getBooks()

        listAdapter = ListAdapter(books,this) { position, action ->
            if(action.equals("editar")){
                var intent = Intent(this, FormActivity::class.java)
                intent.putExtra("action","editar")
                intent.putExtra("idBook", books[position].id)
                startActivity(intent)
            } else {
                //deletar
                if(dbHandler.deleteBook(books[position].id)){
                    books = dbHandler.getBooks()
                    listAdapter.notifyDataSetChanged()
                }
            }
        }
        linearLayoutManager = LinearLayoutManager(this)
        rvBooks.layoutManager = linearLayoutManager
        rvBooks.adapter = listAdapter

        //Toast.makeText(this, "Total de livros: $cont",
        // Toast.LENGTH_LONG).show()
    }

    override fun onSupportNavigateUp(): Boolean {
        /*
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()

         */
        return true;
    }
}