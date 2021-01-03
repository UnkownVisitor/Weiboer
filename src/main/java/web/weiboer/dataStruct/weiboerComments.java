package web.weiboer.dataStruct;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
public class weiboerComments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private Timestamp time;

    @ManyToOne()
    private weiboerContent fatherContent;
    @ManyToOne()
    private weiboerUser poster;

    public weiboerComments() {}
}