package edu.utdallas.blockingFIFO;

import edu.utdallas.taskExecutor.Task;

public class BlockingFIFO {
	private Task[] buffer;
	private int nextin, nextout;
	private int count;
	private int queuesize;
	private Object notfull, notempty; // Monitors used for synchronization
	
	// FIFOqueue Constructor. Takes queue size as an argument. Initializes starting variables and monitors.
	public BlockingFIFO(int size) {
		buffer = new Task[size];
		queuesize = size;
		count = 0;
		nextin = 0;
		nextout = 0;
		notfull = new Object();
		notempty = new Object();
	}
// Method to add an item to the queue. Takes a task object and if the queue is not full, adds it.
	public void put(Task task) 
	{
		
		// Since synchronized methods were not an option we decided to use synchronized blocks to the monitor
		// objects. One thread is allowed to enter the while loop, and will block on the wait command of the
		// monitor. Should a thread release from the wait, but go to sleep before moving the next 
		// synchronized block, it will still have to test the loop condition again before moving on to the
		// next synchronized block. This prevents a race condition occurring.
		synchronized (notfull) {
		while (count == queuesize) {
			try {
				notfull.wait(); // Buffer is full, wait for take
			} catch (InterruptedException e) {}
		}
		}
		// A thread that gets free from the full test above will fall through into this section. It will
		// only allow a single thread to be active while it adds an item to the list
			synchronized(notempty) {
				  buffer[nextin] = task;
				  nextin = (nextin + 1) % queuesize;
				  count++;
				  notempty.notify(); // Signal waiting take threads
	  		}
		
	}

	public Task take() 
	{
		//These two blocks follow the same functionality as the ones above.
		synchronized (notempty) {
		while (count == 0) {
				try {
					notempty.wait(); // Buffer is empty, wait for put
				} catch (InterruptedException e) {
					
				} 
		}
		}
	
			synchronized(notfull) {
				Task result = buffer[nextout];
				nextout = (nextout + 1) % queuesize;
				count--;
				notfull.notify(); // Signal waiting put threads
				return result;
			}
		}
	}
