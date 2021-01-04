package web.weiboer.controller;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Timestamp;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;

import web.weiboer.dao.CommentRepository;
import web.weiboer.dao.ContentRepository;
import web.weiboer.dao.UserRepository;
import web.weiboer.dataStruct.weiboerComments;
import web.weiboer.dataStruct.weiboerUser;
import web.weiboer.dataStruct.weiboerContent;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
public class mainController {
    private String u_email;
    private Long nowID = Long.valueOf(0);
    private Long nowContentID = Long.valueOf(0);
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ContentRepository contentRepository;
    @Autowired
    private CommentRepository commentRepository;

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login(Model model){
        model.addAttribute("one",new weiboerUser(nowID));
        return "login";
    }
    @PostMapping(value="/login")
    public String login_func(HttpServletResponse response,Model model,weiboerUser temp){
        if (userRepository.existsByEmailAndPassword(temp.getEmail(),temp.getPassword())) {
            temp = userRepository.findByEmailAndPassword(temp.getEmail(), temp.getPassword()).get(0);
            System.out.println("login!");
            System.out.println(temp.getName());
            System.out.println(temp.getEmail());
            System.out.println(temp.getPassword());
            model.addAttribute("one",null);
            model.addAttribute("u_email",temp.getEmail());
            model.addAttribute("u_name",temp.getName());
            Cookie cookie = new Cookie("u_email",temp.getEmail());
            cookie.setMaxAge(24 * 60 * 60);
            cookie.setPath("/");
            response.addCookie(cookie);
            cookie = new Cookie("u_name",temp.getName());
            cookie.setMaxAge(24 * 60 * 60);
            cookie.setPath("/");
            response.addCookie(cookie);
            cookie = new Cookie("u_id",temp.getId().toString());
            cookie.setMaxAge(24 * 60 * 60);
            cookie.setPath("/");
            response.addCookie(cookie);

            return "redirect:/main";
        }
        else{
            model.addAttribute("one",new weiboerUser(nowID));
            return "login";
        }
    }

    @RequestMapping(value="/reg",method = RequestMethod.GET)
    public String register(String Username,String Password,String Email,Model model){
        if(userRepository.existsByEmail(Email) || Email==null){
            model.addAttribute("one",new weiboerUser(nowID));
            return "register";
        }
        else{
            model.addAttribute("u_email",Email);
            model.addAttribute("u_name",Username);
            weiboerUser user = new weiboerUser(nowID,Username,Password,Email, 0L, 0L);
            userRepository.save(user);
            return "redirect:/main";
        }
    }

    @PostMapping(value="/reg")
    public String reg_func(HttpServletResponse response,Model model,weiboerUser temp){
        if(userRepository.findByEmail(temp.getEmail())!=null) {
            System.out.println("email duplicate!");
            return "redirect:/reg";
        }
        temp.setId(nowID);
        temp.setFoNum((long) 0);
        temp.setFoedNum((long) 0);
        nowID++;
        System.out.println("register!");
        System.out.println(temp.getName());
        System.out.println(temp.getEmail());
        System.out.println(temp.getPassword());
        Cookie cookie = new Cookie("u_email",temp.getEmail());
        cookie.setMaxAge(24 * 60 * 60);
        cookie.setPath("/");
        response.addCookie(cookie);
        cookie = new Cookie("u_name",temp.getName());
        cookie.setMaxAge(24 * 60 * 60);
        cookie.setPath("/");
        response.addCookie(cookie);
        cookie = new Cookie("u_id",temp.getId().toString());
        cookie.setMaxAge(24 * 60 * 60);
        cookie.setPath("/");
        response.addCookie(cookie);
        userRepository.save(temp);
        return "redirect:/main";
    }

