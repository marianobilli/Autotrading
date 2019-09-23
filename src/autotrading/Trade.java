package autotrading;

import autotrading.term.Term;
import autotrading.tools.Archivo;

public class Trade {

	public void simular(Empresa irsa, String fechaDesde) throws Exception {
		Cotizacion actual = irsa.getCotizaciones().get(fechaDesde);
		Archivo.grabarCotizaciones("fecha \t low \t open \t close \t high \t ema short \t ema mid \t signal\t ishigh \t islow \t candle  \t shorttrend \t midtrend \t longtrend \t action\n ");
		Archivo.grabarInversiones("Fecha compra \tValor compra\tFecha venta\tValor venta\tDinero Invertido\tGanancia\tRentabilidad\tDinero disponible\n");
		
		do {
			analizar(actual);
			if(actual.hasSiguiente())
				actual = actual.getSiguiente();
		} while (actual.hasSiguiente());
		if(irsa.getInversiones().getCantAcciones() > 0 )
			irsa.vender(actual);
	}

	private void analizar(Cotizacion actual) throws Exception {
		int cantAcciones = 0;
		
		double signalValue = actual.evaluators.getSignalValue();
		
		if( signalValue < 0 && 50 < Term.singleton.daysFromTo(actual.getEmpresa().getCotizaciones().getFirst().getFecha(), actual.getFecha(), actual.getEmpresa().getCotizaciones()) ){ // negative value means SELL
			cantAcciones = actual.getEmpresa().getCantAcciones();
			if(cantAcciones > 0){
				actual.getEmpresa().vender(actual);
				Archivo.grabarCotizaciones(actual.printInfo() + "-1\n");
				return;
			}
		}
		
		  if(signalValue > 0  && 50 < Term.singleton.daysFromTo(actual.getEmpresa().getCotizaciones().getFirst().getFecha(), actual.getFecha(), actual.getEmpresa().getCotizaciones())// positive value means BUY 
				&& CuentaBroker.getSingleton().hayDineroDisponible()){
			actual.getEmpresa().comprar(getCantidadCompra(actual), actual);
			Archivo.grabarCotizaciones(actual.printInfo() + "1\n");
			return;
		}
		Archivo.grabarCotizaciones(actual.printInfo() + "0\n");
	}

	private int getCantidadCompra(Cotizacion actual) {
		return (int) Math.abs(CuentaBroker.getSingleton().getDineroParaCompra() / 
				 actual.getClose());
	}	
}
