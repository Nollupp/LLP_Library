package LLP_Algos;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class LLPInterface 
{
    int GlobalState[];
    CyclicBarrier barrier;
    boolean endAlgo;

    void     init(int index)              { ; };             // Initialize index state
    void     always(int index)            { ; };             // Defines variables derived from G. These variables can be viewed as macros
    void     ensure(int index)            { ; };             // Ensure a certain condition
    void     advance(int index)           { ; };             // Advance the forbidden index
    boolean  forbidden(int index)         { return true; };  // Decides whether a index is forbidden

    void     printGlobalState()           { ; };             // Print the global state out to the console
    Runnable processorThread(int index,
                             AtomicBoolean endAlgo)  { return () -> {}; };  // What each processor should do in parallel

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
        barrier = new CyclicBarrier(numProcessors);
        AtomicBoolean endAlgo = new AtomicBoolean(false);

        ExecutorService executor = Executors.newFixedThreadPool(numProcessors);
        for (int i = 0; i < numProcessors; i++) 
        {
            executor.submit(processorThread(i, endAlgo));
        }

        executor.shutdown();

        try 
        {
            if (!executor.awaitTermination(800, TimeUnit.MILLISECONDS)) 
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