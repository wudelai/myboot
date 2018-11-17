package com.wdl.myboot;

import org.junit.Test;

import java.util.*;
import java.util.concurrent.*;

public class ExecutorServiceTest {
    //创建一个线程池、
    ExecutorService excutorService = Executors.newFixedThreadPool(10);

    //提交十个任务
    @Test
    public void testTask() throws InterruptedException, ExecutionException {
        //测试 三种 并发线程 的执行结果
//        testTask1();
//        ExecutorServiceTest ss = ExecutorServiceTest :: new;
        testTask2();

//        testTask3();
    }
    public void testTask1() throws InterruptedException, ExecutionException {
        //创建存储执行结果的容器
        List<Future<Map<String,String>>> results = new ArrayList<>();

        long start = System.currentTimeMillis();
        for (int i = 0 ; i< 10 ; i++){
           Future<Map<String,String>> result = excutorService.submit(() -> {
               Map<String,String> map = new HashMap<>();
               int sleepTime = new Random().nextInt(1000);
               Thread.sleep(sleepTime);
               map.put("msg","线程睡了"+sleepTime+" ms");
               return map;
           });
            //将task 添加进任务队列
            results.add(result);
        }
        //输出结果
        for (int j = 0 ; j< 10 ; j++){
            //获取包含结果胡future 对象
            Future<Map<String,String>> future = results.get(j);
            //从future 中取出结果，（若尚未返回结果，则get 方法被阻塞，直到返回结果）
            Map<String,String> result = future.get();
            System.out.println("----:"+result);
        }
        long end = System.currentTimeMillis();
        System.out.println("----:" +(end - start)+ " ms");
    }

    public void testTask2() throws InterruptedException, ExecutionException {
//        for (int i = 0 ; i< 10 ; i++){
//            System.out.println("----:"+System.currentTimeMillis());
//        }
        ExecutorService excutorService = Executors.newFixedThreadPool(5);
        //创建存储任务的容器
        List<Callable<Map<String,String>>> tasks = new ArrayList<>();

        long start = System.currentTimeMillis();
        for (int i = 0 ; i< 10 ; i++){
            Callable<Map<String,String>> task = () -> {
                Map<String,String> map = new HashMap<>();
                int sleepTime = new Random().nextInt(1000);
                Thread.sleep(sleepTime);
                map.put("msg","线程睡了"+sleepTime+" ms");
                return map;
            };
//            excutorService.submit(task);
            //将task 添加进任务队列
            tasks.add(task);
        }
        //获取 10 个任务胡返回结果
        List<Future<Map<String,String>>> results = excutorService.invokeAll(tasks,18,TimeUnit.SECONDS);
        System.out.println("excutorService:"+ excutorService);
        excutorService.shutdown();
        System.out.println("线程池关闭");
//        System.out.println(excutorService);
        //输出结果
        for (int j = 0 ; j< results.size() ; j++){
            //获取包含结果胡future 对象
            Future<Map<String,String>> future = results.get(j);
            //从future 中取出结果，（若尚未返回结果，则get 方法被阻塞，直到返回结果）
            Map<String,String> result = null;
            try {
                result = future.get();
            }catch (CancellationException ignore) {
                System.out.println("----任务超时：CancellationException:");
            } catch (ExecutionException ignore) {
                System.out.println("----任务异常：ExecutionException:");
            } catch (Exception e){
                System.out.println("系统异常");
            }
            System.out.println("----:"+result+System.currentTimeMillis());
        }
        long end = System.currentTimeMillis();
        System.out.println("----:" +(end - start)+ " ms");
        //Thread.sleep(1000);
        excutorService.isShutdown();
        while(!excutorService.isTerminated()){
            Thread.sleep(100);
            System.out.println(excutorService);
        }
        System.out.println(excutorService);

    }

    /**
     * 这个方法不行，直接阻塞了
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public void testTask3() throws InterruptedException, ExecutionException {
        //创建存储任务的容器
        final BlockingQueue<Future<Map<String, String>>> queue = new LinkedBlockingDeque<>(10);
        final CompletionService<Map<String, String>> completionService = new ExecutorCompletionService<>(excutorService,queue);
        long start = System.currentTimeMillis();
        for (int i = 0 ; i< 10 ; i++){
            excutorService.submit(new Callable<Map<String, String>>() {
                @Override
                public Map<String, String> call() throws Exception {
                    Map<String,String> map = new HashMap<>();
                    int sleepTime = new Random().nextInt(1000);
                    Thread.sleep(sleepTime);
                    map.put("msg","线程睡了"+sleepTime+" ms");
                    return map;
                }
            }) ;
        }
        //输出结果
        for (int j = 0 ; j< 10 ; j++){
            //获取包含返回结果的future 对象，若整个阻塞队列中还没有一条线程返回结果，那么调用 take 将会被阻塞，当然你可以调用poll,不会被阻塞，若没有结果会返回null,poll he take 返回正确结果后，会将该结果从队列中删除
            Future<Map<String,String>> future = completionService.take();
            //从future 中取出结果,这里存储胡future 已经拥有执行结果，get 不会被阻塞
            Map<String,String> result = future.get();
            System.out.println("----:"+result);
        }
        long end = System.currentTimeMillis();
        System.out.println("----:" +(end - start)+ " ms");
    }

}
