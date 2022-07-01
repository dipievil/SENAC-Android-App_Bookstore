package br.dipievil.appbookstore.repository

import android.content.ContentValues
import android.content.Context
import android.database.DatabaseErrorHandler
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import br.dipievil.appbookstore.model.Book

class DbHandler(
    context: Context?,
    errorHandler: DatabaseErrorHandler?
) : SQLiteOpenHelper(context, NAME, null, VERSION, errorHandler) {

    companion object{
        const val NAME = "Bookstore"
        const val VERSION = 1
        const val TABLE = "books"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createQuery = "CREATE TABLE IF NOT EXISTS $TABLE (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, "+
                "title TEXT, "+
                "pages INTEGER)"
        db?.execSQL(createQuery)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }

    fun addBook( book: Book): Boolean{
        var db = this.writableDatabase

        val values = ContentValues()
        values.put("title", book.title)
        values.put("pages", book.pages)
        val returnInsert = db.insert(TABLE,null,values)
        db.close()
        return returnInsert >= 0
    }

    fun updateBook( book: Book): Boolean{
        var db = this.writableDatabase

        val values = ContentValues()
        values.put("title", book.title)
        values.put("pages", book.pages)
        val returnInsert = db.update(TABLE,values, "id = "+book.id, null)
        db.close()
        return returnInsert >= 0
    }

    fun deleteBook(idBook: Int) : Boolean{
        var db = this.writableDatabase
        val returnDelete = db.delete(TABLE, "id = "+idBook, null)
        db.close()
        return returnDelete >= 0
    }

    fun getBooks() : ArrayList<Book>{
        var books = ArrayList<Book>()
        var db = this.readableDatabase
        val query = "SELECT * FROM $TABLE ORDER BY title"
        val cursor = db.rawQuery(query, null)
        if (cursor != null){
            if(cursor.count > 0){
                cursor.moveToFirst()

                do {
                    val id = cursor.getInt(0)
                    val title = cursor.getString(1)
                    val pages = cursor.getInt(2)
                    val book = Book(id, title, pages)
                    books.add(book)
                } while(cursor.moveToNext())
            }
        }

        return books
    }

    fun getBookById(idBook: Int) : Book?{
        var db = this.readableDatabase
        var book: Book
        val query = "SELECT * FROM $TABLE WHERE id = $idBook ORDER BY title"
        val cursor = db.rawQuery(query, null)
        if (cursor != null){
            if(cursor.count > 0){
                cursor.moveToFirst()
                val id = cursor.getInt(0)
                val title = cursor.getString(1)
                val pages = cursor.getInt(2)
                val book = Book(id, title, pages)
                return book
            }
        }
        return null
    }
}