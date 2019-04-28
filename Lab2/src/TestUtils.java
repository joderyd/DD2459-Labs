import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;


public class TestUtils {
    private static String TESTFILES_PATH = "C:\\Users\\jonth\\git\\solRel\\Lab2\\src\\testFiles";
    private TestUtils(){}


    public static String arrayToString(int[] A){
        StringBuilder sb = new StringBuilder();
        if(A.length < 1){
            throw new IllegalArgumentException("Array must not be empty");
        }
        else{
            sb.append(A.length + ",");
        }
        for(int i=0; i<A.length; i++){
            sb.append(A[i] + ",");
        }
        sb.append("\n");
        return sb.toString();
    }

    public static int[] stringToArray(String string) {
        String[] subArray = string.split(",");
        int size = Integer.parseInt(subArray[0]);
        int[] array = new int[size];
        for(int i=1; i<array.length; i++) {
            array[i] = Integer.parseInt(subArray[i]);
        }
        return array;
    }


    public static void createPairwiseTestArrays(String filename, int size, int integer) {
        int[] A = new int[size];  //default all zero's
        StringBuilder sb = new StringBuilder();
        sb.append(arrayToString(A));

        // 1-wise test
        for(int i=0; i<size; i++) {
            A[i] = integer;
            sb.append(arrayToString(A));
            A[i] = 0;
        }

        // 2-wise test
        for(int i=0; i<size; i++) {
            A = new int[size];
            A[i] = integer;
            for(int k=i+1; k<size; k++) {
                A[k] = integer;
                sb.append(arrayToString(A));
                A[k] = 0;
            }
        }

        stringToFile(sb.toString(), filename);
    }



    public static void createRandomTestArrays(String filename, int numberOfArrays, int maxSize, int maxInt, int falseKey) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for(int i=0; i<numberOfArrays; i++) {
            int size = Math.abs(random.nextInt(maxSize));
            while(size ==0){
                size = Math.abs(random.nextInt(maxSize));
            }
            sb.append(size + ",");

            int[] array = new int[size];
            for(int j=0; j<size; j++) {
                int element = random.nextInt(maxInt);
                if(element == falseKey){
                    element = falseKey-1;
                }
                if(random.nextBoolean()){
                    element = -element;
                }
                array[j] = element;
                sb.append(element + ",");
            }
            sb.append("\n");
        }
        stringToFile(sb.toString(), filename);
    }

    
    
    private static synchronized void stringToFile(String string, String filename){
        File file = new File(TESTFILES_PATH + File.separator + filename);
        FileWriter fileWriter;
        try {
            if(!file.exists()){
                file.createNewFile();
                fileWriter = new FileWriter(file);
            }
            else{
                fileWriter = new FileWriter(file, true);
            }
            fileWriter.write(string);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
