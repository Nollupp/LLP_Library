package TestSuite;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import InputOutputClasses.LISInput;
import InputOutputClasses.LISOutput;
import LLP_Algos.LISLLP;

public class Test_LIS {
    public static void main(String[] args) throws Exception
    {
        int[] t1 = {0, 1, 2, 3, 4, 15, 9}; // should be {1, 2, 3, 4, 5, 6, 6}
        int[] t2 = {109, 1, 2, 3, -5, 5, 8, 10, 12, 16}; // should be {1, 1, 2, 3, 1, 4, 5, 6, 7, 8}
        int[] t3 = {-4, 10, 3, 18, 23, 17, 19}; // should be {1, 2, 2, 3, 4, 3, 4}
        int[] t4 = {}; // should detect empty set

        List<int[]> tAll = new ArrayList<>();
        tAll.add(t1);
        tAll.add(t2);
        tAll.add(t3);
        tAll.add(t4);

        for(int i = 0; i < tAll.size(); i++){
            int testNumber = i + 1;
            System.out.println("***************************");
            System.out.println("Begin test: " + testNumber);
            LISInput input = new LISInput(tAll.get(i));
            LISOutput output;

            LISLLP lisAlgo = new LISLLP(input);
            output = lisAlgo.runLISLLP();

            if (output == null)
            {
                System.out.println("No LIS for given input");
            }
            else
            {
                System.out.println(Arrays.toString(output.subsequence));
            }
        }
    }
}
