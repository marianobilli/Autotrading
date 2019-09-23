package autotrading.testSuite;

import static org.junit.Assert.*;

import java.nio.file.Paths;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import autotrading.*;
import autotrading.term.TermType;

public class CotizacionesTest {
	
	static Empresa e;
	
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
	public void testAdd() throws Exception {
		Cotizacion c = new Cotizacion(e, "2013-05-30,875.71,877.99,864.29,868.31,2013400,868.31" );
		assertEquals(3469, e.getCotizaciones().size(),0);
		e.getCotizaciones().add(c);
		assertEquals(3470, e.getCotizaciones().size(),0);
		assertEquals(c, e.getCotizaciones().get(e.getCotizaciones().lastKey()));
	}

	@Test
	public void testGetNext() throws Exception {
		Cotizacion siguiente = e.getCotizaciones().get("2013-04-15");
		Cotizacion anterior = e.getCotizaciones().get("2013-04-12");
		assertEquals(siguiente, anterior.getSiguiente());
	}

	@Test
	public void testGetPrevious() throws Exception {
		Cotizacion siguiente = e.getCotizaciones().get("2013-04-15");
		Cotizacion anterior = e.getCotizaciones().get("2013-04-12");
		assertEquals(anterior, siguiente.getAnterior());
	}

	@Test
	public void testGetLastHigh() {
		Cotizacion realLastHigh;
		Cotizacion lastHigh;

		realLastHigh = e.getCotizaciones().get("2002-04-05");
		lastHigh = e.getCotizaciones().getLastHigh("2002-04-05", 15);
		assertEquals(realLastHigh.getFecha(), lastHigh.getFecha());
		
		realLastHigh = e.getCotizaciones().get("2002-03-12");
		lastHigh = e.getCotizaciones().getLastHigh("2002-03-15", 15);
		assertEquals(realLastHigh.getFecha(), lastHigh.getFecha());
		
		realLastHigh = e.getCotizaciones().get("2002-03-12");
		lastHigh = e.getCotizaciones().getLastHigh("2002-03-15", 50);
		assertEquals(realLastHigh.getFecha(), lastHigh.getFecha());
		
		realLastHigh = e.getCotizaciones().get("2002-03-12");
		lastHigh = e.getCotizaciones().getLastHigh("2002-03-15", 150);
		assertEquals(realLastHigh.getFecha(), lastHigh.getFecha());
	}

	@Test
	public void testGetLastLow() {
		Cotizacion realLastlow = e.getCotizaciones().get("2001-11-23");
		Cotizacion lastlow = e.getCotizaciones().getLastLow("2001-11-30", 15);
		assertEquals(realLastlow.getFecha(), lastlow.getFecha());
		
		realLastlow = e.getCotizaciones().get("2001-11-23");
		lastlow = e.getCotizaciones().getLastLow("2001-11-30", 50);
		assertEquals(realLastlow.getFecha(), lastlow.getFecha());
		
		realLastlow = e.getCotizaciones().get("2001-11-23");
		lastlow = e.getCotizaciones().getLastLow("2001-11-30", 150);
		assertEquals(realLastlow.getFecha(), lastlow.getFecha());
	}

	@Test
	public void testGetLastHigherHigh() throws Exception {
		Cotizacion realLastHigherHigh = e.getCotizaciones().get("2000-03-17");
		Cotizacion lastHigherHigh = e.getCotizaciones().getLastHigherHigh("2000-04-03", TermType.SHORT);
		assertEquals(realLastHigherHigh.getFecha(), lastHigherHigh.getFecha());

		realLastHigherHigh = e.getCotizaciones().get("2005-03-10");
		lastHigherHigh = e.getCotizaciones().getLastHigherHigh("2005-05-18", TermType.MID);
		assertEquals(realLastHigherHigh.getFecha(), lastHigherHigh.getFecha());
		
		realLastHigherHigh = e.getCotizaciones().get("2005-02-28");
		lastHigherHigh = e.getCotizaciones().getLastHigherHigh("2005-05-05", TermType.MID);
		assertEquals(realLastHigherHigh.getFecha(), lastHigherHigh.getFecha());
		
		realLastHigherHigh = e.getCotizaciones().get("2005-02-28");
		lastHigherHigh = e.getCotizaciones().getLastHigherHigh("2005-09-20", TermType.LONG);
		assertEquals(realLastHigherHigh.getFecha(), lastHigherHigh.getFecha());
	}

	@Test
	public void testGetLastLowerLow() throws Exception {
		Cotizacion realLastLowerLow = e.getCotizaciones().get("2001-12-03");
		Cotizacion lastLowerLow = e.getCotizaciones().getLastLowerLow("2001-12-12", TermType.SHORT);
		assertEquals(realLastLowerLow.getFecha(), lastLowerLow.getFecha());

		realLastLowerLow = e.getCotizaciones().get("2001-12-03");
		lastLowerLow = e.getCotizaciones().getLastLowerLow("2002-01-30", TermType.MID);
		assertEquals(realLastLowerLow.getFecha(), lastLowerLow.getFecha());
		
		realLastLowerLow = e.getCotizaciones().get("2001-12-03");
		lastLowerLow = e.getCotizaciones().getLastLowerLow("2002-06-19", TermType.LONG);
		assertEquals(realLastLowerLow.getFecha(), lastLowerLow.getFecha());

	}

}
