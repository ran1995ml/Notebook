import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @ClassName: Junit5Test
 * @Description:
 * @Author: rwei
 * @Date: 2022/11/5 17:40
 */

//@SpringBootTest
public class Junit5Test {


    @Test
    void testAssertion(){
        int val = cal(3,3);
        Assertions.assertEquals(5,val);
    }

    int cal(int i,int j){
        return i+j;
    }

    @ParameterizedTest
    @ValueSource(ints = {1,2,3,4,5})
    @Test
    void testParameter(int i){
        System.out.println(i);
    }

    @Test
    @DisplayName("快速失败")
    void testFail(){

        Assertions.fail("fail");
    }

    @Test
    @DisplayName("异常断言")
    void testException(){
        Assertions.assertThrows(ArithmeticException.class,()->{int i=10/0;},"正常运行");
    }

    @Test
    @DisplayName("组合断言")
    void allTest(){
        Assertions.assertAll("test",
                ()->Assertions.assertTrue(true&&true),
                ()->Assertions.assertEquals(1,2));
    }


    @DisplayName("testDisplayname")
    @Test
    void testDisplayName(){
        System.out.println(1);
    }

    @RepeatedTest(5)
    @Test
    public void test(){
        System.out.println("test");
    }

    @BeforeEach
    void testBeforeEach(){
        System.out.println("测试开始了");
    }

    @AfterEach
    void testAfterEach(){
        System.out.println("测试结束了");
    }

    @BeforeAll
    static void testBeforeAll(){
        System.out.println("所有测试开始了");
    }


}
