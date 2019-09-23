package autotrading;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import autotrading.tools.Archivo;
public class Empresa {

	private String simbolo;
	private Cotizaciones cotizaciones = new Cotizaciones();
	private Inversiones inversiones = new Inversiones();
	
	public String getSimbolo() {
		return simbolo;
	}

	public Inversiones getInversiones() {
		return inversiones;
	}
	
	public Cotizaciones getCotizaciones(){
		return this.cotizaciones;
	}
	
	public Empresa(String simbolo){
		this.simbolo = simbolo;
	}
	
	public Empresa(Path filePath){
		Charset cs = Charset.defaultCharset();
		ArrayList<String> lines = null;
		// Read file into arraylist lines
		try (BufferedReader reader = Files.newBufferedReader(filePath, cs)){
			lines = new ArrayList<>();
			String line = null;
			while( (line = reader.readLine()) != null )
				if (!line.contains("Date"))
					lines.add(line);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		// Set up the object
		this.simbolo = filePath.getFileName().toString();		
		//Loop file and add cotizaciones
		Collections.reverse(lines);
		Iterator<String> i = lines.iterator();
		while(i.hasNext())
			cotizaciones.add(new Cotizacion(this, i.next()));
	}

	public int getCantAcciones() {
		return getInversiones().getCantAcciones();
	}	
	
	public void comprar(int cantidad, Cotizacion actual){	
		if (inversiones.getInversionesAbiertas().size() == 1)
			return;
		
		Inversion nueva = new Inversion(cantidad, actual);
		CuentaBroker.getSingleton().sacarDinero( actual.getClose() * cantidad  );
		inversiones.add(nueva);
	}
	
	public void vender(Cotizacion actual) throws Exception{
		if( inversiones.getInversionesAbiertas().size() > 1 )
			throw new Exception("al vender hay mas de una inversion abierta");
		else if ( inversiones.getInversionesAbiertas().size() == 0 )
			throw new Exception("al vender no se encontro una inversion abierta");
	
		Inversion ultima = inversiones.getInversionesAbiertas().iterator().next();
		ultima.setVenta(actual);
		Archivo.grabarInversiones(ultima.inversionInfo());
		CuentaBroker.getSingleton().addDinero(ultima.getRetornoDinero() );
	}

	public void addCotizacion(String fecha, double open, double high,
			double low, double close, int volumen, double adjClose) {
		cotizaciones.add(new Cotizacion(this, fecha, open, high, low, close, volumen, adjClose));
	}

}
