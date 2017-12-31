package edu.utdallas.taskExecutorImpl;

import edu.utdallas.blockingFIFO.BlockingFIFO;
import edu.utdallas.blockingFIFO.TaskRunner;
import edu.utdallas.taskExecutor.Task;
import edu.utdallas.taskExecutor.TaskExecutor;

public class TaskExecutorImpl implements TaskExecutor
{
	TaskRunner[] taskpool; // Array for the thread pool.
	private BlockingFIFO FIFOqueue = new BlockingFIFO(100); // Queue size is set to 100.
	
	// Thread Executor Constructor. Takes a thread pool size as an argument.
	// Creates an array for the thread objects and initializes them.
	public TaskExecutorImpl(int poolsize) {
		taskpool = new TaskRunner[poolsize];
		for (int i = 0; i < poolsize; i++) {
			taskpool[i] = new TaskRunner(FIFOqueue);
		}
	}

	@Override
	public void addTask(Task task)
	{
		try{
		FIFOqueue.put(task);
		}
		catch(Exception ex){
			ex.printStackTrace();
			System.out.println(ex);
		}
	}
}