package kr.co.dao;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import kr.co.vo.ReplyVO;

@Repository
public class ReplyDAOImpl implements ReplyDAO{
	
	@Inject
	SqlSession sqlSession;
	
	// 댓글 조회
	@Override
	public List<ReplyVO> readReply(int bno) throws Exception {
		return sqlSession.selectList("replyMapper.readReply", bno);
	}
	
	// 댓글 작성
	@Override
	public void writeReply(ReplyVO rvo) throws Exception {
		sqlSession.insert("replyMapper.writeReply",rvo);
	}
	
	// 댓글 수정
	@Override
	public void updateReply(ReplyVO rvo) throws Exception {
		sqlSession.update("replyMapper.updateReply",rvo);
	}

	// 댓글 삭제
	@Override
	public void deleteReply(ReplyVO rvo) throws Exception {
		sqlSession.delete("replyMapper.deleteReply",rvo);
	}
	
	// 선택된 댓글 조회
	@Override
	public ReplyVO selectReply(int rno) throws Exception {
		return sqlSession.selectOne("replyMapper.selectReply",rno);
	}

}
