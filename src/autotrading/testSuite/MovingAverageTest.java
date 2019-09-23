package autotrading.testSuite;

import static org.junit.Assert.assertEquals;

import java.nio.file.Paths;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import autotrading.Empresa;
import autotrading.term.TermType;

public class MovingAverageTest {
	
	public static Empresa e;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		try {
			e = new Empresa(Paths.get("TestSuiteFiles/IRSABA.csv"));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testCalculateSMA() {
		double expectedSMA;
		double calculatedSMA;
		
		// one day before having enough days to calculate
		expectedSMA = 0;
		calculatedSMA = e.getCotizaciones().get("2000-01-20").getMA().calculateSMA(TermType.SHORT);
		assertEquals(expectedSMA, calculatedSMA, 0.0001);
		
		// first day of enough days to calculate
		expectedSMA = 3.0339;
		calculatedSMA = e.getCotizaciones().get("2000-01-21").getMA().calculateSMA(TermType.SHORT);
		assertEquals(expectedSMA, calculatedSMA, 0.0001);

		// second  day of enough days to calculate
		expectedSMA = 3.0173;
		calculatedSMA = e.getCotizaciones().get("2000-01-24").getMA().calculateSMA(TermType.SHORT);
		assertEquals(expectedSMA, calculatedSMA, 0.0001);

		// any  day of enough days to calculate
		expectedSMA = 3.0413;
		calculatedSMA = e.getCotizaciones().get("2000-01-31").getMA().calculateSMA(TermType.SHORT);
		assertEquals(expectedSMA, calculatedSMA, 0.0001);
		
		expectedSMA = 3.1716;
		calculatedSMA = e.getCotizaciones().get("2000-03-10").getMA().calculateSMA(TermType.MID);
		assertEquals(expectedSMA, calculatedSMA, 0.0001);
		
		expectedSMA = 2.86;
		calculatedSMA = e.getCotizaciones().get("2000-07-28").getMA().calculateSMA(TermType.LONG);
		assertEquals(expectedSMA, calculatedSMA, 0.0001);
	}

	@Test
	public void testCalculateEMA() {
		double expectedEMA;
		double calculatedEMA;
		
		// First EMA
		expectedEMA =  3.0285; 
		calculatedEMA = e.getCotizaciones().get("2000-01-21").getMA().calculateEMA(TermType.SHORT);
		assertEquals(expectedEMA, calculatedEMA, 0.0001);
		
		// Second EMA
		expectedEMA =   3.0237; 
		calculatedEMA = e.getCotizaciones().get("2000-01-24").getMA().calculateEMA(TermType.SHORT);
		assertEquals(expectedEMA, calculatedEMA, 0.0001);
		
		// Any EMA
		expectedEMA =   3.0876; 
		calculatedEMA = e.getCotizaciones().get("2000-01-31").getMA().calculateEMA(TermType.SHORT);
		assertEquals(expectedEMA, calculatedEMA, 0.0001);
	}

	@Test
	public void testHasEMASSlopeChangedToNegative() {
		assertEquals(true, e.getCotizaciones().get("2000-03-27").getMA().hasEMASlopeChangedToNegative(TermType.SHORT));
	}

	@Test
	public void testHasEMASlopeChangedToPositive() {
		assertEquals(true, e.getCotizaciones().get("2000-06-02").getMA().hasEMASlopeChangedToPositive(TermType.SHORT));
	}

	@Test
	public void testGetSignalValue() {
		assertEquals(-0.3484, e.getCotizaciones().get("2000-04-05").getMA().getSignalValue(), 0.0001 );
	}

}
