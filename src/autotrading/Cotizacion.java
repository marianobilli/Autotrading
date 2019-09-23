package autotrading;

import java.text.DecimalFormat;

import autotrading.evaluators.EvaluatorType;
import autotrading.evaluators.Evaluators;
import autotrading.evaluators.candlestick.Candlestick;
import autotrading.evaluators.movingaverage.MovingAverage;
import autotrading.term.TermType;
import autotrading.trend.PointType;

public class Cotizacion implements Comparable<Cotizacion>{
		
		public Evaluators evaluators = new Evaluators(this);
		private Empresa empresa;
		private String fecha;
		private double open;
		private double high;
		private double low;
		private double close;
		private long volume;
		private double adjClose;
		private PointType pointType = PointType.NONE;
		
		public double getOpen() {
			return open;
		}

		public double getHigh() {
			return high;
		}

		public double getLow() {
			return low;
		}

		public double getAdjClose() {
			return adjClose;
		}

		public Cotizacion(Empresa empresa, String fecha, double open, double high, double low, 
						  double close, int volumen, double adjClose) {
			this.empresa = empresa;
			this.fecha = fecha;
			this.open = open;
			this.high = high;
			this.low = low;
			this.close = close;
			this.volume = volumen;
			this.adjClose = adjClose;
		}
				
		public Cotizacion(Empresa empresa, String yahooQuoteFileLine) {
			this.empresa = empresa;
			
			String[] fields = yahooQuoteFileLine.split(",");
			this.fecha = fields[0];
			this.open = Double.parseDouble(fields[1]);
			this.high = Double.parseDouble(fields[2]);
			this.low =  Double.parseDouble(fields[3]);
			this.close =  Double.parseDouble(fields[4]);
			this.volume = Long.parseLong(fields[5]);
			this.adjClose =  Double.parseDouble(fields[6]);
		}

		public Empresa getEmpresa() {
			return empresa;
		}

		public String getFecha() {
			return fecha;
		}

		public double getClose() {
			return close;
		}

		public float getVolumen() {
			return volume;
		}

		public int compareTo(Cotizacion o) {
			if( this.equals(o) )
				return 0;
			return -1;
		}

		public boolean hasAnterior() {
			if( empresa.getCotizaciones().getPrevious(this) != null )
				return true;
			return false;
		}

		public Cotizacion getAnterior() {
			return empresa.getCotizaciones().getPrevious(this);
		}

		public boolean hasSiguiente() {
			if( empresa.getCotizaciones().getNext(this) != null )
				return true;
			return false;
		}

		public Cotizacion getSiguiente() {
			return empresa.getCotizaciones().getNext(this);
		}

		public String printInfo() throws Exception {
			DecimalFormat df = new DecimalFormat("0.0000");
			String info = "";
			
				info = getFecha() + "\t" + 
							df.format(getLow()) + "\t" +
							df.format(getOpen()) + "\t" +
							df.format(getClose()) + "\t" +
							df.format(getHigh()) + "\t" +
							df.format(getMA().getEMA(TermType.SHORT)) + "\t" +
							df.format(getMA().getEMA(TermType.MID)) + "\t" +
							df.format(evaluators.getSignalValue()) + "\t" +
							isHigh()+ "\t" + isLow()+ "\t" +
							//((Candlestick) evaluators.get(EvaluatorType.CANDLESTICK)).getCandleType() + "\t" +
							getEmpresa().getCotizaciones().getTrend().getTrend(TermType.SHORT) + "\t" +
							getEmpresa().getCotizaciones().getTrend().getTrend(TermType.MID) + "\t" +
							getEmpresa().getCotizaciones().getTrend().getTrend(TermType.LONG)  + "\t" ;
				return info;
		}

		public MovingAverage getMA() {
			return (MovingAverage) evaluators.get(EvaluatorType.MOVINGAVERAGE);
		}

		public Candlestick getCandle() {
			return (Candlestick) evaluators.get(EvaluatorType.CANDLESTICK);
		}

		public void setPointType(PointType type) {
			this.pointType = type;
		}

		public boolean isHigh() {
			if( pointType == PointType.HIGH)
				return true;
			return false;
		}

		public boolean isLow() {
			if( pointType == PointType.LOW)
				return true;
			return false;
		}

}
