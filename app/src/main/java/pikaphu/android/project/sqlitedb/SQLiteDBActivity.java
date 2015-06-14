package pikaphu.android.project.sqlitedb;


import java.util.List;
import java.sql.SQLException;

import android.app.Dialog;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import pikaphu.android.project.sqlitedb.contactapp.Contact;
import pikaphu.android.project.sqlitedb.contactapp.ContactDataSource;

public class SQLiteDBActivity extends ListActivity {
    final static String TAG = "SQLite";
    private ContactDataSource datasource;
    List<Contact> values;

    // on start open db connection and show all contact data.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main); //setContentView(R.layout.main);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        Button add_contact = (Button) findViewById(R.id.btn_Add);
        add_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addContact();
            }
        });

        try
        {
            datasource = new ContactDataSource(this);
            datasource.open();
            showAllContact();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    // List all contact from db
    private void showAllContact() {
        // setListAdapter(null);
        // clear then get data
        values = datasource.getAllContact();
        ArrayAdapter<Contact> adapter = new ArrayAdapter<Contact>(this,
                android.R.layout.simple_list_item_1, values);

        //android.R.id.list
        setListAdapter(adapter);
    }

    // when tap on list item.
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l,v,position,id);
        showContactDetail(position);
    }

    // show data at db cursor
    private void showContactDetail(final int id) {
        @SuppressWarnings("unchecked")
        final ArrayAdapter<Contact> adapter = (ArrayAdapter<Contact>) getListAdapter();
        final Contact contact = (Contact) getListAdapter().getItem(id);

        // Dialog for action
        final Dialog dialog = new Dialog(SQLiteDBActivity.this);
        dialog.setContentView(R.layout.detail_contact);
        dialog.setTitle("Contact Detail");
        dialog.setCancelable(true);

        // View for display data
        TextView txt_firstname = (TextView) dialog.findViewById(R.id.txtvw_Firstname);
        TextView txt_surname = (TextView) dialog.findViewById(R.id.txtvw_Surname);
        TextView txt_title = (TextView) dialog.findViewById(R.id.txtvw_Title);
        TextView txt_birthdate = (TextView) dialog.findViewById(R.id.txtvw_Birthdate);
        TextView txt_address = (TextView) dialog.findViewById(R.id.txtvw_Address);
        TextView txt_phone = (TextView) dialog.findViewById(R.id.txtvw_Phone);

        txt_firstname.setText("Firstname: \t" + contact.getFirstname());
        txt_surname.setText("Surname: \t" + contact.getSurname());
        txt_title.setText("Title: \t" + contact.getTitle());
        txt_birthdate.setText("Birthdate: \t" + contact.getBirthdate());
        txt_address.setText("Address: \t" + contact.getAddress());
        txt_phone.setText("Phone: \t" + contact.getPhone());

        //edit
        Button btn_edit = (Button) dialog.findViewById(R.id.btn_Edit);
        btn_edit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getListAdapter().getCount() > 0) {
                    editContact(contact);
                    dialog.dismiss();
                }
            }
        });

        //delete
        Button btn_delete = (Button) dialog.findViewById(R.id.btn_Delete);
        btn_delete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getListAdapter().getCount() > 0) {
                    datasource.deleteContact(contact); // remove from db
                    adapter.remove(contact);// remove from displayed array
                    dialog.dismiss();
                    Toast.makeText(SQLiteDBActivity.this, "Delete data succeed", Toast.LENGTH_LONG).show();
                }
            }
        });

        //close dialog
        Button btn_cancel = (Button) dialog.findViewById(R.id.btn_Close);
        btn_cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    // edit data
    public void editContact(final Contact contact) {
        // set layout dialog for editing
        final Dialog dialog = new Dialog(SQLiteDBActivity.this);
        dialog.setContentView(R.layout.add_contact);
        dialog.setTitle("Edit Contact");
        dialog.setCancelable(true);

        // find and define controls
        final EditText edtxtFirstname = (EditText) dialog.findViewById(R.id.edtxt_Firstname);
        final EditText edtxtSurname = (EditText) dialog.findViewById(R.id.edtxt_Surname);
        final EditText edtxtTitle = (EditText) dialog.findViewById(R.id.edtxt_Title);
        final EditText edtxtBirthdate = (EditText) dialog.findViewById(R.id.edtxt_Birthdate);
        final EditText edtxtAddress = (EditText) dialog.findViewById(R.id.edtxt_Address);
        final EditText edtxtPhone = (EditText) dialog.findViewById(R.id.edtxt_Phone);

        // assign data to controls
        edtxtFirstname.setText(contact.getFirstname());
        edtxtSurname.setText(contact.getSurname());
        edtxtTitle.setText(contact.getTitle());
        edtxtBirthdate.setText(contact.getBirthdate());
        edtxtAddress.setText(contact.getAddress());
        edtxtPhone.setText(contact.getPhone());

        // setup controls
        Button btnSave = (Button) dialog.findViewById(R.id.btn_Save);
        btnSave.setText("Update");
        btnSave.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // get string from controls input
                String valueFirstname = edtxtFirstname.getText().toString();
                String valueSurname = edtxtSurname.getText().toString();
                String valueTitle = edtxtTitle.getText().toString();
                String valueBirthdate = edtxtBirthdate.getText().toString();
                String valueAddress = edtxtAddress.getText().toString();
                String valuePhone = edtxtPhone.getText().toString();

                // set string to object plain
                contact.setFirstname(valueFirstname);
                contact.setSurname(valueSurname);
                contact.setTitle(valueTitle);
                contact.setBirthdate(valueBirthdate);
                contact.setAddress(valueAddress);
                contact.setPhone(valuePhone);

                // then Update "real" datasource
                datasource.updateContact(contact);

                // switch to show data layout
                showAllContact();

                // popup cancel dialog while loading data
                dialog.cancel();
            }
        });
        // add handle to cancel dialog
        Button btnCancel = (Button) dialog.findViewById(R.id.btn_Cancel);
        btnCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        // show dialog on screen
        dialog.show();

        //region "custom edit"
        String apptitle = getResources().getString(R.string.title_activity_sqlite_db);
        String sayhi = getString(R.string.hello_world);
        //endregion
    }

    // Insert new data
    private void addContact() {
        // set layout dialog for add data
        final Dialog dialog = new Dialog(SQLiteDBActivity.this);
        dialog.setContentView(R.layout.add_contact);
        dialog.setTitle("Add New Contact");
        dialog.setCancelable(true);

        // setup button
        Button btnSave = (Button) dialog.findViewById(R.id.btn_Save);
        btnSave.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                // find and define controls
                final EditText edtxtFirstname = (EditText) dialog.findViewById(R.id.edtxt_Firstname);
                final EditText edtxtSurname = (EditText) dialog.findViewById(R.id.edtxt_Surname);
                final EditText edtxtTitle = (EditText) dialog.findViewById(R.id.edtxt_Title);
                final EditText edtxtBirthdate = (EditText) dialog.findViewById(R.id.edtxt_Birthdate);
                final EditText edtxtAddress = (EditText) dialog.findViewById(R.id.edtxt_Address);
                final EditText edtxtPhone = (EditText) dialog.findViewById(R.id.edtxt_Phone);

                // get string from controls input
                String valueFirstname = edtxtFirstname.getText().toString();
                String valueSurname = edtxtSurname.getText().toString();
                String valueTitle = edtxtTitle.getText().toString();
                String valueBirthdate = edtxtBirthdate.getText().toString();
                String valueAddress = edtxtAddress.getText().toString();
                String valuePhone = edtxtPhone.getText().toString();

                @SuppressWarnings("unchecked")
                ArrayAdapter<Contact> adapter = (ArrayAdapter<Contact>) getListAdapter();
                Contact contact = new Contact();
                // set string to object
                contact.setFirstname(valueFirstname);
                contact.setSurname(valueSurname);
                contact.setTitle(valueTitle);
                contact.setBirthdate(valueBirthdate);
                contact.setAddress(valueAddress);
                contact.setPhone(valuePhone);
                contact = datasource.insertContact(contact); // update data then return
                adapter.add(contact);
                dialog.cancel();
            }
        });
        Button btnCancel = (Button) dialog.findViewById(R.id.btn_Cancel);
        btnCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        dialog.show();
    }

    @Override
    protected void onResume() {
        try {
            datasource.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        //datasource.close();
        super.onPause();
    }

/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sqlite_db, menu);
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
*/

}// end cls
