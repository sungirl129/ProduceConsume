package produceconsumer;

public class ProduceConsume
{  
    public static void main(String args[])
    {  
        Info info = new Info();  
        Producer produce = new Producer(info) ; 
        Consumer consume = new Consumer(info) ; 
        new Thread(produce).start() ;  
        
        new Thread(consume).start() ;  
    }  
}  


class Info
{ 
    private String key = "key";
    private String value = "value";
    private boolean flag = true;//flag = true������Ҫ����
    
    public synchronized void set(String key, String value)
    {  
        while(!flag)
        {  
            try
            {  
                super.wait() ;  
            }
            catch(InterruptedException e)
            {  
                e.printStackTrace() ;  
            }  
        }  
        this.setKey(key); 
        this.setValue(value);
        flag  = false ; //�ı��־λ����ʾ����ȡ��  
        super.notify();  
    }  
    
    public synchronized void get()
    {  
        while(flag)
        {  
            try
            {  
                super.wait() ;  
            }
            catch(InterruptedException e)
            {  
                e.printStackTrace() ;  
            }  
        }   
        System.out.println(this.getValue() + " : " + this.getValue()) ;  
        flag  = true ;    
        super.notify();  
    }  
    
    private void setKey(String key)
    {  
        this.key = key ;  
    }  
    private void setValue(String value)
    {  
        this.value = value ;  
    }  
    private String getKey()
    {  
        return this.key ;  
    }  
    private String getValue()
    {  
        return this.value ;  
    }  
}


class Producer implements Runnable
{ 
    private Info info = null ;      // ����Info����  
    public Producer(Info info)
    {  
        this.info = info ;  
    }  
    
    public void run()
    {  
        for(int i = 0; i < 20; i++)
        {  
        	String key = "key" + (i + 1);
        	String value = "value" + (i + 1);
        	this.info.set(key, value);
        }  
    }  
}  

//������
class Consumer implements Runnable
{  
    private Info info = null ;  
    public Consumer(Info info)
    {  
        this.info = info ;  
    }  
    public void run()
    {  
        for(int i = 0; i < 20; i++)
        {  
            this.info.get() ;  
        }  
    }  
}  

