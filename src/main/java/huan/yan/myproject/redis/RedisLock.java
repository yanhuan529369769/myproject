package huan.yan.myproject.redis;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import redis.clients.jedis.Jedis;

import java.util.concurrent.TimeUnit;

public class RedisLock {
    private static Jedis jedis;  //非切片额客户端连接
    private static Config config;
    private static RedissonClient redisson;

    static {
        //Redis缓存服务器
        jedis = new Jedis("127.0.0.1", 6379);
        //权限认证
        //jedis.auth();
        config = new Config();
        config.useSingleServer().setAddress("127.0.0.1:6379");

        redisson = Redisson.create(config);
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(()->{
            Lock("myLock");
            System.out.println("thread1.start");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("thread1.end");
            unLock("myLock");
        });
        Thread thread2 = new Thread(()->{
            Lock("myLock");
            System.out.println("thread2.start");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("thread2.end");
            unLock("myLock");
        });
        Thread thread3 = new Thread(()->{
            Lock("myLock");
            System.out.println("thread3.start");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("thread3.end");
            unLock("myLock");
        });

//        thread1.start();
//        thread2.start();
//        thread3.start();
//        thread1.join();
//        thread2.join();
//        thread3.join();
        jedis.setnx("myLock","aabb");

        System.out.println(jedis.get("myLock"));


    }


    public static void Lock(String lockName){

        RLock rlock = redisson.getLock(lockName);

        rlock.lock(10, TimeUnit.SECONDS);
    }

    public static void unLock(String lockName){
        RLock rlock = redisson.getLock(lockName);
        rlock.unlock();
    }




}
