package fileReadWrite;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class readWrite 
{
    private final ReadWriteLock lock;   //定义锁    
    public readWrite()
    {
        lock = new ReentrantReadWriteLock();      
    }

    public static void main(String[] args)
    {
    	readWrite rw = new readWrite();
    	ExecutorService executors = Executors.newFixedThreadPool(5);
    	for(int i = 0; i < 2; i++)
    	{
    		executors.execute(rw.new Reader());
    	}
        executors.execute(rw.new Writer("I love ZhuangQinfa!"));
        for(int i = 0; i < 2; i++)
    	{
    		executors.execute(rw.new Reader());
    	}
        executors.execute(rw.new Writer("I love ZhuangQinfa forever!!!!!!!!!!"));
        executors.execute(rw.new Reader());
        executors.shutdown();
    }

    class Reader implements Runnable
    {
        @Override
        public void run() 
        {
        	lock.readLock().lock();
        	List<String> lines = new ArrayList<>();
			try 
			{
				lines = Files.readAllLines(Paths.get("E:\\fileTest.txt"), StandardCharsets.UTF_8);
			}
			catch (IOException e) 
			{
				e.printStackTrace();
			}  
    		StringBuilder sb = new StringBuilder();  
    		for(String line : lines)
    		{  
    		    sb.append(line);  
    		}  
    		String fromFile = sb.toString();  
    		System.out.println(Thread.currentThread().getName() + " : 读文件:");
    		System.out.println(fromFile); 
    		lock.readLock().unlock();
        }
    }

    class Writer implements Runnable 
    {
    	String content = null;
    	public Writer(String str)
    	{
    		content = str;
    	}
        @Override
        public void run() 
        {
            lock.writeLock().lock();
            try
            {
				Files.write(Paths.get("E:\\fileTest.txt"), content.getBytes());
			} 
            catch (IOException e)
            {
				e.printStackTrace();
			}
            System.out.println(Thread.currentThread().getName() + "写文件：" + content); 
            lock.writeLock().unlock();
        }
    }
}