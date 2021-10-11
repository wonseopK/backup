package data.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import data.dto.ServiceDto;
import mysql.db.DbConnect;

public class ServiceDao {
	DbConnect db = new DbConnect();
	//CREATE
	//insert
	public boolean insertContent(ServiceDto dto) {
		boolean check = false;
		Connection conn = db.getConnection();
		PreparedStatement ps = null;
		String sql ="insert into service (category, writer, open, mobile, email, subject, contents, file, writeday, id)"
				+ " values (?,?,?,?,?,?,?,?,now(),?)";
		
		try {
			ps = conn.prepareStatement(sql);
			//바인딩
			ps.setString(1, dto.getCategory());
			ps.setString(2, dto.getWriter());
			ps.setString(3, dto.getOpen());
			ps.setString(4, dto.getMobile());
			ps.setString(5, dto.getEmail());
			ps.setString(6, dto.getSubject());
			ps.setString(7, dto.getContents());
			ps.setString(8, dto.getFile());
			ps.setString(9, dto.getId());
			//실행
			check = ps.execute();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.dbClose(ps, conn);
		}
		return check;
	}
	
	
	//READ
	//Totalcount of contents
	public int getTotalCount() {
		int n = 0;
		Connection conn = db.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql ="select count(*) from service";
		
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next()) {
				n = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.dbClose(ps, conn);
		}
		return n;
	}
	//Take Contents from table as much as you want
	public List<ServiceDto> getList(int start, int perpage) {
		List<ServiceDto> list = new ArrayList<ServiceDto>();
		Connection conn = db.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql ="select * from service order by num desc limit ?, ?";
		
		try {
			ps = conn.prepareStatement(sql);
			//바인딩
			ps.setInt(1, start);
			ps.setInt(2, perpage);
			rs = ps.executeQuery();
			while(rs.next()) {
				String num = rs.getString("num");
				String category = rs.getString("category");
				String writer = rs.getString("writer");
				String open = rs.getString("open");
				String mobile = rs.getString("mobile");
				String email = rs.getString("email");
				String subject = rs.getString("subject");
				String contents = rs.getString("contents");
				int views = rs.getInt("views");
				String file = rs.getString("file");
				String status = rs.getString("status");
				Timestamp writeday = rs.getTimestamp("writeday");
				String id = rs.getString("id");
				ServiceDto dto = new ServiceDto(num, category, writer, open, mobile, email, subject,
						contents, views, file, status, writeday, id);
				//list에 추가
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.dbClose(ps, conn);
		}
		return list;
	}
	
	//num에 해당하는 dto 반환
	public ServiceDto getData(String number) {
		ServiceDto dto = new ServiceDto();
		Connection conn = db.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql ="select * from service where num = ?";
		
		try {
			ps = conn.prepareStatement(sql);
			//바인딩
			ps.setString(1, number);
			rs = ps.executeQuery();
			if(rs.next()) {
				String num = rs.getString("num");
				String category = rs.getString("category");
				String writer = rs.getString("writer");
				String open = rs.getString("open");
				String mobile = rs.getString("mobile");
				String email = rs.getString("email");
				String subject = rs.getString("subject");
				String contents = rs.getString("contents");
				int views = rs.getInt("views");
				String file = rs.getString("file");
				String status = rs.getString("status");
				Timestamp writeday = rs.getTimestamp("writeday");
				String id = rs.getString("id");
				dto = new ServiceDto(num, category, writer, open, mobile, email, subject,
						contents, views, file, status, writeday, id);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.dbClose(ps, conn);
		}
		return dto;
	}
	
	
	//UPDATE
	//updateReadCount
	public void updateViewsCount(String num) {
		Connection conn = db.getConnection();
		PreparedStatement ps = null;
		String sql ="update service set views = views+1 where num = ?";
		
		try {
			ps = conn.prepareStatement(sql);
			//바인딩
			ps.setString(1, num);
			//실행
			ps.execute();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.dbClose(ps, conn);
		}
	}
	//update Q&A detail
	public boolean updateDetailInfo(ServiceDto dto) {
		boolean check = false;
		Connection conn = db.getConnection();
		PreparedStatement ps = null;
		String sql ="update service set category=?, open=?, mobile=?, email=? , subject=? , contents=?, file=? where num=?";
		
		try {
			ps = conn.prepareStatement(sql);
			//바인딩
			ps.setString(1, dto.getCategory());
			ps.setString(2, dto.getOpen());
			ps.setString(3, dto.getMobile());
			ps.setString(4, dto.getEmail());
			ps.setString(5, dto.getSubject());
			ps.setString(6, dto.getContents());
			ps.setString(7, dto.getFile());
			ps.setString(8, dto.getNum());
		
			//실행
			check = ps.execute();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.dbClose(ps, conn);
		}
		return check;
	}
	
	
	//DELETE
	//DELETE CONTENT BY WRITER
	public boolean deleteContent(String num) {
		boolean check = false;
		Connection conn = db.getConnection();
		PreparedStatement ps = null;
		String sql = "delete from service where num = ?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, num);
			check = ps.execute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.dbClose(ps, conn);
		}
		return check;
	}
}
