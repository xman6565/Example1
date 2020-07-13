package kr.co.controller;

import java.io.File;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.co.service.BoardService;
import kr.co.service.ReplyService;
import kr.co.vo.BoardVO;
import kr.co.vo.Criteria;
import kr.co.vo.PageMaker;
import kr.co.vo.ReplyVO;
import kr.co.vo.SearchCriteria;

@Controller
@RequestMapping("/board/*")
public class BoardController {
	
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	@Inject
	BoardService service;
	
	@Inject
	ReplyService replyService;
	
	// 게시판 글 작성 화면
	@RequestMapping(value="/board/writeView", method = RequestMethod.GET)
	public void writeView() throws Exception{
		logger.info("writeView");
	}
	
	// 게시판 글 작성
	@RequestMapping(value="/board/write", method = RequestMethod.POST)
	public String write(BoardVO boardVO, MultipartHttpServletRequest mpRequest) throws Exception{

		service.write(boardVO,mpRequest);
		
		return "redirect:/board/list";
	}
	
	// 게시글 목록 조회
	@RequestMapping(value="/list",method = RequestMethod.GET)
	public String list(Model model, @ModelAttribute("scri") SearchCriteria scri) throws Exception{
		logger.info("list");
		
		//이제 BoardController로 들어와서 URL은 /list로 정하고 
		//오라클 > 다오 > 서비스 > 컨트롤러로 가져온 데이터들을 jsp에 뿌려주는 작업을 해야 합니다.
		//model은 데이터를 담을 그릇이고 addAttribute("list", service.list())는 
		//service.list()에 담긴 데이터를 "list"라는 이름으로 담을것이다 라는 뜻으로 해석하시면됩니다. 
		
		
		model.addAttribute("list",service.list(scri));
		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(scri);
		pageMaker.setTotalCount(service.listCount(scri));
		
		model.addAttribute("pageMaker",pageMaker);
		
		return "board/list";
	}
	
	// 게시글 조회
	@RequestMapping(value="/readView", method=RequestMethod.GET)
	public String read(BoardVO boardVO, @ModelAttribute("scri") SearchCriteria scri,Model model) throws Exception{
		logger.info("read");
		
		//list에서 가져온 SearchCriteria값을 사용하기위해 매개변수에 파라미터를 통해 값을 받고
		//model을 이용하여 scri를 보내줍니다.
		model.addAttribute("read",service.read(boardVO.getBno()));
		model.addAttribute("scri",scri);
		
		List<ReplyVO> replyList = replyService.readReply(boardVO.getBno());
		model.addAttribute("replyList",replyList);
		
		List<Map<String,Object>> fileList = service.selectFileList(boardVO.getBno());
		model.addAttribute("file",fileList);
		return "board/readView";
	}
	
	// 게시글 수정뷰
	@RequestMapping(value="/updateView", method = RequestMethod.GET)
	public String updateView(BoardVO boardVO,@ModelAttribute("scri") SearchCriteria scri, Model model) throws Exception{
		logger.info("updateView");
		
		model.addAttribute("update",service.read(boardVO.getBno()));
		model.addAttribute("scri",scri);
		
		List<Map<String,Object>> fileList = service.selectFileList(boardVO.getBno());
		model.addAttribute("file",fileList);
		
		return "board/updateView";
	}
	
	//게시글 수정
	@RequestMapping(value="/update", method = RequestMethod.POST)
	public String update(BoardVO boardVO,
						 @ModelAttribute("scri") SearchCriteria scri,
						 RedirectAttributes rttr,
						 @RequestParam(value="fileNoDel[]") String[] files,
						 @RequestParam(value="fileNameDel[]") String[] fileNames,
						 MultipartHttpServletRequest mpRequest) throws Exception{
		logger.info("update");
		
		service.update(boardVO,files,fileNames, mpRequest);
		
		rttr.addAttribute("page",scri.getPage());
		rttr.addAttribute("perPageNum",scri.getPerPageNum());
		rttr.addAttribute("searchType", scri.getSearchType());
		rttr.addAttribute("keyword", scri.getKeyword());
		
		return "redirect:/board/list";
	}
	
