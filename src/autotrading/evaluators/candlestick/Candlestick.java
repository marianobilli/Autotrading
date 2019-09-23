package autotrading.evaluators.candlestick;

import autotrading.Cotizacion;
import autotrading.evaluators.Evaluator;

public class Candlestick implements Evaluator{
	
	private Cotizacion cotizacion;
	private CandleType candleType;
	
	private Double bodySize;
	private Double lowWickSize;
	private Double highWickSize;
	
	
	private CandleBodyType bodyType;
	private CandleLongitude lowWickLongitude;
	private CandleLongitude highWickLongitude;
	private double signalValue;
	private boolean analized = false;
	
	
	public Candlestick(Cotizacion cotizacion) throws Exception {
		this.cotizacion = cotizacion;
	}

	private CandleType analizeCandle() throws Exception{
		analizeBody();
		analizeLowWick();
		analizeHighWick();
		analizeCandleType();
		
		return candleType;
	}
	
	public CandleType getCandleType(){
		return this.candleType;
	}
	
	public boolean isDoji(){
		if (bodyType == CandleBodyType.DOJI)
			return true;
		return false;
	}
	
	
	@SuppressWarnings("incomplete-switch")
	private void analizeCandleType() throws Exception {
		
			if (isDoji()){
				candleType = CandleType.DOJI;
				return;
			}
			
			if ( lowWickLongitude == CandleLongitude.NON_EXISTENT && 
				(highWickLongitude == CandleLongitude.TWO_TIMES_LONG || highWickLongitude == CandleLongitude.THREE_TIMES_LONG_OR_MORE ))
				
				switch(cotizacion.getEmpresa().getCotizaciones().getTrend().getLastTrend()){
				case UPTREND: candleType = CandleType.SHOOTINGSTAR; break;
				case DOWNTREND: candleType = CandleType.INVERTEDHAMMER; break;
				}

			if ( highWickLongitude == CandleLongitude.NON_EXISTENT && 
				(lowWickLongitude == CandleLongitude.TWO_TIMES_LONG || lowWickLongitude == CandleLongitude.THREE_TIMES_LONG_OR_MORE )){

				if( candleType != null && candleType != CandleType.UNDEFINED)	
					throw new Exception("Ya se habia seteado el candleType");
			
					switch(cotizacion.getEmpresa().getCotizaciones().getTrend().getLastTrend()){
					case UPTREND: candleType = CandleType.HANGINGMAN; break;
					case DOWNTREND: candleType = CandleType.HAMMER; break;
					}
			}
		
		if ( (lowWickLongitude == CandleLongitude.NON_EXISTENT  || lowWickLongitude == CandleLongitude.VERYSHORT) &&
			 (highWickLongitude == CandleLongitude.NON_EXISTENT || highWickLongitude == CandleLongitude.VERYSHORT) 	){
			
			if( candleType != null && candleType != CandleType.UNDEFINED )	
				throw new Exception("Ya se habia seteado el candleType");
			
			if ( bodyType == CandleBodyType.BULLISH)
				candleType = CandleType.YOSEN;
			else if( bodyType == CandleBodyType.BEARISH)
				candleType = CandleType.INSEN;
		}
		
		if ( candleType == null)
			candleType = CandleType.UNDEFINED;
	}

	private void analizeLowWick() {
		switch(bodyType){
		case BULLISH: lowWickSize = cotizacion.getOpen() - cotizacion.getLow() ; break;
		case BEARISH: lowWickSize = cotizacion.getClose() - cotizacion.getLow() ; break;
		case DOJI: lowWickSize = cotizacion.getClose() - cotizacion.getLow() ; break;
		}
		
		if ( lowWickSize == 0 )
			lowWickLongitude = CandleLongitude.NON_EXISTENT;
		else if ( lowWickSize > 0 && lowWickSize < (bodySize/3)  )
			lowWickLongitude = CandleLongitude.VERYSHORT;
		else if ( lowWickSize*2 >= bodySize && lowWickSize*3 < bodySize )
			lowWickLongitude = CandleLongitude.TWO_TIMES_LONG;
		else if ( lowWickSize*3 >= bodySize  )
			lowWickLongitude = CandleLongitude.THREE_TIMES_LONG_OR_MORE;
		else
			lowWickLongitude = CandleLongitude.NORMAL;
	}
	
	private void analizeHighWick() {
		switch(bodyType){
		case BULLISH: highWickSize = cotizacion.getHigh() - cotizacion.getClose() ; break;
		case BEARISH: highWickSize = cotizacion.getHigh() - cotizacion.getOpen() ; break;
		case DOJI: highWickSize = cotizacion.getHigh() - cotizacion.getClose() ; break;
		}

		if ( highWickSize == 0 )
			highWickLongitude = CandleLongitude.NON_EXISTENT;
		else if ( highWickSize > 0 && highWickSize < (bodySize/3)  )
			highWickLongitude = CandleLongitude.VERYSHORT;
		else if ( highWickSize*2 >= bodySize && highWickSize*3 < bodySize )
			highWickLongitude = CandleLongitude.TWO_TIMES_LONG;
		else if ( lowWickSize*3 >= bodySize  )
			highWickLongitude = CandleLongitude.THREE_TIMES_LONG_OR_MORE;
		else
			highWickLongitude = CandleLongitude.NORMAL;
	}

	private void analizeBody() {
		if (cotizacion.getOpen() == cotizacion.getClose())
			bodyType = CandleBodyType.DOJI;
		if (cotizacion.getOpen() > cotizacion.getClose())
			bodyType = CandleBodyType.BEARISH;
		if (cotizacion.getOpen() < cotizacion.getClose())
			bodyType = CandleBodyType.BULLISH;
		
		bodySize = Math.abs(cotizacion.getClose() - cotizacion.getOpen());
	}

	@SuppressWarnings("incomplete-switch")
	@Override
	public double getSignalValue() throws Exception {
		
		if( analized )
			return signalValue;
		
		switch(analizeCandle()){
		case YOSEN: analized = true; return signalValue = bodySize / cotizacion.getClose();
		case INSEN: analized = true; return signalValue = (bodySize / cotizacion.getClose())*-1;
		case DOJI: analized = true; return 0;
		}
		
		// Analize if there is hammer from previous day and get confirmation
		if( !cotizacion.hasAnterior()){
			analized = true;
			return signalValue = 0;
		}
		
		if( cotizacion.getAnterior().getCandle().candleType == CandleType.HAMMER ||
			cotizacion.getAnterior().getCandle().candleType == CandleType.INVERTEDHAMMER	)
			// Confirmation
			if ( cotizacion.getClose() > cotizacion.getAnterior().getClose() ){
				// Hammer confirmed
				analized = true;
				return signalValue = (cotizacion.getClose() - cotizacion.getAnterior().getClose())/cotizacion.getClose();
				}

		// Analize if there is HANGINGMAN from previous day and get confirmation
		if( cotizacion.getAnterior().getCandle().candleType == CandleType.HANGINGMAN ||
			cotizacion.getAnterior().getCandle().candleType == CandleType.SHOOTINGSTAR	)
			// Confirmation
			if ( cotizacion.getClose() < cotizacion.getAnterior().getClose() ){
				// Hammer confirmed
				analized = true;
				return signalValue = (cotizacion.getClose() - cotizacion.getAnterior().getClose())/cotizacion.getClose()*-1;
				}
		analized = true;
		return signalValue = 0;
	}
}
