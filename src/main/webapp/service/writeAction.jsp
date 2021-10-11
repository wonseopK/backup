<%@page import="data.dto.ServiceDto"%>
<%@page import="data.dao.ServiceDao"%>
<%@page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy"%>
<%@page import="com.oreilly.servlet.MultipartRequest"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	//세션으로부터 db에 저장할 아이디 얻기
	
	String myid = (String)session.getAttribute("myid");
	
	System.out.println(myid); 
	//이미지가 업로드될 실제 경로 구하기
	String realPath = getServletContext().getRealPath("/files");
	System.out.println(realPath); 
	
	int uploadSize = 1024*1024*4;//1mb *4
	MultipartRequest multi = null;
	
	try{
		multi = new MultipartRequest(request, realPath, uploadSize, "utf-8", new DefaultFileRenamePolicy());
		//(주의)request 가 아닌 multi 변수로 모든 폼데이터를 읽어야한다
		String category = multi.getParameter("category");
		String writer = multi.getParameter("writer");
		String open = multi.getParameter("open");
		String mobile = multi.getParameter("mobile");
		if(mobile.equals("")){
			mobile = "미입력";
		}
		String mail = multi.getParameter("mail");
		if(mail.equals("")){
			mail = "미입력";
		}
		String subject = multi.getParameter("subject");
		String contents = multi.getParameter("contents");
		String file = multi.getFilesystemName("file");//실제 업로드될 파일명 
		if(file == null){
			file = "미입력";
		}
		
		//check of the values
		System.out.println(category);
		System.out.println(writer);
		System.out.println(open);
		System.out.println(mobile);
		System.out.println(mail);
		System.out.println(subject);
		System.out.println(contents);
		System.out.println(file);
			
		//dto 에 저장
		ServiceDto dto = new ServiceDto(category, writer, open, mobile, mail, subject, contents, file, myid);
		System.out.println(myid + "2");
		//dao 선언
		ServiceDao dao = new ServiceDao();
		//insert
		dao.insertContent(dto);
		//방명록으로 이동
		String path = "../index.jsp?main=service/qnalist.jsp";
		response.sendRedirect(path);
	} catch (Exception e){
		System.out.println("업로드오류:" + e.getMessage());
	}
%>