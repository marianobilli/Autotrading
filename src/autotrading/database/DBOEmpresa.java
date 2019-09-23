package autotrading.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import autotrading.Empresa;

public class DBOEmpresa {

	public static void getCotizaciones(Empresa empresa) throws Exception {
		
		Connection conn = Database.getSingleton().getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		String query = null;
		
		stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		query = "SELECT * FROM historicdailyquotes where " +
				"symbol = " + "\"" + empresa.getSimbolo() + "\"";
		rs = stmt.executeQuery(query);
		while (rs.next()) {
			empresa.addCotizacion(rs.getString("date"), 
								rs.getDouble("open"), 
								rs.getDouble("high"), 
								rs.getDouble("low"), 
								rs.getDouble("close"), 
								rs.getInt("volume"),
								rs.getDouble("adjClose") 
								);
		}
	}
}
