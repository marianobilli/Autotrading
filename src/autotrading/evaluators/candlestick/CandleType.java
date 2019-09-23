package autotrading.evaluators.candlestick;

public enum CandleType {
	// Bullish
	YOSEN, // full body (buying opotunity)
	
	// Bearish
	INSEN, // full body (selling opotunity)
	
	HAMMER, // small body at the top - wick 2 or 3 times larger (during downtrend is a buying oportunity)
	HANGINGMAN, // small body at the top - wick 2 or 3 times larger (during uptrend is a selling oportunity)

	INVERTEDHAMMER, // small body at the bottom - during downtrend, trend may reverse to up - buy oportunity) 
	SHOOTINGSTAR,    // small body at the bottom - during uptrend, trend may revers to down - sell oportunity )
	
	UNDEFINED, DOJI
}

