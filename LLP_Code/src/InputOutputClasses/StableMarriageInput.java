package InputOutputClasses;

import java.util.List;
import java.util.Map;

public class StableMarriageInput {
    public Map<Integer, List<Integer>> menPreferences;
    public Map<Integer, List<Integer>> womenPreferences;

    public StableMarriageInput(Map<Integer, List<Integer>> menPreferences,
                               Map<Integer, List<Integer>> womenPreferences) {
        this.menPreferences = menPreferences;
        this.womenPreferences = womenPreferences;
    }
}