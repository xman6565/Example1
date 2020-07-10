package kr.co.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.co.service.MemberService;
import kr.co.vo.MemberVO;

@Controller
@RequestMapping("/member/*")
public class MemberController {
	
	private static final Logger logger =LoggerFactory.getLogger(MemberController.class);

	@Inject
	MemberService mService;
	
	@Autowired
	BCryptPasswordEncoder pwdEncoder;
	
	// 회원가입 get
	//회원가입 폼으로 이동할때에는 GET메서드를 타고 회원가입 버튼을 눌렀을때 POST메서드를 타게끔 작성해줍니다.
	@RequestMapping(value="/register",method=RequestMethod.GET)
	public void getRegister() throws Exception{
		logger.info("get register");
	}
	
	// 회원가입 post
	@RequestMapping(value="/register", method = RequestMethod.POST)
	public String postRegister(MemberVO mvo) throws Exception{
		logger.info("post register");
		int result = mService.idChk(mvo);
		try {
			if(result == 1) {
				return "/member/register";
			}else if(result == 0) {
				//회원가입 요청이 들어오면 비밀번호를 암호화하여 vo에 다시 넣어줍니다. 그리고 회원가입 서비스를 실행합니다.
				String inputPass = mvo.getUserPass();
				String pwd= pwdEncoder.encode(inputPass);
				mvo.setUserPass(pwd);
				
				mService.register(mvo);
			}
			// 여기에서~ 입력된 아이디가 존재한다면 -> 다시 회원가입 페이지로 돌아가기
			// 존재하지 않는다면 -> register
		}catch(Exception e) {
			throw new RuntimeException();
		}
		
		return "redirect:/";
	}
	
	// 로그인
	@RequestMapping(value="/login",method = RequestMethod.POST)
	public String login(MemberVO mvo, HttpSession session, RedirectAttributes rttr) throws Exception{
		logger.info("post login");
		
		session.getAttribute("member");
		MemberVO login = mService.login(mvo);
		boolean pwdMatch = pwdEncoder.matches(mvo.getUserPass(), login.getUserPass());
		
		if(login != null && pwdMatch == true) {
			session.setAttribute("member", login);
			
		}else {
			session.setAttribute("member", null);
			rttr.addFlashAttribute("msg",false);
		}
		
		return "redirect:/";
	}
	
	// 로그아웃
	@RequestMapping(value="/logout",method = RequestMethod.GET)
	public String logout(HttpSession session) throws Exception{
		session.invalidate();
		
		return "redirect:/";
	}
	
	// 회원정보 수정뷰
	@RequestMapping(value="/memberUpdateView", method = RequestMethod.GET)
	public String registerUpdateView() throws Exception{
		logger.info("==== memberUpdateView ====");
		
		return "member/memberUpdateView";
	}
	
	// 회원정보 수정
	@RequestMapping(value="/memberUpdate", method = RequestMethod.POST)
	public String registerUpdate(MemberVO mvo, HttpSession session) throws Exception{
		logger.info("==== memberUpdate ====");
		
		mService.memberUpdate(mvo);
		session.invalidate();
		
		return "redirect:/";
	}
	
	// 회원 탈퇴 GET
	@RequestMapping(value="/memberDeleteView", method = RequestMethod.GET)
	public String memeberDeleteView() throws Exception{
		return "member/memberDeleteView";
	}
	
	// 회원 탈퇴 POST
	@RequestMapping(value="/memberDelete", method = RequestMethod.POST)
	public String memeberDelete(MemberVO mvo, HttpSession session, RedirectAttributes rttr) throws Exception{
		
//		// 세션에 있는 member를 가져와 member변수에 넣어준다.
//		MemberVO member = (MemberVO)session.getAttribute("member");
//		// 세션에 있는 비밀번호
//		String sessionPass = member.getUserPass();
//		// vo로 들어오는 비밀번호
//		String mvoPass = mvo.getUserPass();
//		
//		if(!(sessionPass.equals(mvoPass))) {
//			rttr.addFlashAttribute("msg",false);
//			return "redirect:/member/memberDeleteView";
//		}
		mService.memberDelete(mvo);
		session.invalidate();
		return "redirect:/";
	}
	
	// 패스워드 체크
	@ResponseBody
	@RequestMapping(value="/passChk",method = RequestMethod.POST)
	public int passChk(MemberVO mvo) throws Exception{
		int result = mService.passChk(mvo);
		return result;
	}
	
	// 패스워드 체크
	@ResponseBody
	@RequestMapping(value="/idChk",method = RequestMethod.POST)
	public int idChk(MemberVO mvo) throws Exception{
		int result = mService.idChk(mvo);
		return result;
	}
}
