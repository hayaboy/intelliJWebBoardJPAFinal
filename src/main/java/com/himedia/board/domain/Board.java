package com.himedia.board.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@ToString(exclude = "member") // 순환 참조 문제 해결하기 위해
@Entity
public class Board {
    @Id
    @GeneratedValue
    private Long seq;
    private String title;
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false) //JPA가 수정 작업 처리시 JPA 구현체인 하이버네이트가 수정 SQL에 해당 칼럼을 포함하지 않도록 설정
    private Date createDate = new Date();

    @Column(updatable = false)
    private Long cnt = 0L;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID", nullable = false, updatable = false)  // @JoinColumn 외래키 관리,  nullable = false 내부 조인(Inner Join)
    private Member member;

    public void setMember(Member member) {
        this.member = member;
        member.getBoardList().add(this);
    }




}
