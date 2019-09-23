package autotrading.tools;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Archivo {

	public static File fileCot;
	public static FileWriter fwCot;
	public static BufferedWriter bwCot;
	
	public static File fileInv;
	public static FileWriter fwInv;
	public static BufferedWriter bwInv;
	
	public static void grabarCotizaciones(String content){

		try {

			if (Archivo.fileCot == null )
				Archivo.fileCot = new File("./auxfiles/cotizaciones.txt");
			if( Archivo.fwCot == null)
				fwCot = new FileWriter(fileCot.getAbsoluteFile());
			if( Archivo.bwCot == null)
				bwCot = new BufferedWriter(fwCot);		
			if (!fileCot.exists()) {
				fileCot.createNewFile();
			}

			bwCot.write(content);
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public static void grabarInversiones(String content){

		try {

			if (Archivo.fileInv == null )
				Archivo.fileInv = new File("./auxfiles/inversiones.txt");
			if( Archivo.fwInv == null)
				fwInv = new FileWriter(fileInv.getAbsoluteFile());
			if( Archivo.bwInv == null)
				bwInv = new BufferedWriter(fwInv);		
			if (!fileInv.exists()) {
				fileInv.createNewFile();
			}

			bwInv.write(content);
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public static void cerrar() throws IOException{
		Archivo.bwCot.close();
		Archivo.bwInv.close();
	}
	
}
