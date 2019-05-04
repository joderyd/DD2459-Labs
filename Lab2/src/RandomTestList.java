import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;


public class RandomTestList {
    static final String FILENAME_RANDOM = "src/testFiles/randomizedTests.txt";
    static int numOfArrays = 100;
    static int maxInt = 300;
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
        TestUtils.createRandomizedTestArrays(FILENAME_RANDOM, numOfArrays, 20, maxInt, 5);
        readTests(FILENAME_RANDOM, 200);
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


    @RepeatedTest(50)
    public synchronized void falseMembershipTest(RepetitionInfo repetitionInfo) {
        if (testArrays.size() > 0) {
            int[] A = testArrays.remove(0);
            int falseKey = maxInt+1;
            boolean F = ListOperators.membership(A, maxInt+1);
            assertFalse(F);

            // Logging test result
            stringBuilder.append("\n\nTest repetition #" + repetitionInfo.getCurrentRepetition() +
                    "\nkey= " + maxInt+1 + "\nArray= " + TestUtils.arrayToString(A));
            if(!F){
                stringBuilder.append("key = " + falseKey + " was successfully not found!\n");
                falseKeysNotFound ++;
            }
            else{
                stringBuilder.append("key = " + falseKey + " was unsuccessfully found!\n");
                falseKeysFound ++;
            }
            stringBuilder.append("=====================\n");
        }
    }





    @RepeatedTest(50)
    public synchronized void trueMembershipTest(RepetitionInfo repetitionInfo){
        if(testArrays.size()>0) {
            int[] A = testArrays.remove(0);
            int idx = new Random().nextInt(A.length);
            int key = A[idx];
            boolean T = ListOperators.membership(A, key);
            assertTrue(T);

            // Logging test result
            stringBuilder.append("\n\nTest repetition #" + repetitionInfo.getCurrentRepetition() +
                    "\nkey= " + key + "\nArray= " + TestUtils.arrayToString(A));
            if(T){
                stringBuilder.append("key = " + key + " was successfully found!\n");
                trueKeysFound ++;
            }
            else{
                stringBuilder.append("key = " + key + " was NOT found!\n");
                trueKeysNotFound ++;
            }
            stringBuilder.append("========================\n");
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
        int[] a = new int[]{1,2,3};
        assertNotNull(a);
        try{
            ListOperators.membership(null, 1);
        }
        catch(Exception e){
            assertTrue(e.getClass() == NullPointerException.class);
        }
    }


    @AfterAll
    static void printResult(){
        File file = new File("src/testFiles/randomizedTest_RESULT.txt");
        if(file.exists()){
            file.delete();
        }
        try {
            FileWriter fileWriter = new FileWriter(new File("src/testFiles/randomizedTest_RESULT.txt"), true);
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