	// 게시글 삭제
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public String delete(BoardVO boardVO,@ModelAttribute("scri") SearchCriteria scri,RedirectAttributes rttr) throws Exception{
		logger.info("delete");
		
		service.delete(boardVO.getBno());
		
		rttr.addAttribute("page",scri.getPage());
		rttr.addAttribute("perPageNum", scri.getPerPageNum());
		rttr.addAttribute("searchType", scri.getSearchType());
		rttr.addAttribute("keyword",scri.getKeyword());
		
		return "redirect:/board/list";
	}
	
	// 댓글 작성
	@RequestMapping(value="/replyWrite", method=RequestMethod.POST)
	public String replyWrite(ReplyVO rvo, SearchCriteria scri, RedirectAttributes rttr) throws Exception{
		logger.info("reply Write");
		
		replyService.writeReply(rvo);
		
		rttr.addAttribute("bno", rvo.getBno());
		rttr.addAttribute("page", scri.getPage());
		rttr.addAttribute("perPageNum", scri.getPerPageNum());
		rttr.addAttribute("searchType", scri.getSearchType());
		rttr.addAttribute("keyword", scri.getKeyword());
		
		return "redirect:/board/readView";
	}
	
	// 댓글 수정 GET
	@RequestMapping(value="/replyUpdateView", method=RequestMethod.GET)
	public String replyUpdateView(ReplyVO rvo, SearchCriteria scri, Model model) throws Exception{
		logger.info("replyUpdateView");
		
		model.addAttribute("replyUpdate", replyService.selectReply(rvo.getRno()));
		model.addAttribute("scri",scri);
		
		return "board/replyUpdateView";
	}
	
	// 댓글 수정 POST
	@RequestMapping(value="/replyUpdate", method=RequestMethod.POST)
	public String replyUpdate(ReplyVO rvo, SearchCriteria scri, RedirectAttributes rttr) throws Exception{
		logger.info("replyUpdate");
		
		replyService.updateReply(rvo);
		
		rttr.addAttribute("bno",rvo.getBno());
		rttr.addAttribute("page",scri.getPage());
		rttr.addAttribute("perPageNum",scri.getPerPageNum());
		rttr.addAttribute("searchType", scri.getSearchType());
		rttr.addAttribute("keyword",scri.getKeyword());
		
		return "redirect:/board/readView";
	}
	
	// 댓글 삭제 GET
	@RequestMapping(value="/replyDeleteView", method=RequestMethod.GET)
	public String replyDeleteView(ReplyVO rvo, SearchCriteria scri, Model model) throws Exception{
		logger.info("replyDeleteView");
		
		model.addAttribute("replyDelete", replyService.selectReply(rvo.getRno()));
		model.addAttribute("scri",scri);
		
		return "board/replyDeleteView";
	}
	
	// 댓글 삭제 POST
	@RequestMapping(value="/replyDelete", method=RequestMethod.POST)
	public String replyDelete(ReplyVO rvo, SearchCriteria scri, RedirectAttributes rttr) throws Exception{
		logger.info("replyDelete");
		
		replyService.deleteReply(rvo);
		
		rttr.addAttribute("bno",rvo.getBno());
		rttr.addAttribute("page",scri.getPage());
		rttr.addAttribute("perPageNum", scri.getPerPageNum());
		rttr.addAttribute("searchType", scri.getSearchType());
		rttr.addAttribute("keyword",scri.getKeyword());
		
		return "redirect:/board/readView";
	}
	
	// 첨부파일 다운
	@RequestMapping(value="/fileDown")
	public void fileDown(@RequestParam Map<String, Object> map, HttpServletResponse response) throws Exception{
		Map<String,Object> resultMap = service.selectFileInfo(map);
		System.out.println("resultMap : " + resultMap);
		String storedFileName = (String) resultMap.get("STORED_FILE_NAME");
		System.out.println("storedFileName : " + storedFileName);
		String originalFileName = (String) resultMap.get("ORG_FILE_NAME");
		System.out.println("originalFileName : " + originalFileName);
		// 파일을 저장했던 위치에서 첨부파일을 읽어 byte[] 형식으로 변환한다.
		byte fileByte[] = FileUtils.readFileToByteArray(new File("C:\\Users\\nuriapp\\eclipse-workspace\\file\\" + storedFileName));
		
		response.setContentType("applcation/octet-stream");
		response.setContentLength(fileByte.length);
		response.setHeader("Content-Disposition", "attachment; fileName=\""+URLEncoder.encode(originalFileName, "UTF-8")+"\";");
		response.getOutputStream().write(fileByte);
		response.getOutputStream().flush();
		response.getOutputStream().close();
	}
	
}
