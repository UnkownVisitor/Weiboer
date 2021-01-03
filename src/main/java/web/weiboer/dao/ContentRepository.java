package web.weiboer.dao;
import org.springframework.data.jpa.repository.JpaRepository;
import web.weiboer.dataStruct.weiboerContent;

import java.util.Optional;

public interface ContentRepository extends JpaRepository<weiboerContent,Long> {
    Optional<weiboerContent> findById(Long id);
}
