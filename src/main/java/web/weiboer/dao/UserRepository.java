package web.weiboer.dao;
import org.springframework.data.jpa.repository.JpaRepository;
import web.weiboer.dataStruct.weiboerUser;

import java.util.List;

public interface UserRepository extends JpaRepository<weiboerUser,Long> {
    weiboerUser findByEmail(String email);
    List<weiboerUser> findByEmailAndPassword(String email,String password);
    boolean existsByEmail(String email);
    boolean existsByEmailAndPassword(String email,String password);
}