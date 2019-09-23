package autotrading.evaluators.movingaverage;

import autotrading.Cotizacion;
import autotrading.evaluators.Evaluator;
import autotrading.term.Term;
import autotrading.term.TermType;

public class MovingAverage implements Evaluator{
	
	public static final float INTERVALO_PENDIENTE = 2;
	
	private Cotizacion cotizacion;
	
	//Simple Moving Average
	private double smaShortTerm = 0;
	private double smaMidTerm = 0;
	private double smaLongTerm = 0;
	
	// Exponential Moving Average
	private double emaShortTerm = 0;
	private double emaMidTerm = 0;
	private double emaLongTerm = 0;
	
	private double emaShortTermSlope = 0;
	private double emaMidTermSlope = 0;
	private double emaLongTermSlope = 0;

	public MovingAverage(Cotizacion cotizacion){
		this.cotizacion = cotizacion;
	}

	public double getSMA(TermType plazo){
		switch(plazo){
		case SHORT: 
			if( smaShortTerm == 0)
				return smaShortTerm = calculateSMA(TermType.SHORT);
			return smaShortTerm;
		case MID: 
			if( smaMidTerm == 0)
				return smaMidTerm = calculateSMA(TermType.MID);
			return smaMidTerm;
		case LONG: 
			if( smaLongTerm == 0)
				return smaLongTerm = calculateSMA(TermType.LONG);
			return smaLongTerm;
		}
	return 0;
	}
	
	public double getEMA(TermType termType) {
		switch(termType){
		case SHORT: 
			if( emaShortTerm == 0)
				return emaShortTerm = calculateEMA(TermType.SHORT);
			return emaShortTerm;
		case MID: 
			if( emaMidTerm == 0)
				return emaMidTerm = calculateEMA(TermType.MID);
			return emaMidTerm;
		case LONG: 
			if( emaLongTerm == 0)
				return emaLongTerm = calculateEMA(TermType.LONG);
			return emaLongTerm;
		}
	return 0;
	}
	
	public double getEMASlope(TermType plazo){
		switch(plazo){
		case SHORT: 
			if ( emaShortTermSlope == 0)
				return emaShortTermSlope = calculateEMASlope(TermType.SHORT);
			return emaShortTermSlope;
		case MID: 
			if ( emaMidTermSlope == 0)
				return emaMidTermSlope = calculateEMASlope(TermType.MID);
			return emaMidTermSlope;
		case LONG: 
			if ( emaLongTermSlope == 0)
				return emaLongTermSlope = calculateEMASlope(TermType.LONG);
			return emaLongTermSlope;
		}
	return 0;
	}
	
	public double calculateSMA(TermType termType) {
		Cotizacion current = cotizacion;		
		int term = Term.singleton.getDaysforTerm(termType);
		float simpleMeanAverage = 0;
		int i;
		for (i = 1; i <= term ; i++) {
			simpleMeanAverage  += current.getClose();
			if ( current.hasAnterior() )
				current = current.getAnterior();
			else
				break;
			
		}
		if (i == term || i == term + 1)
			simpleMeanAverage = simpleMeanAverage / term;
		else
			return 0;
		return simpleMeanAverage;
	}
	
	public double calculateEMA(TermType termType) {
		int term = Term.singleton.getDaysforTerm(termType);
		double multiplier = 2.0 / (term + 1); 
		double exponentialMovingAverage = 0;
		
		if( cotizacion.hasAnterior() && cotizacion.getAnterior().getMA().getSMA(termType) != 0 )
			exponentialMovingAverage = (( cotizacion.getClose() - cotizacion.getAnterior().getMA().getEMA(termType) ) * multiplier ) + 
									    cotizacion.getAnterior().getMA().getEMA(termType) ;
		else // for first time SMA is used as the previus EMA
			exponentialMovingAverage = (( cotizacion.getClose() - getSMA(termType) ) * multiplier ) + 
										getSMA(termType) ;
		
		return exponentialMovingAverage;
	}
	

	public  boolean hasEMASlopeChangedToNegative(TermType plazo) {
		if( cotizacion.hasAnterior() &&
			cotizacion.getAnterior().getMA().getEMASlope(plazo) >= 0 && 
			this.getEMASlope(plazo) < 0 )
				return true;
			return false;
	}
	
	public  boolean hasEMASlopeChangedToPositive(TermType plazo) {
		if( cotizacion.hasAnterior() &&
			cotizacion.getAnterior().getMA().getEMASlope(plazo) <= 0 && 
			this.getEMASlope(plazo) > 0 )
			return true;
		return false;
	}
	
	public double calculateEMASlope(TermType plazo) {
		
		double valorInicial = 0;
		double valorFinal = 0;
		Cotizacion actual = cotizacion;
		
		for (int i = 0; i < INTERVALO_PENDIENTE; i++) {	
			if( actual.hasAnterior())
				actual = actual.getAnterior();
			else
				return 0;
		}
		
		valorFinal = this.getEMA(plazo);
		valorInicial = actual.getMA().getEMA(plazo);
		
		if (valorFinal == 0 || valorInicial == 0)
			return 0;
		else
		// Calculo de pendiente
			return Math.atan( (double)(valorFinal-valorInicial) / (INTERVALO_PENDIENTE/10) );
		}

		public boolean isSMADownTrend() {
			if (!cotizacion.hasAnterior())
				return false;
			
			if( cotizacion.getAnterior().getMA().getSMA(TermType.SHORT) > cotizacion.getAnterior().getMA().getSMA(TermType.MID)
				&& getSMA(TermType.SHORT) < getSMA(TermType.MID))
				return true;
			else
				return false;
		}
		
		public boolean isSMAUpTrend(){
			if (!cotizacion.hasAnterior())
				return false;
			
			if( cotizacion.getAnterior().getMA().getSMA(TermType.SHORT) < cotizacion.getAnterior().getMA().getSMA(TermType.MID)
					&& getSMA(TermType.SHORT) > getSMA(TermType.MID))
					return true;
				else
					return false;
		}
		
		public boolean isSellCrossover() {
			if (!cotizacion.hasAnterior())
				return false;
			
			if( cotizacion.getAnterior().getMA().getEMA(TermType.SHORT) > cotizacion.getAnterior().getMA().getEMA(TermType.MID)
				&& getEMA(TermType.SHORT) < getEMA(TermType.MID))
				return true;
			else
				return false;
		}
		
		public boolean isBuyCrossover(){
			if (!cotizacion.hasAnterior())
				return false;
			
			if( cotizacion.getAnterior().getMA().getEMA(TermType.SHORT) < cotizacion.getAnterior().getMA().getEMA(TermType.MID)
					&& getEMA(TermType.SHORT) > getEMA(TermType.MID))
					return true;
				else
					return false;
		}

		public double getSignalValue() {
			
			// crossover rule between short term and mid term
			if( isBuyCrossover() )
				return Math.abs(getEMASlope(TermType.SHORT) + getEMASlope(TermType.MID)) + getEMASlope(TermType.LONG)  ;
			else if( isSellCrossover())
				return -1 * Math.abs(getEMASlope(TermType.SHORT) + getEMASlope(TermType.MID) + getEMASlope(TermType.LONG));

			return 0;
		}
		
}
