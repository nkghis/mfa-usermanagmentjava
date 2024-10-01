package ics.ci.mfausermanagmentjava.controller;

import ics.ci.mfausermanagmentjava.utils.WebUtils;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.security.core.userdetails.User;

import java.security.Principal;

@Controller
public class MainController {





    @RequestMapping(value = { "/", "/login" }, method = RequestMethod.GET)
    public String welcomePage(Model model, Principal principal) {

        //Check if user is connected
        if (null == principal){
            model.addAttribute("title", "Authentification");
            return "main/loginPage";
        }


        return "redirect:/dashboard";

    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String adminPage(Model model, Principal principal) {

        User loginedUser = (User) ((Authentication) principal).getPrincipal();



        String userInfo = WebUtils.toString(loginedUser);
        model.addAttribute("userInfo", userInfo);

        return "main/adminPage";
    }




    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public String userInfo(Model model, Principal principal) {
        //Check if user is connected
        if (null == principal){
            model.addAttribute("title", "Authentification");
            return "main/loginPage";
        }

        //List<Reception> receptions = receptionRepository.findByEstDisponibleTrue();

        //String test = "test";

        // After user login successfully.
        String userName = principal.getName();


       // System.out.println("User Name: " + userName);

        //User loginedUser = (User) ((Authentication) principal).getPrincipal();

        //String userInfo = WebUtils.toString(loginedUser);
        //model.addAttribute("userInfo", userInfo);
        model.addAttribute("title", "Tableau de bord");


        return "main/dashboard";
    }

    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public String accessDenied(Model model, Principal principal) {

        if (principal != null) {
            User loginedUser = (User) ((Authentication) principal).getPrincipal();

            String userInfo = WebUtils.toString(loginedUser);

            model.addAttribute("userInfo", userInfo);

            String message = "Hi " + principal.getName() //
                    + "<br> You do not have permission to access this page!";
            model.addAttribute("message", message);

        }

        return "main/403Page";
    }





}
