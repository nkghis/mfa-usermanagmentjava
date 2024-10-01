package ics.ci.mfausermanagmentjava;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync
@EnableScheduling
public class MfaUsermanagmentjavaApplication {

	public static void main(String[] args) {

		ApplicationContext ctx = SpringApplication.run(MfaUsermanagmentjavaApplication.class, args);

		/*UserRepository userRepository = ctx.getBean(UserRepository.class);
		AppUser user = userRepository.findByUserName("admin");

		Collection<AppRole> roleList = user.getRoles();
		AppRole role = new AppRole(9L,"ROLE_TEST");
		roleList.add(role);
		roleList.stream().forEach(appRole -> System.out.println(appRole.getRoleName()));
		String s ="";*/

		/*UserRepository userRepository = ctx.getBean(UserRepository.class);
		AppUser user = userRepository.findByUserName("admin");
		//UserMapper userMapper = ctx.getBean(Use""rMapper.class);
		UserService userService = ctx.getBean(UserService.class);

		//UserDTO dto = userService.userToDto(user);
		//UserDTO dto = userMapper.userToUserDTO(user);
		//UserDTO dto = UserMapper.INSTANCE.userToUserDTO(user);
		UserMapper roleMapper = ctx.getBean(UserMapper.class);

		//UserDTO dto = userService.userToDto(user);
		UserDTO dto = roleMapper.userToUserDTO(user);


		RoleRepository roleRepository = ctx.getBean(RoleRepository.class);
		RoleService roleService = ctx.getBean(RoleService.class);

		AppRole role = roleRepository.findByRoleId(1L);

		RoleDTO dtorole = roleService.roleToDTO(role);

		//RoleDTO roledto = roleMapper.roleToRoleDTO()

		String u = "";*/


	/*	String p = "123";
		String password = EncrytedPasswordUtils.encrytePassword(p);

		System.out.println("===============DEBUT TRANSACTION=======================");
		UserRepository userRepository = ctx.getBean(UserRepository.class);
		AppUser user1 = userRepository.save(new AppUser("ADMIN","Admin","admin@admin.ci",password,true, true));
		AppUser user2 = userRepository.save(new AppUser("USER","User","user@user.ci",password,true, true));

		System.out.println("===============AJOUT CLIENT=======================");
		userRepository.findAll().forEach(u->System.out.println(u.getEmail()));
		System.out.println("Utilisateurs ajoutés avec succes");
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println("===============AJOUT ROLE=======================");
		RoleRepository roleRepository = ctx.getBean(RoleRepository.class);
		AppRole roleadmin = roleRepository.save(new AppRole("ROLE_ADMIN"));
		AppRole roleuser = roleRepository.save(new AppRole("ROLE_USER"));

		roleRepository.findAll().forEach(u->System.out.println(u.getRoleName()));
		System.out.println("Roles ajoutés avec succes");
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++");

		System.out.println("===============AFFECTATION ROLE PAR USER=======================");
		UserRoleRepository userRoleRepository = ctx.getBean(UserRoleRepository.class);
		userRoleRepository.save(new UserRole(user1,roleadmin));
		userRoleRepository.save(new UserRole(user1,roleuser));
		userRoleRepository.save(new UserRole(user2,roleuser));


		System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&FIN DE LA TRANSACTION&&&&&&&&&&&&&&&&&&&");*/




		String server ="Server start on http://localhost:8098";
		System.out.println(server);


	}


}
