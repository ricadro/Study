package net.i2finance.socketAD;

import java.util.concurrent.*;  

/** 
 * ����ִ���� 
 *  
 * @author laobai
 * @since 1.0.0 <p>2015-6-8 ����10:33:09</p> 
 */  
public class ThreadPoolTaskExecutor {  
  
    private ThreadPoolTaskExecutor() {  
  
    }  
  
    private static ExecutorService executor = Executors.newCachedThreadPool(new ThreadFactory() {  
        int count;  
  
        /* ִ����������Ҫ����������̳߳���û���̵߳�ʱ�������øó��򡣶���callable���͵ĵ���ͨ����װ�Ժ�ת��Ϊrunnable */  
        public Thread newThread(Runnable r) {  
            count++;  
            Thread invokeThread = new Thread(r);  
            invokeThread.setName("Courser Thread-" + count);  
            invokeThread.setDaemon(false);// //????????????  
  
            return invokeThread;  
        }  
    });  
  
    public static void invoke(Runnable task, TimeUnit unit, long timeout) throws TimeoutException, RuntimeException {  
        invoke(task, null, unit, timeout);  
    }  
  
    public static <T> T invoke(Runnable task, T result, TimeUnit unit, long timeout) throws TimeoutException,  
            RuntimeException {  
        Future<T> future = executor.submit(task, result);  
        T t = null;  
        try {  
            t = future.get(timeout, unit);  
        } catch (TimeoutException e) {  
            throw new TimeoutException("Thread invoke timeout ...");  
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        }  
        return t;  
    }  
  
    public static <T> T invoke(Callable<T> task, TimeUnit unit, long timeout) throws TimeoutException, RuntimeException {  
        // ���ｫ�����ύ��ִ�����������Ѿ��������������첽�ġ�  
        Future<T> future = executor.submit(task);  
        // System.out.println("Task aready in thread");  
        T t = null;  
        try {  
            /* 
             * ����Ĳ�����ȷ�������Ƿ��Ѿ���ɣ�������������Ժ�  
             * 1)��invoke()�ĵ����̱߳���˵ȴ��������״̬ 
             * 2)���߳̿��Խ������̵߳Ĵ����� 
             */  
            t = future.get(timeout, unit);  
        } catch (TimeoutException e) {  
            throw new TimeoutException("Thread invoke timeout ...");  
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        }  
  
        return t;  
    }  
}  
