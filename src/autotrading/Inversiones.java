package autotrading;

import java.util.ArrayList;
import java.util.List;

public class Inversiones extends ArrayList<Inversion> {

	private static final long serialVersionUID = -5141444598508208883L;
	private List<Inversion> inversiones;
	public Inversion ultima;
	
	public boolean addInversion(Inversion inversion){
		return inversiones.add(inversion);		
	}

	public Inversiones getInvercionesFinalizadas(){
		Inversiones cerradas = new Inversiones();
		for(Inversion i:this)
			if(i.isFinalizada())
				cerradas.add(i);
		return cerradas;	
		}
	
	public Inversiones getInversionesAbiertas(){
		Inversiones abiertas = new Inversiones();
		for(Inversion i:this)
			if(!i.isFinalizada())
				abiertas.add(i);
		return abiertas;
	}

	public int getCantAcciones() {
		Inversiones abiertas = getInversionesAbiertas();
		int totalAcciones = 0;
		for(Inversion i:abiertas){
			totalAcciones += (i.getCantAcciones());
		}
		return totalAcciones;
	}

	public Inversiones getInversionesConGanancia(){
		Inversiones finalizadas = getInvercionesFinalizadas();
		Inversiones conGanancia = new Inversiones();
		Inversion actual = null;
		for(Inversion i:finalizadas)
			if(i.getGanancia() > 0 )
				conGanancia.add(actual);
		return conGanancia;
	}
	
	public Inversiones getInversionesConPerdida(){
		Inversiones finalizadas = getInvercionesFinalizadas();
		Inversiones conPerdida = new Inversiones();
		Inversion actual = null;
		for(Inversion i:finalizadas)
			if(i.getGanancia() < 0 )
				conPerdida.add(actual);
		return conPerdida;
	}
	
}
