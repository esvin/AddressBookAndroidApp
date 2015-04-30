package com.quantum.addresbook;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class Reporte extends ListActivity{
	Intent  intent;
	TextView productoId;
	DBTools dbtools = new DBTools(this);
	String personaId="";
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reporte);
        Intent theIntent = getIntent();
        personaId =	 theIntent.getStringExtra("Cod_Persona");
       
        ArrayList<HashMap<String,String>> contactList = dbtools.getAllCompras(personaId);
        
        if(contactList.size()!=0){
        	
        	ListView listView = getListView();
        	
        	listView.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> arg0, View view,
						int arg2, long arg3) {					
				}});
        	ListAdapter adapter = new SimpleAdapter(Reporte.this,contactList,R.layout.product_entry,
        						  new String[]{"Cod_Producto","Nombre_Producto"}, new int[]{R.id.productoid,R.id.productdata});
        	setListAdapter(adapter);
        }
        
        
    }
    
    public void regresa(View view){
    	
    	Intent theIntent = new Intent(getApplicationContext(), Productos.class);
    	theIntent.putExtra("Cod_Persona", personaId);
    	startActivity( theIntent );
    }	
 }
