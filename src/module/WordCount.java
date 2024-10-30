package module;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @Master SID
 */
public class WordCount {
	/**
	* @param fichier, une liste de  liste de String, represente le fichier charge en memoire dans une List
	* @return la liste des termes distincts
	*/
	public static List<String> distinctWord(List<List<String>> fichier){
		List<String> result = new ArrayList<String>();
		for(List<String> ligne:fichier)
			for(String w:ligne)
				if(!result.contains(w))
					result.add(w);
		return result;	
	}
	
	public static int wordcount(List<List<String>> fichier, String word){
		int nombre=0;
		for(List<String> ligne:fichier)
			for(String w:ligne)
				if(w.equalsIgnoreCase(word))
					nombre++;
		return nombre;
	}
    
	public static List<List<String>> readFile(File file) {
        List<List<String>> result = new ArrayList<List<String>>();
		String line;
        try {
			BufferedReader in= new BufferedReader(new FileReader(file));
            while((line = in.readLine())!= null) {
		System.out.println(line+" ---");	
                result.add(Arrays.asList(line.split(" ")));
	    }
			in.close();
		} catch (IOException e) {
            System.out.println(e);
        }
		return result;
	}
	public static void main(String t[]){
		List<List<String>> result = readFile(new File("File0.txt"));
		List<String> terms=distinctWord(result);
		try{
			BufferedWriter bw;
			if(!(new File("WordCount-Sortie.txt")).exists()){
		        bw=new BufferedWriter(new FileWriter("WordCount-Sortie.txt"));
				for(String terme:terms){
					bw.write(terme+" "+wordcount(result,terme));
					bw.write("\n");
				}
				bw.close();
			}
		}catch(IOException exc){}
	}
}
