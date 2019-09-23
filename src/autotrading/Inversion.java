package autotrading;

import java.text.DecimalFormat;

public class Inversion {
	
	public static final String VENTA  = "VENTA";
	public static final String COMPRA = "COMPRA";
	private Cotizacion compra;
	private Cotizacion venta;
	private int cantAcciones; 
	

	public Inversion(int cantAcciones, Cotizacion compra) {
		this.compra = compra;
		this.cantAcciones = cantAcciones;
	}

	public Cotizacion getCompra() {
		return compra;
	}

	private Cotizacion getVenta() {
		return venta;
	}

	public boolean isFinalizada(){
		if( compra != null && venta != null)
			return true;
		return false;
		
	}
	
	public double getRentabilidad(){
		double inversion = compra.getClose() * cantAcciones;
		return (getGanancia() / inversion )*100; 	
	}
	
	public float getRentabilidad(Cotizacion cotizacion){
		double inversion = compra.getClose() * cantAcciones;
		return (float) (( getGanancia(cotizacion) / inversion ) * 100); 
	}
	
	public double getGanancia(Cotizacion cotizacion){
		double importeCompra = compra.getClose() * cantAcciones;
		double importeVenta  = cotizacion.getClose() * cantAcciones;
		double comisionCompra = (compra.getClose() * cantAcciones) * 
								(CuentaBroker.getSingleton().getComision() / 100);

		double comisionVenta = (cotizacion.getClose() * cantAcciones) * 
				(CuentaBroker.getSingleton().getComision() / 100);

		return  importeVenta - importeCompra - comisionCompra - comisionVenta;
	}
	
	public double getGanancia(){
		
		double importeCompra = compra.getClose() * cantAcciones;
		double importeVenta  = venta.getClose() * cantAcciones;
		double comisionCompra = (compra.getClose() * cantAcciones) * 
								(CuentaBroker.getSingleton().getComision() / 100);

		double comisionVenta = (venta.getClose() * cantAcciones) * 
				(CuentaBroker.getSingleton().getComision() / 100);

		return  importeVenta - importeCompra - comisionCompra - comisionVenta;
	}
	
	public double getRetornoDinero(){

		double importeVenta  = venta.getClose() * cantAcciones;
		double comisionCompra = (compra.getClose() * cantAcciones) * 
								(CuentaBroker.getSingleton().getComision() / 100);

		double comisionVenta = (venta.getClose() * cantAcciones) * 
				(CuentaBroker.getSingleton().getComision() / 100);

		return  importeVenta - comisionCompra - comisionVenta;
	}
	
	public boolean wasProfitable(){
		double importeCompra = compra.getClose() * cantAcciones;
		double importeVenta  = venta.getClose() * cantAcciones;
		double comisionCompra = (compra.getClose() * cantAcciones) * 
								(CuentaBroker.getSingleton().getComision() / 100);

		double comisionVenta = (venta.getClose() * cantAcciones) * 
				(CuentaBroker.getSingleton().getComision() / 100);
		
		if( (importeVenta - importeCompra - comisionCompra - comisionVenta) > 0)
			return true;
		return false;
		
	}

	public int getCantAcciones() {
		return cantAcciones;
	}


	public void setVenta(Cotizacion actual) {
		venta = actual;	
	}

	public String headerInfo() {
		String info ="Fecha compra \t"+
				"Valor compra\t"+
				"Fecha venta\t"+
				"Valor venta\t"+
				"Dinero Invertido\t"+
				"Ganancia\t"+
				"Rentabilidad\t"+
				"Dinero disponible";
		return info;
	}
	
	public String inversionInfo(){
		DecimalFormat df = new DecimalFormat("0.0000");
		
		String info = compra.getFecha() + "\t" + 
				df.format(compra.getClose()) + "\t" + 
				getVenta().getFecha() + "\t" + 
				df.format(getVenta().getClose()) + "\t" + 
				df.format(getCompra().getClose() * getCantAcciones()) + "\t" +
				df.format(getGanancia())  + "\t" +
				df.format(getRentabilidad()) + "\t" +
				df.format(CuentaBroker.getSingleton().getDineroDisponible())+"\n";
		
		return info;
				
	}
}
