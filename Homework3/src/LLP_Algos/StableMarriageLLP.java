package LLP_Algos;

import java.util.Map;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class StableMarriageLLP extends LLPInterface 
{
    // Macros for the "always" call
    int currentWomen[];

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
    }
    
    public void runStabelMarriageLLP()
    {
        this.runAlgo(this.menPreferences.size());
    }

    // ----------------------- LLP functionality -------------------------------------------------------

    @Override
    public void printGlobalState()
    {
        System.out.println(Arrays.toString(this.GlobalState));
    }

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
    public Runnable processorThread(int man, AtomicBoolean endAlgo)
    {
        return () -> {  // This is what each processor should do in parallel

            this.init(man);

            while (endAlgo.get() == false)    
            { 
                this.waitForThreadSync(); // Wait for every processor 

                endAlgo.set(true); // Set algorithm to end after this superstep

                this.waitForThreadSync(); // Wait for every processor

                this.always(man);         // Reevaluate any macros

                if (this.forbidden(man))  // Check if the thread has a forbidden index
                {
                    endAlgo.set(false);  // If there is a forbidden index, algo must not end yet
                    this.advance(man);            // Advance the forbidden index
                }

                this.waitForThreadSync();  // Wait for every processor
            }
        };
    }
}