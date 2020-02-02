package T3;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.StringReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Vector;
import java.util.zip.GZIPInputStream;

import java.util.Comparator;
import java.util.PriorityQueue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

//import javax.swing.text.html.AccessibleHTML.TableElementInfo.TableAccessibleContext;

import com.google.gson.JsonParser;
import com.google.gson.JsonElement;
import com.google.gson.stream.JsonReader;

import org.apache.tika.language.LanguageIdentifier;

public class Main {

	@SuppressWarnings({ "resource", "deprecation" })
	public static void main(String args[]) throws IOException {
		JsonParser parser = new JsonParser();
		Lectura lectura=new Lectura();
		LinkedHashMap<String,Integer> Diccionario=new LinkedHashMap<>();
		int num=0;
		//descomentar LeerDiccionario  para generar el vector de archivos diferentes
		//int num = lectura.LeerDiccionario(args[1], Diccionario);
		Vector<String> VectoCaracteristicas=new Vector<>();
		/*decomentar leervector para generar el vector de archivos diferentes, comentar la linea en caso de correr el mismo .gz*/
		//lectura.LeerVector(args[2], VectoCaracteristicas);
		num++;
		System.out.println(args[0]);
		String cadena;

		Vector<Tabla> wiki = new Vector();
		lectura.LeerWiki(args[1], wiki);

		/*for(Tabla tabla : wiki){
			Vector<String> wiki_headers = tabla.getVectorHeader();
			for(String wiki_header : wiki_headers){
				System.out.print(wiki_header+",");		
			}System.out.println("\n");	
		}*/

		System.out.println("size: "+wiki.size());

		BufferedReader b = new BufferedReader(new InputStreamReader(new GZIPInputStream(new FileInputStream(args[0])), "UTF-8"));
		Escritura Escribir=new Escritura();
		Escribir.CrearSalida();
		Funciones Funcion=new Funciones();
		String Salida=args[0].substring(6,(args[0].length()-8));
		Salida="Resultados/"+Salida+".txt";
		PrintStream  ps4=System.out; // save
		FileOutputStream fout=new  FileOutputStream(Salida);
		PrintStream ps =new PrintStream(fout);
		System.setOut(ps);
		HashSet<String> set=new HashSet<>();
		int table = 1;
		double[] values = {0.0,0.0,0.0};
		int[] max = {0,0,0,0,0,0,0,0,0,0,0};
		int[] min = {0,0,0,0,0,0,0,0,0,0,0};
		int[] avg = {0,0,0,0,0,0,0,0,0,0,0};
		int i_max = 0;
		int i_min = 0;
		int i_avg = 0;
		
		PriorityQueue<Match> queue=new PriorityQueue<Match>(10, new Comparator<Match>() {
			@Override
			public int compare(Match a, Match b) {
				if (a.getMatch() > b.getMatch()) return -1;
				if (a.getMatch() < b.getMatch()) return 1;
				return 0;
			}
		});

		List<String> list = new ArrayList<String>();
		
		while((cadena = b.readLine())!=null) {
			JsonReader jsonReader = new JsonReader(new StringReader(cadena));
			jsonReader.setLenient(true);
			JsonElement datos = parser.parse(jsonReader);
			//Funcion.dumpJSONElement(datos, " ");
			Tabla T=Funcion.JSONIterativo(datos);
			jsonReader.close();
			LanguageIdentifier identifier = new LanguageIdentifier(T.getTextBeforeTable());
			String languaje=identifier.getLanguage();
			if(languaje.equals("en")) {
				//num=Funcion.Diccionario(T,Diccionario,num,VectoCaracteristicas);
				T.VectorHeader();
				if(T.getVectorHeader().size() > 0){
					
					System.out.println("Table: "+table);
					String best_match = Funcion.Match(T, wiki, values);

					i_max = (int)(values[0]/10);
					i_min = (int)(values[1]/10);
					i_avg = (int)(values[2]/10);

					max[i_max]++;
					min[i_min]++;
					avg[i_avg]++;

					//System.out.println(T);
					System.out.println("\n******************\n");
					table++;

					queue.add(new Match(values[0],best_match,table));
					if(values[0]>0) list.add(best_match);
				}
			}
		}
		//Map<String, Integer> map = new HashMap<String, Integer>();
		Map<String, Integer> treeMap = new TreeMap<String, Integer>();

		for (String temp : list) {
			Integer count = treeMap.get(temp);
			treeMap.put(temp, (count == null) ? 1 : count + 1);
		}
					
		//Map<String, Integer> treeMap = new TreeMap<String, Integer>(map);

		System.out.println("###################################\n");
		System.out.println("Final\n\n");
		System.out.println("Total Tables = "+table);
		System.out.println("Wiki Tables = "+wiki.size());

		System.out.println("\nMAX:");
		for(int i=0; i < max.length; i++){
			if(i == 10) System.out.println("["+(i*10)+"%] = "+max[i]);
			else System.out.println("["+(i*10)+"% - "+(i+1)*10+"%[ = "+max[i]);
		}
		System.out.println("\nMIN:");
		for(int i=0; i < max.length; i++){
			if(i == 10) System.out.println("["+(i*10)+"%] = "+min[i]);
			else System.out.println("["+(i*10)+"% - "+(i+1)*10+"%[ = "+min[i]);
		}
		System.out.println("\nAVG:");
		for(int i=0; i < max.length; i++){
			if(i == 10) System.out.println("["+(i*10)+"%] = "+avg[i]);
			else System.out.println("["+(i*10)+"% - "+(i+1)*10+"%[ = "+avg[i]);
		}
		System.out.println("\nCommon Tables:");
		System.out.println(Funciones.entriesSortedByValues(treeMap));
		//Funcion.printMap(treeMap);

		System.out.println("\nTop Matching:");
		int size1 = queue.size();
		while (queue.size() != 0 /*&& size1-queue.size() < 20*/)
        {
			Match aux = queue.remove();
            System.out.println(aux.getMatch()+"%, title: "+aux.getTitle()+", table: "+aux.getTable());
        }
		b.close();
		System.setOut(ps4);
		//Escribir.EscribirDiccionario(Diccionario, args[1]);
		//Escribir.EscribirVector(VectoCaracteristicas, args[2]);
		System.out.println("Salida generada en "+Salida);
	}

}
