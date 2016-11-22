package file;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class file {
	public static void main(String[] args) throws IOException
	{
		//Java 7 例子  
		// Files.readAllBytes默认以UTF-8编码读入文件，故文件的编码如果不是UTF-8，那么中文内容会出现乱字符  
		System.out.println(new String(Files.readAllBytes(Paths.get("E:\\fileTest.txt"))));  
		 // Java 8例子  
		List<String> lines = Files.readAllLines(Paths.get("E:\\fileTest.txt"), StandardCharsets.UTF_8);  
		StringBuilder sb = new StringBuilder();  
		for(String line : lines){  
		    sb.append(line);  
		}  
		String fromFile = sb.toString();  
		System.out.println(fromFile);  
	}	
}


