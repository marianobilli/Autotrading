package autotrading.tools;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;


public class SourceFiles {

		public static final Charset CHARSET = Charset.forName("UTF8");
		public ArrayList<Path> filePaths = new ArrayList<>();
		
		public SourceFiles(String sourceFolder){
			Path sourcePath = Paths.get(sourceFolder);
			MyFileVisitor visitor = new MyFileVisitor(filePaths);
			try {
				Files.walkFileTree(sourcePath, visitor);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			
			Iterator<Path> i = filePaths.iterator();
			while( i.hasNext() )
				System.out.println("File found: " + i.next().getFileName());
		}
		
		
}
