package T3;


import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Vector;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Comparator;

import java.util.Map;

public class Funciones {

	boolean imprimo=true;
	public  void dumpJSONElement(JsonElement elemento,String salto) {
		salto+="  ";
		if (elemento.isJsonObject()) {

			JsonObject obj = elemento.getAsJsonObject();
			java.util.Set<java.util.Map.Entry<String,JsonElement>> entradas = obj.entrySet();
			java.util.Iterator<java.util.Map.Entry<String,JsonElement>> iter = entradas.iterator();
			while (iter.hasNext()) {
				java.util.Map.Entry<String,JsonElement> entrada = iter.next();
				/*Columnas a filtrar*/

				System.out.println("["+entrada.getKey()+"]");
				this.imprimo=true;

				dumpJSONElement(entrada.getValue(),salto);

			}

		} else if (elemento.isJsonArray()) {
			JsonArray array = elemento.getAsJsonArray();
			//System.out.println("Es array. Numero de elementos: " + array.size());
			System.out.println(salto+"[");
			for(int i=0;i<array.size();i++) {
				JsonElement entrada=array.get(i);
				dumpJSONElement(entrada,salto);

			}
			System.out.println(salto+"]");
		} else if (elemento.isJsonPrimitive()&&this.imprimo) {
			//System.out.println("Es primitiva");
			JsonPrimitive valor = elemento.getAsJsonPrimitive();
			if (valor.isBoolean()) {
				System.out.println(salto+valor.getAsBoolean());
			} else if (valor.isNumber()) {
				System.out.println(salto+valor.getAsNumber());
			} else if (valor.isString()) {
				System.out.println(salto+valor.getAsString());
			}
		} else if (elemento.isJsonNull()) {
			System.out.println("Es NULL");
		} 
	}

	public Tabla JSONIterativo(JsonElement elemento) {
		Tabla T=new Tabla();
		JsonObject obj = elemento.getAsJsonObject();
		JsonArray jarray  = obj.getAsJsonArray("relation");
		String relation[][] = null;
		int filas=jarray.size();
		int columnas=0;
		for(int i=0;i<jarray.size();i++) {
			JsonArray entrada=jarray.get(i).getAsJsonArray();
			columnas=entrada.size();
			if(i==0) relation=new String[jarray.size()][entrada.size()];
			for(int k=0;k<entrada.size();k++) {
				relation[i][k]=entrada.get(k).toString();
			}

		}
		T.setFilas(filas);
		T.setColumnas(columnas);
		T.setRelation(relation);
		T.setPageTitle(obj.get("pageTitle").getAsString());
		T.setTitle(obj.get("title").getAsString());
		T.setUrl(obj.get("url").getAsString()) ;
		T.setHasHeader(obj.get("hasHeader").getAsString()) ;
		T.setHeaderPosition(obj.get("headerPosition").getAsString()) ;
		T.setTableType(obj.get("tableType").getAsString()) ;
		T.setTableNum(obj.get("tableNum").getAsString()) ;
		T.setS3Link(obj.get("s3Link").getAsString()) ;
		T.setRecordEndOffset(obj.get("recordEndOffset").getAsString()) ;
		T.setRecordOffset(obj.get("recordOffset").getAsString()) ;
		T.setTableOrientation(obj.get("tableOrientation").getAsString()) ;
		if(obj.has("TableContextTimeStampBeforeTable")){
			T.setTableContextTimeStampBeforeTable(obj.get("TableContextTimeStampBeforeTable").getAsString()) ;
		}
		if(obj.has("lastModified")) {
			T.setLastModified(obj.get("lastModified").getAsString()) ;
		}
		if(obj.has("TableContextTimeStampAfterTable")) {
			T.setLastModified(obj.get("TableContextTimeStampAfterTable").getAsString()) ;
		}
		T.setTextBeforeTable(obj.get("textBeforeTable").getAsString());
		T.setTextAfterTable(obj.get("textAfterTable").getAsString()) ;
		T.setHasKeyColumn(obj.get("hasKeyColumn").getAsString()) ;
		T.setKeyColumnIndex(obj.get("keyColumnIndex").getAsString()) ;

		return T;

	}

