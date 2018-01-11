package net.i2finance.socket;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

public class PooledRemoteFileServer {
	/** ���������. */
	protected int maxConnections;

	//�˿�
	protected int listenPort;

	//�����
	protected ServerSocket serverSocket;

	/**
	 * Instantiates a new pooled remote file server.
	 * 
	 * @param aListenPort
	 *            the a listen port
	 * @param maxConnections
	 *            the max connections
	 */
	public PooledRemoteFileServer(int aListenPort, int maxConnections) {
		listenPort = aListenPort;// �����˿�
		this.maxConnections = maxConnections;// ���ͬʱ����
	}

	/**
	 * ��ʼ���أ�ÿ�δ���һ��Runnableʵ����Ȼ�󴴽��̶߳���
	 */
	public void setUpHandlers() {
		for (int i = 0; i < maxConnections; i++) {
			PooledConnectionHandler currentHandler = new PooledConnectionHandler();
			// �߳�������һֱ���Socket���У�����ѯ�ķ�ʽ
			// ����Ƿ����µĿͻ���������������еĻ���ȡ
			// �������޵Ļ�������ȴ�ֱ��������
			new Thread(currentHandler, "Handler" + i).start();
		}
	}

	/**
	 * ���տͻ�������
	 */
	public void acceptConnections() {
		try {
			System.out.println("�����Ҫ�����ˡ�������");
			ServerSocket server = new ServerSocket(listenPort, 5);
			Socket incomingConnection = null;
			while (true) {
				incomingConnection = server.accept();
				handleConnection(incomingConnection);
			}
		} catch (BindException be) {
			System.out.println("");
		} catch (IOException ioe) {
			System.out.println("" + listenPort);
		}
	}

	/**
	 * �ڳ��д���Socket����
	 * 
	 * @param connectionToHandle
	 *            the connection to handle
	 */
	protected void handleConnection(Socket connectionToHandle) {
		PooledConnectionHandler.processRequest(connectionToHandle);
	}

	public static void main(String args[]) {
		PooledRemoteFileServer server = new PooledRemoteFileServer(5500, 3);
		// ��ʼ���̳߳�
		server.setUpHandlers();
		// ��ʼ��ָ���˿ڵȴ�����������
		server.acceptConnections();
	}
}
