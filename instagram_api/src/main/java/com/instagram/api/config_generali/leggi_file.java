package com.instagram.api.config_generali;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public abstract class leggi_file {

	public String leggi(String path){
		String file = "";
		try {
			Scanner scanner = new Scanner(new File(path));
			while (scanner.hasNext()) {
				file += scanner.next();
			}
		} catch (FileNotFoundException e) {
			System.out.println("Il file nella posizione " + path + " non esiste");
		}
		return file;
	}
	
}
