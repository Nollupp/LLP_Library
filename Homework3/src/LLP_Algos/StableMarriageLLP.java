package LLP_Algos;

import java.util.Map;
import InputOutputClasses.StableMarriageInput;
import InputOutputClasses.StableMarriageOutput;

import java.util.HashMap;
import java.util.List;


public class StableMarriageLLP extends LLPInterface 
{
    // Macros for the "always" call
    int currentWomen[];

    // Input to algo
    Map<Integer, List<Integer>> menPreferences;
    Map<Integer, List<Integer>> womenPreferences;

    // Create the stable marriage LLP algo 
    public StableMarriageLLP(StableMarriageInput input)
    {
        // Copy Stable marriage parameters into LLP object
        this.menPreferences         = input.menPreferences;
        this.womenPreferences       = input.womenPreferences;

        // Create blank macro
        this.currentWomen  = new int[menPreferences.size()];
    }
    
    public StableMarriageOutput runStableMarriageLLP()
    {
        // Run algo, give global state size
        this.runAlgo(this.menPreferences.size());

        // Once algo is done, return the output
        Map<Integer, Integer> pairings = new HashMap<Integer, Integer>();

        for (int i = 0; i < this.menPreferences.size(); i++) // Iterate over pairings and store them
        {
            int man   = i;
            int woman = this.GlobalState[i];

            pairings.put(man, woman);
        }

        return new StableMarriageOutput(pairings);
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
    public void ensure(int index) {
        // Unused in this implementation/algo
        ;
    }

}