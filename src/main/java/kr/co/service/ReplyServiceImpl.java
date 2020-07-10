package kr.co.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.co.dao.ReplyDAO;
import kr.co.vo.ReplyVO;

@Service
public class ReplyServiceImpl implements ReplyService{
	
	@Inject
	ReplyDAO rdao;
	
	// 댓글 조회
	@Override
	public List<ReplyVO> readReply(int bno) throws Exception {
		return rdao.readReply(bno);
	}

	// 댓글 작성
	@Override
	public void writeReply(ReplyVO rvo) throws Exception {
		rdao.writeReply(rvo);
	}

	// 댓글 수정
	@Override
	public void updateReply(ReplyVO rvo) throws Exception {
		rdao.updateReply(rvo);
	}

	@Override
	public void deleteReply(ReplyVO rvo) throws Exception {
		rdao.deleteReply(rvo);
	}

	@Override
	public ReplyVO selectReply(int rno) throws Exception {
		return rdao.selectReply(rno);
	}

}
