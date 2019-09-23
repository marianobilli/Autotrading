package autotrading;

import org.apache.commons.collections15.bidimap.TreeBidiMap;

import autotrading.term.Term;
import autotrading.term.TermType;
import autotrading.trend.PointType;
import autotrading.trend.Trend;

public class Cotizaciones extends TreeBidiMap<String, Cotizacion>  {
	
	private Trend trend = new Trend(this);
	
	public Trend getTrend(){
		return this.trend;
	}
	
	public void add(Cotizacion cotizacion) {
		Cotizacion anteultima = null;
		
		try {
			anteultima = get(lastKey());
		} catch (Exception e) {
			// Nada que hacer
		}
		put(cotizacion.getFecha(), cotizacion);

		// analizar pivot points del dia anterior
		if (anteultima != null ){
			analizeHigh(anteultima);
			analizeLow(anteultima);
		}
		//getTrend().analizeTrend(cotizacion.getFecha());
		
	}

	public Cotizacion getNext(Cotizacion cotizacion){
		try {
			return get(nextKey(cotizacion.getFecha()));	
		} catch (Exception e) {
			return null;
		}
	}
	
	public Cotizacion getPrevious(Cotizacion cotizacion){
		try {
			String fechaAnterior = previousKey(cotizacion.getFecha());
			return get(fechaAnterior);	
		} catch (Exception e) {
			return null;
		}
	}
		
	private void analizeHigh(Cotizacion cotizacion){
		Cotizacion siguiente = getNext(cotizacion);
		
		if( cotizacion.hasAnterior() ) {
			Cotizacion anterior = getPrevious(cotizacion);
			
			if (cotizacion.getHigh() > anterior.getHigh() && 
			    cotizacion.getHigh() > siguiente.getHigh() ){
				cotizacion.setPointType(PointType.HIGH);
			}
			
			if (cotizacion.getHigh() == anterior.getHigh() && cotizacion.getHigh() > siguiente.getHigh() )
				// recorrer hasta encontrar uno mas chico y devlover High, o uno mas grande y salir
				while(anterior.hasAnterior()){
					anterior = anterior.getAnterior();
					
					if (cotizacion.getHigh() > anterior.getHigh() ){
						cotizacion.setPointType(PointType.HIGH);
						return;
					}
					if (cotizacion.getHigh() < anterior.getHigh() )
						return;
			}	
			
		}
  		// Si no tiene anterior solo comparo este con el siguiente
		else if (cotizacion.getHigh() > siguiente.getHigh()) {
				cotizacion.setPointType(PointType.HIGH);
				return;
			} else
				return;
	}
	
	private void analizeLow(Cotizacion cotizacion){
		Cotizacion siguiente = getNext(cotizacion);
		
		if( cotizacion.hasAnterior()){
			Cotizacion anterior = getPrevious(cotizacion);
			
		    if (cotizacion.getLow() < anterior.getLow() && 
			    cotizacion.getLow() < siguiente.getLow() ){
				cotizacion.setPointType(PointType.LOW);
				return;
		    }
		    //  si son iguales recorrer hasta encontrar uno mas grande y devlover low, o uno mas chico y salir
			if (cotizacion.getLow() == anterior.getLow() && cotizacion.getLow() < siguiente.getLow() )
				while(anterior.hasAnterior()){
					anterior = anterior.getAnterior();
					if (cotizacion.getLow() < anterior.getLow() ){
						cotizacion.setPointType(PointType.LOW);
						return;
					}
					if (cotizacion.getLow() > anterior.getLow() )
						return;
			}
		}
		// Si no tiene anterior solo comparo este con el siguiente
		else if( cotizacion.getLow() < siguiente.getLow() ){
			cotizacion.setPointType(PointType.LOW);
			return;
		}
		else
			return;
	}
	
	public Cotizacion getLastHigh(String fecha, int daysLeft){
		String currentKey = fecha;
		Cotizacion current = null;
		Cotizacion higher = null;
		int daysCount = 0;
		
 		while( currentKey != null && daysCount <= daysLeft-1 ){
			current = get(currentKey);
			
			if ( higher == null)
				higher = current;
			else if ( current.getHigh() > higher.getHigh() )
				higher = current;
			
			if (current.isHigh())
				return current;
			else
				currentKey = previousKey(currentKey);
			daysCount += 1;
		}
		if (higher != null)
			return higher;
		return null;
	}

	public Cotizacion getLastLow(String fecha, int daysLeft){
		String currentKey = fecha;
		Cotizacion current = null;
		Cotizacion lower = null;
		int daysCount = 0;
		
		while( currentKey != null && daysCount <= daysLeft-1 ){
			current = get(currentKey);
			
			if ( lower == null)
				lower = current;
			else if ( current.getLow() < lower.getLow() )
				lower = current;
				
			if (current.isLow())
				return current;
			else
				currentKey = previousKey(currentKey);
			daysCount += 1;
		}
		
		if (lower != null)
			return lower;
		return null;
	}

	
	public Cotizacion getLastHigherHigh(String startDate, TermType term) throws Exception{
		Cotizacion actual = get(startDate);
		Cotizacion higher = actual;
		int daysTerm = Term.singleton.getDaysforTerm(term);
		
		for (int i = 0; actual.hasAnterior() && i < daysTerm  ;i++){
			if ( actual.getHigh() > higher.getHigh() )
				higher = actual;
			actual = actual.getAnterior();
		}
		return higher;
	}
	
	public Cotizacion getLastLowerLow(String startDate, TermType term) throws Exception{
		Cotizacion actual = get(startDate);
		Cotizacion lower = actual;
		int daysTerm = Term.singleton.getDaysforTerm(term);
		
		for (int i = 0; actual.hasAnterior() && i < daysTerm  ;i++){
			if ( actual.getLow() < lower.getLow() )
				lower = actual;
			actual = actual.getAnterior();
		}
		return lower;
	}

	public Cotizacion getFirst() {
		return get(firstKey());
	}
}
