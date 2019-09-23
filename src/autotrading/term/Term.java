package autotrading.term;

import java.util.TreeMap;

import autotrading.Cotizaciones;

public class Term {
	
	public static Term singleton = new Term(15, 50, 150);
	
	private TreeMap<TermType, Integer> terms = new TreeMap<TermType, Integer>();
	
	public Term(int shortTerm, int midTerm, int longTerm){
	
		terms.put(TermType.SHORT, new Integer(shortTerm));
		terms.put(TermType.MID,  new Integer(midTerm));
		terms.put(TermType.LONG, new Integer(longTerm));
	}
	
	public int daysFromTo(String from, String to, Cotizaciones cotizaciones){
		
		int days = 0;
		String currentKey = from;
		
		while( currentKey != null && currentKey.compareTo(to) != 0 ){
			currentKey = cotizaciones.nextKey(currentKey);
			days += 1;
		}
		return days;
	}
	
	public TermType getCorrespondingTerm(String from, String to, Cotizaciones cotizaciones){
		
		int days = daysFromTo(from, to, cotizaciones);
		
		if( days > terms.get(TermType.MID) )
			return TermType.LONG;
		
		if( days > terms.get(TermType.SHORT) )
			return TermType.MID;
		else
			return TermType.SHORT;
	}
	
	public int getDaysforTerm(TermType term){
		return terms.get(term);
	}

	
}
