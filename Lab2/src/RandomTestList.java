import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;


public class RandomTestList {
    private static final String TESTFILES_PATH = "C:\\Users\\jonth\\git\\solRel\\Lab2\\src\\testFiles";
    private static final String FILENAME_RANDOM = "randomTests.txt";
    private static final int numOfArrays = 30;
    private static final int maxInt = 30;
    //private static int[] testArray;
    private static ArrayList<int[]> trueArrays, falseArrays;
    private static StringBuilder stringBuilder;
    private static int trueKeysFound, trueKeysNotFound;
    private static int falseKeysNotFound, falseKeysFound;


    static void createTestArrays(){
        TestUtils.createRandomTestArrays(FILENAME_RANDOM, numOfArrays, maxInt, 20, 5);
    }


    @BeforeAll
    static void init(){
        trueKeysFound = 0;
        trueKeysNotFound = 0;
        falseKeysFound = 0;
        falseKeysNotFound = 0;
        stringBuilder = new StringBuilder();
        trueArrays = new ArrayList<>();
        falseArrays = new ArrayList<>();
        createTestArrays();
        //TestUtils.createPairwiseTestArrays("pairwiseTests.txt", 20, 1);
        File fileRandom = new File(TESTFILES_PATH + File.separator + FILENAME_RANDOM);
        try {
            BufferedReader randomBufferedReader = new BufferedReader(new FileReader(fileRandom));
            String nextRandomLine = randomBufferedReader.readLine();
            while(nextRandomLine!=null){
                int[] a = TestUtils.stringToArray(nextRandomLine);
                trueArrays.add(a);
                falseArrays.add(a);
                nextRandomLine = randomBufferedReader.readLine();
            }
            randomBufferedReader.close();
        } catch (Exception e) { e.printStackTrace(); }
    }




    @BeforeEach
    synchronized void setUp(){ }


    @RepeatedTest(20)
    public synchronized void falseMembershipTest(RepetitionInfo repetitionInfo) {
        System.out.println("\n\nTest repetition #" + repetitionInfo.getCurrentRepetition());
        if (falseArrays.size() > 0) {
            int[] A = falseArrays.remove(0);
            boolean F = ListOperators.membership(A, maxInt+1);
            System.out.println("key= " + maxInt+1 + "\nArray= " + TestUtils.arrayToString(A));
            assertFalse(F);
            if(!F){
                stringBuilder.append("key = " + maxInt+1 + " was successfully not found!\n");
                falseKeysNotFound ++;
            }
            else{
                stringBuilder.append("key = " + maxInt+1 + " was unsuccessfully found!\n");
                falseKeysFound ++;
            }
            stringBuilder.append("=====================\n");
        }
    }





    @RepeatedTest(20)
    public void trueMembershipTest(RepetitionInfo repetitionInfo){
        System.out.println("\n\nTest repetition #" + repetitionInfo.getCurrentRepetition());
        if(trueArrays.size()>0) {
            int[] A = trueArrays.remove(0);
            Random random = new Random();
            int index = random.nextInt(A.length);
            int key = A[index];
            System.out.println("key= " + key + "\nArray= " + TestUtils.arrayToString(A)
            + "\nSorted= " + TestUtils.arrayToString(ListOperators.bubbleSort(A)));

            boolean T = ListOperators.membership(A, key);
            assertTrue(T);

            stringBuilder.append("Test #" + repetitionInfo.getCurrentRepetition() + "\n");
            stringBuilder.append("testList = " + TestUtils.arrayToString(A) + "\n");
            if(T){
                stringBuilder.append("key = " + key + " was successfully found!\n");
                trueKeysFound ++;
            }
            else{
                stringBuilder.append("key = " + key + " was NOT found!\n");
                trueKeysNotFound ++;
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
        try {
            FileWriter fileWriter = new FileWriter(new File("src/testFiles/randomTestResult"), true);
            fileWriter.write("~TEST RESULT~" +
                    "\ntrueKeysFound = " + trueKeysFound +
                    "\ntrueKeysNotFound = " + trueKeysNotFound +
                    "\nfalseKeysFound = " + falseKeysFound +
                    "\nfalseKeysNotFound = " + falseKeysNotFound +
                    "\n\n\n\n Detailed test result:\n");
            fileWriter.write(stringBuilder.toString());
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
