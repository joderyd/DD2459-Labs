import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import java.io.*;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class PairwiseTestList {
    static final String FILENAME_PAIRWISE = "src/testFiles/pairwiseTests.txt";
    static final int NON_ZERO = 3;
    static final int MAX_SIZE = 20;
    static StringBuilder stringBuilder;
    static ArrayList<int[]> testArrays;
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
        File file = new File(FILENAME_PAIRWISE);
        if(file.exists()){
            file.delete();
        }
        TestUtils.createPairwiseTestArrays(FILENAME_PAIRWISE, MAX_SIZE, NON_ZERO);
        readTests(FILENAME_PAIRWISE);
    }


    @RepeatedTest(211)
    public synchronized void testPairwise(RepetitionInfo repetitionInfo){
        if (testArrays.size() > 0) {
            int[] A = testArrays.remove(0);
            String aString = TestUtils.arrayToString(A);

            //First repetition is a special case to make the method logResult() easier
            if(repetitionInfo.getCurrentRepetition() == 1){
                int sum=0;
                for(int i : A){
                    if(i==0){
                        sum += 1;
                    }
                }
                boolean T = sum == A.length;
                if(T) {
                    stringBuilder.append("\n\nTest repetition #" + 1 + "\nTestList = " + TestUtils.arrayToString(A) +
                            "key = 0 was successfully found!\nkey = " + NON_ZERO + " was successfully not found!\n");
                    trueKeysFound++;
                    falseKeysNotFound++;
                }
                assertTrue(T);
            }
            else{
                boolean F = ListOperators.membership(A, NON_ZERO +1);
                boolean T = ListOperators.membership(A, NON_ZERO);
                logResult(repetitionInfo.getCurrentRepetition(), aString, T, F);
                assertFalse(F);
                assertTrue(T);
            }
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
        File file = new File("src/testFiles/pairwiseTest_Result.txt");
        if(file.exists()){
            file.delete();
        }
        try {
            FileWriter fileWriter = new FileWriter(new File("src/testFiles/pairwiseTest_Result.txt"), true);
            String resultSummary = "\n\n~PAIRWISE TEST RESULT~" +
                    "\n--- summary ---" +
                    "\ntrueKeysFound = " + trueKeysFound +
                    "\ntrueKeysNotFound = " + trueKeysNotFound +
                    "\nfalseKeysFound = " + falseKeysFound +
                    "\nfalseKeysNotFound = " + falseKeysNotFound +
                    "\n-----------------------------------"+
                    "\n\nDETAILED TEST RESULT:\n----------------------------------\n";

            fileWriter.write(resultSummary + stringBuilder.toString());
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    static synchronized void readTests(String filename){
        File file = new File(FILENAME_PAIRWISE);
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String nextRandomLine = bufferedReader.readLine();
            int counter = 0;
            while(nextRandomLine!=null && counter < 211){
                counter++;
                int[] a = TestUtils.stringToArray(nextRandomLine);
                testArrays.add(a);
                nextRandomLine = bufferedReader.readLine();
            }
            bufferedReader.close();
        } catch (Exception e) { e.printStackTrace(); }
    }




    private synchronized void logResult(int repInfo, String testList, boolean T, boolean F){
        stringBuilder.append("\n\nTest repetition #" + repInfo + "\nTestList = " + testList);
        int falseKey = NON_ZERO +1;
        if(!F){
            stringBuilder.append("key = " + falseKey + " was successfully not found!\n");
            falseKeysNotFound ++;
        }
        if(F){
            stringBuilder.append("key = " + falseKey + " was unsuccessfully found!\n");
            falseKeysFound ++;
        }

        if(T){
            stringBuilder.append("key = " + NON_ZERO + " was successfully found!\n");
            trueKeysFound ++;
        }
        if(!T){
            stringBuilder.append("key = " + NON_ZERO + " was NOT found!\n");
            trueKeysNotFound ++;
        }
        stringBuilder.append("=====================\n");
    }


}
