package com.example.finalmadproject.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalmadproject.Database.DatabaseHelper;
import com.example.finalmadproject.R;
import com.example.finalmadproject.TaskManagement.MainActivity;
import com.example.finalmadproject.TaskManagement.ReadTaksSelectable;

import java.util.ArrayList;
import java.util.List;

import static com.example.finalmadproject.TaskManagement.Task.TaskEntry.TASK_NAME;

public class ViewAllTasks extends AppCompatActivity {

    //DatabaseHelper dbHandler;
    Button addView;
    TextView taskSelected;
    DatabaseHelper mydb;
    String[] listItems;
    String[] list2; //Testing
    boolean[] checkedItems;
    private ListView layout;
    ArrayList<Integer> userItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_tasks);
        mydb = new DatabaseHelper(this);
        //Pass list ID to the List View page
        Intent intent = getIntent();
        String message = intent.getStringExtra(CustomAdapter.EXTRA_ID);
        System.out.println("ID: " + message);

        //System.out.println(message);
        //dbHandler = new DatabaseHelper(ViewAllTasks.this);
        //new one
        //storeTaskInArrays();

        addView = findViewById(R.id.btnaddTask);
        //taskSelected = findViewById(R.id.tvTaskSelected);
        layout = findViewById(R.id.listView);

        //-------------------------------
        //List -> ArrayList
        //Call the function when approaching to the page
        //Append the items to the list.
        //-------------------------------

        //listItems = getResources().getStringArray(R.array.Tasks);

        //Getting data from the DB.
        DatabaseHelper db = new DatabaseHelper(this);
        SQLiteDatabase database = db.getReadableDatabase();
        Cursor object = db.readTasks(database);
        int count2 = 0;
        //Testing
        while (object.moveToNext()){

            count2++;
        }

        //Create String Array
        //List<String> list = new ArrayList<>();
        list2 = new String[count2];
        int count = 0;
        for(object.moveToFirst(); !object.isAfterLast(); object.moveToNext()){
            list2[count] = object.getString(object.getColumnIndex(TASK_NAME));
            count++;
        }


        checkedItems = new boolean[count];

        addView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(ViewAllTasks.this);
                mBuilder.setTitle(R.string.dialog_title);
                mBuilder.setMultiChoiceItems(list2, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                      //  if (isChecked) {
                          // if (!userItems.contains(position)) {
                             //  userItems.add(position);
                          // }
                       // } else if (userItems.contains(position)) {
                          //  userItems.remove(position);
                     //  }

                        if(isChecked){
                            userItems.add(position);
                            String tasks = list2[0];
                            System.out.println(tasks);
                            //boolean result = mydb.insertListTaskData()
                        }else{
                            userItems.remove((Integer.valueOf(position)));
                        }
                    }
                });
                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton(R.string.ok_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {

                        //Set the list to the Adapter
                        ListAdapter adapter = new ArrayAdapter<>(getApplication(),android.R.layout.simple_list_item_1,list2);
                        layout.setAdapter(adapter);
//                        String item = "";
//                        for (int i = 0; i < userItems.size(); i++) {
//                            item = item + listItems[userItems.get(i)];
//                            if (i != userItems.size() - 1) {
//                                item = item + ", ";
//                            }
//                        }
//                        taskSelected.setText(item);
                    }
                });

                mBuilder.setNegativeButton(R.string.dismiss_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                mBuilder.setNeutralButton(R.string.clear_all_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        for (int i = 0; i < checkedItems.length; i++) {
                            checkedItems[i] = false;
                            userItems.clear();
                            taskSelected.setText("");
                        }
                    }
                });

                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });
    }



    //new Method
   /* void storeTaskInArrays(){
        Cursor cursor = dbHandler.readAlltasks();
        if(cursor.getCount() == 0){
            Toast.makeText(this,"No data", Toast.LENGTH_SHORT).show();
        }else{
            while(cursor.moveToNext()){
               // id.add(cursor.getString(0));
                //title.add(cursor.getString(1));
                //description.add(cursor.getString(2));
                titleTask.add(cursor.getString(0));
                desTask.add(cursor.getString(1));
            }
        }
    }
    */
}