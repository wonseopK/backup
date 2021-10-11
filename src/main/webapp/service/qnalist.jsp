<%@page import="data.dto.ServiceDto"%>
<%@page import="java.util.List"%>
<%@page import="data.dao.ServiceDao"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8 ">
<script src="https://code.jquery.com/jquery-3.5.0.js"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<title>CUSTOMERSERVICE</title>
<style>
body{
 background-color: rgba(5,20,31,0.03);
 }
.baseBtn {
	width: 185px;
	height: 40px;
	background-color: #333;
	border: 1px solid #333;
	cursor: pointer;
	color: white;
	
}
.baseBtn:hover {
	background-color: rgb(118, 118, 118);
	color: white;
	border: 1px solid rgb(118, 118, 118);
}
.write{
	margin-top: 20px;
	margin-left: 900px;
}
.inner{
    width:1200px;
    margin: 0 auto;
    position: relative;
    padding-top:10px;
	    
   	background-color: white;
}
.inner .title{
	margin-top: 80px;
    width: 982px;
    text-align: center;
    font-size: 30px;
    color: #05141f;
    line-height: 30px;
    margin-left: 109px;
    box-sizing: border-box;
    
}
.inner .container{
   	margin-top:30px;
    margin-left: 30px;
    font-size: 16px;
    text-align: center;
    box-sizing: border-box;
    border-collapse: collapse;
}
.inner .container tr{
    height: 66px;;
}
.inner .container th{
    
    background-color: #333;
	color: white;
    text-align: center;
}
.inner .container td{
    border-bottom: 1px solid #EEEEEE;
}
.inner .container td a:hover{
	cursor: pointer;
}
.inner .container .col1,.col2,.col4,.col5,.col6, .col7{
    width: 100px;
}
.inner .container .col3{
    width: 470px;;
}
.inner .page{
	width: 200px;
	margin-top:30px;
	margin-left:450px;
	width: 300px;
/* 	background-color: pink; */
	margin-bottom: 20px;
	
}
.inner .page li a{
	margin: 0 5px; 
	border: 1px solid #333;
	color: #333;
}
.inner .page li a{
	margin: 0 5px; 
	border: 1px solid #333;
	color: #333;
}
.inner .page .pagination .active a {
    background-color: rgb(118, 118, 118);
    color: white;
}

</style>
</head>
<%	
	String login = (String)session.getAttribute("loginok");
	String myid = (String)session.getAttribute("myid");
	ServiceDao dao = new ServiceDao();
	//페이징 처리에 필요한 변수선언
	//한페이지에 나타낼 글의수
	int perPage = 10; //한페이지에 보여질 글의 갯수
	int start; //각페이지에서 불러올 db의 시작번호
	int totalCount; // 총 글의 수
	
	//페이지 목록 숫자
	int totalPage; //총 페이지수
	int currentPage; //현재 페이지 번호
	int perBlock = 5; //몇개의 페이지번호씩 표현할것인가
	int startPage; //각 블럭에 표시할 시작페이지
	int endPage; //각 블럭에 표시할 마지막페이지
	
	//총 갯수
	totalCount = dao.getTotalCount();
	
	//현재 페이지 번호 읽기 (단 null 일 경우 1페이지로 설정)
	if(request.getParameter("currentPage") == null){
		currentPage = 1;
	}else {
		currentPage = Integer.parseInt(request.getParameter("currentPage"));
	}
	
	//총페이지 갯수 구하기
	totalPage = totalCount/perPage + (totalCount%perPage == 0 ? 0 : 1);
	
	//각 블럭의 시작페이지
	startPage = (currentPage-1)/perBlock*perBlock+1;
	endPage = startPage+perBlock-1;
	
	if(endPage>totalPage){
		endPage = totalPage;
	}
	
	//각 페이지에서 불러올 시작번호
	start = (currentPage-1)*perPage; //오라클은 첫번이1이라 1더해야함
	//각페이지에서 필요한 게시글 가져오기
	List<ServiceDto> list = dao.getList(start, perPage);
	
	if(list.size()==0 && totalCount>0){
		//현재페이지의 list가 더이상없을 경우 이전페이지의 데이터를 가져온다
	%>
		<script>
		location.href="index.jsp?main=service/qnalist.jsp?currentPage=<%=currentPage-1%>"
		</script>
	<%}
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	//각페이지에 출력할 시작번호
	int no = totalCount-(currentPage-1)*perPage;
