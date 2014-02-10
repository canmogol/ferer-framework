package com.fererlab.test.forkjoin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * acm 11/23/12 1:58 PM
 */
public class ForkJoinTest {

    public static void main(String[] args) {
        new ForkJoinTest();
    }

    public ForkJoinTest() {
        runTest();
    }

    private void runTest() {
        forkJoinTest1();
    }

    private void forkJoinTest1() {
        ForkJoinPool pool = new ForkJoinPool(32767);

        List<MyTask> myTasks = new ArrayList<MyTask>();
        for (int i = 0; i < 20; i++) {
            MyTask myTask = new MyTask("" + i + (i < 10 ? " " : ""));
            myTasks.add(myTask);
            pool.submit(myTask);
        }

        System.out.println("myTasks: " + myTasks + " pool.getActiveThreadCount(): " + pool.getActiveThreadCount());

        for (int i = 0; i < 10; i++) {
            myTasks.get(i).join();
        }

        System.out.println("end!");

    }

}

class MyTask extends RecursiveTask<Integer> {

    private String name;

    public MyTask(String name) {
        this.name = name;
    }

    @Override
    protected Integer compute() {
        int wait = new Random().nextInt(10);
        wait = ((wait < 0 ? wait * -1 : wait) == 0 ? 1 : wait) + 10;
        System.out.println("[" + name + "][" + (Thread.currentThread().getName() + ":" + Thread.currentThread().getId() + "]  ") + "will wait: " + wait + " seconds, ");
        try {
            Thread.sleep(wait * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("[" + name + "][" + (Thread.currentThread().getName() + ":" + Thread.currentThread().getId() + "]  ") + "done");
        return wait;
    }
}
