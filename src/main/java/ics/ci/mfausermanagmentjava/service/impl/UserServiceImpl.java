package ics.ci.mfausermanagmentjava.service.impl;

import ics.ci.mfausermanagmentjava.dto.UserDTO;
import ics.ci.mfausermanagmentjava.entity.AppRole;
import ics.ci.mfausermanagmentjava.entity.AppUser;
import ics.ci.mfausermanagmentjava.entity.UserRole;
import ics.ci.mfausermanagmentjava.mapper.UserMapper;
import ics.ci.mfausermanagmentjava.repository.RoleRepository;
import ics.ci.mfausermanagmentjava.repository.UserRepository;
import ics.ci.mfausermanagmentjava.repository.UserRoleRepository;
import ics.ci.mfausermanagmentjava.service.RoleService;
import ics.ci.mfausermanagmentjava.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Sort;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.*;

@Service
@Transactional
@Slf4j
/*@RequiredArgsConstructor*/
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final UserRoleRepository userRoleRepository;
    private final Environment env;

    //for use you have to mvn clean and build project if property underline in red
    private final UserMapper userMapper ;
    private final RoleRepository roleRepository;
    private final JavaMailSender mailSender;


    public UserServiceImpl(UserRepository userRepository, RoleService roleService, UserRoleRepository userRoleRepository, Environment env, UserMapper userMapper, RoleRepository roleRepository, JavaMailSender mailSender) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.userRoleRepository = userRoleRepository;
        this.env = env;
        this.userMapper = userMapper;
        this.roleRepository = roleRepository;
        this.mailSender = mailSender;
    }



    @Override
    public AppUser create(AppUser user) {
        log.info("User added : {}", user.getEmail());
        return userRepository.save(user);
    }

    @Override
    public List<AppUser> all() {
        return userRepository.findAll();
    }

    @Override
    public List<AppUser> allSortByRoleProperty(String roleProperty) {
        return userRepository.findAll(Sort.by(Sort.Direction.DESC,roleProperty));
    }

    @Override
    public List<AppUser> getUserListWithRoleInString(List<AppUser> Users) {
        //Init Collection
        Collection<AppRole> appRoles;
        for (AppUser u : Users){
            //Get roles for a user
            appRoles = u.getRoles();
            //ini array list
            ArrayList<String> arrayList = new ArrayList<>();

            for (AppRole a : appRoles){
                // Get User Role Name
                String s = a.getRoleName();

                //Add in list array
                arrayList.add(s);
            }

            // Convert Array to string without bracket
            String role = Arrays.toString(arrayList.toArray()).replace("[", "").replace("]", "");

            //Set String to mesroles;
            u.setMesroles(role);
        }

        return Users;
    }

    @Override
    public AppUser findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Boolean existsByEmail(String email) {
       return userRepository.existsByEmail(email);
    }

    @Override
    public Boolean hasRole(AppUser user, String roleName) {
        List<AppRole> roles = roleService.getRolesByUser(user);
        List<String> stringRoles = new ArrayList<String>();
        for (AppRole r : roles){
            String rname = r.getRoleName();
            stringRoles.add(rname);
        }
        boolean role = false;
        role = stringRoles.contains(roleName);

        return role;
    }

    @Override
    public UserDTO userToDto(AppUser user) {


        return userMapper.userToUserDTO(user);
        //return UserMapper.INSTANCE.userToUserDTO(user);
    }

    @Override
    public AppUser findById(Long id) {
        Optional<AppUser> user = userRepository.findById(id);
        if(user.isPresent()){
            return user.get();
        }else {
            throw new EntityNotFoundException("User not found : " + id);
        }

    }

    @Override
    public AppRole findRoleByName(String roleName) {
        return roleRepository.findByRoleName(roleName);
    }

    @Override
    public AppRole findRoleById(Long id) {

        Optional<AppRole> role = roleRepository.findById(id);
        if(role.isPresent()){
            return role.get();
        }else {
            throw new EntityNotFoundException("Role not found : " + id);
        }

    }

    @Override
    public List<String> rolenames(AppUser user) {

        List<UserRole> userRoles = userRoleRepository.findByAppUser(user);
        List<String> rolenames = new ArrayList<>();
        for (UserRole ur : userRoles){
            String rolename = ur.getAppRole().getRoleName();
            rolenames.add(rolename);
        }
        return rolenames;
    }

    @Override
    public void getOneTimePassword(AppUser user) throws MessagingException, UnsupportedEncodingException, InterruptedException {

        String OTP = getRandomNumberString();
        log.info("OTP : {} ",OTP);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encodeOTP = bCryptPasswordEncoder.encode(OTP);
        user.setOtp(encodeOTP);
        user.setOtpRequestTime(new Date());
        userRepository.save(user);


        sendOTPEmail(user, OTP);
        log.info("OTP Email sent : {} ",user.getEmail());
    }

    @Override
    public void clearOTP(AppUser user) {
        user.setOtp(null);
        user.setOtpRequestTime(null);
        userRepository.save(user);
    }

    @Async
    public void sendOTPEmail(AppUser user, String OTP) throws MessagingException, UnsupportedEncodingException, InterruptedException {

        String email = user.getEmail();

        log.info("Sleeping now for 1s ...");
        //Délai de 1 secondes avant execution du code ci-dessous
        Thread.sleep(1000);
        log.info("Awake now... ");

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        String from = env.getProperty("spring.mail.username");
        helper.setFrom(from, "Authentification Support");
        helper.setTo(user.getEmail());
        String subject = "Voici votre mot de passe à usage unique (OTP) - Expire dans 5 minutes !";
        String content = "<p> Bonjour "+ user.toString() +", </p>"
                + "<p> Pour des raisons de sécurité, vous devez utiliser le mot de passe à usage unique suivant pour vous connecter.</p>"
                + "<p> <b>"+ OTP +"</b></p>"
               /* + "<br>"*/
                + "<p> Remarque : cet OTP est configuré pour expirer dans 5 minutes.</p>"
                + "<p> Merci.</p>"
                + "<p> L' administrateur. </p>";
        helper.setSubject(subject);
        helper.setText(content, true);
        mailSender.send(message);

    }

    private static String getRandomNumberString() {
        // It will generate 6 digit random Number.
        // from 0 to 999999
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        // this will convert any number sequence into 6 character.
        return String.format("%06d", number);
    }
}
