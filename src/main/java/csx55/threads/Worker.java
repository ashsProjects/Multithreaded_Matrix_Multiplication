package csx55.threads;

public class Worker extends Thread {
    private TaskQueue queue;

    public Worker(TaskQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Task task = queue.poll();
                if (task != null) {
                    task.multiply();
                    MatrixThreads.latch.countDown();
                }
            } catch (Exception e) {
                continue;
            }
        }
    }
}
