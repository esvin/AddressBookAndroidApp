package com.quantum.addresbook;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class DBTools extends SQLiteOpenHelper {

		public DBTools(Context applicationContext){
			//super(applicationContext,"contactbook.db",null,1);
			super(applicationContext,"mayaka.db",null,1);
		}

		@Override
		public void onCreate(SQLiteDatabase database) {
			// TODO Auto-generated method stub
		  String consulta_persona="create table Persona(Cod_Persona INTEGER PRIMARY KEY,"+
								  "Dpi INTEGER, Nombre1 TEXT,Nombre2 TEXT,"+
							      "Apellido1 TEXT,Apellido2 TEXT,Nit INTEGER,"+
							       "Estado_Civil TEXT,Genero TEXT,Tipo_Persona INTEGER)";
		  
		  String consulta_producto="create table Producto(Cod_Producto INTEGER PRIMARY KEY,"+
				  				   "Tipo_Producto TEXT,Nombre_Producto TEXT,"+
				  				   "Talla_Producto TEXT,Color_Producto TEXT,"+
				  				   "Precio_Producto MONEY)";
			
		  String consulta_carrito="create table carrito(Cod_Producto INTEGER,"
				  				+ "Cod_Persona INTEGER,Cantidad_Producto INTEGER,"
				  				+ "Comprado INTEGER,primary key(Cod_Producto,Cod_Persona),"
				  				+ "foreign key(Cod_Producto) references Producto(Cod_Producto),"
				  				+ "foreign key(Cod_Persona) references Persona(Cod_Persona))";
		  database.execSQL(consulta_persona);
		  database.execSQL(consulta_producto);
		  database.execSQL(consulta_carrito);
		  
	      String query ="CREATE TABLE CONTACTS(contactid INTEGER PRIMARY KEY,firstname TEXT,"+
						 "lastName TEXT, phoneNumber TEXT, emailAddress TEXT, homeAddress TEXT)";
		 
	     // database.execSQL(query);
		
		}

		@Override
		public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
			String query="DROP TABLE IF EXISTS CONTACTS";
			database.execSQL(query);
			onCreate(database);
		}
		public void deletedata(String table){
			SQLiteDatabase database=this.getWritableDatabase();
			String query="DELETE FROM "+table;
			database.execSQL(query);
			
		}
		public void insertContact(HashMap<String, String> queryValues){
			
			SQLiteDatabase database=this.getWritableDatabase();
			//store key value pairs column name and the datatype
			ContentValues values =new ContentValues();
			values.put("firstName", queryValues.get("firstName"));
			values.put("lastName", queryValues.get("lastName"));
			values.put("phoneNumber", queryValues.get("phoneNumber"));
			values.put("emailAddress", queryValues.get("emailAddress"));
			values.put("homeAddress", queryValues.get("homeAddress"));
			
			database.insert("contacts", null, values);
			database.close();
		}
	
		public int updateContact(HashMap<String, String> queryValues){
			SQLiteDatabase database=this.getWritableDatabase();
			//store key value pairs column name and the datatype
			ContentValues values =new ContentValues();
			values.put("firstName", queryValues.get("firstName"));
			values.put("lastName", queryValues.get("lastName"));
			values.put("phoneNumber", queryValues.get("phoneNumber"));
			values.put("emailAddress", queryValues.get("emailAddress"));
			values.put("homeAddress", queryValues.get("homeAddress"));
			
			return database.update("contacts", values, "contactid"+"=?", new String[]{queryValues.get("contactid")});
		}
		public void deleteContact(String id){
			
			SQLiteDatabase database=this.getWritableDatabase();	
			
			String deletequery="DELETE FROM contacts WHERE contactid='"+id+"'";
			
			database.execSQL(deletequery);
		}
		public ArrayList<HashMap<String,String>> getAllContacts(){
			
			ArrayList<HashMap<String,String>> contactArrayList = new ArrayList<HashMap<String,String>>();
		
			String selectQuery= "SELECT * FROM Contacts order by lastName";

			SQLiteDatabase database=this.getWritableDatabase();	
			
			Cursor cursor = database.rawQuery(selectQuery, null);
			
			if(cursor.moveToFirst()){
				do{
					HashMap<String, String> contactMap = new HashMap<String, String>();
					
					contactMap.put("contactid", cursor.getString(0));
					contactMap.put("firstName", cursor.getString(1));
					contactMap.put("lastName", cursor.getString(2));
					contactMap.put("phoneNumber", cursor.getString(3));
					contactMap.put("emailAddress", cursor.getString(4));
					contactMap.put("homeAddress", cursor.getString(5));
					
					contactArrayList.add(contactMap);
						
				}while(cursor.moveToNext());
			}
			return contactArrayList;		
		}
	public HashMap<String,String> getContactInfo(String id){

		HashMap<String,String> contactMap= new HashMap<String,String>();

		SQLiteDatabase database=this.getReadableDatabase();

		String selectQuery= "SELECT * FROM Contacts where contactid='"+id+"'";
		
		Cursor cursor = database.rawQuery(selectQuery, null);
		
		if(cursor.moveToFirst()){
			do{

				
				contactMap.put("contactid", cursor.getString(0));
				contactMap.put("firstName", cursor.getString(1));
				contactMap.put("lastName", cursor.getString(2));
				contactMap.put("phoneNumber", cursor.getString(3));
				contactMap.put("emailAddress", cursor.getString(4));
				contactMap.put("homeAddress", cursor.getString(5));
					
			}while(cursor.moveToNext());
		}
		return contactMap;		

		
	}
	/*------------------AREA APLICACION---MAYAKA-------------*/
	public void insertCompra(HashMap<String, String> queryValues){
		
		SQLiteDatabase database=this.getWritableDatabase();
		//store key value pairs column name and the datatype
		ContentValues values =new ContentValues();
		values.put("Cod_Producto", queryValues.get("Cod_Producto"));
		values.put("Cod_Persona", queryValues.get("Cod_Persona"));
		values.put("Cantidad_Producto", queryValues.get("Cantidad_Producto"));
		values.put("Comprado",  queryValues.get("Comprado"));
		
		database.insert("carrito", null, values);
		database.close();
	}
