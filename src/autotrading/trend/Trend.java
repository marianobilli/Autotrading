package autotrading.trend;

import java.util.TreeMap;

import autotrading.Cotizacion;
import autotrading.Cotizaciones;
import autotrading.term.Term;
import autotrading.term.TermType;

public class Trend extends TreeMap<TermType, TrendType>{
	
	private static final long serialVersionUID = 2212431470274975181L;
	private Cotizaciones cotizaciones;
		
	public Trend(Cotizaciones cotizaciones){
		this.cotizaciones = cotizaciones; 
	}
	
	public TrendType getTrend(TermType termType){
		return get(termType);
	}
	
	public TrendType getLastTrend(){
		
		if( get(TermType.SHORT) != null && get(TermType.SHORT) != TrendType.UNDEFINED)
			return get(TermType.SHORT);
		
		if( get(TermType.MID) != null && get(TermType.MID) != TrendType.UNDEFINED)
			return get(TermType.MID);
		
		if( get(TermType.LONG) != null && get(TermType.LONG) != TrendType.UNDEFINED)
			return get(TermType.LONG);
		
		return TrendType.UNDEFINED;
	}
		
	public TrendType analizeTrend(String fecha) throws Exception {
		
		// short term

		TermType term = TermType.SHORT;
		int daysTerm = Term.singleton.getDaysforTerm(term);
		Cotizacion lastHigherHigh = cotizaciones.getLastHigherHigh(fecha,term);
		Cotizacion lastHigh = cotizaciones.getLastHigh(fecha,daysTerm);
		Cotizacion lastLowerLow = cotizaciones.getLastLowerLow(fecha,term);
		Cotizacion lastLow = cotizaciones.getLastLow(fecha,daysTerm);
		setTrend( lastHigherHigh, lastHigh, lastLowerLow, lastLow,  fecha,  term );

		// mid term
		term = TermType.MID;
		daysTerm = Term.singleton.getDaysforTerm(term);
		lastHigherHigh = cotizaciones.getLastHigherHigh(fecha,term);
		lastHigh = cotizaciones.getLastHigh(fecha,daysTerm);
		lastLowerLow = cotizaciones.getLastLowerLow(fecha,term);
		lastLow = cotizaciones.getLastLow(fecha,daysTerm);
		setTrend( lastHigherHigh, lastHigh, lastLowerLow, lastLow,  fecha,  term );

		// long term
		term = TermType.LONG;
		daysTerm = Term.singleton.getDaysforTerm(term);
		lastHigherHigh = cotizaciones.getLastHigherHigh(fecha,term);
		lastHigh = cotizaciones.getLastHigh(fecha,daysTerm);
		lastLowerLow = cotizaciones.getLastLowerLow(fecha,term);
		lastLow = cotizaciones.getLastLow(fecha,daysTerm);
		setTrend( lastHigherHigh, lastHigh, lastLowerLow, lastLow,  fecha,  term );

		
		TrendType trend = get(TermType.SHORT);
		if ( trend != null)
			return trend;
		
		trend = get(TermType.MID);
		if ( trend != null)
			return trend;
		
		trend = get(TermType.LONG);
		if ( trend != null)
			return trend;
		
		return TrendType.UNDEFINED;

	}
	
	private void setTrend(Cotizacion lastHigherHigh,Cotizacion lastHigh,Cotizacion lastLowerLow,Cotizacion lastLow, String fecha, TermType term ) throws Exception{
		cotizaciones = lastHigh.getEmpresa().getCotizaciones();
		
		TrendType highstrend = analizeDowntrend(lastHigherHigh,lastHigh,lastLowerLow,lastLow,fecha);
		TrendType lowstrend = analizeUptrend(lastHigherHigh,lastHigh,lastLowerLow,lastLow,fecha);
		
		int highsTrendAge = Term.singleton.daysFromTo(lastHigherHigh.getFecha(), lastHigh.getFecha(), cotizaciones);
		int lowsTrendAge =  Term.singleton.daysFromTo(lastLowerLow.getFecha(), lastLow.getFecha(), cotizaciones);
		
		if ( highstrend == lowstrend )
			put(term, highstrend);
		else if ( highstrend == TrendType.UNDEFINED && lowstrend != highstrend )
			put(term, lowstrend);
		else if ( lowstrend   == TrendType.UNDEFINED && lowstrend != highstrend )
			put(term, highstrend);
		else if ( lowstrend   != TrendType.UNDEFINED && lowstrend != TrendType.UNDEFINED )
			if ( highsTrendAge > 0 && highsTrendAge > lowsTrendAge)
				put(term, highstrend);
			else if ( lowsTrendAge > 0 && lowsTrendAge > highsTrendAge)
				put(term, lowstrend);
			else
			put(term, TrendType.UNDEFINED);
		}
		

	private TrendType analizeDowntrend(Cotizacion lastHigherHigh,Cotizacion lastHigh,Cotizacion lastLowerLow,Cotizacion lastLow, String fecha ){
		// Analize High Values
		if( lastHigherHigh != null )
			if ( lastHigherHigh.equals(lastHigh) )
				return TrendType.UNDEFINED;
			else if(lastHigherHigh.getHigh() > lastHigh.getHigh() )
				return TrendType.DOWNTREND;
		return TrendType.UNDEFINED;
	}
	
	private TrendType analizeUptrend(Cotizacion lastHigherHigh,Cotizacion lastHigh,Cotizacion lastLowerLow,Cotizacion lastLow, String fecha ){
		// Analize Low Values
		if( lastLowerLow != null )
			if ( lastLowerLow.equals(lastLow) )
				return TrendType.UNDEFINED;
			else if( lastLowerLow != null &&	lastLowerLow.getLow() < lastLow.getLow() )
				return TrendType.UPTREND;
		return TrendType.UNDEFINED;
	}
	
}
