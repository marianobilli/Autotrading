package autotrading.demo;

import autotrading.Companies;
import autotrading.CuentaBroker;
import autotrading.Trade;
import autotrading.tools.Archivo;

public class Demo {

    public static void main(String[] args) throws Exception  {
    	//Empresa irsa = new Empresa("IRSA.BA");
    	//DBOEmpresa.getCotizaciones(irsa);
    	//Database.newSingleton();
    	
    	// Set up
    	
    	Trade trade = new Trade();
    	Companies companies = Companies.readFromDirectory("cotizaciones");

    	System.out.println("Companies read: " + companies.size());
    	System.out.println();

    	System.out.println(companies.get(0).getSimbolo());
    	CuentaBroker.getSingleton().setDineroDisponible(10000.0);
    	System.out.println("Dinero al comienzo:"+ CuentaBroker.getSingleton().getDineroDisponible());    	
    	trade.simular(companies.get(0), companies.get(0).getCotizaciones().getFirst().getFecha());
    	System.out.println("Dinero al final:"+ CuentaBroker.getSingleton().getDineroDisponible());
    	System.out.println();

    	Archivo.cerrar();    	
    }

}