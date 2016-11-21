package threadPool;

import java.util.Stack;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class threadPool
{
    public static void main(String[] args)
    {
    	CFood food = new CFood();
        ExecutorService executorService = Executors.newCachedThreadPool();
        for(int i = 0; i < 10; i++)
        {
        	executorService.execute(new Producer(food,i));
        	 executorService.execute(new Consumer(food));
        }
        executorService.shutdown();
    }
}

class CFood
{
	private Stack<String> stack = new Stack<>(); 
	private int count = 0;
	public synchronized void produceFood(String food)
	{
		if(count >= 10)
		{
			try
			{
				super.wait();
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
		produce(food);
		super.notifyAll();
	}
	
	public synchronized String consumeFood()
	{
		String food = null;
		if(count <= 0)
		{
			try 
			{
				super.wait();
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
		food = consume();
		super.notifyAll();
		return food;	
	}
	
	private void produce(String food)
	{
		stack.add(food);
		count++;
	}
	
	private String consume()
	{
		String food = null;
//		if(!stack.empty())
//		{
			food = stack.pop();
//		}
		count--;
		return food;
	}
}

class Producer implements Runnable
{
	private CFood food = null;
	private int index;
	public String strFood[] = {"a","b","c","d","e","1","2","3","4","5","6","7","8","9","10","11",
			"12","13","14","15"};
	
	public Producer(CFood food,int i)
	{
		this.food = food;
		index = i;
	}
    public void run()
    {
        System.out.println("生产者线程：" + Thread.currentThread().getName());
        food.produceFood("food" + strFood[index]);
    }
}

class Consumer implements Runnable
{
	private CFood food = null;
	public Consumer(CFood food)
	{
		this.food = food;
	}
	
    public void run()
    {
        System.out.println("消费者线程：" + Thread.currentThread().getName());
        System.out.println(food.consumeFood());
    }
}