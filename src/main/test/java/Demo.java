import com.dongbin.framework.beans.factory.BeanFactory;
import dongbin.Student;
import dongbin.UserTest;
import org.junit.Test;

/**
 * Created by dongbin on 2018/4/27.
 */
public class Demo {

    @Test
    public void test() throws Exception {
        BeanFactory factory = new BeanFactory();

        UserTest userTest = factory.getBean(UserTest.class);
        Student student = factory.getBean(Student.class);
        System.out.println();
    }
}
