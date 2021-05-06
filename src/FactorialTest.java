import org.junit.Test;

import static org.junit.Assert.*;

public class FactorialTest {

    @Test
    public void testFactorial() throws InterruptedException {
        assertEquals(Factorial.factorial(6), (6*5*4*3*2*1));
        assertEquals(Factorial.factorial(4), (4*3*2*1));
        assertEquals(Factorial.factorial(10), (10*9*8*7*6*5*4*3*2*1));
    }
}