package net.i2finance.Thread;

public class TestRunnableCreate implements Runnable{
	private int num =100;
	Object o =new Object();
	@Override
	public void run() {
		/*while(true){
			   if(num >0){
				   System.out.println(Thread.currentThread().getName()+"sale."+num--);
			   }
		   }*/
		while(true){
			/*//ͬ����ǰ��
			//ͬ�������
			synchronized(o){
				sale();
			}*/
			sale();
		   }
		
	}
	private synchronized void sale() {
		if(num >0){
			System.out.println(Thread.currentThread().getName()+"sale."+num--+"this:"+this);
		}
	}
}
