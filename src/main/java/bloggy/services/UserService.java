package bloggy.services;

import java.util.List;


import bloggy.dtos.CredentialsDto;
import bloggy.dtos.SignUpDto;
import bloggy.dtos.UserDto;
import bloggy.models.User;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserService {

	public UserDto login(CredentialsDto credentialsDto);
	public UserDto register(SignUpDto userDto);
	public UserDto findByEmail(String email);


} 