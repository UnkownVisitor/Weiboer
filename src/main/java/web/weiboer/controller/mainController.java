package web.weiboer.controller;
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

}
