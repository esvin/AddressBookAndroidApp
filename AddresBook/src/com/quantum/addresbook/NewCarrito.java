package com.quantum.addresbook;

import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class NewCarrito extends Activity{
	
	EditText Nombre_Producto;
	EditText Tipo_Producto;
	EditText Talla_Producto;
	EditText Color_Producto;
	EditText Precio_Producto;
	EditText Cantidad_Producto;
	String productoId ="";
	String personaId = "";
	
	DBTools  dbTools =new DBTools(this);
	
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_carrito);
        
        Nombre_Producto= (EditText) findViewById(R.id.nombreEdittext);
        Tipo_Producto= (EditText) findViewById(R.id.tipoEdittext);
        Talla_Producto= (EditText) findViewById(R.id.tallaEditText);
        Color_Producto= (EditText) findViewById(R.id.colorEditText);
        Precio_Producto= (EditText) findViewById(R.id.precioEdittext);
        Cantidad_Producto= (EditText) findViewById(R.id.cantidadEditText);
    	
    
        Intent theIntent = getIntent();
        
        productoId = theIntent.getStringExtra("Cod_Producto");
        personaId =	 theIntent.getStringExtra("Cod_Persona");
        HashMap<String,String> productlist = dbTools.getProductoInfo(productoId);
        
        if(productlist.size()!=0){
        	
        	Nombre_Producto.setText(productlist.get("Nombre_Producto"));
        	Nombre_Producto.setEnabled(false);
        	Tipo_Producto.setText(productlist.get("Tipo_Producto"));
        	Tipo_Producto.setEnabled(false);
        	Talla_Producto.setText(productlist.get("Talla_Producto"));
        	Talla_Producto.setEnabled(false);
        	Color_Producto.setText(productlist.get("Color_Producto"));
        	Color_Producto.setEnabled(false);
        	Precio_Producto.setText(productlist.get("Precio_Producto"));
        	Precio_Producto.setEnabled(false);
        	Cantidad_Producto.setText("1");
        }
	}
	
	public void addNewCarrito(View view){
		
	    HashMap<String,String> queryValuesMap= new HashMap<String,String>();
	    
        queryValuesMap.put("Cod_Producto", productoId);
        queryValuesMap.put("Cod_Persona", personaId);
        queryValuesMap.put("Cantidad_Producto", Cantidad_Producto.getText().toString());
        queryValuesMap.put("Comprado","0" );
        dbTools.insertCompra(queryValuesMap);
		this.callMainActivity(view);
	}
	
public void callMainActivity(View view){
		
		Intent intent = new Intent(getApplication(),Productos.class);
		intent.putExtra("Cod_Persona",personaId);
		startActivity(intent);
		
	}
	

}
