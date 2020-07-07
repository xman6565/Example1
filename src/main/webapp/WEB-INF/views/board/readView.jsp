<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<title>게시판</title>
</head>
<script type="text/javascript">
	$(document).ready(function(){
		var formObj = $("form[name='readForm']");
		
		// 수정
		$(".update_btn").on("click",function(){
			formObj.attr("action","/board/updateView");
			formObj.attr("method","get")
			formObj.submit();
		})
		
		// 삭제
		$(".delete_btn").on("click",function(){
			
			var deleteYN = confirm("삭제하시겠습니까");
			if(deleteYN == true){
			
			formObj.attr("action","/board/delete");
			formObj.attr("method","post");
			formObj.submit();
			}
		})
		
		// 목록
		$(".list_btn").on("click", function(){
			location.href="/board/list?page=${scri.page}"
						 + "$perPageNum=${scri.perPageNum}"
						 + "$searchType=${scri.searchType}$keyword=${scri.keyword}";
			
		})
		
		// 취소
		$(".list_btn").on("click",function(){
			
			location.href="/board/list";
		})
	})
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
			<form name="readForm" role="form" method="post">
					<input type="hidden" id="bno" name="bno" value="${read.bno}" />
					<input type="hidden" id="page" name="page" value="${scri.page}" />
					<input type="hidden" id="perPageNum" name="perPageNum" value="${scri.perPageNum}"/>
					<input type="hidden" id="searchType" name="searchType" value="${scri.searchType}"/>
					<input type="hidden" id="keyword" name="keyword" value="${scri.keyword}"/>
			</form>
				<table>
					<tr>
						<td>
							<label for="title">제목</label><input type="text" id="title" name="title" value="${read.title}"  readonly="readonly"/>
						</td>
					</tr>
					<tr>
						<td>
							<label for="subject">내용</label><textarea id="subject" name="subject" readonly="readonly"><c:out value="${read.subject}" /></textarea>
						</td>
					</tr>
					<tr>
						<td>
							<label for="writer">작성자</label><input type="text" id="writer" name="writer" value="${read.writer}"  readonly="readonly"/>
						</td>
					</tr>
					<tr>
						<td>
							<label for="regdate">작성날짜</label>
							<fmt:formatDate value="${read.regdate}" pattern="yyyy-MM-dd"/>
						</td>
					</tr>
				</table>
				<div>
					<button type="submit" class="update_btn">수정</button>
					<button type="submit" class="delete_btn">삭제</button>
					<button type="submit" class="list_btn">목록</button>
				</div>
				
				<!-- 댓글 -->
				<div id="reply">
					<ol class="replyList">
						<c:forEach items="${replyList}" var="replyList">
							<li>
								<p>
									작성자 : ${replyList.writer}<br/>
									작성 날짜 : <fmt:formatDate value="${replyList.regdate}" pattern="yyyy-MM-dd"/>
								</p>
								<p>${replyList.subject}</p>
							</li>
						</c:forEach>
					</ol>
				</div>
		</section>
		<hr/>
	</div>
</body>
</html>