public void insertPersona(String cod,String dpi,String name1,String name2,String apellido1,String apellido2,String nit,String estado,String genero,String tipo){
		
		SQLiteDatabase database=this.getWritableDatabase();
		//store key value pairs column name and the datatype
		ContentValues values =new ContentValues();
		values.put("Cod_Persona", cod);
		values.put("Dpi", dpi);
		values.put("Nombre1",name1 );
		values.put("Nombre2",  name2);
		values.put("Apellido1",  apellido1);
		values.put("Apellido2",  apellido2);
		values.put("Nit",  nit);
		values.put("Estado_Civil",  estado);
		values.put("Genero",  genero);
		values.put("Tipo_Persona",  tipo);
		database.insert("Persona", null, values);
		database.close();
	}
public void insertProducto(String cod,String tipo,String nombre,String talla, String color,String precio){
	
	SQLiteDatabase database=this.getWritableDatabase();
	//store key value pairs column name and the datatype
	ContentValues values =new ContentValues();
	values.put("Cod_Producto", cod);
	values.put("Tipo_Producto", tipo);
	values.put("Nombre_Producto", nombre);
	values.put("Talla_Producto",talla );
	values.put("Color_Producto",  color);
	values.put("Precio_Producto",  precio);
	database.insert("Producto", null, values);
	database.close();
}
public int updateCarrito(HashMap<String, String> queryValues){
		SQLiteDatabase database=this.getWritableDatabase();
		//store key value pairs column name and the datatype
		ContentValues values =new ContentValues();
		values.put("Cod_Producto", queryValues.get("Cod_Producto"));
		values.put("Cod_Persona", queryValues.get("Cod_Persona"));
		values.put("Cantidad_Producto", queryValues.get("Cantidad_Producto"));
		values.put("Comprado", "1");
		
		return database.update("carrito",values, "Cod_Producto"+"=? and Cod_Persona"+"=?", new String[]{queryValues.get("Cod_Producto"),queryValues.get("Cod_Persona")});
	}
public int Total(String user){
		int solution=-1;
		SQLiteDatabase database=this.getReadableDatabase();
		Cursor c;
		c = database.rawQuery("select sum((Producto.Precio_Producto * carrito.Cantidad_Producto))as total from Producto,carrito where Producto.Cod_Producto = carrito.Cod_Producto and carrito.Cod_Persona ="+user, null);
		
		if(c.moveToFirst())
		    solution = c.getInt(0);
		
		c.close();
		return solution;
	}
