import java.io.*;
import java.util.Random;


public class TestUtils {
    private TestUtils(){}


    public static synchronized String arrayToString(int[] A){
        StringBuilder sb = new StringBuilder();
        if(A.length < 1){
            throw new IllegalArgumentException("Array must not be empty");
        }
        else{
            sb.append(A.length + ": [");
        }
        for(int i=0; i<A.length-1; i++){
            sb.append(A[i] + ", ");
        }
        sb.append(A[A.length-1]);
        sb.append("]\n");
        return sb.toString();
    }



    public static int[] stringToArray(String string) {
        String[] splitLength = string.split(": \\[");
        int len = Integer.parseInt(splitLength[0]);
        int[] array = new int[len];
        String arrayString = splitLength[1];
        arrayString = arrayString.substring(0, arrayString.length()-1);
        String[] subA = arrayString.split(", ");
        for(int i=0; i<len; i++){
            array[i] = Integer.parseInt(subA[i]);
        }
        return array;
    }



    public static void createPairwiseTestArrays(String filename, int size, int nonZero) {
        int[] A = new int[size];  //default all zero's
        StringBuilder sb = new StringBuilder();
        sb.append(arrayToString(A));

        // 1-wise test
        for(int i=0; i<size; i++) {
            A[i] = nonZero;
            sb.append(arrayToString(A));
            A[i] = 0;
        }
        // 2-wise test
        for(int i=0; i<size; i++) {
            A = new int[size];
            A[i] = nonZero;
            for(int k=i+1; k<size; k++) {
                A[k] = 0-nonZero;
                sb.append(arrayToString(A));
                A[k] = 0;
            }
        }
        writeFile(sb.toString(), filename);
    }



    public static void createRandomizedTestArrays(String filename, int numberOfArrays, int maxSize, int maxInt, int falseKey) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        int[] array;

        for(int i=0; i<numberOfArrays; i++) {
            int size = Math.abs(random.nextInt(maxSize));
            while(size ==0){
                size = Math.abs(random.nextInt(maxSize));
            }
            array = new int[size];
            for(int j=0; j<size; j++) {
                int element = random.nextInt(maxInt);
                if(element == falseKey){
                    element = falseKey-1;
                }
                if(random.nextBoolean()){
                    element = -element;
                }
                array[j] = element;
            }
            sb.append(arrayToString(array));
        }
        writeFile(sb.toString(), filename);
    }



    static synchronized void writeFile(String string, String filename){
        File file = new File(filename);
        FileWriter fileWriter;
        try {
            if(!file.exists()){
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
