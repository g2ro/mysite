package mysite.repository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import mysite.security.UserDetailsImpl;
import mysite.vo.UserVo;

public class UserDetailsServiceImpl implements UserDetailsService {
	
	private final UserRepository userRepository;
	
	public UserDetailsServiceImpl(UserRepository userRepository) {
		this.userRepository =userRepository;
	}
	@Override
	public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByEmail(username, UserDetailsImpl.class);
		// 오버로드가 안되는 이유는 리턴 타입이 달라지기 때문에 다음과 같이 리턴 타입을 지정하여 진행한다.

	}

}
