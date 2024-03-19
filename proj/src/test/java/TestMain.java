import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestMain {

    @Test
    public void test1(){
        Boolean expectedResult = true;
        Boolean result = true;

        Assertions.assertEquals(expectedResult, result);
    }
}
