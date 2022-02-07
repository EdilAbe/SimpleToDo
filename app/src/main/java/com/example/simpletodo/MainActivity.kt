package com.example.simpletodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.FileUtils
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.io.IOException
import java.nio.charset.Charset
import java.util.*


class MainActivity : AppCompatActivity() {
    var listOfTasks= mutableListOf<String>()

    // adapter is defined later in our code so inorder to access it now, we can create a variable
    lateinit var adapter: TaskItemAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       val onLongClickListener = object: TaskItemAdapter.OnLongClickListener{
           override fun onItemLongClicked(position: Int) {
              //when an item is long clicked, we want to remove the item from the list
           listOfTasks.removeAt(position)
              // notify the adapter that our dataset has changed
           adapter.notifyDataSetChanged()

               saveItems()
           }

       }
        // this will populate items by reading from the file
        loadItems()


        //detect when the user clicks on the add button
     //   findViewById<Button>(R.id.button).setOnClickListener{
            // code in here will be executed when a user clicks on a button


        //look up recyclerview in layout
      val recyclerView= findViewById<RecyclerView>(R.id.recyclerView)
        // create adapter passing in the sample user data
       adapter =TaskItemAdapter(listOfTasks,onLongClickListener)
        // Attach the adapter to the recylerview to populate items
        recyclerView.adapter =adapter
        //layout manager tells the recycler view how to set up itself
        recyclerView.layoutManager = LinearLayoutManager(this)

  // set up the button and input filed , so that the user can enter a task
       val inputTextField = findViewById<EditText>(R.id.addTaskField)

        // fist we find the button by its id
        findViewById<Button>(R.id.button).setOnClickListener{
           // once the user click on the button
            // we first grab the text the user inputted into the edit text field
val userInputtedTask = inputTextField.text.toString()


            // then add the string to list of tasks
listOfTasks.add(userInputtedTask)
            //notify the adapter that the data has been updated
            adapter.notifyItemInserted(listOfTasks.size-1)
            //reset the field
            inputTextField.setText("")
            //When to save the files
            saveItems()

        }

        }

    // save the data the user has inputted
    // by writing and reading from a specific file
    //create a method to get the data file we need
fun getDataFile(): File {
        //every line is going to represent a specific task in our list of tasks

    return File (filesDir, "data.txt")
}
    //load the items by reading every line in the data file
    fun loadItems(){
        try {
       listOfTasks= FileUtils.readLines(getDataFile(),Charset.defaultCharset())
        }catch (ioException:IOException){
           ioException.printStackTrace()
        }

    }
    //save items by writing them into our data file
    fun saveItems(){
       try{
            FileUtils.WriteLines(getDataFile(), listOfTasks)
       } catch (ioException: IOException){
           ioException.printStackTrace()
       }
    }
    }

