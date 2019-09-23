package autotrading.tools;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;

public class MyFileVisitor extends SimpleFileVisitor<Path> {

	private ArrayList<Path> filePaths;
		
	public MyFileVisitor(ArrayList<Path> filePaths) {
		this.filePaths = filePaths;
	}

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
			throws IOException {
		if(attrs.isRegularFile())
			filePaths.add(file);
		return FileVisitResult.CONTINUE;
	}
	
	
	
}
