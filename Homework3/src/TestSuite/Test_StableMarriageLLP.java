package TestSuite;
import java.util.Map;

import LLP_Algos.StableMarriageLLP;
import InputOutputClasses.StableMarriageInput;
import InputOutputClasses.StableMarriageOutput;

import java.util.List;
import java.util.Arrays;
import java.util.HashMap;

public class Test_StableMarriageLLP 
{
    public static void main(String[] args) throws Exception 
    {
        // Create stable marriage input:
        Map<Integer, List<Integer>> menPreferences    = new HashMap<>();
        Map<Integer, List<Integer>> womenPreferences  = new HashMap<>();
        
        menPreferences.put(0, Arrays.asList(1, 2, 0, 3));
        menPreferences.put(1, Arrays.asList(1, 2, 0, 3));
        menPreferences.put(2, Arrays.asList(1, 2, 0, 3));
        menPreferences.put(3, Arrays.asList(1, 2, 0, 3));

        womenPreferences.put(0, Arrays.asList(3, 2, 1, 0));
        womenPreferences.put(1, Arrays.asList(1, 0, 2, 3));
        womenPreferences.put(2, Arrays.asList(0, 1, 3, 2));
        womenPreferences.put(3, Arrays.asList(0, 1, 3, 2));
        
        StableMarriageInput input = new StableMarriageInput(menPreferences, womenPreferences);
        StableMarriageOutput output;

        // Run stable marriage algo with input
        StableMarriageLLP stableMarriageAlgo = new StableMarriageLLP(input);
        output = stableMarriageAlgo.runStableMarriageLLP();
        
        if (output == null)
        {
            System.out.println("No stable marriage for given input");
        }
        else
        {
            // Print output from stable marriage algo
            for (int man = 0; man < output.pairings.size(); man++)
            {
                System.out.println("Man: " + man + " | Woman: " + output.pairings.get(man));
            }

        }
    }
}
