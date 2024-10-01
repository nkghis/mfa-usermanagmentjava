package ics.ci.mfausermanagmentjava.service.impl;


import ics.ci.mfausermanagmentjava.config.AppuserUserDetails;
import ics.ci.mfausermanagmentjava.entity.AppUser;
import ics.ci.mfausermanagmentjava.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {


    private final UserService userService;

    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        AppUser appUser = userService.findByEmail(email);

        if (appUser == null) {
           // System.out.println("User not found! " + userName);
            throw new UsernameNotFoundException("User " + email + " was not found in the database");
        }

       // System.out.println("Found User: " + appUser);

        // [ROLE_USER, ROLE_ADMIN,..]

       /* List<String> roleNames = userService.rolenames(appUser);

        List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
        if (roleNames != null) {
            for (String role : roleNames) {
                // ROLE_USER, ROLE_ADMIN,..
                GrantedAuthority authority = new SimpleGrantedAuthority(role);
                grantList.add(authority);
            }
        }

        UserDetails userDetails = (UserDetails) new User(appUser.getEmail(), //
                appUser.getEncrytedPassword(), grantList);

        return userDetails;*/
        return new AppuserUserDetails(appUser);
    }

}
