package web.weiboer.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.net.HttpCookie;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;

import web.weiboer.dao.ContentRepository;
import web.weiboer.dao.PictureRepository;
import web.weiboer.dao.UserRepository;
import web.weiboer.dataStruct.weiboerUser;
import web.weiboer.dataStruct.weiboerContent;
import web.weiboer.dataStruct.weiboerPictures;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
public class mainController {
    private String u_email;
    private Long nowID = Long.valueOf(0);
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ContentRepository contentRepository;
    @Autowired
    private PictureRepository pictureRepository;

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
        model.addAttribute("u_email",temp.getEmail());
        model.addAttribute("u_name",temp.getName());
        model.addAttribute("u_id",temp.getId().toString());
        return "redirect:/main";
    }

    @DeleteMapping(value="/logout/{u_id}")
    public String logout(HttpServletRequest request,HttpServletResponse response,@PathVariable int u_id)
    {
        System.out.println("logout! "+u_id);
        Cookie[] cookies = request.getCookies();
        if(cookies==null)System.out.println("no cookies!");
        else{
            for (Cookie tempc:cookies){
                if(tempc.getName().equals("u_id"))
                {
                    if (Objects.equals(tempc.getValue(), String.valueOf(u_id)))
                    {
                        for(Cookie cookie:cookies){
                            if(cookie.getName().equals("u_id")||cookie.getName().equals("u_email")||cookie.getName().equals("u_name")){
                                System.out.println("set "+cookie.getName()+cookie.getValue());
                                cookie.setMaxAge(0);
                                System.out.println("set "+cookie.getName()+cookie.getMaxAge());
                                response.addCookie(cookie);
                            }
                        }
                        break;
                    }
                }
            }
        }
        return "redirect:/main";
    }

    @RequestMapping({"/", "main"})
    public ModelAndView index(Model model,
                              @RequestParam(value = "page",defaultValue = "1")Integer page){
        ModelAndView mav = new ModelAndView("home");
        return mav;
    }

    @RequestMapping("/detail")
    public ModelAndView detail(Model model,
                               @RequestParam(value = "id")Long id){
        ModelAndView mav = new ModelAndView();
        return mav;
    }

    @RequestMapping("/user")
    public ModelAndView user(Model model,
                             @RequestParam(value = "id")Long id){
        ModelAndView mav = new ModelAndView();
        return mav;
    }

    @RequestMapping("/forget")
    public ModelAndView forget(Model model,
                            @RequestParam(value = "id")Long id){
        ModelAndView mav = new ModelAndView();
        return mav;
    }

    @RequestMapping("/api/weibo")
    public String api_weibo(){
        return "";
    }

    @RequestMapping("/api/img")
    public String api_picture(@RequestParam(value = "img_id")Long img_id){
        return "";
    }
    @RequestMapping("/api/comment")
    public String api_comment(){
        return "";
    }
    @RequestMapping("/api/follow")
    public String api_follow(){
        return "";
    }
    @RequestMapping("/api/follower")
    public String api_follower(){
        return "";
    }


}
