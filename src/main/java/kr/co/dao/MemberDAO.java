package kr.co.dao;

import kr.co.vo.MemberVO;

public interface MemberDAO {
	
	// 회원가입
	public void register(MemberVO mvo) throws Exception;
	
	// 로그인
	public MemberVO login(MemberVO mvo) throws Exception;
	
	// 회원정보 수정
	public void memberUpdate(MemberVO mvo) throws Exception;
	
	// 회원탈퇴
	public void memberDelete(MemberVO mvo) throws Exception;
	
	// 패스워드 체크
	public int passChk(MemberVO mvo) throws Exception;
	
	// 아이디 중복체크
	public int idChk(MemberVO mvo) throws Exception;
} 
