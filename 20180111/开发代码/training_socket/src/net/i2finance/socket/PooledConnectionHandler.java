package net.i2finance.socket;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class PooledConnectionHandler implements Runnable {
	/** The client connection of Socket. */
	protected Socket connection;

	/** The request pool. */
	protected static List pool = new LinkedList();

	/**
	 * Instantiates a new pooled connection handler.
	 */
	public PooledConnectionHandler() {
	}

	public void run() {
		while (true) {
			// ��Ϊ�����ж���߳�ͬʱȥPool��ȡSocket���д���
			// ��������������ͬ������ֹͬһ�����󱻶�δ���
			synchronized (pool) {
				while (pool.isEmpty()) {
					try {
						pool.wait();// û����������ȴ�
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				// �ӳ���ȡ��һ��Socket��׼�����д���
				connection = (Socket) pool.remove(0);
			}
			// ȡ��Socket������Ҫͬ���ˣ���Ϊ��ʱ��Connection�Ƕ���
			// �����ԣ����߳��ڲ��Լ��������漰������Դ�ķ���
			handleConnection();
		}
	}

	/**
	 * Process request, append Socket to pool and notify all waitting thread
	 * 
	 * @param requestToHandle
	 *            the request to handle
	 */
	public static void processRequest(Socket requestToHandle) {
		// ��Ϊ�п�����������������ʱ������һ���߳�
		// ���ڴӳ���ȡSocket������������Ҫͬ��һ��
		synchronized (pool) {
			// �����Կͻ��˵�������ӵ��������ĩβ
			pool.add(pool.size(), requestToHandle);
			// ֪ͨ�������ڵȴ����߳���������������
			// ��ʱ���д���wait״̬���߳̽�������
			pool.notifyAll();
		}
	}

	/**
	 * Handle connection.
	 */
	public void handleConnection() {
		try {
			PrintWriter streamWriter = new PrintWriter(System.out);
			BufferedReader streamReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			// String fileToRead = streamReader.readLine();
			// System.out.println(fileToRead);
			// BufferedReader fileReader = new BufferedReader(new
			// FileReader(fileToRead));
			String line = null;
			while ((line = streamReader.readLine()) != null) {
				streamWriter.println(line);
				streamWriter.flush();
			}
			// fileReader.close();
			streamWriter.close();
			streamReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("");
		} catch (IOException e) {
			System.out.println("" + e);
		}
	}

}