	public String Match(Tabla T, Vector<Tabla> wiki, double[] values){
		Vector<String> headers = T.getVectorHeader();
		System.out.println(headers);
		Vector<String> wiki_headers = new Vector<>();
		int[] match_tables = {0,0,0,0,0,0,0,0,0,0,0};
		double total = headers.size();
		double matching_rate = 0;
		values[0] = -1; values[1] = Double.MAX_VALUE; values[2] = 0;
		String best_match = "";
		for(Tabla tabla : wiki){
			wiki_headers = tabla.getVectorHeader();
			double matching = 0;
			for(String header : headers) {
				for(String wiki_header : wiki_headers){
					//if(header.contains(wiki_header) || header.equals(wiki_header)){
					if(wiki_header.contains(header) || wiki_header.equals(header)){
						matching++;
						break;
					}
				}
			}
			matching_rate = (matching/total)*100;

			match_tables[(int)(matching_rate/10)]++;
			
			if(matching_rate > values[0]){
				values[0] = matching_rate;
				best_match = tabla.getTitle();
			} 
			if(matching_rate < values[1] && matching_rate > 0) values[1] = matching_rate;
			values[2] += matching_rate;
			//System.out.println(matching_rate);
		}
		values[2] = values[2]/wiki.size();
		if(values[1] > 100) values[1] = 0;
		System.out.println("Best match: "+best_match);
		System.out.println("Max match: "+values[0]+"%");
		System.out.println("Min match: "+values[1]+"%");
		System.out.println("Avg match: "+values[2]+"%");
		System.out.println("\nDistribution:");
		for(int i=0; i < match_tables.length; i++){
			if(i == 10) System.out.println("["+(i*10)+"%] = "+match_tables[i]);
			else System.out.println("["+(i*10)+"% - "+(i+1)*10+"%[ = "+match_tables[i]);
		}

		return best_match;
	}

