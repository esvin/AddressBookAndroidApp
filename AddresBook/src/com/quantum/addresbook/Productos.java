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

public class Productos extends ListActivity{
	Intent  intent;
	TextView productoId;
	DBTools dbtools = new DBTools(this);
	String personaId="";
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.productos_start);
        Intent theIntent = getIntent();
        personaId =	 theIntent.getStringExtra("Cod_Persona");
       
        ArrayList<HashMap<String,String>> contactList = dbtools.getAllProductos();
        
        if(contactList.size()!=0){
        	
        	ListView listView = getListView();
        	
        	listView.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> arg0, View view,
						int arg2, long arg3) {
					
						productoId =(TextView) view.findViewById(R.id.productoid);
						
						String productoIdValue = productoId.getText().toString();
						
						Intent theIntent = new Intent(getApplication(), NewCarrito.class);
						theIntent.putExtra("Cod_Producto", productoIdValue);
						theIntent.putExtra("Cod_Persona",personaId);
						startActivity(theIntent);
						
				}});
        	ListAdapter adapter = new SimpleAdapter(Productos.this,contactList,R.layout.product_entry,
        						  new String[]{"Cod_Producto","Nombre_Producto"}, new int[]{R.id.productoid,R.id.productdata});
        	setListAdapter(adapter);
        }
        
        
    }
    
    public void showCarrito(View view){
    	
    	Intent theIntent = new Intent(getApplicationContext(), Carrito.class);
    	theIntent.putExtra("Cod_Persona", personaId);
    	startActivity( theIntent );
    	
    }
public void showReporte(View view){
    	
    	Intent theIntent = new Intent(getApplicationContext(), Reporte.class);
    	theIntent.putExtra("Cod_Persona", personaId);
    	startActivity( theIntent );
    	
    }

}
