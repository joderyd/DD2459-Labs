import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;


public class RandomTestList {
    static final String FILENAME_RANDOM = "src/testFiles/randomizedTests.txt";
    static final int NUM_OF_ARRAYS = 220;
    static final int MAX_INT = 300;
    static final int MAX_ARRAY_SIZE = 20;
    static final int FALSE_KEY = 5;
    static ArrayList<int[]> testArrays;
    static StringBuilder stringBuilder;
    static int trueKeysFound, trueKeysNotFound;
    static int falseKeysNotFound, falseKeysFound;


    @BeforeAll
    public static void init(){
        trueKeysFound = 0;
        trueKeysNotFound = 0;
        falseKeysFound = 0;
        falseKeysNotFound = 0;
        testArrays = new ArrayList<>();
        stringBuilder = new StringBuilder();
        File file = new File(FILENAME_RANDOM);
        if(file.exists()){
            file.delete();
        }
        TestUtils.createRandomizedTestArrays(FILENAME_RANDOM, NUM_OF_ARRAYS, MAX_ARRAY_SIZE, MAX_INT, FALSE_KEY);
        readTests(FILENAME_RANDOM, NUM_OF_ARRAYS);
    }



    static synchronized void readTests(String filename, int maxArrays){
        File file = new File(filename);
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String nextRandomLine = bufferedReader.readLine();
            int counter = 0;
            while(nextRandomLine!=null && counter < maxArrays){
                counter++;
                int[] a = TestUtils.stringToArray(nextRandomLine);
                testArrays.add(a);
                nextRandomLine = bufferedReader.readLine();
            }
            bufferedReader.close();
        } catch (Exception e) { e.printStackTrace(); }

    }


    @RepeatedTest(105)
    public synchronized void falseMembershipTest(RepetitionInfo repetitionInfo) {
        if (testArrays.size() > 0) {
            int[] A = testArrays.remove(0);
            String unsortedString = TestUtils.arrayToString(A);
            boolean F = ListOperators.membership(A, MAX_INT +1);

            // Logging test result
            stringBuilder.append("\n\nTest repetition #" +
                    repetitionInfo.getCurrentRepetition() + "\n(falseMembershipTest)\nUnsorted Array= " +
                    TestUtils.arrayToString(A) + "Sorted Array= " + unsortedString + "\n");
            if(!F){
                stringBuilder.append("key = " + FALSE_KEY + " was successfully not found!\n");
                falseKeysNotFound ++;
            }
            else{
                stringBuilder.append("key = " + FALSE_KEY + " was unsuccessfully found!\n");
                falseKeysFound ++;
            }
            stringBuilder.append("=====================\n");
            assertFalse(F);
        }
    }





    @RepeatedTest(105)
    public synchronized void trueMembershipTest(RepetitionInfo repetitionInfo){
        if(testArrays.size()>0) {
            int[] A = testArrays.remove(0);
            String unsortedString = TestUtils.arrayToString(A);
            int idx = new Random().nextInt(A.length);
            int key = A[idx];
            boolean T = ListOperators.membership(A, key);

            // Logging test result
            stringBuilder.append("\n\nTest repetition #" +
                    repetitionInfo.getCurrentRepetition() + "\n(trueMembershipTest)\nUnsorted Array= " +
                    TestUtils.arrayToString(A) + "Sorted Array= " + unsortedString + "\n");
            if(T){
                stringBuilder.append("key = " + key + " was successfully found!\n");
                trueKeysFound ++;
            }
            else{
                stringBuilder.append("key = " + key + " was NOT found!\n");
                trueKeysNotFound ++;
            }
            stringBuilder.append("========================\n");
            assertTrue(T);
        }
    }




    @Test
    public void testEmptyArray(){
        int[] A = new int[0];
        try{
            ListOperators.membership(A, 1);
        }
        catch(Exception e){
            assertTrue(e.getClass() == IllegalArgumentException.class);
        }
    }



    @Test
    public void testNullMembership(){
        try{
            ListOperators.membership(null, 1);
        }
        catch(Exception e){
            assertTrue(e.getClass() == NullPointerException.class);
            assertTrue(e.getMessage() == "Array must not be null");
        }
    }


    @AfterAll
    static void printResult(){
        File file = new File("src/testFiles/randomizedTest_Result.txt");
        if(file.exists()){
            file.delete();
        }
        try {
            FileWriter fileWriter = new FileWriter(new File("src/testFiles/randomizedTest_Result.txt"), true);
            String resultSummary = "\n\n~ RANDOMIZED TEST RESULT~" +
                    "\n--- summary ---" +
                    "\ntrueKeysFound= " + trueKeysFound +
                    "\ntrueKeysNotFound= " + trueKeysNotFound +
                    "\nfalseKeysFound= " + falseKeysFound +
                    "\nfalseKeysNotFound= " + falseKeysNotFound +
                    "\n-----------------------------------"+
                    "\n\nDETAILED TEST RESULT:\n----------------------------------\n";

            fileWriter.write(resultSummary + stringBuilder.toString());
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
