package web.weiboer.dao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import web.weiboer.dataStruct.weiboerContent;
import web.weiboer.dataStruct.weiboerUser;

import java.util.List;
import java.util.Optional;

public interface ContentRepository extends JpaRepository<weiboerContent,Long> {
    Optional<weiboerContent> findById(Long id);
    List<weiboerContent> findAllByPoster(weiboerUser poster);
    Page<weiboerContent> findAllPageByPoster(Optional<weiboerUser> poster, Pageable pageable);
}
