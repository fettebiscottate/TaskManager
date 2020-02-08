package com.example.TaskManager;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DataBaseHelper myDb;
    String filtroSel = "";
    Task task;
    ListView listView;
   //CardView cardView;
    ArrayList<Task> taskArrayList;
    customListAdapter listAdapter;

    Cursor data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        loadActivity();

    }

    @Override
    protected void onResume() {
        super.onResume();

        loadActivity();
    }



    private void loadActivity() {

        final Spinner filtro = (Spinner)findViewById(R.id.spinner_filter_cat);
        myDb = new DataBaseHelper(this);


        taskArrayList = new ArrayList<>();

        listView = (ListView) findViewById(R.id.list_view);
        data = myDb.getListContent();
        taskArrayList.clear();
        showTaskList(data, taskArrayList, listAdapter);


        ArrayList <String> categorie = new ArrayList<>();

        categorie = myDb.getAllCat();
        categorie.add(0,"ALL");
        categorie.add(1,"DONE");
        categorie.add(2,"ONDOING");
        categorie.add(3,"TO DO");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, categorie);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        filtro.setAdapter(adapter);


        filtro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filtroSel = filtro.getSelectedItem().toString();
                if (filtroSel.equalsIgnoreCase("ALL")) {
                    showTaskList(data, taskArrayList, listAdapter);
                }

                else if (filtroSel.equalsIgnoreCase("DONE")){

                    String closed = "CLOSE";
                    ArrayList<Task> filterList = onSelecetedSpinner(closed);
                    showTaskList(data, filterList, listAdapter);
                }

                else if (filtroSel.equalsIgnoreCase("ONDOING")){

                    ArrayList<Task> filterList = onSelecetedSpinner(filtroSel);
                    showTaskList(data, filterList, listAdapter);
                }

                else if (filtroSel.equalsIgnoreCase("TO DO")){

                    String open = "OPEN";
                    ArrayList<Task> filterList = onSelecetedSpinner(open);
                    showTaskList(data, filterList, listAdapter);
                }

                else {

                    ArrayList<Task> filterList = onSelecetedSpinner(filtroSel);
                    showTaskList(data, filterList, listAdapter);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                Toast.makeText(MainActivity.this, "Null Filter selected ", Toast.LENGTH_SHORT).show();
                showTaskList(data, taskArrayList, listAdapter);
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Task task = (Task) listView.getItemAtPosition(position);
                String title = task.getName();
                String cat = task.getCategory();
                Intent intent = new Intent(MainActivity.this, editTask.class);
                intent.putExtra("title", title);
                intent.putExtra("cat", cat);
                startActivity(intent);
            }
        });

        //Il bottone rimanda all' add Task activity
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent add_intent = new Intent(MainActivity.this,add_task.class);
                startActivity(add_intent);

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(MainActivity.this, "no settings here", Toast.LENGTH_SHORT).show();
        }
        if (id == R.id.action_category){
            Intent add_intent = new Intent(MainActivity.this,editCategory.class);
            startActivity(add_intent);
        }
        if(id == R.id.action_calendar){
            Intent add_intent = new Intent(MainActivity.this,CalendarActivity.class);
            startActivity(add_intent);
        }
        if(id == R.id.action_graph){
            Intent add_intent = new Intent(MainActivity.this, Graph.class);
            startActivity(add_intent);
        }

        return super.onOptionsItemSelected(item);
    }

    //funzione che una volta richiamata mostra nella listview principale i tasj estratti dal db
    public void showTaskList (Cursor dataC, ArrayList <Task> taskArrayList, customListAdapter listAdapter) {

        if (dataC.getCount() == 0) {

            Toast.makeText(MainActivity.this, "empty category db", Toast.LENGTH_LONG).show();
        }


        else {
            while (dataC.moveToNext() ) {
                    task = new Task(dataC.getString(1), dataC.getString(2), dataC.getString(3), dataC.getString(5), dataC.getString(6));
                    taskArrayList.add(task);


            }
            listAdapter = new customListAdapter(this, R.layout.list_adapter_view_mennets, taskArrayList);
            listView = (ListView) findViewById(R.id.list_view);
            listView.setAdapter(listAdapter);
            listAdapter.notifyDataSetChanged();
        }
    }

    public ArrayList onSelecetedSpinner (String select){
        ArrayList <Task> filter = new ArrayList<>();
        if (select.equalsIgnoreCase("CLOSE")||select.equalsIgnoreCase("ONDOING")||select.equalsIgnoreCase("OPEN")){
            for (int i = 0; i < taskArrayList.size(); i++) {
                if (taskArrayList.get(i).getState().equalsIgnoreCase(select)) {
                    filter.add(taskArrayList.get(i));
                }
            }
        }
        else {
            for (int i = 0; i < taskArrayList.size(); i++) {
                if (taskArrayList.get(i).getCategory().equalsIgnoreCase(select)) {
                    filter.add(taskArrayList.get(i));
                }
            }
        }

        listAdapter = new customListAdapter(this,R.layout.list_adapter_view_mennets,filter);
        listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();

        return filter;
    }
}
