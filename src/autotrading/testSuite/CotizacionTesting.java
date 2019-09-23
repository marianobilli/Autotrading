package autotrading.testSuite;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import autotrading.Cotizacion;
import autotrading.Cotizaciones;
import autotrading.Empresa;
import autotrading.trend.PointType;

public class CotizacionTesting {
	
	private final double LOW = 10;
	private final double OPEN = 20;
	private final double CLOSE = 30;
	private final double HIGH = 40;
	private final int VOLUMEN = 500;
	private final double ADJCLOSE = 30;
	private Empresa e = new Empresa("TEST");
	Cotizaciones cotizaciones ;
	Cotizacion c1;
	Cotizacion c2;
	Cotizacion c3;
	Cotizacion c4;
	Cotizacion c5;
		
	@Before
	public void setUp() throws Exception {
		e.addCotizacion("2010-01-01", OPEN, HIGH, LOW, CLOSE, VOLUMEN, ADJCLOSE);
		e.addCotizacion("2010-01-02", OPEN, HIGH+1, LOW, CLOSE, VOLUMEN, ADJCLOSE);
		e.addCotizacion("2010-01-03", OPEN, HIGH, LOW, CLOSE, VOLUMEN, ADJCLOSE);
		e.addCotizacion("2010-01-04", OPEN, HIGH, LOW-1, CLOSE, VOLUMEN, ADJCLOSE);
		e.addCotizacion("2010-01-05", OPEN, HIGH, LOW, CLOSE, VOLUMEN, ADJCLOSE);		
		cotizaciones = e.getCotizaciones();
		c1 = cotizaciones.get(cotizaciones.firstKey());
		c2 = c1.getSiguiente();
		c3 = c2.getSiguiente();
		c4 = c3.getSiguiente();
		c5 = c4.getSiguiente();

	}


	@Test
	public void testGetOpen() {
		assertEquals(c1.getOpen(), OPEN, 0);
	}

	@Test
	public void testGetHigh() {
		assertEquals(c1.getHigh(), HIGH, 0);
	}

	@Test
	public void testGetLow() {
		assertEquals(c1.getLow(), LOW, 0);
	}

	@Test
	public void testGetClose() {
		assertEquals(c1.getClose(), CLOSE, 0);
	}

	@Test
	public void testGetAdjClose() {
		assertEquals(c1.getAdjClose(), ADJCLOSE, 0);
	}

	@Test
	public void testGetEmpresa() {
		assertEquals(c1.getEmpresa(), e);
	}

	@Test
	public void testGetFecha() {
		assertTrue(c1.getFecha().compareTo("2010-01-01") == 0 );
	}

	@Test
	public void testGetVolumen() {
		assertEquals(c1.getVolumen(),VOLUMEN, 0);
	}

	@Test
	public void testHasAnterior() {
		assertFalse(c1.hasAnterior());
		assertTrue(c2.hasAnterior());
	}

	@Test
	public void testGetAnterior() {
		assertEquals(c1, c2.getAnterior());
	}

	@Test
	public void testHasSiguiente() {
		assertFalse(c5.hasSiguiente());
		assertTrue(c4.hasSiguiente());
	}

	@Test
	public void testGetSiguiente() {
		assertEquals(c1.getSiguiente(), c2);
	}

	@Test
	public void testSetPointType() {
		c1.setPointType(PointType.LOW);
		assertTrue(c1.isLow());
	}

	@Test
	public void testIsHigh() {
		assertTrue(c2.isHigh());
	}

	@Test
	public void testIsLow() {
		assertTrue(c4.isLow());
	}

}
