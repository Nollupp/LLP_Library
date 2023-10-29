package TestSuite;
import java.util.Map;

import LLP_Algos.StableMarriageLLP;

import java.util.List;
import java.util.Arrays;
import java.util.HashMap;

public class Test_StableMarriageLLP 
{
    public static void main(String[] args) throws Exception 
    {
        Map<Integer, List<Integer>> menPreferences    = new HashMap<>();
        Map<Integer, List<Integer>> womenPreferences  = new HashMap<>();
        
        menPreferences.put(0, Arrays.asList(1, 2, 0, 3));
        menPreferences.put(1, Arrays.asList(1, 2, 0, 3));
        menPreferences.put(2, Arrays.asList(1, 2, 0, 3));
        menPreferences.put(3, Arrays.asList(1, 2, 0, 3));
        
        womenPreferences.put(0, Arrays.asList(0, 2, 1, 3));
        womenPreferences.put(1, Arrays.asList(1, 0, 2, 3));
        womenPreferences.put(2, Arrays.asList(0, 1, 2, 3));
        womenPreferences.put(3, Arrays.asList(0, 1, 2, 3));
    
        StableMarriageLLP stableMarriageAlgo = new StableMarriageLLP(menPreferences,
                                                                     womenPreferences);

        stableMarriageAlgo.runStableMarriageLLP();
        stableMarriageAlgo.printGlobalState();
    }
}
