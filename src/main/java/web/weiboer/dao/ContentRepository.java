package web.weiboer.dao;
import org.springframework.data.jpa.repository.JpaRepository;
import web.weiboer.dataStruct.weiboerContent;
public interface ContentRepository extends JpaRepository<weiboerContent,Long> {
}
