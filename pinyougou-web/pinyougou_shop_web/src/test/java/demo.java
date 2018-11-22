import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class demo {

    @Test
    public void test(){
        BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
        String p=bCryptPasswordEncoder.encode("123456");
        System.out.println(p);
    }
}
