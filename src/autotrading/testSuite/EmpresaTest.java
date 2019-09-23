package autotrading.testSuite;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.nio.file.Paths;

import org.junit.BeforeClass;
import org.junit.Test;

import autotrading.Empresa;

public class EmpresaTest {

	static public Empresa empresa;
	

	@BeforeClass
	public static void setUpBeforeClass() {
		try {
			empresa = new Empresa(Paths.get("TestSuiteFiles/IRSABA.csv"));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	public void testGetSimbolo() {
		assertEquals("IRSABA.csv", empresa.getSimbolo());
	}

	@Test
	public void testGetInversiones() {
		assertTrue(empresa.getInversiones() != null);
	}

	@Test
	public void testGetCotizaciones() {
		assertEquals(3469, empresa.getCotizaciones().size(),0);
	}

	@Test
	public void testAddCotizacion() throws Exception {
		empresa.addCotizacion("2015-01-01", 4.5, 6.7, 4.2, 5.0, 6000, 6.1);
		assertEquals(3470, empresa.getCotizaciones().size(),0);
	}
	
	@Test
	public void testEmpresaPath() {
		assertTrue(empresa != null);
	}

	@Test
	public void testComprar() {
		empresa.comprar(500, empresa.getCotizaciones().get("2000-06-23"));
		assertEquals(empresa.getCotizaciones().get("2000-06-23"),
				empresa.getInversiones().getInversionesAbiertas().get(0).getCompra());
		assertEquals(500, empresa.getInversiones().getInversionesAbiertas().get(0).getCantAcciones());		
	}
	
	@Test
	public void testVender() throws Exception {
		empresa.comprar(500, empresa.getCotizaciones().get("2000-06-23"));
		empresa.vender(empresa.getCotizaciones().get("2013-05-17"));
		assertTrue(empresa.getInversiones().getInversionesConGanancia().size() == 1);
	}
}
