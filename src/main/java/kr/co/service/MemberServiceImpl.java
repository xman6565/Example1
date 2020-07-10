package kr.co.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.co.dao.MemberDAO;
import kr.co.vo.MemberVO;

@Service
public class MemberServiceImpl implements MemberService{
	
	@Inject
	MemberDAO mdao;
	
	//회원가입
	@Override
	public void register(MemberVO mvo) throws Exception {
		mdao.register(mvo);
	}
	
	// 로그인
	@Override
	public MemberVO login(MemberVO mvo) throws Exception {
		return mdao.login(mvo);
	}
	
	// 회원정보 수정
	@Override
	public void memberUpdate(MemberVO mvo) throws Exception {
		mdao.memberUpdate(mvo);
	}
	
	// 회원탈퇴
	@Override
	public void memberDelete(MemberVO mvo) throws Exception {
		mdao.memberDelete(mvo);
	}
	
	// 비밀번호 체크
	@Override
	public int passChk(MemberVO mvo) throws Exception {
		
		int result = mdao.passChk(mvo);
		return result;
	}
	
	// 아이디 중복확인
	@Override
	public int idChk(MemberVO mvo) throws Exception {
		int result = mdao.idChk(mvo);
		return result;
	}
}
