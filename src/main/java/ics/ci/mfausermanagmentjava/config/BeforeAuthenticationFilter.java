package ics.ci.mfausermanagmentjava.config;

import ics.ci.mfausermanagmentjava.entity.AppUser;
import ics.ci.mfausermanagmentjava.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

@Component
@Slf4j
public class BeforeAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final UserService userService;

    public BeforeAuthenticationFilter(UserService userService) {
        this.userService = userService;
        super.setUsernameParameter("email");
        super.setRequiresAuthenticationRequestMatcher( new AntPathRequestMatcher("/login", "POST") );
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        //System.out.println("Authentication Attempt");
        String email = request.getParameter("email");
        log.info("Login Attempt : {} ,  {}",email, request.getRemoteAddr());
        AppUser user = userService.findByEmail(email);

        if(user != null) {

            //Check is field mfa is enable, if is enable use mfa or use username & password;
            if (user.isMfa() ){

                if (user.isOTPRequired()){
                    return super.attemptAuthentication(request, response);
                }

                try {
                    //generate OTP and send email
                    userService.getOneTimePassword(user);
                    throw new InsufficientAuthenticationException("OTP");
                }catch (MessagingException | UnsupportedEncodingException | InterruptedException ex) {
                    throw new AuthenticationServiceException("Error while sending OTP to your email", ex);
                }

            }

        }

        return super.attemptAuthentication(request, response);
    }

    //Change this value to 0.63 or other higther than 0.50 for disable Otp Authentication
    private float getGoogleRecaptchaScore(){
        return 0.63f;
    }


    @Autowired
    @Override

    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    @Autowired
    @Override
    public void setAuthenticationSuccessHandler(AuthenticationSuccessHandler successHandler) {
        super.setAuthenticationSuccessHandler(successHandler);
    }



    @Autowired
    @Override
    public void setAuthenticationFailureHandler(AuthenticationFailureHandler failureHandler) {
        super.setAuthenticationFailureHandler(failureHandler);
    }


}

