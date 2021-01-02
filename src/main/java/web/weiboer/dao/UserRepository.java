package web.weiboer.dao;
import org.springframework.data.jpa.repository.JpaRepository;
import web.weiboer.dataStruct.weiboerUser;
public interface UserRepository extends JpaRepository<weiboerUser,Long> {
}