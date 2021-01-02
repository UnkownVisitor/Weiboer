package web.weiboer.controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import web.weiboer.dao.ContentRepository;
import web.weiboer.dao.PictureRepository;
import web.weiboer.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class mainController {
    @Autowired
    private UserRepository productRepository;
    @Autowired
    private ContentRepository contentRepository;
    @Autowired
    private PictureRepository pictureRepository;

    @GetMapping("/login")
    public String login(){
        return "main";
    }

    @RequestMapping("/")
    public ModelAndView index(Model model,
                              @RequestParam(value = "page",defaultValue = "1")Integer page){
        ModelAndView mav = new ModelAndView();
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

    @RequestMapping("/login")
    public ModelAndView login(Model model,
                             @RequestParam(value = "id")Long id){
        ModelAndView mav = new ModelAndView();
        return mav;
    }

    @RequestMapping("/reg")
    public ModelAndView reg(Model model,
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

    }

    @RequestMapping("/api/picture")
    public String api_weibo(){

    }
    @RequestMapping("/api/weibo")
    public String api_weibo(){

    }
    @RequestMapping("/api/weibo")
    public String api_weibo(){

    }

}
