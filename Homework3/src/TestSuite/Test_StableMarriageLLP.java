package TestSuite;
import java.util.*;

import LLP_Algos.StableMarriageLLP;
import InputOutputClasses.StableMarriageInput;
import InputOutputClasses.StableMarriageOutput;

public class Test_StableMarriageLLP 
{
    public static void main(String[] args) throws Exception 
    {
        List<Map<Integer, List<Integer>>> tAll = new ArrayList<>();

        Map<Integer, List<Integer>> menPreferences1    = new HashMap<>();
        Map<Integer, List<Integer>> womenPreferences1  = new HashMap<>();
        Map<Integer, List<Integer>> menPreferences2    = new HashMap<>();
        Map<Integer, List<Integer>> womenPreferences2  = new HashMap<>();
        Map<Integer, List<Integer>> menPreferences3    = new HashMap<>();
        Map<Integer, List<Integer>> womenPreferences3  = new HashMap<>();

        // <-------INPUT PREFERENCE LIST FOR TEST 1 HERE ------->
        menPreferences1.put(0, Arrays.asList(1, 2, 0, 3));
        menPreferences1.put(1, Arrays.asList(1, 2, 0, 3));
        menPreferences1.put(2, Arrays.asList(1, 2, 0, 3));
        menPreferences1.put(3, Arrays.asList(1, 2, 0, 3));
        womenPreferences1.put(0, Arrays.asList(3, 2, 1, 0));
        womenPreferences1.put(1, Arrays.asList(1, 0, 2, 3));
        womenPreferences1.put(2, Arrays.asList(0, 1, 3, 2));
        womenPreferences1.put(3, Arrays.asList(0, 1, 3, 2));
        //
        // <-------INPUT PREFERENCE LIST FOR TEST 2 HERE ------->
        menPreferences2.put(0, Arrays.asList(0, 1));
        menPreferences2.put(1, Arrays.asList(0, 1));
        womenPreferences2.put(0, Arrays.asList(1, 0));
        womenPreferences2.put(1, Arrays.asList(1, 0));
        //
        // <-------INPUT PREFERENCE LIST FOR TEST 3 HERE ------->
        menPreferences3.put(0, Arrays.asList(4, 3, 2, 1, 0));
        menPreferences3.put(1, Arrays.asList(3, 4, 2, 0, 1));
        menPreferences3.put(2, Arrays.asList(4, 2, 3, 1, 0));
        menPreferences3.put(3, Arrays.asList(4, 1, 3, 2, 0));
        menPreferences3.put(4, Arrays.asList(1, 3, 2, 0, 4));
        womenPreferences3.put(0, Arrays.asList(3, 2, 1, 0, 4));
        womenPreferences3.put(1, Arrays.asList(3, 2, 0, 1, 4));
        womenPreferences3.put(2, Arrays.asList(4, 3, 1, 0, 2));
        womenPreferences3.put(3, Arrays.asList(1, 2, 3, 4, 0));
        womenPreferences3.put(4, Arrays.asList(3, 2, 1, 4, 0));
        //

        // Add our tests to test suite
        tAll.add(menPreferences1);
        tAll.add(womenPreferences1);
        tAll.add(menPreferences2);
        tAll.add(womenPreferences2);
        tAll.add(menPreferences3);
        tAll.add(womenPreferences3);

        for(int i = 0; i < tAll.size(); i = i + 2)
        {
            StableMarriageInput input = new StableMarriageInput(tAll.get(i), tAll.get(i + 1));
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
}
