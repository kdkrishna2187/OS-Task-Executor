package edu.utdallas.blockingFIFO;

import edu.utdallas.taskExecutor.Task;
import edu.utdallas.blockingFIFO.BlockingFIFO;

public class TaskRunner implements Runnable {
	BlockingFIFO FIFOqueue;
	
	public TaskRunner(BlockingFIFO queueref) {
		FIFOqueue = queueref;
		Thread taskthread = new Thread(this);
		taskthread.start();
	}
	
	@Override
	// Run function. Attempts to grab a task form the FIFOqueue. Based on the condition of the FIFOqueue
	// it will either take its task and run or be blocked (see BlockingFIFO.java). If an error occurs
	// within the task that throws an exception it will attempt to display any accompanying message.
	public void run() {
	    while(true) {
	        Task queueTask = FIFOqueue.take();
	        
	        try {
	            queueTask.execute();
	        }
	        catch(Throwable th) {
	        	System.out.println(th.getMessage());
	        }
	    }
	}

}