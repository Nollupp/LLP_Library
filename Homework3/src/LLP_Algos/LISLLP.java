package LLP_Algos;

import java.util.*;

import InputOutputClasses.LISInput;
import InputOutputClasses.LISOutput;
import InputOutputClasses.StableMarriageOutput;

public class LISLLP extends LLPInterface
{
    int[] arr;
    Map<Integer, List<Integer>> pre; // let pre(j) be the set of indices which have an incoming edge to j.
    Boolean[] fixed;

    public LISLLP(LISInput input)
    {
        arr = input.elements;

        fixed = new Boolean[arr.length];

        pre = new HashMap<Integer,List<Integer>>();

        for(int i = 0; i < input.elements.length; i++){
            pre.put(i, new ArrayList<>());
        }
    }

    public LISOutput runLISLLP()
    {
        // Run algo, give global state size
        if (arr.length == 0 || !runAlgo(arr.length))
        {
            // There is no solution vector in this lattice, so return null;
            System.out.println("Could not find solution or the given array is empty");
            return null;
        }

        return new LISOutput(GlobalState);
    }

    // ----------------------- LLP functionality -------------------------------------------------------

    @Override
    public void init(int currentIndex)
    {
        GlobalState[currentIndex] = 1;
        fixed[currentIndex] = false;

        for(int i = 0; i < currentIndex; i++){
            if(arr[i] < arr[currentIndex]) {
                pre.get(currentIndex).add(i);
            }
        }
    }

    @Override
    public boolean forbidden(int currentIndex)  // This function decides whether a index is forbidden
    {
        boolean everyPredIsFixed = true;
        for (Integer i : pre.get(currentIndex))
        {
            if (!fixed[i])
            {
                everyPredIsFixed = false;
                break;
            }
        }

        if(!fixed[currentIndex] && everyPredIsFixed){
            return true;
        }

        return false;
    }

    @Override
    public void advance(int currentIndex)
    {
        // Advance the forbidden index
        int max = 1;
        for (Integer i : pre.get(currentIndex))
        {
            if(max < GlobalState[i] + 1){
                max = GlobalState[i] + 1;
            }
        }

        GlobalState[currentIndex] = max;
        fixed[currentIndex] = true;
    }

    @Override
    public void always(int currentMan)
    {
        // Not used in this algo
    }

    @Override
    public boolean ensure(int currentIndex)
    {
        // Not used in this algo
        return false;
    }
}
