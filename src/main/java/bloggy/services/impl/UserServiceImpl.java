package bloggy.services.impl;

import java.nio.CharBuffer;
import java.util.Optional;

import bloggy.dtos.CredentialsDto;
import bloggy.dtos.SignUpDto;
import bloggy.dtos.UserDto;
import bloggy.services.UserService;
import bloggy.exceptions.AppException;
import lombok.RequiredArgsConstructor;
import bloggy.mappers.UserMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import bloggy.models.User;
import bloggy.repositories.UserRepository;
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	private final UserMapper userMapper;

	public UserDto login(CredentialsDto credentialsDto) {
		User user = userRepository.findByEmail(credentialsDto.getEmail())
				.orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

		if (passwordEncoder.matches(CharBuffer.wrap(credentialsDto.getPassword()), user.getPassword())) {
			return userMapper.toUserDto(user);

		}
		throw new AppException("Invalid password", HttpStatus.BAD_REQUEST);
	}

	public UserDto register(SignUpDto userDto) {
		Optional<User> optionalUser = userRepository.findByEmail(userDto.getEmail());

		if (optionalUser.isPresent()) {
			throw new AppException("Email already exists", HttpStatus.BAD_REQUEST);
		}

		User user = userMapper.signUpToUser(userDto);
		user.setLastName(userDto.getLastName());
		user.setPassword(passwordEncoder.encode(CharBuffer.wrap(userDto.getPassword())));

		User savedUser = userRepository.save(user);

		return userMapper.toUserDto(savedUser);
	}

	public UserDto findByEmail(String email) {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
		return userMapper.toUserDto(user);
	}

}