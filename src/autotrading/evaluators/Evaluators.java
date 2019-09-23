package autotrading.evaluators;

import java.util.Iterator;
import java.util.TreeMap;

import autotrading.Cotizacion;
import autotrading.evaluators.movingaverage.MovingAverage;

public class Evaluators extends TreeMap<EvaluatorType, Evaluator> implements Iterable<Evaluator>{

	private static final long serialVersionUID = -2335376267427111663L;
	
	public Evaluators(Cotizacion cotizacion) {
		put(EvaluatorType.MOVINGAVERAGE, new MovingAverage(cotizacion));
		//put(EvaluatorType.CANDLESTICK, new Candlestick(cotizacion));
	}

	public double getSignalValue() throws Exception{
		double signalValue = 0;
		for(Evaluator e:this)
			signalValue += e.getSignalValue();
		return signalValue;
	}

	public Iterator<Evaluator> iterator() {
		return values().iterator();
	}

}
