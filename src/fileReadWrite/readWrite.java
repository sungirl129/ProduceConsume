package fileReadWrite;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class readWrite 
{
    private final ReadWriteLock lock;   //������
    private static int readCount = 0;    //���ߵ�����

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
        	System.out.println("��1λ���߽���,��ǰ����" + readCount + "λ����");
        }

        public void after() 
        {  
            readCount --;
            System.out.println("��һλ�����뿪����ǰ����" + readCount + "λ����");
            lock.readLock().unlock();
        }

    }

    class Writer implements Runnable 
    {
        @Override
        public void run() 
        {
            lock.writeLock().lock();
            System.out.println("д������д");
            lock.writeLock().unlock();
        }
    }
}