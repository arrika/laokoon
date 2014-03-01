package lt.skankjo.laokoon.web.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author skankjo
 */
@Controller
public class HelloController {

    @RequestMapping(value = "/hello.spring", method = GET)
    @ResponseBody
    public String hello() {
        return "Hello, World, from Spring!";
    }
}
