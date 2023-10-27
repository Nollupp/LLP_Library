import java.util.Map;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

class StableMarriageLLP implements LLPInterface 
{
    int GlobalState[];

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

        // Create a blank global state
        this.GlobalState = new int[menPreferences.size()];
    }

    // ----------------------- LLP functionality -------------------------------------------------------

    @Override
    public void init(int index)
    {
        this.GlobalState[index] = 1;
    }

    @Override
    public void always(int index)
    {
        ;
    }

    @Override
    public boolean forbidden(int index) 
    {
        // This function decides whether a index is forbidden
        return true;
    }

    @Override
    public void advance(int index) 
    {
        // Advance the forbidden index
        GlobalState[index]++;
    }

    @Override
    public Runnable processorThread(int index)
    {
        return () -> {
            // This is what each processor should do in parallel
            this.init(index);
            this.always(index);
            if (this.forbidden(index))
            {
                this.advance(index);
            }
        };
    }
}

public class App {
    public static void main(String[] args) throws Exception 
    {
        Map<Integer, List<Integer>> menPreferences    = new HashMap<>();
        Map<Integer, List<Integer>> womenPreferences  = new HashMap<>();
        
        menPreferences.put(0, Arrays.asList(1, 2, 3));
        menPreferences.put(1, Arrays.asList(1, 2, 3));
        menPreferences.put(2, Arrays.asList(1, 2, 3));
        menPreferences.put(3, Arrays.asList(1, 2, 3));
        menPreferences.put(4, Arrays.asList(1, 2, 3));

        StableMarriageLLP stableMarriageAlgo = new StableMarriageLLP(menPreferences,
                                                                     womenPreferences);
        stableMarriageAlgo.runAlgo(10);

        System.out.println(Arrays.toString(stableMarriageAlgo.GlobalState));
    }
}
