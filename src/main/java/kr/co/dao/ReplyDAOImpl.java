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

}
