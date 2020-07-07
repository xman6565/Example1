<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판</title>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
</head>
<script type="text/javascript">
	$(document).ready(function(){
		var formObj = $("form[name='writeForm']");
		$(".write_btn").on("click",function(){
			if(fn_valiChk()){
				return false;
			}
			formObj.attr("action","/board/write");
			formObj.attr("method","post");
			formObj.submit();
		});
	})
	function fn_vailChk(){
		var regForm = $("form[name='writeForm'] .chk").length;
		for(var i = 0; i<regForm; i++){
			if($(".chk").eq(i).val() == "" || $(".chk").eq(i).val() == null){
				alert($(".chk").eq(i).attr("title"));
				return true;
			}
		}
	}
</script>
<body>
	<div id="root">
		<header>
			<h1>게시판</h1>
		</header>
		<hr/>
		
		<div>
			<%@include file="nav.jsp" %>
		</div>
		<hr/>
		
		<section id="container">
			<form name = "writeForm" method="post" action="/board/write">
				<table>
					<tbody>
						<tr>
							<td>
								<label for="title">제목</label><input type="text" id="title" name="title" class="chk" title="제목을 입력하세요"/>
							</td>
						</tr>
						<tr>
							<td>
								<label for="subject">내용</label><textarea id="subject" name="subject" class="chk" title="내용을 입력하세요"></textarea>
							</td>
						</tr>
						<tr>
							<td>
								<label for="writer">작성자</label><input type="text" id="writer" name="writer" class="chk" title="작성자를 입력하세요"/>
							</td>
						</tr>
						<tr>
							<td>
								<button class="write_btn" type="submit">작성</button>
							</td>
						</tr>
					</tbody>
				</table>
			</form>
		</section>
	</div>
</body>
</html>