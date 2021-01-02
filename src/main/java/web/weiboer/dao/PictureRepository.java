package web.weiboer.dao;
import org.springframework.data.jpa.repository.JpaRepository;
import web.weiboer.dataStruct.weiboerPictures;
public interface PictureRepository extends JpaRepository<weiboerPictures,Long> {
}