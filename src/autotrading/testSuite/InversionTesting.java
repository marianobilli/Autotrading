package autotrading.testSuite;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import autotrading.Cotizacion;
import autotrading.Cotizaciones;
import autotrading.Empresa;
import autotrading.Inversion;

public class InversionTesting {

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
	Inversion abierta;
	Inversion cerradaGanancia;
	Inversion cerradaPerdida;
	
	
	@Before
	public void setUp() throws Exception {
		e.addCotizacion("2010-01-01", OPEN, HIGH, LOW, CLOSE, VOLUMEN, ADJCLOSE);
		e.addCotizacion("2010-01-02", OPEN, HIGH+1, LOW, CLOSE, VOLUMEN, ADJCLOSE);
		e.addCotizacion("2010-01-03", OPEN, HIGH, LOW, CLOSE*4, VOLUMEN, ADJCLOSE);
		e.addCotizacion("2010-01-04", OPEN, HIGH, LOW-1, CLOSE-2, VOLUMEN, ADJCLOSE);
		e.addCotizacion("2010-01-05", OPEN, HIGH, LOW, CLOSE*2, VOLUMEN, ADJCLOSE);		
		cotizaciones = e.getCotizaciones();
		c1 = cotizaciones.get(cotizaciones.firstKey());
		c2 = c1.getSiguiente();
		c3 = c2.getSiguiente();
		c4 = c3.getSiguiente();
		c5 = c4.getSiguiente();
		
		abierta = new Inversion(10, c1);
		cerradaGanancia = new Inversion(10, c1);
		cerradaGanancia.setVenta(c5);
		
		cerradaPerdida = new Inversion(10, c2);
		cerradaPerdida.setVenta(c4);
	}


	@Test
	public void testGetRentabilidad() {
		assertEquals(95.5, cerradaGanancia.getRentabilidad(), 0);
		assertEquals(-9.56, cerradaPerdida.getRentabilidad(), 0.01);
	}

	@Test
	public void testGetRentabilidadCotizacion() {
		assertEquals(95.5, abierta.getRentabilidad(c5), 0);
		assertEquals(95.5, abierta.getRentabilidad(c5), 0);
	}

	@Test
	public void testGetGananciaCotizacion() {
		assertEquals(286.5, abierta.getGanancia(c5), 0);
		assertEquals(286.5, abierta.getGanancia(c5), 0);
	}

	@Test
	public void testGetGanancia() {
		assertEquals(286.5, cerradaGanancia.getGanancia(), 0);
		assertEquals(-28.7, cerradaPerdida.getGanancia(), 0);
	}

	@Test
	public void testGetRetornoDinero() {
		assertEquals(586.5, cerradaGanancia.getRetornoDinero(), 0);
		assertEquals(271.3, cerradaPerdida.getRetornoDinero(), 0);
	}

	@Test
	public void testWasProfitable() {
		assertTrue(cerradaGanancia.wasProfitable());
		assertFalse(cerradaPerdida.wasProfitable());
	}
}
