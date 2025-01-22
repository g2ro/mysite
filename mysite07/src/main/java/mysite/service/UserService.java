package mysite.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import mysite.repository.UserRepository;
import mysite.vo.UserVo;

@Service
public class UserService {
	private PasswordEncoder passwordEncoder;
	private UserRepository userRepository;
	
	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public void join(UserVo userVo) {
		userVo.setPassword(passwordEncoder.encode(userVo.getPassword()));
		userRepository.insert(userVo);
	}
	
	public UserVo getUser(String email, String password) {
		return userRepository.findByEmailAndPassword(email, password);
	}

	public UserVo getUser(Long id) {
		return userRepository.findById(id);
	}

	public UserVo getUser(String email) {
		UserVo userVo = userRepository.findByEmail(email, UserVo.class);
		userVo.setPassword(""); //네트워크 상에 password가 다니는게 좋지 못함. 내부에서 처리를 하면 되기 때문에 객체를 받고 난후 set을 통해 password를 가려준다. 
		return userVo;
	}
	
	public void update(UserVo userVo) {
		userRepository.update(userVo);
		
	}



}
