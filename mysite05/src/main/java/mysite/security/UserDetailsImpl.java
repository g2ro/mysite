package mysite.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import mysite.vo.UserVo;

@SuppressWarnings("serial")
public class UserDetailsImpl extends UserVo implements UserDetails {

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
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
