import java.math.BigInteger;

public class Factorial {
    /*
    The Big O for factorial will be O(n log n) for all cases.
    n refers to input size.
    log n is the algorithm to the base of 2. When n is divided into halves we get log n time complexity.
    */
    public static BigInteger factorial(long n) {
        BigInteger[] array = new BigInteger[(int) n];
        int firstIndex = 0;
        int lastIndex = array.length - 1;

        long l = 1;
        while (n > 0) {
            array[(int) l - 1] = BigInteger.valueOf(n);
            l++;
            n--;
        }

        return mergeSortMultiply(array, firstIndex, lastIndex);
    }

    public static BigInteger mergeSortMultiply(BigInteger[] nArray, int firstIndex, int lastIndex) {
        if (firstIndex >= lastIndex) {
            return nArray[0];
        }
        int middle = firstIndex + (lastIndex - firstIndex) / 2;

        mergeSortMultiply(nArray, firstIndex, middle);
        mergeSortMultiply(nArray, middle + 1, lastIndex);
        return merge(nArray, firstIndex, middle, lastIndex);
    }

    public static BigInteger merge(BigInteger[] arr, int first, int middle, int last) {
        BigInteger firstNumbers = BigInteger.valueOf(0);
        BigInteger secondNumbers = BigInteger.valueOf(0);
        for (int i = first; i <= middle; i++) {
            if (firstNumbers.equals(new BigInteger("0"))) {
                firstNumbers = arr[i];
            } else {
                firstNumbers = firstNumbers.multiply(arr[i]);
            }
        }

        for (int j = middle + 1; j <= last; j++) {
            if (secondNumbers.equals(new BigInteger("0"))) {
                secondNumbers = arr[j];
            } else {
                firstNumbers = firstNumbers.multiply(arr[j]);
            }
        }

        return (firstNumbers.multiply(secondNumbers));
    }

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        System.out.println(factorial(100000));
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println("Regular Factorial took: " + (float)elapsedTime/1000 + " seconds");
    }
}