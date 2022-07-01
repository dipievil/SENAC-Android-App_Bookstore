package br.dipievil.appbookstore.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import br.dipievil.appbookstore.R
import br.dipievil.appbookstore.model.Book
import br.dipievil.appbookstore.repository.DbHandler

class FormActivity : AppCompatActivity() {

    lateinit var etTitle: EditText
    lateinit var etPages: EditText
    lateinit var btnSave: Button

    var action: String? = ""
    var book: Book? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        etTitle = findViewById(R.id.etTitle)
        etPages = findViewById(R.id.etPages)
        btnSave = findViewById(R.id.btnSave)
        action= intent.getStringExtra("action")
        if( action.equals("inserir")){
            book = Book()
        } else {
            val idBook = intent.getIntExtra("idBook",0)
            //Get the book!
            book = DbHandler(this,null).getBookById(idBook)
            if(book == null){
                finish()
            } else {
              loadForm()
            }
        }
    }

    fun loadForm(){
        etTitle.setText(book?.title)
        etPages.setText(book?.pages.toString())
    }

    fun save(view: View){
        book?.title = etTitle.text.toString()

        if(etPages.text.toString().isEmpty()){
            book?.pages = 0
        } else {
            book?.pages = etPages.text.toString().toInt()
        }

        val dbHandler = DbHandler(this,null)
        if(action.equals("inserir")){
            dbHandler.addBook(book!!)
            etTitle.text.clear()
            etPages.text.clear()
        } else {
            dbHandler.updateBook(book!!)
            finish()
        }
    }

}