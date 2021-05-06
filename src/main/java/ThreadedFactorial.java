import com.google.common.collect.Lists;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class ThreadedFactorial {
    /*
    The Big O for factorial will be O(n log n) for all cases.
    n refers to input size.
    log n is the algorithm to the base of 2. When n is divided into halves we get log n time complexity.
    */
    public static BigInteger factorial(long n, int numberOfThreads) throws InterruptedException {
        List<BigInteger> list = new ArrayList<BigInteger>((int) n);
        int firstIndex = 0;
        int lastIndex = (int) (n - 1);

        int l = 1;
        while (n > 0) {
            list.add(l-1, BigInteger.valueOf(n));
            l++;
            n--;
        }

        return mergeSort(list, firstIndex, lastIndex, numberOfThreads);
    }

    public static BigInteger mergeSort(List<BigInteger> nArray, int firstIndex, int lastIndex, int numberOfThreads) throws InterruptedException {
        if (firstIndex >= lastIndex) {
            return nArray.get(0);
        }
        int middle = firstIndex + (lastIndex - firstIndex) / 2;
        final List<List<BigInteger>> splittedList = Lists.partition(nArray, nArray.size() / 5);

        for (List<BigInteger> list: splittedList) {
            FactorialThread ft = new FactorialThread(list, 0, list.size() - 1);
            ft.start();
        }

        return merge(nArray, firstIndex, middle, lastIndex);
    }

    public static BigInteger mergeSortMultiply(List<BigInteger> nList, int firstIndex, int lastIndex) {
        if (firstIndex >= lastIndex) {
            return nList.get(0);
        }
        int middle = firstIndex + (lastIndex - firstIndex) / 2;

        mergeSortMultiply(nList, firstIndex, middle);
        mergeSortMultiply(nList, middle + 1, lastIndex);
        return merge(nList, firstIndex, middle, lastIndex);
    }

    public static BigInteger merge(List<BigInteger> arr, int first, int middle, int last) {
        BigInteger firstNumbers = BigInteger.valueOf(0);
        BigInteger secondNumbers = BigInteger.valueOf(0);
        for (int i = first; i <= middle; i++) {
            if (firstNumbers.equals(new BigInteger("0"))) {
                firstNumbers = arr.get(i);
            } else {
                firstNumbers = firstNumbers.multiply(arr.get(i));
            }
        }

        for (int j = middle + 1; j <= last; j++) {
            if (secondNumbers.equals(new BigInteger("0"))) {
                secondNumbers = arr.get(j);
            } else {
                firstNumbers = firstNumbers.multiply(arr.get(j));
            }
        }

        return (firstNumbers.multiply(secondNumbers));
    }

    public static void main(String args[]) {
        try {
            long startTime = System.currentTimeMillis();

            System.out.println(factorial(100000, 10));
            long stopTime = System.currentTimeMillis();
            long elapsedTime = stopTime - startTime;
            System.out.println("10 Threaded Factorial took: " + (float)elapsedTime/1000 + " seconds");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class FactorialThread extends Thread {
    private List<BigInteger> nArray;
    private int firstIndex;
    private int lastIndex;

    FactorialThread(List<BigInteger> nArray, int firstIndex, int lastIndex) {
        this.nArray = nArray;
        this.firstIndex = firstIndex;
        this.lastIndex = lastIndex;
    }

    public void run() {
        ThreadedFactorial.mergeSortMultiply(nArray, firstIndex, lastIndex);
    }
}