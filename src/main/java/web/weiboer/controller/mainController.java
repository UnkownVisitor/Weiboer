package web.weiboer.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;

import web.weiboer.dao.ContentRepository;
import web.weiboer.dao.PictureRepository;
import web.weiboer.dao.UserRepository;
import web.weiboer.dataStruct.weiboerUser;
import web.weiboer.dataStruct.weiboerContent;
import web.weiboer.dataStruct.weiboerPictures;


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
    public String login_func(Model model,weiboerUser temp){
        if (userRepository.existsByEmailAndPassword(temp.getEmail(),temp.getPassword())) {
            temp = userRepository.findByEmailAndPassword(temp.getEmail(), temp.getPassword()).get(0);
            System.out.println("login!");
            System.out.println(temp.getName());
            System.out.println(temp.getEmail());
            System.out.println(temp.getPassword());
            model.addAttribute("one",null);
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
            weiboerUser user = new weiboerUser(nowID,Username,Password,Email, 0L, 0L);
            userRepository.save(user);
            return "redirect:/main";
        }
    }

    @PostMapping(value="/reg")
    public String reg_func(weiboerUser temp){
        temp.setId(nowID);
        temp.setFoNum((long) 0);
        temp.setFoedNum((long) 0);
        nowID++;
        System.out.println("register!");
        System.out.println(temp.getName());
        System.out.println(temp.getEmail());
        System.out.println(temp.getPassword());
        userRepository.save(temp);
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
