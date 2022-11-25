package com.himedia.board.domain;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString(exclude = "boardList") // 순환 참조 문제 해결하기 위해
@Entity
public class Member {
    @Id
    @Column(name="MEMBER_ID")
    private String id;

    private String password;

    private String name;

    @Enumerated(EnumType.STRING) //회원의 권한 정보를 문자열로 저장하기 위해서 
    private Role role;

    private boolean enabled;  //스프링 시큐리티에서 사용자 사용 여부를 제어할 때 사용

    @OneToMany(mappedBy = "member", fetch = FetchType.EAGER)  //일대다(1:N) 관계 매팅, FetchType.EAGER는 Member가 조회될 때 관련된 Board 목록도 같이 조회
    private List<Board>boardList= new ArrayList<Board>();


}
