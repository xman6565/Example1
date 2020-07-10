package kr.co.dao;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import kr.co.vo.MemberVO;

@Repository
public class MemberDAOImpl implements MemberDAO{
	
	@Inject
	SqlSession sqlSession;
	// 회원가입
	@Override
	public void register(MemberVO mvo) throws Exception {
		sqlSession.insert("memberMapper.register",mvo);
	}
	
	// 로그인
	@Override
	public MemberVO login(MemberVO mvo) throws Exception {
		return sqlSession.selectOne("memberMapper.login",mvo);
	}
	
	// 회원정보 수정
	@Override
	public void memberUpdate(MemberVO mvo) throws Exception {
		sqlSession.update("memberMapper.memberUpdate",mvo);
	}
	
	// 회원탈퇴
	@Override
	public void memberDelete(MemberVO mvo) throws Exception {
		sqlSession.delete("memberMapper.memberDelete",mvo);
	}

	// 패스워드 체크
	@Override
	public int passChk(MemberVO mvo) throws Exception {
		
		int result = sqlSession.selectOne("memberMapper.passChk",mvo);
		return result;
	}
	
	// 아이디 중복확인
	@Override
	public int idChk(MemberVO mvo) throws Exception {
		int result = sqlSession.selectOne("memberMapper.idChk",mvo);
		
		return result;
	}
	
}
