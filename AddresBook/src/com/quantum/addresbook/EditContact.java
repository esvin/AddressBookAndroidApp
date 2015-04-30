package com.quantum.addresbook;

import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditContact extends Activity {

	EditText firstName;
	EditText lastName;
	EditText phoneNumber;
	EditText emailAddress;
	EditText homeAddress;
	DBTools  dbTools =new DBTools(this);
	
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_contact);
        
        firstName= (EditText) findViewById(R.id.firstName);
        lastName= (EditText) findViewById(R.id.lastName);
        phoneNumber= (EditText) findViewById(R.id.phoneNumber);
        emailAddress= (EditText) findViewById(R.id.emailAddress);
        homeAddress= (EditText) findViewById(R.id.homeaddress);
	
        Intent theIntent = getIntent();
        
        String contactId = theIntent.getStringExtra("contactid");
        
        HashMap<String,String> contactlist = dbTools.getContactInfo(contactId);
        
        if(contactlist.size()!=0){
        	
        	firstName.setText(contactlist.get("firstName"));
        	lastName.setText(contactlist.get("lastName"));
        	phoneNumber.setText(contactlist.get("phoneNumber"));
        	emailAddress.setText(contactlist.get("emailAddress"));
        	homeAddress.setText(contactlist.get("homeAddress"));
        }
	}
	
	public void editContact(View view){
	    HashMap<String,String> queryValuesMap= new HashMap<String,String>();
	    
		firstName= (EditText) findViewById(R.id.firstName);
        lastName= (EditText) findViewById(R.id.lastName);
        phoneNumber= (EditText) findViewById(R.id.phoneNumber);
        emailAddress= (EditText) findViewById(R.id.emailAddress);
        homeAddress= (EditText) findViewById(R.id.homeaddress);
	
        Intent theIntent = getIntent();
        
        String contactId = theIntent.getStringExtra("contactid");
        
        queryValuesMap.put("contactid", contactId);
        queryValuesMap.put("firstName", firstName.getText().toString());
        queryValuesMap.put("lastName", lastName.getText().toString());
        queryValuesMap.put("phoneNumer", phoneNumber.getText().toString());
        queryValuesMap.put("emailAddress", emailAddress.getText().toString());
		queryValuesMap.put("homeAddress", homeAddress.getText().toString());
		dbTools.updateContact(queryValuesMap);
		this.callMainActivity(view);
	}
	public void deleteContact(View view){
		
		Intent theIntent = getIntent();
		String contactId =theIntent.getStringExtra("contactid");
		
		dbTools.deleteContact(contactId);
		this.callMainActivity(view);
		
	}
public void callMainActivity(View view){
		
		Intent intent = new Intent(getApplication(),MainActivity.class);
		startActivity(intent);
		
	}
	
	
}
