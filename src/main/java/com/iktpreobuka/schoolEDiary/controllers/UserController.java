package com.iktpreobuka.schoolEDiary.controllers;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.schoolEDiary.controllers.utils.Encryption;
import com.iktpreobuka.schoolEDiary.controllers.utils.RESTError;
import com.iktpreobuka.schoolEDiary.entities.AdminEntity;
import com.iktpreobuka.schoolEDiary.entities.UserEntity;
import com.iktpreobuka.schoolEDiary.entities.DTO.AdminDTO;
import com.iktpreobuka.schoolEDiary.entities.DTO.UserDTO;
import com.iktpreobuka.schoolEDiary.repositories.AdminRepository;
import com.iktpreobuka.schoolEDiary.repositories.UserRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO : Razmisli da prebacis da bude 1 zajednicki Controller za sve klase koje extenduju UserEntity. Pa kada stigne UserDTO, da tu odlucis koji je tip, da li Teacher, Student, Admin...
// TODO : Razmisli da renameujes ovo, mozda niej dobro da se Controller za login zove ovako? Mada je i on u materijalima
// TODO : Resi i izbrisi sve TODOs do ispita :)

@RestController
public class UserController {

	@Value("${spring.security.secret-key}")
	private String secretKey;

	@Value("${spring.security.token-duration}")
	private Integer tokenDuration;

	@Autowired
	UserRepository userRepository;

	@Autowired
	AdminRepository AdminRepository;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(method = RequestMethod.POST, path = "/login")
	public ResponseEntity<?> login(@RequestParam("user_email") String email, @RequestParam("password") String pwd) {
		logger.info("Received login request from user with e-mail : " + email);
		UserEntity userEntity = userRepository.findByEmail(email).get();
		if (userEntity != null && Encryption.validatePassword(pwd, userEntity.getPassword())) {
			String token = getJWTToken(userEntity);
			UserDTO user = new UserDTO();
			user.setEmail(email);
			user.setToken(token);
			logger.info("Successfull login ot the user : " + userEntity.toString());
			return new ResponseEntity<>(user, HttpStatus.OK);
		}
		logger.info("Unsuccesfull login of user with e-mail : " + email);
		return new ResponseEntity<>("Wrong credentials", HttpStatus.UNAUTHORIZED);
	}

	/**
	 * End-point that doesn't need authorization, so the Admin with "ROLE_ADMIN" can
	 * be added to database. Afterwards, the Admin can add all other users to the
	 * database
	 * 
	 * @param teacher
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/addAdmin")
	public ResponseEntity<?> addAdmin(@RequestBody AdminDTO adminDTO) {

		// perform check if same username is used in database
		// !subjectRepository.findByName(newSubject.getName()).equals(null)
		if (AdminRepository.findByUsername(adminDTO.getUsername()).isPresent()) {
			return new ResponseEntity<RESTError>(new RESTError(440, "Username already used, choose another."),
					HttpStatus.FORBIDDEN);
		}
		// TODO: proveri da li pasvord postoji?!

		// check for matching password and repeatedPassword
		if (adminDTO.getPassword().equals(adminDTO.getRepeatedPassword())) {
			return new ResponseEntity<RESTError>(new RESTError(441, "Passwords not matching"), HttpStatus.FORBIDDEN);  // TODO : Prebaci ove provere da se odradjuju u Valdidation objektu sa strane. A neke mogu da se rese i JSON anotacijama
		}

		AdminEntity admin = new AdminEntity();

		admin.setUsername(adminDTO.getUsername());
		admin.setEmail(adminDTO.getEmail());
		// adding encrypted password into database
		admin.setPassword(Encryption.getPassEncoded(adminDTO.getPassword()));
		admin.setRole(adminDTO.getRole());
		admin.setFirstName(adminDTO.getFirstName());
		admin.setLastName(adminDTO.getLastName());

		return new ResponseEntity<AdminEntity>(AdminRepository.save(admin), HttpStatus.OK);
	}

	private String getJWTToken(UserEntity userEntity) {
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils
				.commaSeparatedStringToAuthorityList(userEntity.getRole().getName());
		String token = Jwts.builder().setId("softtekJWT").setSubject(userEntity.getEmail())
				.claim("authorities",
						grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + this.tokenDuration))
				.signWith(SignatureAlgorithm.HS512, this.secretKey.getBytes()).compact();
		return "Bearer " + token;
	}

}
