package ics.ci.mfausermanagmentjava.controller;

import ics.ci.mfausermanagmentjava.entity.*;
import ics.ci.mfausermanagmentjava.repository.RoleRepository;
import ics.ci.mfausermanagmentjava.repository.UserRepository;
import ics.ci.mfausermanagmentjava.repository.UserRoleRepository;
import ics.ci.mfausermanagmentjava.utils.EncrytedPasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @RequestMapping(value = "/admin/users")
    public String indexUser(Model model){

        //List<AppUser> users = userRepository.findAll();
        List<AppUser> users = userRepository.findAll(Sort.by(Sort.Direction.DESC,"userId"));
        model.addAttribute("listusers",users);
        model.addAttribute("title", "Utilisateur - Liste");
        return "user/index";
    }

    @RequestMapping(value = "/admin/users/new", method = RequestMethod.GET)
    public String newUser(Model model){

        List<AppRole> roles = roleRepository.findAll();
        model.addAttribute("monuser",new AppUser());
        model.addAttribute("title", "Utilisateur - Nouveau");
        model.addAttribute("roles", roles);
        return "user/new";

    }

    @RequestMapping(value = "/admin/users/save", method = RequestMethod.POST)
    public String saveUser(@Valid AppUser user, Errors errors, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request){

        if (errors.hasErrors()){
            System.out.println("error YES");
            model.addAttribute("monuser", new AppUser());
            //model.addAttribute("errors", errors);
            return "user/new";
        }
        //Encrypt Password
        String password = EncrytedPasswordUtils.encrytePassword(user.getEncrytedPassword());
        user.setEncrytedPassword(password);;
        user.setEnabled(true);

        // Save User
        AppUser u = userRepository.save(user);

        //Get role form Select multiple value
        String[] selected = request.getParameterValues("role");

        //Add role
        for (String s : selected){

            Long id = Long.parseLong(s);
            AppRole role = roleRepository.getOne(id);
            UserRole userRole = new UserRole();
            userRole.setAppUser(u);
            userRole.setAppRole(role);

            userRoleRepository.save(userRole);


        }
        redirectAttributes.addFlashAttribute("messagesucces","Opération éffectée avec succès");
        return "redirect:/admin/users";
    }

    @RequestMapping(value = "/admin/users/edit/{id}", method = RequestMethod.GET)
    public String editUser(@PathVariable Long id, Model model){

        AppUser u = userRepository.getOne(id);

        model.addAttribute("user", u);
        model.addAttribute("title", "Utilisateur - Edition");
        return "user/edit";
    }

    @RequestMapping(value = "/admin/users/delete/{id}", method = RequestMethod.GET)
    public String deleteUser(@PathVariable Long id){

        userRepository.deleteById(id);
        return "redirect:/admin/users";
    }

/*    @RequestMapping(value = "/admin/users/finByUsername/{name}", method = RequestMethod.GET)
    @ResponseBody
    //public String findUser(@PathVariable String name){
    public Boolean findUser(@PathVariable String name){
        String username = name;
        //AppUser user = userRepository.findByUserName(name);
        Boolean u = userRepository.existsByUserName(name);
        *//*if (user == null){

            System.out.println("not found");
            String msg = "<span style=\"color:green;\">Nom d'utilisateur disponible</span>";
            *//**//*String msg = "<p>Username unavailable</p>";*//**//*
            //String mp = "/";
            return msg;
            //return "redirect:/admin/users/new";
        }
        else{
            System.out.println("trouve");
            *//**//*String msg = "<span style="+"color:red;"+">Username unavailable</span>";*//**//*
            String msg = "<span style=\"color:red;\">Nom d'utilisateur indisponible</span>";
            *//**//*String msg = "<p>Username available</p>";*//**//*
            //return "redirect:/admin/users/new";
            return msg;
        }*//*
        //return "redirect:/admin/users/new";
        return u;
    }*/

}
