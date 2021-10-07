<%@page import="data.dao.LoginDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8 ">
<title>회원탈퇴</title>
<link href="https://fonts.googleapis.com/css2?family=Dokdo&family=Gaegu&family=Gugi&family=Nanum+Pen+Script&display=swap" rel="stylesheet">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<link rel="stylesheet" href="../css/login.css">
<script src="https://code.jquery.com/jquery-3.5.0.js"></script>
</head>
<body>
<%
	request.setCharacterEncoding("utf-8"); //get 일때는 사용안해도됨
	String key = request.getParameter("key");
	if(key == null){
		//아이디 입력폼
%>
	<form action="deleteMember.jsp" method = "post" class = "form-inline">
		<input type = "hidden" name = "key" value = "result">
		<!-- <table class = "table table-bordered">
			<tr height="100">
				<td>
					<img alt="" src="../image2/s5.JPG" width = "70" align="left">
					<br>
					<b>이름을 입력해주세요</b> <br>
					<input type = "text" name = "name" class = "form-control" style = "width: 100px;"
					 autofocus = "autofocus" required="required">
					 <br>
					 <button type = "submit" class = "btn btn-info btn-sm">중복체크</button>
				</td>
			</tr>		
		</table> -->
		<input type="text" name = "pw" id ="nameCheck" placeholder="비밀번호 재확인" required >
		<button type = "submit" class = "baseBtn" id = "sameNameCheckBtn" >비밀번호확인</button>
	</form> 
	<%} else {
		//이름 db체크
		//이름읽기
		String pw = request.getParameter("pw");
		//dao 선언
		LoginDao dao = new LoginDao();
		//이름이 db 에 존재하는지 체크
		boolean userPass = dao.userInfoCheck(3, pw); 
		if(false){%>
			<script type="text/javascript">
				alert("비밀번호가 일치하지 않습니다.");
				location.href = "deleteMember.jsp";
			</script>
		<%} else {%>
		    <input type="text" name = "pw" id ="nameCheck" placeholder="비밀번호 재입력" readonly value = "사용 가능한 이름입니다." >
			<button type = "submit" class = "baseBtn" id = "btnuse" >탈퇴하기</button>
		<%}
	}
%>		
<script>
	$("#btnuse").click(function() {
		//현재창 닫기
		window.close();
		location.href = "logout/logoutAction.jsp";
		
	})
</script>
</body>
</html>