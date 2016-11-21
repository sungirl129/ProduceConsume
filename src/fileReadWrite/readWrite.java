package fileReadWrite;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class readWrite 
{
    private final ReadWriteLock lock;   //定义锁
    private static int readCount = 0;    //读者的数量

    public readWrite()
    {
        lock = new ReentrantReadWriteLock();      
    }

    public static void main(String[] args)
    {
    	readWrite rw = new readWrite();
    	ExecutorService executors = Executors.newFixedThreadPool(5);
        executors.execute(rw.new Reader());
        executors.execute(rw.new Reader());
        executors.execute(rw.new Writer());
        executors.execute(rw.new Reader());
        executors.shutdown();
    }

    class Reader implements Runnable
    {
        @Override
        public void run() 
        {
        	read();
        	after();
        }

        public void read() 
        {  
        	lock.readLock().lock();
        	readCount++;
        	System.out.println("有1位读者进入,当前共有" + readCount + "位读者");
        }

        public void after() 
        {  
            readCount --;
            System.out.println("有一位读者离开，当前共有" + readCount + "位读者");
            lock.readLock().unlock();
        }

    }

    class Writer implements Runnable 
    {
        @Override
        public void run() 
        {
            lock.writeLock().lock();
            System.out.println("写者正在写");
            lock.writeLock().unlock();
        }
    }
}