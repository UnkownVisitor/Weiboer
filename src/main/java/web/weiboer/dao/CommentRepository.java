package web.weiboer.dao;
import org.springframework.data.jpa.repository.JpaRepository;
import web.weiboer.dataStruct.weiboerComments;
import web.weiboer.dataStruct.weiboerContent;
import web.weiboer.dataStruct.weiboerUser;

import java.util.List;

public interface CommentRepository extends JpaRepository<weiboerComments,Long> {
    List<weiboerComments> findAllByFatherContent(weiboerContent fatherContent);
}