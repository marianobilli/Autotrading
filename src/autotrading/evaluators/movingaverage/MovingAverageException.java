package autotrading.evaluators.movingaverage;

import autotrading.Cotizacion;

public class MovingAverageException extends Exception {
	
	public MovingAverageException(Cotizacion cotizacion, int cantCotizaciones) {
	
		new Exception("Para empresa:" + cotizacion.getEmpresa() + 
					  ". Para la fecha:" + cotizacion.getFecha().toString() + 
					  "No hay " +  cantCotizaciones + " previas");
	}
	
	private static final long serialVersionUID = -1076557474163527601L;

}