%>
<body>

<div class = "inner">
  <div class="title">
    <h1>K-CAR</h1>
  </div>
	<table class = "container">
		<tr>
			<th class="col1">번호</th>
			<th class="col2">카테고리</th>
			<th class="col3">제목</th>
			<th class="col4">진행상태</th>
			<th class="col5">작성자</th>
			<th class="col6">등록일</th>
			<th class="col7">조회수</th>
		</tr>
    	<%for(ServiceDto dto:list){%>
    	<tr>
    		<td><%=no--%></td>
    		<td><%=dto.getCategory()%></td>
    		<td>
    			<a class = "detail" num = <%=dto.getNum()%>>
    			<%-- href = "index.jsp?main=service/detail.jsp?num=<%=dto.getNum()%>&currentPage=<%=currentPage%>&key=list" --%>
				<%
				if(dto.getOpen().equals("no")){%>	
					<span class = "glyphicon glyphicon-lock lock2" >
						<input type = "hidden" value = "<%=dto.getId()%>" name = "lock" class = "lock">
					</span>	
					
				<%}%> <%=dto.getSubject()%></a>
				
    		</td>
    		<td><%=dto.getStatus()%></td>
    		<td><%=dto.getWriter()%></td>
    		<td><%=sdf.format(dto.getWriteday())%></td>
    		<td><%=dto.getViews()%></td>
    	</tr>
    	<%}%>
    
    
	</table>
	<div class = "write">
		<button type="button" class="baseBtn writeBtn">작성하기</button>
	</div>
	<!-- 페이징 -->
<div class = "page">
	<ul class="pagination">
	<%
	//이전
	if(startPage>1)
	{%>
		<li>
			<a href="index.jsp?main=service/qnalist.jsp?currentPage=<%=startPage-1%>"><span class = "glyphicon glyphicon-chevron-left"></span></a>
		</li>
	<%}
	  
	for(int pp=startPage;pp<=endPage;pp++)
	{
		if(pp==currentPage)//현재페이지일때 active
		{%>
			<li class="active">
			<a href="index.jsp?main=service/qnalist.jsp?currentPage=<%=pp%>"><%=pp%></a>
			</li>
		<%}else{%>
			<li>
			<a href="index.jsp?main=service/qnalist.jsp?currentPage=<%=pp%>"><%=pp%></a>
			</li>
		<%}
	}
	
	//다음
	if(endPage<totalPage)
	{%>
		<li>
			<a href="index.jsp?main=service/qnalist.jsp?currentPage=<%=endPage+1%>"><span class = "glyphicon glyphicon-chevron-right"></span></a>
		</li>
	<%}
	%>  
	</ul>
</div>
</div>
<script>
		$(".detail").click(function(){
			let num = $(this).attr("num")
			if($(this).children().find(".lock").attr("name") === "lock"){
				if("<%=myid%>" === $(this).children().find(".lock").val() || "<%=myid%>" === "master" ){
					location.href = "index.jsp?main=service/detail.jsp?num="+num+"&currentPage=<%=currentPage%>&key=list";
				}else{
					alert("작성자와 관리자만 접근이 가능합니다.")
					return;
				}
			}else{
				location.href = "index.jsp?main=service/detail.jsp?num="+num+"&currentPage=<%=currentPage%>&key=list";
			}
		});
		
		$(".writeBtn").click(function() {
			if("<%=login%>" != 'yes'){
				alert("로그인이 필요한 페이지입니다.")
				location.href = 'index.jsp?main=login/loginMain.jsp';
				return;
			}else{
			 location.href = 'index.jsp?main=service/writeqna.jsp';
			}
		})
		
</script>

</body>
</html>