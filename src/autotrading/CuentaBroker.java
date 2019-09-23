package autotrading;

public class CuentaBroker {
	
	private static CuentaBroker singleton = new CuentaBroker();
	static {
		singleton.addDinero(2000);
	}
	
	private double dineroDisponible;
	private double comision = 1.5;
	
	public void setDineroDisponible(Double dineroDisponible){
		this.dineroDisponible = dineroDisponible;
	}
	
	public double getComision() {
		return comision;
	}

	public static CuentaBroker getSingleton() {
		if(singleton == null)
			setSingleton();
		return singleton;
	}

	public double getDineroDisponible() {
		return this.dineroDisponible;
	}
	
	private static void setSingleton(){
		singleton = new CuentaBroker();
	}
	
	public CuentaBroker(){
		dineroDisponible = 0;
	}
	
	public void addDinero(Double importe){
		dineroDisponible += importe;
	}

	public void addDinero(int importe) {
		dineroDisponible += importe;
	}

	public void sacarDinero(double d) {
		dineroDisponible -= d;
	}

	public boolean hayDineroDisponible() {
		if(dineroDisponible > 100)
			return true;
		return false;
	}

	public double getDineroParaCompra() {
			return getDineroDisponible();
	}

}
