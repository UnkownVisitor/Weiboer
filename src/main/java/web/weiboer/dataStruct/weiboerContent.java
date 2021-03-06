package web.weiboer.dataStruct;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class weiboerContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private Timestamp time;

    private Integer likeNum;
    private Integer commentNum;

    private String likeList="";

    @JsonIgnore
    @ManyToOne( cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn
    private weiboerUser poster;

    @OneToMany(mappedBy = "fatherContent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<weiboerComments> comments = new ArrayList<>();

    public weiboerContent(){}
    public weiboerContent(String _content,weiboerUser user){poster=user;content=_content;}
}