	public int Diccionario(Tabla T,LinkedHashMap<String,Integer> Diccionario,int num,Vector<String> VectoCaracteristicas) {
		//System.out.println(header);
		Boolean header = Boolean.valueOf(T.getHasHeader());
		String letra;
		String chara="";
		if(header) {
			String relaciones[][]=T.getRelation();
			if(T.getTableOrientation().compareTo("VERTICAL")==0) {
				//System.out.println(T.getTableType());
				//System.out.println(T);
				for(int i=0;i<T.getColumnas();i++) {
					letra=relaciones[0][i];
					letra=letra.replaceAll("\"", "");
					letra=letra.replaceAll(":", "");
					letra=letra.replaceAll(";", "");
					letra=letra.replaceAll("\\?", "");
					letra=letra.replaceAll("\\(", "");
					letra=letra.replaceAll("\\)", "");
					//letra=letra.replaceAll(".", "");
					//letra=letra.replaceAll(",", "");
					//System.out.println(letra);
					if(letra.compareTo("")!=0) {
						if(!Diccionario.containsKey(letra)) {
							Diccionario.put(letra, num);
							chara=chara+" "+String.valueOf(num);
							num++;
						}else {
							chara=chara+" "+String.valueOf(num);
						}

					}

				}
				//System.out.println("**************\n\n");
				/*if(T.getHeaderPosition().compareTo("FIRST_COLUMN")==0) {
					for(int i=0;i<T.getColumnas();i++) {
						letra=relaciones[0][i];
						letra=letra.replaceAll("\"", "");
						//System.out.println(letra);
						if(!Diccionario.containsKey(letra) && letra.compareTo("")!=0) {
							Diccionario.put(letra, num);
							num++;
						}

					}
				}else if(T.getHeaderPosition().compareTo("FIRST_ROW")==0) {
					System.out.println(T.getTableType());
					System.out.println(T);
					for(int i=0;i<T.getFilas();i++) {
						letra=relaciones[i][0];
						letra=letra.replaceAll("\"", "");
						System.out.println(letra);
						if(!Diccionario.containsKey(letra)&& letra.compareTo("")!=0) {
							Diccionario.put(letra, num);
							num++;
						}
					}
					System.out.println("**************\n\n");
				}*/
			}else if(T.getTableOrientation().compareTo("HORIZONTAL")==0) {
				//System.out.println(T);
				for(int i=0;i<T.getFilas();i++) {
					letra=relaciones[i][0];
					letra=letra.replaceAll("\"", "");
					letra=letra.replaceAll(":", "");
					letra=letra.replaceAll(";", "");
					letra=letra.replaceAll("\\?", "");
					letra=letra.replaceAll("\\(", "");
					letra=letra.replaceAll("\\)", "");
					//letra=letra.replaceAll(".", "");
					//	letra=letra.replaceAll(",", "");
					//System.out.println(letra);
					if(letra.compareTo("")!=0) {
						if(!Diccionario.containsKey(letra)) {
							Diccionario.put(letra, num);
							chara=chara+" "+String.valueOf(num);
							num++;
						}else {
							chara=chara+" "+String.valueOf(num);
						}

					}
				}
				//System.out.println("**************\n\n");
				/*if(T.getHeaderPosition().compareTo("FIRST_COLUMN")==0) {
					System.out.println(T);
					for(int i=0;i<T.getColumnas();i++) {
						letra=relaciones[0][i];
						letra=letra.replaceAll("\"", "");
						System.out.println(letra);
						if(!Diccionario.containsKey(letra)&& letra.compareTo("")!=0) {
							Diccionario.put(letra, num);
							num++;
						}

					}
					System.out.println("**************\n\n");
				}else if(T.getHeaderPosition().compareTo("FIRST_ROW")==0) {
					System.out.println(T);
					for(int i=0;i<T.getFilas();i++) {
						letra=relaciones[i][0];
						letra=letra.replaceAll("\"", "");
						System.out.println(letra);
						if(!Diccionario.containsKey(letra)&& letra.compareTo("")!=0) {
							Diccionario.put(letra, num);
							num++;
						}
					}
					System.out.println("**************\n\n");
				}*/
			}/*else if(T.getTableOrientation().compareTo("MIXED")==0) {
				//System.out.println(T);
				if(T.getHeaderPosition().compareTo("FIRST_COLUMN")==0) {
					for(int i=0;i<T.getColumnas();i++) {
						letra=relaciones[0][i];
						letra=letra.replaceAll("\"", "");
						//System.out.println(letra);
						if(!Diccionario.containsKey(letra)&& letra.compareTo("")!=0) {
							Diccionario.put(letra, num);
							num++;
						}

					}
				}else if(T.getHeaderPosition().compareTo("FIRST_ROW")==0){
					for(int i=0;i<T.getFilas();i++) {
						letra=relaciones[i][0];
						letra=letra.replaceAll("\"", "");
						//System.out.println(letra);
						if(!Diccionario.containsKey(letra)&& letra.compareTo("")!=0) {
							Diccionario.put(letra, num);
							num++;
						}
					}
				}
			}*/
		}
		VectoCaracteristicas.addElement(chara);
		return num;
	}

	public void printMap(Map<String, Integer> map){
		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			System.out.println("Title : " + entry.getKey() + ", Recurrencias : "
				+ entry.getValue());
		}
	}

	static <K,V extends Comparable<? super V>>
	SortedSet<Map.Entry<K,V>> entriesSortedByValues(Map<K,V> map) {
	    SortedSet<Map.Entry<K,V>> sortedEntries = new TreeSet<Map.Entry<K,V>>(
	        new Comparator<Map.Entry<K,V>>() {
	            @Override public int compare(Map.Entry<K,V> e1, Map.Entry<K,V> e2) {
	                int res = e1.getValue().compareTo(e2.getValue());
					//return res != 0 ? res : -1;
					if(res > 0) return -1;
					if(res < 0) return 1;
					return 0;
	            }
	        }
	    );
	    sortedEntries.addAll(map.entrySet());
	    return sortedEntries;
	}
}
