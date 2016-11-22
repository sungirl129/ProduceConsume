package file;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class file {
	public static void main(String[] args) throws IOException
	{
		//Java 7 ����  
		// Files.readAllBytesĬ����UTF-8��������ļ������ļ��ı����������UTF-8����ô�������ݻ�������ַ�  
		System.out.println(new String(Files.readAllBytes(Paths.get("E:\\fileTest.txt"))));  
		 // Java 8����  
		List<String> lines = Files.readAllLines(Paths.get("E:\\fileTest.txt"), StandardCharsets.UTF_8);  
		StringBuilder sb = new StringBuilder();  
		for(String line : lines){  
		    sb.append(line);  
		}  
		String fromFile = sb.toString();  
		System.out.println(fromFile);  
	}	
}


