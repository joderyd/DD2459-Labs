/**
 * @author
 * Jonathan Öderyd
 * oderyd@kth.se
 * 19950214-8159
 *
 * DD2459
 */


public class ListOperators {
    private ListOperators(){}





    /**
     *
     * @param A array of integer values of any length. must be sorted
     * @param key integer value that is searched for in A. must not be null
     * @return the index of the key value in the array. If key does not exist in A, -1 is returned
     *
     * @exception NullPointerException if A == null
     * @exception IllegalArgumentException if A is empty
     */
    public static int binarySearch(int[] A, int key) throws NullPointerException, IllegalArgumentException{
        //@requires A is sorted
        //@ensures index of key is returned if key exists in A. otherwise -1 is returned.
        if (A == null) {
            throw new NullPointerException("Array or key must not be null!");
        }
        if (A.length < 1) {
            throw new IllegalArgumentException("List must not be empty");
        }
        
        int x = 0;
        int l = 1;
        int r = A.length - 1;
        while( key != A[x] && (l<=r)){
            x = (l + r) / 2;
            if (key < A[x]) {
                r = x - 1;
            } else {
                l = x + 1;
            }
        }
        
        if(key==A[x]){
            return x;
        }
        else{
            return -1;
        }
    }





    /**
     * Method for sorting array of integers. The same array will be returned but in sorted order
     *
     * @param A array of integer values of any length. must not be null
     * @return array of same length and the same values as A, but in sorted order
     * @exception NullPointerException if A == null
     */
    public static int[] bubbleSort(int[] A){
        //@requires A.length >0
        //@ensures A is returned in sorted order
        if(A==null){
            throw new NullPointerException("Array must not be null");
        }
        for(int i=0; i<A.length;i++) {
            for(int j=1; j< (A.length-i); j++) {
                if(A[j-1] > A[j]) {
                    int swap = A[j-1];
                    A[j-1] = A[j];
                    A[j] = swap;
                }
            }
        }
        return A;
    }





    /**
     * Method membership returns a Boolean value that should be 1 if the input key occurs in
     * the input array, and 0 if it does not occur anywhere in the array
     *
     * @param A array of integer values of any length.
     * @param key value that will be be queried for membership
     * @return true if key exists in A, otherwise false
     */
    public static synchronized boolean membership(int[] A, int key) {
        //@requires A!=null && A.length >=1 && No element in A == null
        //@ensures that functions returns true if key exists in A, otherwise false
        int[] sorted = bubbleSort(A);
        int n = binarySearch(sorted, key);
        return(n!= -1);
    }

}
