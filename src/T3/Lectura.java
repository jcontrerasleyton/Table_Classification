package T3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Vector;

import java.io.StringReader;

import com.google.gson.JsonParser;
import com.google.gson.JsonElement;
import com.google.gson.stream.JsonReader;

import org.apache.tika.language.LanguageIdentifier;

public class Lectura {
	@SuppressWarnings("finally")
	public int LeerDiccionario(String dirDic,HashMap<String,Integer> Diccionario) {
		int num=0;
		File archivo = null;
		FileReader fr = null;
		BufferedReader br = null;

		try {
			// Apertura del fichero y creacion de BufferedReader para poder
			// hacer una lectura comoda (disponer del metodo readLine()).
			archivo = new File (dirDic);
			fr = new FileReader (archivo);
			br = new BufferedReader(fr);

			// Lectura del fichero
			String linea;
			while((linea=br.readLine())!=null) {
				String[] parts = linea.split(";");
				num=Integer.valueOf(parts[1]);
				Diccionario.put(parts[0],num);
				
			}
				
		}
		catch(Exception e){
			e.printStackTrace();
		}finally{
			// En el finally cerramos el fichero, para asegurarnos
			// que se cierra tanto si todo va bien como si salta 
			// una excepcion.
			try{                    
				if( null != fr ){   
					fr.close();     
				}                  
			}catch (Exception e2){ 
				e2.printStackTrace();
			}
			return num;
		}

	}
	
	public void LeerVector(String dirDic,Vector<String> Caracteristicas) {
		File archivo = null;
		FileReader fr = null;
		BufferedReader br = null;

		try {
			// Apertura del fichero y creacion de BufferedReader para poder
			// hacer una lectura comoda (disponer del metodo readLine()).
			archivo = new File (dirDic);
			fr = new FileReader (archivo);
			br = new BufferedReader(fr);

			// Lectura del fichero
			String linea;
			while((linea=br.readLine())!=null) {
				Caracteristicas.addElement(linea);
			}
				
		}
		catch(Exception e){
			e.printStackTrace();
		}finally{
			// En el finally cerramos el fichero, para asegurarnos
			// que se cierra tanto si todo va bien como si salta 
			// una excepcion.
			try{                    
				if( null != fr ){   
					fr.close();     
				}                  
			}catch (Exception e2){ 
				e2.printStackTrace();
			}
		}
	}

	public void LeerWiki(String path,Vector<Tabla> wiki) {
		JsonParser parser = new JsonParser();
		Funciones Funcion=new Funciones();
		HashSet<String> set=new HashSet<>();
		File archivo = null;
		FileReader fr = null;
		BufferedReader br = null;

		try {
			// Apertura del fichero y creacion de BufferedReader para poder
			// hacer una lectura comoda (disponer del metodo readLine()).
			archivo = new File (path);
			fr = new FileReader (archivo);
			br = new BufferedReader(fr);
			int cont = 0;

			// Lectura del fichero
			String cadena;

			while((cadena = br.readLine())!=null) {
				try{
					JsonReader jsonReader = new JsonReader(new StringReader(cadena));
					jsonReader.setLenient(true);
					JsonElement datos = parser.parse(jsonReader);
					//Funcion.dumpJSONElement(datos, " ");
					Tabla T=Funcion.JSONIterativo(datos);
					jsonReader.close();
					//LanguageIdentifier identifier = new LanguageIdentifier(T.getTextBeforeTable());
					//String languaje=identifier.getLanguage();
					//System.out.println("******************\n");
					//if(languaje.equals("en")) {
						//num=Funcion.Diccionario(T,Diccionario,num,VectoCaracteristicas);
						T.VectorHeader();
						//System.out.println(T);
						//System.out.println("******************\n");
					//}
					cont++;
					System.out.println(cont);
					wiki.add(T);

					//System.out.println("******************\n");
				}catch(Exception e){

				}	
			}
				
		}
		catch(Exception e){
			e.printStackTrace();
		}finally{
			// En el finally cerramos el fichero, para asegurarnos
			// que se cierra tanto si todo va bien como si salta 
			// una excepcion.
			try{                    
				if( null != fr ){   
					fr.close();     
				}                  
			}catch (Exception e2){ 
				e2.printStackTrace();
			}
		}
	}
}
