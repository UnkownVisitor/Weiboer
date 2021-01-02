package web.weiboer.controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import web.weiboer.dao.ContentRepository;
import web.weiboer.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class mainController {
    @Autowired
    private UserRepository productRepository;
    @Autowired
    private ContentRepository contentRepository;

    @GetMapping("/login")
    public String login(){
        return "main";
    }

    @RequestMapping("/main")
    public ModelAndView index(Model model,
                              @RequestParam(value = "page",defaultValue = "1")Integer page){
        ModelAndView mav = new ModelAndView();
        return mav;
    }

    @RequestMapping("/my")
    public ModelAndView my(Model model,
                           @RequestParam(value = "username")String username){
        ModelAndView mav = new ModelAndView();
        return mav;
    }
}
