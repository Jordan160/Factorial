import java.math.BigInteger;

public class ThreadedFactorial extends Thread {
    /*
    The Big O for factorial will be O(n log n) for all cases.
    n refers to input size.
    log n is the algorithm to the base of 2. When n is divided into halves we get log n time complexity.
    */
    public static BigInteger factorial(long n) throws InterruptedException {
        BigInteger[] array = new BigInteger[(int) n];
        int firstIndex = 0;
        int lastIndex = array.length - 1;

        long l = 1;
        while (n > 0) {
            array[(int) l - 1] = BigInteger.valueOf(n);
            l++;
            n--;
        }

        return mergeSort(array, firstIndex, lastIndex);
    }

    public static BigInteger mergeSort(BigInteger[] nArray, int firstIndex, int lastIndex) throws InterruptedException {
        if (firstIndex >= lastIndex) {
            return nArray[0];
        }
        int middle = firstIndex + (lastIndex - firstIndex) / 2;

        FactorialThread tf1 = new FactorialThread(nArray, firstIndex, middle);
        FactorialThread tf2 = new FactorialThread(nArray, middle + 1, lastIndex);

        tf1.start();
        tf2.start();
        tf1.join();
        tf2.join();

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

    public static void main(String args[]) {
        try {
            long startTime = System.currentTimeMillis();

            System.out.println(factorial(20));
            long stopTime = System.currentTimeMillis();
            long elapsedTime = stopTime - startTime;
            System.out.println("2-thread MergeSort takes: " + (float)elapsedTime/1000 + " seconds");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class FactorialThread extends Thread {
    private BigInteger[] nArray;
    private int firstIndex;
    private int lastIndex;

    FactorialThread(BigInteger[] nArray, int firstIndex, int lastIndex) {
        this.nArray = nArray;
        this.firstIndex = firstIndex;
        this.lastIndex = lastIndex;
    }

    public void run() {
        try {
            ThreadedFactorial.mergeSort(nArray, firstIndex, lastIndex);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}