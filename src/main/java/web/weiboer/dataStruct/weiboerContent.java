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

    @JsonIgnore
    @ManyToOne( cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn
    private weiboerUser poster;

    @OneToMany(mappedBy = "poster", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<weiboerPictures> picture;
}