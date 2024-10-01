package ics.ci.mfausermanagmentjava.service.impl;

import ics.ci.mfausermanagmentjava.dto.RoleDTO;
import ics.ci.mfausermanagmentjava.entity.AppRole;
import ics.ci.mfausermanagmentjava.entity.AppUser;
import ics.ci.mfausermanagmentjava.entity.UserRole;
import ics.ci.mfausermanagmentjava.mapper.RoleMapper;
import ics.ci.mfausermanagmentjava.repository.RoleRepository;
import ics.ci.mfausermanagmentjava.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
@Transactional
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    private final RoleMapper roleMapper;

    public RoleServiceImpl(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    @Override
    public AppRole create(AppRole role) {

        log.info("Role added : " +role.getRoleName().toUpperCase());
        return roleRepository.save(role);
    }

    @Override
    public AppRole findByRoleName(String roleName) {
        return roleRepository.findByRoleName(roleName);
    }

    @Override
    public List<AppRole> all() {
        return roleRepository.findAll();
    }

    @Override
    public List<AppRole> allSortByRoleProperty(String roleProperty) {
        return roleRepository.findAll(Sort.by(Sort.Direction.DESC,roleProperty));
    }

    @Override
    public List<AppRole> findByRoleNameIsNot(String roleName) {
        return roleRepository.findByRoleNameIsNot(roleName);
    }

    @Override
    public AppRole getById(Long id) {
        return roleRepository.getOne(id);
    }

    @Override
    public Boolean deleteById(Long id) {
        AppRole role = this.getById(id);
        roleRepository.deleteById(id);
        log.info("Role deleted : " +role.getRoleName().toUpperCase());
        return true;
    }

    @Override
    public List<AppRole> getRolesByUser(AppUser user) {
        Collection<UserRole> userroles = user.getUserRoles();
        List<AppRole> roles = new ArrayList<AppRole>();
        for (UserRole userrole :userroles){
            AppRole r = userrole.getAppRole();
            roles.add(r);
        }
        return roles;
    }

    @Override
    @Transactional(readOnly=true)
    public List<String> getRoleNames(List<AppRole> roles) {
        List<String> roleNames = new ArrayList<>();

        for (AppRole a : roles){
            // Get User Role Name
            String s = a.getRoleName();

            //Add in list array
            roleNames.add(s);
        }
        return roleNames;
    }

    @Override
    public RoleDTO roleToDTO(AppRole role) {


        return roleMapper.roleToRoleDTO(role);
    }
}
