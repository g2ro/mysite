package mysite.security;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import mysite.vo.UserVo;

@SuppressWarnings("serial")
public class UserDetailsImpl extends UserVo implements UserDetails {

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority("ROLE_" + getRole())); // 1개만 있을 때 
//		return Arrays.asList(new SimpleGrantedAuthority("ROLE_" + getRole())); // 여러개
	}

	@Override
	public String getUsername() {
		return getEmail();
	}
	
	
	// UserDetails에서 이미 작성되었지만 명시적으로 보여 주기 위해 다음 코드를 작성
//	@Override
//	public String getPassword() {
//		return getPassword();
//	}

}
