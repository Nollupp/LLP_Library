import java.util.Map;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.BrokenBarrierException;

class StableMarriageLLP implements LLPInterface 
{
    int GlobalState[];

    // Macros for the "always" call
    int currentWomen[];

    CyclicBarrier barrier;
    boolean forbiddenLeft;

    // Input to algo
    Map<Integer, List<Integer>> menPreferences;
    Map<Integer, List<Integer>> womenPreferences;

    // Create the stable marriage LLP algo 
    public StableMarriageLLP(Map<Integer, List<Integer>> menPreferences,
                             Map<Integer, List<Integer>> womenPreferences)
    {
        // Copy Stable marriage parameters into LLP object
        this.menPreferences         = menPreferences;
        this.womenPreferences       = womenPreferences;

        // Create a blank global state and blank macro state
        this.GlobalState   = new int[menPreferences.size()];
        this.currentWomen  = new int[menPreferences.size()];

        this.barrier = new CyclicBarrier(menPreferences.size());
        forbiddenLeft = true;
    }

    // ----------------------- LLP functionality -------------------------------------------------------

    @Override
    public void init(int currentMan)
    {
        this.GlobalState[currentMan] = 0;
    }

    @Override
    public void always(int currentMan)
    {
        currentWomen[currentMan] = menPreferences.get(currentMan).get(GlobalState[currentMan]);
    }

    @Override
    public boolean forbidden(int currentMan)  // This function decides whether a index is forbidden
    {
        int currentWoman = currentWomen[currentMan];

        for (int otherMan = 0; otherMan < menPreferences.size(); otherMan++) // Iterate over mens list: 
        {
            if (otherMan == currentMan) // No need to check the current man against himself
            {
                continue;
            }

            for (int woman = 0; woman <= GlobalState[otherMan]; woman++) // For each other man, iterate over their preference 
            {                                                           // list of woman, up to their current preference
                // For each preference:
                //System.out.println("Otherman:");
                if (menPreferences.get(otherMan).get(woman) != currentWoman) // 1) check if that preference is equal to 
                {                                                            //    the current mans preferred current woman
                    continue;
                }

                if (womenPreferences.get(currentWoman).indexOf(otherMan)      // 2)	Check if the current woman prefers 
                    > womenPreferences.get(currentWoman).indexOf(currentMan)) //    other man over current man
                {
                    continue;
                }

                // If we have made it this far, both conditions are true, and we have a forbidden state.
                return true;
            }
        }

        return false;
    }

    @Override
    public void advance(int currentMan) 
    {
        // Advance the forbidden index
        GlobalState[currentMan]++;
    }

    @Override
    public Runnable processorThread(int man)
    {
        return () -> {
            // This is what each processor should do in parallel
            this.init(man);
            int tryCount = 3;
            while (tryCount > 0)    
            {
                this.always(man);
                if (this.forbidden(man))
                {
                    this.advance(man);
                }

                try {
                    barrier.await(); // Wait for other threads
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }

                tryCount--;
            }
        };
    }
}

public class App {
    public static void main(String[] args) throws Exception 
    {
        Map<Integer, List<Integer>> menPreferences    = new HashMap<>();
        Map<Integer, List<Integer>> womenPreferences  = new HashMap<>();
        
        menPreferences.put(0, Arrays.asList(1, 2, 0));
        menPreferences.put(1, Arrays.asList(1, 0, 2));
        menPreferences.put(2, Arrays.asList(2, 0, 1));
        
        womenPreferences.put(0, Arrays.asList(0, 2, 1));
        womenPreferences.put(1, Arrays.asList(1, 0, 2));
        womenPreferences.put(2, Arrays.asList(0, 1, 2));
    

        StableMarriageLLP stableMarriageAlgo = new StableMarriageLLP(menPreferences,
                                                                     womenPreferences);
        stableMarriageAlgo.runAlgo(10);

        System.out.println(Arrays.toString(stableMarriageAlgo.GlobalState));
    }
}
