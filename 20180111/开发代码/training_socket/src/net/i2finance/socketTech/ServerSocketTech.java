package net.i2finance.socketTech;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketTech {
	public static void main(String[] args) throws IOException {
		System.out.println("�ң�������Ҫ��������ע��...");
		//1.����һ���̶��˿ڷ���
		ServerSocket ss=null;
		Socket s=null;
		try {
			ss = new ServerSocket(10003);
			//2.�����˿ڣ��ȴ��ͻ������ӣ��������Ӻ��ȡ���ͻ���socket
			 s=ss.accept();
			//��ӡ�ͻ��˵�IP
			System.out.println(s.getInetAddress().getHostAddress()+"....has connnected");
			//3.��ȡ�ͻ��˵�����
			InputStream in =s.getInputStream();
			byte[] buff =new byte[1024];
			int count =in.read();
			System.out.println(new String(buff,0,count));
		} catch (IOException e) {
			System.out.println();
		}finally{
			//4.�ر�socket
			s.close();
			ss.close();
		}
	}
}
