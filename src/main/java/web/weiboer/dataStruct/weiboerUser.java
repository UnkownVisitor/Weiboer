package web.weiboer.dataStruct;


import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class weiboerUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String password;
    private String email;
    private Long foNum;
    private Long foedNum;
    // 用户发的微博
    @OneToMany(mappedBy = "poster", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<weiboerContent> postedList = new ArrayList<>();
    // 用户的关注列表
    @ManyToOne(fetch = FetchType.LAZY)
    private weiboerUser followed;
    @OneToMany(mappedBy = "followed", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<weiboerUser> followers = new ArrayList<>();
    // 用户的粉丝列表
    @ManyToOne(fetch = FetchType.LAZY)
    private weiboerUser follower;
    @OneToMany(mappedBy = "follower", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<weiboerUser> myFollow = new ArrayList<>();
    // 用户的like
    @ManyToMany()
    private List<weiboerContent> myLikes = new ArrayList<>();
    // 用户的comment
    @OneToMany()
    private List<weiboerComments> myComments = new ArrayList<>();

    public weiboerUser(Long _id,String _name,String _pswd,String _email,Long _foNum,Long _foedNum)
    {id=_id;name = _name;password = _pswd;email = _email;foNum = _foNum;foedNum = _foedNum;}
    public weiboerUser(Long _id)
    {id=_id;}

    public weiboerUser() {}
}
