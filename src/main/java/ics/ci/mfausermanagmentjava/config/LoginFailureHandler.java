package ics.ci.mfausermanagmentjava.config;

import ics.ci.mfausermanagmentjava.entity.AppUser;
import ics.ci.mfausermanagmentjava.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
@Slf4j
public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private final UserService userService;

    public LoginFailureHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        String email = request.getParameter("email");

        log.error("Login Failure : {} ,  {}, {}",request.getParameter("email"), request.getRemoteAddr(), exception.getMessage());

        //String failureRedirectURL = "/login?error=true";
        String failureRedirectURL = "/login?error&email=" + email;


        if (exception.getMessage().contains("OTP")) {
            failureRedirectURL = "/login?otp=true&email=" + email;
        }else {
            AppUser user = userService.findByEmail(email);
            if (user != null && user.isOTPRequired()) {
                failureRedirectURL = "/login?otp=true&email=" + email;
            }
        }

        super.setDefaultFailureUrl(failureRedirectURL);

        super.onAuthenticationFailure(request, response, exception);
    }
}