    @GetMapping(value={"/", "main"})
    public String index(Model model,
                        @RequestParam(value = "start",defaultValue = "0")Integer page,
                        @RequestParam(value = "limit",defaultValue = "10")Integer limit,
                        @RequestParam(value = "method",defaultValue = "time")String method,
                        HttpServletRequest request,HttpServletResponse response){
        System.out.println("sorted by "+method);
        page = page <0 ? 0 :page;
        Sort sort =Sort.by(Sort.Direction.DESC,method);
        Pageable pageable = PageRequest.of(page,limit,sort);
        Page<weiboerContent> contents = contentRepository.findAll(pageable);
        String tempEmail = "";
        if(request.getCookies()!=null){
            for (Cookie cookie : request.getCookies()) {
                if (Objects.equals(cookie.getName(), "u_email")) {
                    tempEmail = cookie.getValue();
                }
            }
        }
        if(userRepository.findByEmail(tempEmail)==null){System.out.println("getting user error!");}
        else{
            Cookie cookie = new Cookie("u_id",userRepository.findByEmail(tempEmail).getId().toString());
            cookie.setMaxAge(24 * 60 * 60);
            cookie.setPath("/");
            response.addCookie(cookie);
        }
        Long tempId = Long.valueOf(-1);
        if(request.getCookies()!=null){
            for(Cookie cookie:request.getCookies()){
                if(Objects.equals(cookie.getName(), "u_id")){
                    tempId = Long.valueOf(cookie.getValue());
                }
                if(Objects.equals(cookie.getName(), " u_id")){
                    tempId = Long.valueOf(cookie.getValue());
                }
            }
        }
        if(userRepository.findById(tempId).isPresent()) {
            weiboerUser me = userRepository.findById(tempId).get();
            model.addAttribute("me", me);
        }
        model.addAttribute("method",method);
        model.addAttribute("contents",contents);
        model.addAttribute("posting",new weiboerContent());
        return "home";
    }
    @PostMapping(value="/main")
    public String add_weibo(HttpServletRequest request,weiboerContent posting){
        System.out.println("new content");
        Cookie[] cookies = request.getCookies();
        String u_email = null;
        if(request.getCookies()!=null){
            for(Cookie cookie :cookies){
                if (cookie.getName().equals("u_email"))
                    u_email=cookie.getValue();
            }
        }
//        posting.setId(nowContentID);
//        nowContentID++;
        posting.setPoster(userRepository.findByEmail(u_email));
        posting.setTime(new Timestamp(System.currentTimeMillis()));
        posting.setLikeNum(0);
        posting.setCommentNum(0);
        System.out.println(u_email);
        System.out.println(posting.getContent());
        contentRepository.save(posting);
        return "redirect:/main";
    }

    @RequestMapping("/detail")
    public String detail(Model model, @RequestParam(value = "id")Long id,
                         HttpServletRequest request){
        System.out.println("find detail "+id.toString());
        Optional<weiboerContent> content = contentRepository.findById(id);
        if(content.isPresent()) {
            System.out.println(content.get().getContent());
            model.addAttribute("weibo", (weiboerContent)content.get());
            List<weiboerComments> comments = commentRepository.findAllByFatherContent((weiboerContent)content.get());
            model.addAttribute("comments", comments);
        }
        model.addAttribute("posting",new weiboerComments());
        Long tempId = Long.valueOf(-1);
        if(request.getCookies()!=null){
            for(Cookie cookie:request.getCookies()){
                if(Objects.equals(cookie.getName(), "u_id")){
                    tempId = Long.valueOf(cookie.getValue());
                }
                if(Objects.equals(cookie.getName(), " u_id")){
                    tempId = Long.valueOf(cookie.getValue());
                }
            }
        }
        if(userRepository.findById(tempId).isPresent()) {
            weiboerUser me = userRepository.findById(tempId).get();
            model.addAttribute("me", me);
        }
        return "detail";
    }
    @PostMapping(value="/detail")
    public String add_comment(HttpServletRequest request,weiboerComments posting, @RequestParam(value = "id")Long id){
        if(Objects.equals(posting.getContent(), "")){return "redirect:/detail?id="+id.toString();}
        System.out.println("new comment");
        Cookie[] cookies = request.getCookies();
        String u_email = null;
        if(request.getCookies()!=null){
            for(Cookie cookie :cookies){
                if (cookie.getName().equals("u_email"))
                    u_email=cookie.getValue();
            }
        }
        if(contentRepository.findById(id).isPresent()) {
            posting.setPoster(userRepository.findByEmail(u_email));
            posting.setFatherContent(contentRepository.findById(id).get());
            posting.setTime(new Timestamp(System.currentTimeMillis()));
            posting.setId(null);
            System.out.println(u_email);
            System.out.println(posting.getContent());
            contentRepository.findById(id).get().setCommentNum(contentRepository.findById(id).get().getCommentNum() + 1);
            commentRepository.save(posting);
        }
        return "redirect:/detail?id="+id.toString();
    }

