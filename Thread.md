### 线程使用

几种用法

==1.new Thread 直接使用==
```python

Thread thread = new Thread() {
            @Override
            public void run() {
                System.out.println("Thread started!");
            }
        };
        thread.start();

```

==2.new Runnable 放入Thread中使用==
```python
Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("Thread with Runnable started!");
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
```

==3.线程池Executors类==

通过Executors提供四种线程池，newFixedThreadPool、newCachedThreadPool、newSingleThreadExecutor、newScheduledThreadPool。

newFixedThreadPool(int count) 创建一个可重用固定线程数的线程池，以共享的无界队列方式来运行这些线程，如果线程都处于活跃状态，后面提交的任务会等待前面执行完成再执行。

newCachedThreadPool创建一个可缓存线程池,在使用缓存型池时，先查看池中有没有以前创建的线程，如果有，就复用.如果没有，就新建新的线程加入池中，缓存型池子通常用于执行一些生存期很短的异步型任务。

newScheduledThreadPool创建一个定长线程池，支持定时及周期性任务执行。

newSingleThreadExecutor创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。

也可以根据自己的需求定义线程池，

```python
Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("Thread with Runnable started!");
            }
        };

        Executor executor = Executors.newCachedThreadPool();
            executor.execute(runnable);
            executor.execute(runnable);
            executor.execute(runnable);
            
        //自己自定义线程池
        //corePoolSize 核心线程池大小
        //maximumPoolSize  最大线程池大小
        //keepAliveTime 线程最大空闲时间
        //TimeUnit 时间单位
        //workQueue 线程等待队列
        //threadFactory 线程创建工厂
        //handler 拒绝策略
        new ThreadPoolExecutor(1,20,
                50L, TimeUnit.SECONDS,new BlockingQueue<Runnable>,
                threadFactory,handler);

            
```

==4.有回调的线程callable==
```python
Callable<String> callable = new Callable<String>() {
            @Override
            public String call() {
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "Done!";
            }
        };
        System.out.println("---00000----"+System.currentTimeMillis());
        ExecutorService executor = Executors.newCachedThreadPool();
        Future<String> future = executor.submit(callable);
        try {
            String result = future.get();
            System.out.println("result: " + result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("---11111----"+System.currentTimeMillis());
        //这个Future<String> future会等到cannable执行完才会赋值，后面的代码都会在赋值后执行
```
==线程安全==

多个线程对同一个值进行修改时，会出现不可控。

解决办法：

==1.synchronized关键字，修饰后的方法在执行时，会加锁控制==
用法如下：
```python

    //synchronized修饰方法
    private synchronized void count() {
        x++;
    }
    //synchronized代码块，monitor可以是任何对象，不同的对象相互不会干扰
    private final Object monitor = new Object();
    private void setName(String newName) {
        synchronized (monitor) {
            name = newName;
        }
    }

```
==1.ReentrantReadWriteLock 读写锁==

只要没有程序进入写锁，读锁就可以进入；
读锁和写锁都没有程序进去，写锁才可以进入。

用法如下：
```python
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
    private ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();
    
    //赋值时写锁控制
     private void count(int newValue) {
        writeLock.lock();
        try {
            x = newValue;
            y = newValue;
        } finally {
            writeLock.unlock();
        }
    }
    
    //取值时读锁控制
    private void print() {
        readLock.lock();
        try {
            System.out.println("values: " + x + ", " + y);
        } finally {
            readLock.unlock();
        }
    }
    
```

==线程操作==
1.线程中断

interrupt() 中断线程，且"中断标记"设置为true。

若线程在阻塞状态时，调用了它的interrupt()方法，那么它的“中断状态”会被清除并且会收到一个InterruptedException异常。 
```python
        Thread thread = new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 1_000_000; i++) {
                    if (Thread.interrupted()) {
                        // 收尾
                        return;
                    }
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        // 收尾
                        return;
                    }
                    System.out.println("number: " + i);
                }
            }
        };
        thread.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread.interrupt();
        
        
        
        //结果打印
        number: 0
        number: 1
        number: 2
```

==线程等待==

wait() 线程等待，可以notifyAll()唤醒
wait() 会释放锁，让出CPU资源
notifyAll() 唤醒所有的线程
notify() 唤醒一个线程，取决于操作系统对多线程管理的实现
```python
private String sharedString;

    private synchronized void initString() {
        sharedString = "rengwuxian";
        notifyAll();
        notify();
    }

    private synchronized void printString() {
        while (sharedString == null) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("String: " + sharedString);
    }
    
    //thread1 读取数据
    //thread2 存储数据
    @Override
    public void runTest() {
        final Thread thread1 = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("thread1--"+System.currentTimeMillis());
                printString();
            }
        };
        thread1.start();
        
        
        Thread thread2 = new Thread() {
            @Override
            public void run() {
                //用了yield方法后，该线程就会把CPU时间让掉，让其他或者自己的线程执行（也就是说本来自己执行的，现在大家公平竞争）
                Thread.yield();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("thread2--"+System.currentTimeMillis());
                initString();
            }
        };
        thread2.start();
    }

```

==自定义handler==

Handler：用来发送消息：sendMessage等多个方法，并实现handleMessage()方法处理回调（还可以使用Message或Handler的Callback进行回调处理，具体可以看看源码）。

Message：消息实体，发送的消息即为Message类型。

MessageQueue：消息队列，用于存储消息。发送消息时，消息入队列，然后Looper会从这个MessageQueen取出消息进行处理。

Looper：与线程绑定，不仅仅局限于主线程，绑定的线程用来处理消息。loop()方法是一个死循环，一直从MessageQueen里取出消息进行处理。

Looper.loop()一直无限循环为什么不会造成ANR
因为所有的事件都是由looper去驱动的。

一般线程创建时没有自己的消息列队，消息处理时就在主线程中完成，如果线程中使用Looper.prepare()和Looper.loop()创建了消息队列就可以让消息处理在该线程中完成，这样的话，那子线程就会一直处于活跃状态，不会随着run方法执行完结束，Looper.loop()之后的代码不会执行。
