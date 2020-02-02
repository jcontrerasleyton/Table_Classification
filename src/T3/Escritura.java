package T3;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Vector;

public class Escritura {
	public void CrearSalida() {
		File folder = new File("Resultados");
		if (!folder.exists()) {
			folder.mkdir();
		}
	}

	public void EscribirDiccionario(HashMap<String, Integer> Diccionario,String ruta) {
		FileWriter fichero = null;
		PrintWriter pw = null;
		try
		{
			fichero = new FileWriter(ruta);
			pw = new PrintWriter(fichero);

			for (Entry<String, Integer> entry : Diccionario.entrySet()) {
				pw.println(entry.getKey()+";"+entry.getValue());
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// Nuevamente aprovechamos el finally para 
				// asegurarnos que se cierra el fichero.
				if (null != fichero)
					fichero.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

	}

	public void EscribirVector(Vector<String> Caracteristicas,String ruta) {
		FileWriter fichero = null;
		PrintWriter pw = null;
				try
		{
			fichero = new FileWriter(ruta);
			pw = new PrintWriter(fichero);

			for (String chara :Caracteristicas) {
				HashMap<Integer,Integer> Numbers=new HashMap<Integer,Integer>();

				String[] parts = chara.split(" ");
				for(int i=0;i<parts.length;i++) {
					if(!parts[i].equals("")) {
						int num=Integer.valueOf(parts[i]);
						if(Numbers.containsKey(num)) {
							int value=Numbers.get(num);
							value++;
							Numbers.put(num, value);
						}else {
							Numbers.put(num, 1);
						}
					}

				}
				/*if(chara.length()>0) {
    				pw.println(chara);
    			}*/
				for (Entry<Integer, Integer> entry : Numbers.entrySet()) {
					pw.print(entry.getKey()+":"+entry.getValue()+" ");
				}
				pw.println("");
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// Nuevamente aprovechamos el finally para 
				// asegurarnos que se cierra el fichero.
				if (null != fichero)
					fichero.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

	}
}