    @RequestMapping("/api/like")
    public String like(Model model, @RequestParam(value="id")Long id,
                       HttpServletRequest request,@RequestParam(value="source")String source){
        Long uId = Long.valueOf(-1);
        System.out.println(source);
        if(request.getCookies()!=null){
            for(Cookie cookie:request.getCookies()){
                if (cookie.getName().equals("u_id")||cookie.getName().equals(" u_id")){
                    uId = Long.valueOf(cookie.getValue());
                }
            }
        }
        System.out.println("uid: "+uId.toString());
        System.out.println("id: "+id.toString());
        Optional<weiboerContent> _content = contentRepository.findById(id);
        Optional<weiboerUser> _user = userRepository.findById(uId);
        if(!_content.isPresent()){System.out.println("get content error!");}
        else if(!_user.isPresent()){System.out.println("get content error!");}
        else{
            weiboerContent content = _content.get();
            weiboerUser user = _user.get();
            List<String> contentLiked = new ArrayList<>();
            for(String i : content.getLikeList().split("&")){ contentLiked.add(i); }
            contentLiked.remove("");
//            System.out.println("before content liked "+content.getLikeNum().toString());
//            System.out.println("before content liked "+content.getLikeList());
//            System.out.println("before user liked "+user.getMyLikeNum().toString());
//            System.out.println("before user liked "+user.getMyLikes());
//            System.out.println(contentLiked);
            if(contentLiked.contains(uId.toString())){
                System.out.println("unlike!");
                content.setLikeNum(content.getLikeNum()-1);
                String temp = "";
                contentLiked.remove(uId.toString());
                for(String l:contentLiked){ temp=temp+l;temp=temp+"&"; }
                content.setLikeList(temp);

                user.setMyLikeNum(user.getMyLikeNum()-1);
                List<String> myLikes = new ArrayList<>();
                for(String i : user.getMyLikes().split("&")){ myLikes.add(i); }
                myLikes.remove("");
                myLikes.remove(id.toString());
                temp = "";
                for(String l:myLikes){ temp=temp+l;temp=temp+"&"; }
                user.setMyLikes(temp);
                contentRepository.save(content);
                userRepository.save(user);
            }
            else{
                System.out.println("like!");
                content.setLikeNum(content.getLikeNum()+1);
                content.setLikeList(content.getLikeList()+"&"+uId.toString());
                user.setMyLikeNum(user.getMyLikeNum()+1);
                user.setMyLikes(user.getMyLikes()+"&"+id.toString());
                contentRepository.save(content);
                userRepository.save(user);
            }
//            System.out.println("after content liked "+contentRepository.findById(id).get().getLikeNum().toString());
//            System.out.println("after content liked "+contentRepository.findById(id).get().getLikeList());
//            System.out.println("after user liked "+userRepository.findById(id).get().getMyLikeNum().toString());
//            System.out.println("after user liked "+userRepository.findById(id).get().getMyLikes());
        }
        return "redirect:/"+source;
    }

    @RequestMapping("/user")
    public String user(Model model,
                       @RequestParam(value = "start", defaultValue = "0") Integer page,
                       @RequestParam(value = "id", defaultValue = "0") Long id,
                       @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                       @RequestParam(value = "method", defaultValue = "time") String method,
                       HttpServletRequest request, HttpServletResponse response){
        System.out.println("user sorted by "+method);
        page = page <0 ? 0 :page;
        Sort sort =Sort.by(Sort.Direction.DESC,method);
        Pageable pageable = PageRequest.of(page,limit,sort);
        String tempEmail = "";
        if(request.getCookies()!=null){
            for(Cookie cookie:request.getCookies()){
                if(Objects.equals(cookie.getName(), "u_email")){
                    tempEmail = cookie.getValue();
                }
            }
        }
        Optional<weiboerUser> user = userRepository.findById(id);
        Page<weiboerContent> contents = contentRepository.findAllPageByPoster(user,pageable);
        if(!user.isPresent()){System.out.println("getting user error!");}
        else{
            Cookie cookie = new Cookie("u_id",userRepository.findByEmail(tempEmail).getId().toString());
            cookie.setMaxAge(24 * 60 * 60);
            cookie.setPath("/");
            response.addCookie(cookie);
        }
        model.addAttribute("method",method);
        model.addAttribute("thisUser",user.get());
        model.addAttribute("contents",contents);
        return "user";
    }

    @RequestMapping("/api/follow")
    public String api_follow(){
        return "";
    }
    @RequestMapping("/api/follower")
    public String api_follower(){
        return "";
    }

    @RequestMapping("/forget")
    public ModelAndView forget(Model model,
                               @RequestParam(value = "id")Long id){
        ModelAndView mav = new ModelAndView();
        return mav;
    }

}
