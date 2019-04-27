import org.junit.jupiter.api.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.*;


public class RandomTestList {
    private static int nIndex;
    private int[] testArray;
    private static ArrayList<int[]> arrays;
    private static int maxInt = 30;
    private static StringBuilder stringBuilder;
    private static int trueKeysFound;
    private static int trueKeysNotFound;
    private static int falseKeysNotFound;
    private static int falseKeysFound;
    private static String TESTFILES_PATH = "C:\\Users\\jonth\\git\\solRel\\Lab2\\src\\testFiles";
    private static String filenameRandom = "randomTests.txt";
    private static final int numOfArrays = 30;



    @BeforeAll
    static void init(){
        nIndex =0;
        trueKeysFound = 0;
        trueKeysNotFound = 0;
        falseKeysFound = 0;
        falseKeysNotFound = 0;
        stringBuilder = new StringBuilder();
        arrays = new ArrayList<>();
        TestUtils.createRandomTestArrays(filenameRandom, numOfArrays, maxInt, 20);
        //TestUtils.createPairwiseTestArrays("pairwiseTests.txt", 20, 1);
        try {
            //System.out.println("Init");
            File fileRandom = new File(TESTFILES_PATH + File.separator + filenameRandom);
            //Scanner randomScanner = new Scanner(new FileReader(fileRandom));
            BufferedReader randomBufferedReader = new BufferedReader(new FileReader(fileRandom));

            String nextRandomLine = randomBufferedReader.readLine();
            while(nextRandomLine!=null){
                //String strArray = randomBufferedReader.readLine();
                int[] testArray = TestUtils.stringToArray(nextRandomLine);
                arrays.add(testArray);
                nextRandomLine = randomBufferedReader.readLine();
            }
            randomBufferedReader.close();
            //randomScanner.close();
        }
        catch (Exception e) {
            e.printStackTrace();
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

    @BeforeEach
    void setUp(){
        testArray = arrays.get(0);
        testArray = arrays.remove(0);
        //testArray = arrays.get(0);
        //System.out.println("Before Each Test");
    }

    @AfterEach
    void tearDown(){
        nIndex++;
        stringBuilder.append("=====================\n");

        //System.out.println("After Each Test");
        //System.out.println("=====================\n");
    }


    void testFalseMember(int falseKey, int[] array){
        assertFalse(ListOperators.membership(array, falseKey));
    }

    //@RepeatedTest(10)
    @Test
    void falseMembershipTest(RepetitionInfo repetitionInfo) {
        System.out.println("Test repetition #" + repetitionInfo.getCurrentRepetition());
        if (arrays.size() > 0) {
            int falseKey = maxInt + 1;
            System.out.println(falseKey);
            boolean F = ListOperators.membership(testArray, falseKey);
            assertTrue(!F);
            //assertFalse(F);
            System.out.println(F + "   (F)\n");
        }
    }




    @RepeatedTest(10)
    void trueMembershipTest(RepetitionInfo repetitionInfo){
        System.out.println("Test repetition #" + repetitionInfo.getCurrentRepetition());
        if(arrays.size()>0) {
            int trueKey = TestUtils.createTrueTestKey(testArray);

            // int falseKey = maxInt + 1;
            //assertNotNull(testArray);

            //boolean T = true;
            //boolean F = false;
            boolean T = ListOperators.membership(testArray, trueKey);
            //F = ListOperators.membership(testArray, falseKey);

            int[] array = testArray.clone();

            assertTrue(T);
            System.out.println(T + "   (T)\n");
            //assertFalse(F);
            int falseKey = maxInt + 1;
            //testFalseMember(999999, array);
            //Append test result to stringbuilder


            /*
            stringBuilder.append("Test #" + repetitionInfo.getCurrentRepetition() + "\n");
            stringBuilder.append("testList = " + TestUtils.arrayToString(testArray) + "\n");
            if(T){
                stringBuilder.append("key = " + trueKey + " was successfully found!\n");
                trueKeysFound ++;
            }
            else{
                stringBuilder.append("key = " + trueKey + " was NOT found!\n");
                trueKeysNotFound ++;
            }

            if(!F){
                stringBuilder.append("key = " + falseKey + " was successfully not found!\n");
                falseKeysNotFound ++;
            }
            else{
                stringBuilder.append("key = " + falseKey + " was unsuccessfully found!\n");
                falseKeysFound ++;
            }
            */
        }
    }

/*
    @RepeatedTest(10)
    public void repetitions(RepetitionInfo repetitionInfo){
        System.out.println("Test repetition #" + repetitionInfo.getCurrentRepetition());
        if(arrays.size()>0) {
            int falseKey = maxInt + 1;

            //boolean F = false;

            //T = ListOperators.membership(testArray, trueKey);
            //boolean F = ListOperators.membership(testArray, falseKey);

            stringBuilder.append("Test #" + repetitionInfo.getCurrentRepetition() + "\n");
            stringBuilder.append("testList = " + TestUtils.arrayToString(testArray) + "\n");

            assertFalse(F);
            if(!F){
                stringBuilder.append("key = " + falseKey + " was successfully not found!\n");
                falseKeysNotFound ++;
            }
            else{
                stringBuilder.append("key = " + falseKey + " was unsuccessfully found!\n");
                falseKeysFound ++;
            }
        }
    }
*/

    @Test
    void testEmptyArray(){
        int[] A = new int[0];
        try{
            ListOperators.membership(A, 1);
        }
        catch(Exception e){
            assertTrue(e.getClass() == IllegalArgumentException.class);
        }
    }



    @Test
    void testNullMembership(){
        assertNotNull(testArray);
        try{
            ListOperators.membership(null, 1);
        }
        catch(Exception e){
            assertTrue(e.getClass() == NullPointerException.class);
        }
    }

}
