package ics.ci.mfausermanagmentjava.config;

import ics.ci.mfausermanagmentjava.entity.AppUser;
import ics.ci.mfausermanagmentjava.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
   private final UserService userService;

    public LoginSuccessHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //System.out.println("Authentication Success");
        String email = request.getParameter("email");
        log.info("Login Success : {} ,  {}",email, request.getRemoteAddr());

        //AppuserUserDetails userDetails = (AppuserUserDetails) authentication.getPrincipal();
        //AppUser user = userDetails.getUser();
        AppUser user = userService.findByEmail(email);

        if (user.isOTPRequired()){
            userService.clearOTP(user);
            log.info("OTP clear : {}",email);
        }
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
