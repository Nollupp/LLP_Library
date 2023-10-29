package LLP_Algos;

import java.util.Arrays;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class LLPInterface 
{
    // LLP Parallel variables
    int GlobalState[];
    CyclicBarrier barrier;
    boolean forbiddenIndexExists;

    // LLP abstract functions (To be defined by implementations that use this library)

    public abstract void init(int index);           // Initialize index state
    public abstract void always(int index);         // Defines variables derived from G. These variables can be viewed as macros
    public abstract void ensure(int index);         // Ensure a certain condition
    public abstract void advance(int index);        // Advance the forbidden index
    public abstract boolean forbidden(int index);   // Decides whether a index is forbidden
    public abstract Runnable processorThread(int index, AtomicBoolean forbiddenIndexExists);  // What each processor should do in parallel
    
    // LLP parallel runtime functions

    public void printGlobalState()  // Print the global state out to the console
    { 
        System.out.println(Arrays.toString(this.GlobalState)); 
    }; 

    void waitForThreadSync()
    {
        try 
        {
            barrier.await(); // Wait for other threads
        } 
        catch (InterruptedException | BrokenBarrierException e) 
        {
            e.printStackTrace();
        }
    }

    void runAlgo(int numProcessors)
    {
        // Java uses multiple cores of CPU for actual parallelism:
        barrier = new CyclicBarrier(numProcessors);
        AtomicBoolean forbiddenIndexExists = new AtomicBoolean(true);

        ExecutorService executor = Executors.newFixedThreadPool(numProcessors);
        for (int i = 0; i < numProcessors; i++) 
        {
            executor.submit(processorThread(i, forbiddenIndexExists));
        }

        executor.shutdown();

        try 
        {
            if (!executor.awaitTermination(2500, TimeUnit.MILLISECONDS)) 
            {
                System.out.println("Tasks did not finish in the given time!");
            } 
            else 
            {
                System.out.println("All tasks completed.");
            }
        } 
        catch (InterruptedException e) 
        {
            System.err.println("awaitTermination was interrupted: " + e.getMessage());
            Thread.currentThread().interrupt(); // Restore interrupted status
        }

    }
}