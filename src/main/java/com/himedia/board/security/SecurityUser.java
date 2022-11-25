package com.himedia.board.security;

import com.himedia.board.domain.Member;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;   //Models core user information retrieved by a UserDetailsService. UserDetailsService :  Core interface which loads user-specific data.


public class SecurityUser extends User {     //UserDetails 구현 (Provides core user information)

    private static final long serialVersionUID = 1L;
    private Member member;

    public SecurityUser(Member member) { //JPA로 검색한 회원 정보로 부모 클래스의 변수들을 초기화
        super(member.getId(), member.getPassword(), AuthorityUtils.createAuthorityList(member.getRole().toString()));
        this.member = member;
    }

    public Member getMember() {
        return member;
    }

}
