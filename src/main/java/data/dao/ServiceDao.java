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
		PreparedStatement ps = null; //id���ĺ��� ���Ӱ��߰�
		String sql ="insert into service (category, writer, open, mobile, email, subject, contents, file, writeday, id, "
				+ "ref, pos, depth)"
				+ " values (?,?,?,?,?,?,?,?,now(),?,?,0,0)";
		//�亯�� ���� ref����
		int ref=getMaxNum()+1;
		System.out.println(ref + " refüũ");
		
		try {
			ps = conn.prepareStatement(sql);
			//���ε�
			ps.setString(1, dto.getCategory());
			ps.setString(2, dto.getWriter());
			ps.setString(3, dto.getOpen());
			ps.setString(4, dto.getMobile());
			ps.setString(5, dto.getEmail());
			ps.setString(6, dto.getSubject());
			ps.setString(7, dto.getContents());
			ps.setString(8, dto.getFile());
			ps.setString(9, dto.getId());
			ps.setInt(10, ref);
			//����
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
//	public int getTotalCount() {
//		int n = 0;
//		Connection conn = db.getConnection();
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		String sql ="select count(*) from service";
//		
//		try {
//			ps = conn.prepareStatement(sql);
//			rs = ps.executeQuery();
//			if(rs.next()) {
//				n = rs.getInt(1);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			db.dbClose(ps, conn);
//		}
//		return n;
//	}
	//Totalcount of contents
	public int getTotalCount(String keyField, String keyWord) {
		Connection con = db.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int totalCount = 0;
		try {
			if(keyWord.trim().equals("")||keyWord==null) {
				//�˻��� �ƴѰ��
				sql = "select count(*) from service";
				pstmt = con.prepareStatement(sql);
			}else {
				//�˻��� ���
				sql = "select count(*) from service where " 
				+ keyField +" like ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, "%"+keyWord+"%");
			}
			rs = pstmt.executeQuery();
			if(rs.next()) totalCount = rs.getInt(1);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.dbClose(rs, pstmt, con);
		}
		return totalCount;
	}
	//Take Contents from table as much as you want
	public List<ServiceDto> getList(String keyFiled, String keyWord, int start, int perpage) {
		Connection conn = db.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<ServiceDto> list = new ArrayList<ServiceDto>();
		String sql ="";
		try {
			if(keyWord.trim().equals("")||keyWord==null) {
				//�˻��� �ƴҶ�
				sql = "select*from service order by ref desc, pos limit ?,?";
				ps = conn.prepareStatement(sql);
				ps.setInt(1, start);
				ps.setInt(2, perpage);
			}else {
				//�˻��϶�
				sql = "select*from service where "+keyFiled+" like ? order by ref desc, pos limit ?,?";
				ps = conn.prepareStatement(sql);
				ps.setString(1, "%"+keyWord+"%");//�ش� Ű���带 �˻��Ѵٴ� ��
				ps.setInt(2, start);
				ps.setInt(3, perpage);
			}
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
				int pos = rs.getInt("pos");
				int ref = rs.getInt("ref");
				int depth = rs.getInt("depth");
				ServiceDto dto = new ServiceDto(num, category, writer, open, mobile, email, subject,
						contents, views, file, status, writeday, id, pos, ref, depth);
				//list�� �߰�
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.dbClose(ps, conn);
		}
		return list;
	}
	
	//num�� �ش��ϴ� dto ��ȯ
	public ServiceDto getData(String number) {
		ServiceDto dto = new ServiceDto();
		Connection conn = db.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql ="select * from service where num = ?";
		
		try {
			ps = conn.prepareStatement(sql);
			//���ε�
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
				int pos = rs.getInt("pos");
				int ref = rs.getInt("ref");
				int depth = rs.getInt("depth");
				dto = new ServiceDto(num, category, writer, open, mobile, email, subject,
						contents, views, file, status, writeday, id, pos, ref, depth);
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
			//���ε�
			ps.setString(1, num);
			//����
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
			//���ε�
			ps.setString(1, dto.getCategory());
			ps.setString(2, dto.getOpen());
			ps.setString(3, dto.getMobile());
			ps.setString(4, dto.getEmail());
			ps.setString(5, dto.getSubject());
			ps.setString(6, dto.getContents());
			ps.setString(7, dto.getFile());
			ps.setString(8, dto.getNum());
		
			//����
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
	
	//�߰��Ǵ� �޼���
	//Board Max Num : num �� �ִ밪 (�亯�� ���� �޼ҵ�)
	public int getMaxNum() {
		Connection con = db.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int maxNum = 0;
		try {
			sql = "select max(num) from service";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) maxNum = rs.getInt(1);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.dbClose(rs, pstmt, con);
		}
		System.out.println(maxNum + "�߽��ѹ��Դϴ�.");
		return maxNum;
	}
	
	//�亯�� �Է� (�߿��ϰ� �����)
	public void replyBoard(ServiceDto dto) {
		Connection con = db.getConnection();
		PreparedStatement ps = null;
		String sql ="insert into service (category, writer, open, mobile, email, subject, contents, file, writeday, id, "
				+ "ref, pos, depth)"
				+ " values (?,?,?,?,?,?,?,?,now(),?,?,?,?)";
		try {
			ps = con.prepareStatement(sql);
			//���ε�
			ps.setString(1, dto.getCategory());
			ps.setString(2, dto.getWriter());
			ps.setString(3, dto.getOpen());
			ps.setString(4, dto.getMobile());
			ps.setString(5, dto.getEmail());
			ps.setString(6, dto.getSubject());
			ps.setString(7, dto.getContents());
			ps.setString(8, dto.getFile());
			ps.setString(9, dto.getId());
			//ref, pos, depth �߿�
			//ref �� �亯 �۵��� �׷� �÷��̴�.
			ps.setInt(10, dto.getRef());//�Է��Ҷ� ���۰� ������ ref ������ �����ų��
			ps.setInt(11, dto.getPos()+1);//������ pos +1
			ps.setInt(12, dto.getDepth()+1);//������ depth +1
			int cnt = ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.dbClose(ps, con);
		
		}
	}
	
	//Board Reply Up : �亯�� ��ġ�� ����
	public void replyUpBoard(int ref, int pos) {
		Connection con = db.getConnection();
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			sql = "update service set pos=pos+1 where ref=? and pos>?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, ref);
			pstmt.setInt(2, pos);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.dbClose(pstmt, con);
		}
	}
	
}
