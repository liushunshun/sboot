package com.xy.controller;


import com.xy.bean.CurrentUser;
import com.xy.bean.UserCreateForm;
import com.xy.bean.validator.UserCreateFormValidator;
import com.xy.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.session.web.http.HttpSessionManager;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Created by XiuYang on 2016/10/24.
 */
@Controller
public class HomeController {

    @Autowired
    private UserService userService;

//    @Autowired
//    private UserCreateFormValidator userCreateFormValidator;

    private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

//    @InitBinder()
//    public void initBinder(WebDataBinder binder) {
//        binder.addValidators(userCreateFormValidator);
//    }

    @RequestMapping("/")
    public ModelAndView home(HttpServletRequest request){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        CurrentUser user = null;
        if(authentication != null && !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated()){
            user = (CurrentUser)authentication.getPrincipal();
        }

        // 使用正常的servlet API获取session，在底层，
        // session是通过Spring Session得到的，并且会存储到Redis或
        // 其他你所选择的数据源中

        HttpSession session = request.getSession();
        String value = session.getId();

        //session.setMaxInactiveInterval();

        /*
         * 在请求中，根据名为org.springframework.session.web.http.HttpSessionManager的key
         * 获得Spring Session session管理器的引用
         */

        HttpSessionManager sessionManager=(HttpSessionManager)request.getAttribute(
                "org.springframework.session.web.http.HttpSessionManager");

        /*
         * 使用session管理器找出所请求session的别名。
         * 默认情况下，session别名会包含在url中，并且请求参数的名称为“_s”。
         * 例如，http://localhost:8080/example?_s=1
         * 将会使如下的代码打印出“Requested Session Alias is: 1”
         */
        String requestedSessionAlias=sessionManager.getCurrentSessionAlias(request);
        System.out.println("Requested Session Alias is:  " + requestedSessionAlias);

        /* 返回一个唯一的session别名id，这个别名目前没有被浏览器用来发送请求。
         * 这个方法并不会创建新的session，
         * 我们需要调用request.getSession()来创建新session。
         */
        String newSessionAlias = sessionManager.getNewSessionAlias(request);

        /* 使用新创建的session别名来建立URL，这个URL将会包含
         * “_s”参数。例如，如果newSessionAlias的值为2的话，
         * 那么如下的方法将会返回“/inbox?_s=2”
         */

        String encodedURL = sessionManager.encodeURL("/inbox", newSessionAlias);
        System.out.println(encodedURL);

        /* 返回session别名与session id所组成的Map，
         * 它们是由浏览器发送请求所形成的。
         */
        Map< String, String > sessionIds = sessionManager.getSessionIds(request);

        LOGGER.info("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        return new ModelAndView("home","currentUser",user);
        
    }
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView getLoginPage(@RequestParam Optional<String> error) {
        return new ModelAndView("login", "error", error);
    }

    //@PreAuthorize("@currentUserServiceImpl.canAccessUser(principal, #id)")
    @RequestMapping("/user/{id}")
    public ModelAndView getUserPage(@PathVariable Long id) {
        LOGGER.debug("Getting user page for user={}", id);
        return new ModelAndView("home", "user", userService.getUserById(id)
                .orElseThrow(() -> new NoSuchElementException(String.format("User=%s not found", id))));
    }

    @RequestMapping("/welcome")
    public ModelAndView welcome() {
        return new ModelAndView("home");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/user/create", method = RequestMethod.GET)
    public ModelAndView getUserCreatePage() {
        LOGGER.debug("Getting user create form");
        return new ModelAndView("addUser");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/user/create", method = RequestMethod.POST)
    public ModelAndView handleUserCreateForm(@Valid @ModelAttribute("form") UserCreateForm form, BindingResult bindingResult) {
        LOGGER.debug("Processing user create form={}, bindingResult={}", form, bindingResult);
        if (bindingResult.hasErrors()) {
            // failed validation
            return new ModelAndView("addUser");
        }
        try {
            userService.create(form);
        } catch (DataIntegrityViolationException e) {
            // probably email already exists - very rare case when multiple admins are adding same user
            // at the same time and form validation has passed for more than one of them.
            LOGGER.warn("Exception occurred when trying to save the user, assuming duplicate email", e);
            bindingResult.reject("email.exists", "Email already exists");
            return new ModelAndView("user_create");
        }
        // ok, redirect
        return new ModelAndView("redirect:/users");
    }
}
