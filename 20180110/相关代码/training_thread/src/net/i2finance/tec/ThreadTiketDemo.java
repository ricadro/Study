package net.i2finance.tec;

public class ThreadTiketDemo extends Thread{
	//��100��Ʊ���ĸ�����ȥ��
	private int num =100;
	Object o =new Object();
	public void run(){
		while(true){
			synchronized (o) {
				sale();
			}
		}
	}
	private void sale() {
		if(num>0){
			System.out.println(Thread.currentThread().getName()+"num:"+ num--);
		}
	}
}
