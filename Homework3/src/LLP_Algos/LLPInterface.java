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
    public abstract boolean forbidden(int index);   /* Decides whether a index is forbidden. If not used in LLP algo,
                                                       the return value MUST be false. */

    // LLP parallel runtime functions

    private Runnable processorThread(int index, AtomicBoolean forbiddenIndexExists)
    {
        return () -> {  // This is what each processor should do in parallel

            this.init(index);
            
            this.ensure(index);

            while (forbiddenIndexExists.get())    
            { 
                if (Thread.currentThread().isInterrupted()) {
                    // Clean up if necessary and then:
                    return;  // or return;
                }
    
                waitForThreadSync(index); // Wait for every processor 

                forbiddenIndexExists.set(false); // Set algorithm to end after this superstep

                waitForThreadSync(index); // Wait for every processor

                this.always(index);         // Reevaluate any macros
                    
                if (this.forbidden(index))  // Check if the thread has a forbidden index
                {
                    forbiddenIndexExists.set(true);  // If there is a forbidden index, algo must not end yet
                    this.advance(index);            // Advance the forbidden index
                }
                
                waitForThreadSync(index);  // Wait for every processor
            }
        };
    }

    void waitForThreadSync(int index)
    {
        try 
        {
            barrier.await();
        } 
        catch (InterruptedException e) 
        {
            Thread.currentThread().interrupt(); // Restore the interrupt status
            return; // Return from the method to let the thread finish
        }
        catch (BrokenBarrierException e) 
        {
            ;
        }
    }

    public boolean runAlgo(int globalStateSize)
    {
        // Allocate memory for the global state:
        this.GlobalState   = new int[globalStateSize];
        
        // Java uses multiple cores of CPU for actual parallelism:
        barrier = new CyclicBarrier(globalStateSize);
        AtomicBoolean forbiddenIndexExists = new AtomicBoolean(true);

        ExecutorService executor = Executors.newFixedThreadPool(globalStateSize);
        for (int i = 0; i < globalStateSize; i++) 
        {
            executor.submit(processorThread(i, forbiddenIndexExists));
        }

        executor.shutdown();

        try 
        {
            if (!executor.awaitTermination(1000, TimeUnit.MILLISECONDS)) 
            {
                System.out.println("Tasks did not finish in the given time!");
                executor.shutdownNow();
                return false;
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

        return true;
    }

    public void printGlobalState()  // Print the global state out to the console
    { 
        System.out.println(Arrays.toString(this.GlobalState)); 
    }; 

}