import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public interface LLPInterface 
{
    default void     init(int index)              { ; };             // Initialize index state
    default void     always(int index)            { ; };             // Defines variables derived from G. These variables can be viewed as macros
    default void     ensure(int index)            { ; };             // Ensure a certain condition
    default void     advance(int index)           { ; };             // Advance the forbidden index
    default boolean  forbidden(int index)         { return true; };  // Decides whether a index is forbidden
    Runnable         processorThread(int index);                     // What each processor should do in parallel


    default void runAlgo(int numProcessors)
    {
        ExecutorService executor = Executors.newFixedThreadPool(numProcessors);
        for (int i = 0; i < numProcessors; i++) 
        {
            executor.submit(processorThread(i));
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

    default void waitTillSync()
    {
        ;
    }
}