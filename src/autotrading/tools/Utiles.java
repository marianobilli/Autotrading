package autotrading.tools;
import java.text.SimpleDateFormat;
import java.util.Date;

	
public class Utiles {

		public static String getString(Date fecha){
			return (new SimpleDateFormat("yyyy-MM-dd").format(fecha));
		}
}
