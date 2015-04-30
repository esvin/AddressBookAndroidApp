package com.quantum.addresbook;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class Carrito extends ListActivity{
	Intent  intent;
	String thisChange = "randomChangeforGitHub";
	TextView productoId;
	EditText total;
	DBTools dbtools = new DBTools(this);
	String personaId="";
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.carrito_start);
        Intent theIntent = getIntent();
        personaId =	 theIntent.getStringExtra("Cod_Persona");
        total =(EditText) findViewById(R.id.totalcarritoEdittext);
        total.setText(String.valueOf(dbtools.Total(personaId)));
 
        ArrayList<HashMap<String,String>> contactList = dbtools.getAllCarritos(personaId,"0");
        
        if(contactList.size()!=0){
        	
        	ListView listView = getListView();
        	
        	listView.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> arg0, View view,
						int arg2, long arg3) {
					
						
						
				}});
        	ListAdapter adapter = new SimpleAdapter(Carrito.this,contactList,R.layout.product_entry,
        						  new String[]{"Cod_Producto","Nombre_Producto"}, new int[]{R.id.productoid,R.id.productdata});
        	setListAdapter(adapter);
        }
        
        
    }
    public void comprarCarrito(View view){
    	
    	int size=dbtools.getAllCarritos(personaId, "0").size();
    		for(int i=0;i<size;i++){
    			dbtools.updateCarrito(dbtools.getAllCarritos(personaId, "0").get(i));
    		}
    	
    }
    public void sincronizarCarrito(View view){
    	
    	//emitir post para crear nuevos carritos y tambien nueva
    }
    public void regresar(View view){   	
    	Intent theIntent = new Intent(getApplicationContext(), Productos.class);
    	theIntent.putExtra("Cod_Persona",personaId);
    	startActivity( theIntent );
    	
    }

}
