public class Factorial {
    /*
    The Big O for factorial will be O(n log n) for all cases.
    n refers to input size.
    log n is the algorithm to the base of 2. When n is divided into halves we get log n time complexity.
    */
    public static long factorial(long n) {
        long[] array = new long[(int) n];
        int firstIndex = 0;
        int lastIndex = array.length - 1;

        long l = 1;
        while (n > 0) {
            array[(int) l - 1] = n;
            l++;
            n--;
        }

        return mergeSortMultiply(array, firstIndex, lastIndex);
    }

    public static long mergeSortMultiply(long[] nArray, int firstIndex, int lastIndex) {
        if (firstIndex >= lastIndex) {
            return nArray[0];
        }
        int middle = firstIndex + (lastIndex - firstIndex) / 2;

        mergeSortMultiply(nArray, firstIndex, middle);
        mergeSortMultiply(nArray, middle + 1, lastIndex);
        return merge(nArray, firstIndex, middle, lastIndex);
    }

    public static long merge(long[] arr, int first, int middle, int last) {
        int firstNumbers = 0;
        int secondNumbers = 0;
        for (int i = first; i <= middle; i++) {
            if (firstNumbers == 0) {
                firstNumbers = (int) arr[i];
            } else {
                firstNumbers = (int) (firstNumbers * arr[i]);
            }
        }

        for (int j = middle + 1; j <= last; j++) {
            if (secondNumbers == 0) {
                secondNumbers = (int) arr[j];
            } else {
                firstNumbers = (int) (firstNumbers * arr[j]);
            }
        }

        return (firstNumbers * secondNumbers);
    }

    public static void main(String[] args) {
        System.out.println(factorial(20));
    }
}