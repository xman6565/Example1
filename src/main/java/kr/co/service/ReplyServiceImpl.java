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

}
