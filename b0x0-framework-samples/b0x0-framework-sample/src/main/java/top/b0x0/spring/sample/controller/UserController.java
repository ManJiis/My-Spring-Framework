package top.b0x0.spring.sample.controller;


import top.b0x0.spring.framework.common.util.ValidateUtil;
import top.b0x0.spring.framework.ioc.annotation.Autowired;
import top.b0x0.spring.framework.ioc.annotation.Controller;
import top.b0x0.spring.framework.webmvc.annotation.RequestMapping;
import top.b0x0.spring.framework.webmvc.annotation.RequestMethodEnum;
import top.b0x0.spring.framework.webmvc.annotation.RequestParam;
import top.b0x0.spring.framework.webmvc.annotation.ResponseBody;
import top.b0x0.spring.framework.webmvc.servlet.helperbean.ModelAndView;
import top.b0x0.spring.sample.pojo.User;
import top.b0x0.spring.sample.service.UserService;
import top.b0x0.spring.sample.util.R;

import java.util.List;

/**
 * @author ManJiis
 * @since 2021-08-22
 * @since JDK1.8
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/test", method = RequestMethodEnum.GET)
    @ResponseBody
    public R<String> test() {
        return new R<>("test", 0, "");
    }

    @RequestMapping(value = "/list", method = RequestMethodEnum.GET)
    public ModelAndView getUserList() {
        List<User> list = userService.listUser();
        return new ModelAndView().setView("user_list.jsp").addObject("list", list);
    }

    @RequestMapping(value = "/detail", method = RequestMethodEnum.GET)
    public ModelAndView getUser(@RequestParam(value = "id") long id) {
        User user = userService.getUserById(id);
        return new ModelAndView().setView("user_detail.jsp").addObject("user", user);
    }

    @RequestMapping(value = "/add", method = RequestMethodEnum.POST)
    @ResponseBody
    public R<User> addUser(@RequestParam(value = "name") String name) {
        if (ValidateUtil.isEmpty(name)) {
            return new R<>(null, -1, "name不能为空");
        }
        User user = userService.addUser(name);
        return new R<>(user, 0, "");
    }

    @RequestMapping(value = "/delete", method = RequestMethodEnum.POST)
    @ResponseBody
    public R<String> deleteUser(@RequestParam(value = "id") long id) {
        userService.deleteUser(id);
        return new R<>("success", 0, "");
    }
}
