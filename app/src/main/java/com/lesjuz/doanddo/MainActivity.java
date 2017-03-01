package com.lesjuz.doanddo;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements EditDialogFragment.EditInterface
{

    private ListView list;
    ArrayAdapter<Todo> adapter;
    ArrayList<Todo> todoList;
    private EditText task;
    private Button add;
    private Todo todo1;
    DbHelper myDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        task= (EditText) findViewById(R.id.txt_Task);
        add= (Button) findViewById(R.id.button_add);
        list= (ListView) findViewById(R.id.list_todo);
        myDb = new DbHelper(this);
        todo1=new Todo();
        proccessListView();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(task.getText().toString().trim())){
                    todo1.setItem(task.getText().toString());
                    myDb.addData(todo1);
                    proccessListView();
                    task.setText("");
                }

            }
        });
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                // TODO Auto-generated method stub

                int DbId=todoList.get(position).getId();

                showDeleteDialog(DbId);
                return true;
            }

        });
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int DbId2=todoList.get(i).getId();
                showEditDialog(DbId2,todoList.get(i).getItem());

            }
        });
    }
    private void showDeleteDialog(final int Dbid) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are You Sure you want to delete the task?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        myDb.delete(Dbid);
                        proccessListView();

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Do something on Cancel
                        dialog.cancel();
                    }
                });
        builder.show();



    }
    public void proccessListView() {
        todoList = new ArrayList<>();
        todoList = myDb.getAllData();
        loadListView(todoList);
    }

    private void loadListView(ArrayList<Todo> item){
        list.setAdapter(new ListAdapter(this, item));
    }


    private void showEditDialog(int id,String task) {
        FragmentManager fm = getSupportFragmentManager();
        EditDialogFragment editNameDialogFragment = EditDialogFragment.newInstance(id,task);
        editNameDialogFragment.show(fm, "fragment_edit_name");
    }

    private void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
    public void updateTask(String inputText,int id) {
        myDb.editList(id,inputText);
        proccessListView();

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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
