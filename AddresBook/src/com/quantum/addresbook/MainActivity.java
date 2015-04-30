package com.quantum.addresbook;



import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
//import android.support.v7.app.ActionBarActivity;
//import android.support.v7.app.ActionBar;
//import android.support.v4.app.Fragment;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.os.Build;

public class MainActivity extends Activity {

	Intent  intent;
	TextView contactId;
	EditText user;
	EditText pass;
	TextView tvIsConnected;
	int flagAction=-1;
	
	String Resultado;
	
	DBTools dbtools = new DBTools(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_window);
        
        user= (EditText) findViewById(R.id.username);
        pass= (EditText) findViewById(R.id.password);
        //sqlThread.start();

      /*----------------------------------REST SERVICES RECIVER--------------*/
        //get reference to the views
        tvIsConnected = (TextView) findViewById(R.id.tvIsConnected);
 
        // check if you are connected or not
        if(isConnected()){
            tvIsConnected.setBackgroundColor(0xFF00CC00);
            tvIsConnected.setText("You are conncted");
        }
        else{
            tvIsConnected.setText("You are NOT conncted");
        }
      //  dbtools.deletedata("Producto");
       if(dbtools.productosCount()==0)
       {
    	   flagAction=0;
        	new HttpAsyncTask().execute("http://10.0.2.2:8000/productos");
       }
     //  new HttpAsyncTask().execute("http://10.0.2.2:8000/factura/");
       
		
        // call AsynTask to perform network operation on separate thread
        /*http://192.168.0.11:8000/productos/53873/*/
     
    
 
        /*----------------------------------REST SERVICES RECIVER--------------*/
        
       /* ArrayList<HashMap<String,String>> contactList =dbtools.getAllContacts();
        if(contactList.size()!=0){
        	ListView listView = getListView();
        	listView.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> arg0, View view,
						int arg2, long arg3) {
						contactId =(TextView) view.findViewById(R.id.contactId);
						
						String contactIdValue = contactId.getText().toString();
						
						Intent theIntent = new Intent(getApplication(), EditContact.class);
						theIntent.putExtra("contactid", contactIdValue);
						startActivity(theIntent);
						
				}});
        	ListAdapter adapter = new SimpleAdapter(MainActivity.this,contactList,R.layout.contacts_entry,
        						  new String[]{"contactid","firstName","lastName"}, new int[]{R.id.contactId,R.id.lastName,R.id.firstName});
        	setListAdapter(adapter);
        }*/
        
        
    }