public int productosCount(){
	int solution=-1;
	SQLiteDatabase database=this.getReadableDatabase();
	Cursor c;
	c = database.rawQuery("select count(*) from Producto", null);
	
	if(c.moveToFirst())
	    solution = c.getInt(0);
	
	c.close();
	return solution;
}
	public ArrayList<HashMap<String,String>> getAllProductos(){
		
		ArrayList<HashMap<String,String>> productosArrayList = new ArrayList<HashMap<String,String>>();
	
		String selectQuery= "SELECT * FROM Producto order by Cod_Producto";

		SQLiteDatabase database=this.getWritableDatabase();	
		
		Cursor cursor = database.rawQuery(selectQuery, null);
		
		if(cursor.moveToFirst()){
			do{
				HashMap<String, String> contactMap = new HashMap<String, String>();
				
				contactMap.put("Cod_Producto", cursor.getString(0));
				contactMap.put("Tipo_Producto", cursor.getString(1));
				contactMap.put("Nombre_Producto", cursor.getString(2)+"\n"+"Talla :"+cursor.getString(3)+"\n"+"Precio :"+cursor.getString(5));
				contactMap.put("Talla_Producto", cursor.getString(3));
				contactMap.put("Color_Producto", cursor.getString(4));
				contactMap.put("Precio_Producto", cursor.getString(5));
				
				productosArrayList.add(contactMap);
					
			}while(cursor.moveToNext());
		}
		return productosArrayList;		
	}
	public String log(String user,String pass){
		String res="";
		String selectQuery= "SELECT Cod_Persona, Nombre1 FROM Persona where Nit="+user;

		SQLiteDatabase database=this.getWritableDatabase();	
		
		Cursor cursor = database.rawQuery(selectQuery, null);
		
		if(cursor.moveToFirst()){
			do{
				res="nitexists";
				if(cursor.getString(1).equals(pass))
				{
					
					res=cursor.getString(0);
					return res;
				}
				
				
					
			}while(cursor.moveToNext());
			res="badpass";
		}
		
		return res;
	}

	public HashMap<String,String> getProductoInfo(String id){

		HashMap<String,String> ProductoMap= new HashMap<String,String>();

		SQLiteDatabase database=this.getReadableDatabase();

		String selectQuery= "SELECT * FROM Producto where Cod_Producto="+id;
		
		Cursor cursor = database.rawQuery(selectQuery, null);
		
		if(cursor.moveToFirst()){
			do{
				ProductoMap.put("Cod_Producto", cursor.getString(0));
				ProductoMap.put("Tipo_Producto", cursor.getString(1));
				ProductoMap.put("Nombre_Producto", cursor.getString(2));
				ProductoMap.put("Talla_Producto", cursor.getString(3));
				ProductoMap.put("Color_Producto", cursor.getString(4));
				ProductoMap.put("Precio_Producto", cursor.getString(5));
					
			}while(cursor.moveToNext());
		}
		return ProductoMap;		

		
	}
public ArrayList<HashMap<String,String>> getAllCarritos(String user, String comprado){
		
		ArrayList<HashMap<String,String>> productosArrayList = new ArrayList<HashMap<String,String>>();
	
		String selectQuery= "SELECT Producto.Cod_Producto,Producto.Nombre_Producto, "
				+ "Producto.Precio_Producto,carrito.Cantidad_Producto "
				+ "FROM carrito,Producto WHERE carrito.Cod_Producto = Producto.Cod_Producto and "
				+ " Carrito.Cod_Persona = "+user+" and carrito.Comprado = "+comprado;

		SQLiteDatabase database=this.getWritableDatabase();	
		
		Cursor cursor = database.rawQuery(selectQuery, null);
		
		if(cursor.moveToFirst()){
			do{
				HashMap<String, String> contactMap = new HashMap<String, String>();
				
				contactMap.put("Cod_Persona", user);
				contactMap.put("Cod_Producto", cursor.getString(0));
				contactMap.put("Nombre_Producto", cursor.getString(1)+"\n Precio: "+cursor.getString(2));
				contactMap.put("Precio_Producto", cursor.getString(2));
				contactMap.put("Cantidad_Producto", cursor.getString(3));
				
				productosArrayList.add(contactMap);
					
			}while(cursor.moveToNext());
		}
		return productosArrayList;		
	}
public ArrayList<HashMap<String,String>> getAllCompras(String user){
	
	ArrayList<HashMap<String,String>> productosArrayList = new ArrayList<HashMap<String,String>>();

	String selectQuery= "SELECT Producto.Cod_Producto,Producto.Nombre_Producto, "
			+ "Producto.Precio_Producto,carrito.Cantidad_Producto,carrito.Comprado "
			+ "FROM carrito,Producto WHERE carrito.Cod_Producto = Producto.Cod_Producto and "
			+ " Carrito.Cod_Persona = "+user;

	SQLiteDatabase database=this.getWritableDatabase();	
	
	Cursor cursor = database.rawQuery(selectQuery, null);
	
	if(cursor.moveToFirst()){
		do{
			HashMap<String, String> contactMap = new HashMap<String, String>();
			
			String comp;
			if(cursor.getString(4).equals("0"))
			{
				comp="En Carrito";
			}
			else{
				comp=" Comprado ";
				
			}
			contactMap.put("Cod_Persona", user);
			contactMap.put("Cod_Producto", cursor.getString(0));
			contactMap.put("Nombre_Producto", cursor.getString(1)+" Precio: "+cursor.getString(2)+"\n"+"Estado: "+comp);
			contactMap.put("Precio_Producto", cursor.getString(2));
			contactMap.put("Cantidad_Producto", cursor.getString(3));
			contactMap.put("Comprado", cursor.getString(4));
			productosArrayList.add(contactMap);
				
		}while(cursor.moveToNext());
	}
	return productosArrayList;		
}
/*----------------AREA APLICACION---MAYAKA-------------*/
		
}
