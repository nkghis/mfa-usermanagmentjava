package ics.ci.mfausermanagmentjava.service;

import ics.ci.mfausermanagmentjava.dto.UserDTO;
import ics.ci.mfausermanagmentjava.entity.AppRole;
import ics.ci.mfausermanagmentjava.entity.AppUser;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface UserService {

    AppUser create(AppUser user);

    List<AppUser> all();

    List<AppUser> allSortByRoleProperty(String roleProperty);

    List<AppUser> getUserListWithRoleInString(List<AppUser> Users);

    AppUser findByEmail(String email);

    Boolean existsByEmail(String email);

    Boolean hasRole(AppUser user, String roleName);

    UserDTO userToDto(AppUser user);
    AppUser findById(Long id);

    //Role
    AppRole findRoleByName(String roleName);
    AppRole findRoleById(Long id);
    List<String> rolenames (AppUser user);

    void getOneTimePassword(AppUser user) throws MessagingException, UnsupportedEncodingException, InterruptedException;

    void clearOTP(AppUser user);
}