/*---------------------------------HTTPREQUEST--- METHODSS--------------*/
    
    public static String GET(String url){
        InputStream inputStream = null;
        String result = "";
        try {
 
            // create HttpClient
        	/*DefaultHttpClient cli= new DefaultHttpClient(new BasicHttpParams());
        	HttpPost httpost = new HttpPost(url);
        	httpost.setHeader("content-type","application/json");
          */
        	HttpClient httpclient = new DefaultHttpClient();
 
            // make GET request to the given URL
            
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));
            
        	
       // 	HttpResponse httpResponse = cli.execute(httpost);
            
            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();
 
            // convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";
 
        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
 
        return result;
    }
 
    private static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;
 
        inputStream.close();
        return result;
 
    }
 
    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) 
                return true;
            else
                return false;   
    }
    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
 
            //return GET(urls[0]);
        	 Map<String, String> contactMap = new HashMap<String, String>();
             contactMap.put("cod_factura", "5");
             contactMap.put("fecha_factura", "12/12/12");
             contactMap.put("cod_cliente", "4");
             
            
      		try {
			makeRequest(urls[0],contactMap);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
      		return "asdasd";
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
           
            if(result=="")
            {
            	Toast.makeText(getBaseContext(), "No Existen Datos!", Toast.LENGTH_LONG).show();
            }
           else{
            Toast.makeText(getBaseContext(), "Received!", Toast.LENGTH_LONG).show();
           
            	if(flagAction==0)
            	{
            		 Resultado="{\"Android\":"+result+"}";
            		creandoProductos();

            	}
            	if(flagAction==1){
            		 Resultado="{\"Android\":["+result+"]}";
            		creandonuevoUsuario();
            	}
           }
           //etResponse.setText(result);
       }
    }
    public void creandoProductos(){
    	  JSONObject jsonResponse;
    	try{
       /****** Creates a new JSONObject with name/value mappings from the JSON string. ********/
       jsonResponse = new JSONObject(Resultado);
        
       /***** Returns the value mapped by name if it exists and is a JSONArray. ***/
       /*******  Returns null otherwise.  *******/
       JSONArray jsonMainNode = jsonResponse.optJSONArray("Android");
        
       /*********** Process each JSON Node ************/

       int lengthJsonArr = jsonMainNode.length();  

       for(int i=0; i < lengthJsonArr; i++) 
       {
           /****** Get Object for each JSON node.***********/
           JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
            
           /******* Fetch node values **********/
           String cod=jsonChildNode.optString("cod_producto").toString();
           String nombre=jsonChildNode.optString("nombre_producto").toString();
           String tipo=jsonChildNode.optString("tipo_producto").toString();
           String color=jsonChildNode.optString("color_producto").toString();
           String talla=jsonChildNode.optString("talla_producto").toString();
           String precio=jsonChildNode.optString("precio_producto").toString();
           
           dbtools.insertProducto(cod, tipo, nombre, talla, color, precio);
           //Log.i("JSON parse", song_name);
      }
        
       /************ Show Output on screen/activity **********/

        
   } catch (JSONException e) {

       e.printStackTrace();
   }
    	
    }
    public void creandonuevoUsuario(){
    
        JSONObject jsonResponse;
              
        try {
              
             /****** Creates a new JSONObject with name/value mappings from the JSON string. ********/
             jsonResponse = new JSONObject(Resultado);
              
             /***** Returns the value mapped by name if it exists and is a JSONArray. ***/
             /*******  Returns null otherwise.  *******/
             JSONArray jsonMainNode = jsonResponse.optJSONArray("Android");
              
             /*********** Process each JSON Node ************/

             int lengthJsonArr = jsonMainNode.length();  

             for(int i=0; i < lengthJsonArr; i++) 
             {
                 /****** Get Object for each JSON node.***********/
                 JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                  
                 /******* Fetch node values **********/
                 String cod=jsonChildNode.optString("cod_persona").toString();
                 String dpi=jsonChildNode.optString("dpi").toString();
                 String name1=jsonChildNode.optString("nombre1").toString();
                 String name2=jsonChildNode.optString("nombre2").toString();
                 String apellido1=jsonChildNode.optString("apellido1").toString();
                 String apellido2=jsonChildNode.optString("apellido2").toString();
                 String nit=jsonChildNode.optString("nit").toString();
                 String estado=jsonChildNode.optString("estado_civil").toString();
                 String genero=jsonChildNode.optString("genero").toString();
                 String tipo=jsonChildNode.optString("tipo_persona").toString();
                 
                 dbtools.insertPersona(cod, dpi, name1, name2, apellido1, apellido2, nit, estado, genero, tipo);
                 
                 //Log.i("JSON parse", song_name);
            }
              
             /************ Show Output on screen/activity **********/

              
         } catch (JSONException e) {
  
             e.printStackTrace();
         }

     	
    	
    }
    /*---------------------------------HTTPREQUEST--- METHODSS--------------*/
	private static JSONObject getJsonObjectFromMap(Map<String,String> params) throws JSONException {

	    //all the passed parameters from the post request
	    //iterator used to loop through all the parameters
	    //passed in the post request
	    Iterator<String> iter = params.keySet().iterator();

	    //Stores JSON
	    JSONObject holder = new JSONObject();

	    //using the earlier example your first entry would get email
	    //and the inner while would get the value which would be 'foo@bar.com' 
	    //{ fan: { email : 'foo@bar.com' } }

	    //While there is another entry
	    while (iter.hasNext()) 
	    {
	    	String key = (String) iter.next();
	    	String data = params.get(key);
	        //puts email and 'foo@bar.com'  together in map
	        holder.put(key, data);
	        
	    }
	    return holder;
	}
    public static String makeRequest(String path, Map<String,String> params) throws Exception 
	{
	    //instantiates httpclient to make request
	    DefaultHttpClient httpclient = new DefaultHttpClient();

	    //url with the post data
	    HttpPost httpost = new HttpPost(path);

	    //convert parameters into JSON object
	    JSONObject holder = getJsonObjectFromMap(params);

	    //passes the results to a string builder/entity
	    StringEntity se = new StringEntity(holder.toString());

	    //sets the post request as the resulting string
	    httpost.setEntity(se);
	    //sets a request header so the page receving the request
	    //will know what to do with it
	    httpost.setHeader("Accept", "application/json");
	    httpost.setHeader("Content-type", "application/json");

	    //Handles what is returned from the page 
	    ResponseHandler<String> responseHandler = new BasicResponseHandler();
	    return httpclient.execute(httpost, responseHandler);
	}
	
    
    
    public void loginAction(View view){
    	String personaId="";
    		 personaId=dbtools.log(user.getText().toString(),pass.getText().toString());
    		 if(!personaId.equals("badpass") && !personaId.equals("nitexists") && !personaId.equals("")){    	
    			Intent theIntent = new Intent(getApplicationContext(), Productos.class);
     	    	theIntent.putExtra("Cod_Persona", personaId);
     	    	startActivity( theIntent );
    	    	}
    		 if(personaId.equals("")){
    			 flagAction=1;
    			 real();
    			 user.setText("");
    			 pass.setText("");
    		 }
    		 else{
    			 user.setText("");
    			 pass.setText("");
    			 
    		 }
    		     }
    public void real(){
    		new HttpAsyncTask().execute("http://10.0.2.2:8000/persona/"+user.getText());
		
    	
    }

    Thread sqlThread = new Thread() {
		 public void run() {
		 try {
		 Class.forName("org.postgresql.Driver");
		 // "jdbc:postgresql://IP:PUERTO/DB", "USER", "PASSWORD");
		 // Si estás utilizando el emulador de android y tenes el PostgreSQL en tu misma PC no utilizar 127.0.0.1 o localhost como IP, utilizar 10.0.2.2
		 
		 Connection conn = DriverManager.getConnection("jdbc:postgresql://192.168.0.11:5433/MAYAKA", "postgres", "nicolle");
		 //En el stsql se puede agregar cualquier consulta SQL deseada.
		 String stsql = "Select version()";
		 Statement st = conn.createStatement();
		 ResultSet rs = st.executeQuery(stsql);
		 rs.next();
		 System.out.println( rs.getString(1)+ "ASDALKSDJLKASJDLKAJDLKJASLKDJALKSDJLAKSJDLA" );
		 try {
			sleep(5);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// getPersonas(conn);
		 //getProductos(conn);
		 conn.close();
		 } catch (SQLException se) {
		 System.out.println("oops! Tacabronchover. Error: " + se.toString());
		 } catch (ClassNotFoundException e) {
		 System.out.println("oops! No se encuentra la clase. Error: " + e.getMessage());
		 }
		 }
		 
		 };
		 public void getPersonas(Connection conn){
			
			 try 
			    {
			      Statement st = conn.createStatement();
			      ResultSet rs = st.executeQuery("SELECT * from persona");
			      
			      while ( rs.next() )
			      {
			    	  dbtools.insertProducto(rs.getString("Cod_Producto"), rs.getString("Cod_Tipo_Producto"), rs.getString("Nombre_Producto"), rs.getString("Talla_Producto"), rs.getString("Color_Producto"), rs.getString("Precio_Producto"));
			      }
			      rs.close();
			      st.close();
			    }
			    catch (SQLException se) {
			      System.err.println("Threw a SQLException CUANDO ESTAVAS ESCRIBIENDO PRODUCTOS.");
			      System.err.println(se.getMessage());
			    }
			 
		 }
		 public void getProductos(Connection conn){
				
			 try 
			    {
			      Statement st = conn.createStatement();
			      ResultSet rs = st.executeQuery("SELECT * from persona");
			      while ( rs.next() )
			      {
			    	  dbtools.insertPersona(rs.getString("Cod_Persona"),rs.getString("Dpi"),rs.getString("Nombre1"), rs.getString("Nombre2"),rs.getString("Apellido1"),rs.getString("Apellidp2"), rs.getString("Nit"), rs.getString("Estado_civil"), rs.getString("Genero"), rs.getString("Tipo_Persona"));    
			        
			      }
			      rs.close();
			      st.close();
			    }
			    catch (SQLException se) {
			      System.err.println("Threw a SQLException CUANDO ESTAVAS ESCRIBIENDO PERSONAS.");
			      System.err.println(se.getMessage());
			    }
			 
		 }
		
		 
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }*/

}
