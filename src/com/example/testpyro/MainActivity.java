package com.example.testpyro;

import java.io.IOException;
import java.net.UnknownHostException;

import net.razorvine.pyro.PyroProxy;
import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// No es la forma correcta de pedir recursos de la red pero nos funciona como ejemplo
		// Deberíamos usar threads o Async Tasks
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		
		//Creamos un boton que hará la conexión en su listener "click"
		Button button = (Button) findViewById(R.id.button1);
		
		//Label de debug
		final TextView text = (TextView) findViewById(R.id.textView1);
		
		button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                   
            	text.setText("hola bro");
            	
            	//Creamos el objeto para la conexión remota
            	//Debemos tener ya agregada nuestra librearía pyrolite
            	PyroProxy remoteobject;
				try {
					//Nos conectamos a la IP que necesitemos 
					//Todo esta basado en las clases del ejemplo warehouse de pyro
					// https://pythonhosted.org/Pyro4/tutorials.html
					remoteobject = new PyroProxy("192.168.1.92", 39001, "warehouse");
					
					//Mandamos a llamar a nuestro método
					Object result = remoteobject.call("list_contents");
					
					//Al resultado de la llamada le hacemos casting a String y lo asignamos a una cadena
	                String message = (String)result;  
	                
	                //Escribimos nuestra cadena que recibimos y listo
	                text.setText(message);
	                
	                //Cerramos la conexión 
	                remoteobject.close();
	                
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					text.setText("no se conoce el host");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					text.setText("entrada salida");
				}
            }
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
