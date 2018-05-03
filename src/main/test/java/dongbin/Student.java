package dongbin;

import com.dongbin.framework.annotation.Resource;
import com.dongbin.framework.annotation.Service;

/**
 * Created by dongbin on 2018/4/27.
 */
@Service
public class Student {
    @Resource
    private UserTest userTest;
}
