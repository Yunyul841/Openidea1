package dao;
	import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import dto.IdeaDTO;

public class IdeaDAO {
	private String username = "system";
	private String password = "11111111";
	private String url = "jdbc:oracle:thin:@localhost:1521:orcl";
	private String driverName = "oracle.jdbc.driver.OracleDriver";
	private Connection conn = null; // 커넥션 자원 변수
	
	// 싱클톤 디자인 코딩 시작
	public static IdeaDAO ideadao=null;
	private IdeaDAO() {
		init();
	}
	public static IdeaDAO getInstance() {
		if(ideadao == null) {
			ideadao = new IdeaDAO();
		}
		return ideadao;
	}
	// 싱글톤 디자인 코딩 끝
	
	private void init() { // 드라이버 로드
		try {
			Class.forName(driverName);
			System.out.println("오라클 드라이버 로드 성공");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private boolean conn() { // 커넥션 가져오는 공통 코드를 메서드로 정의
		try {
			conn = DriverManager.getConnection(url,username,password);
			System.out.println("커넥션 자원 획득 성공");
			return true; // 커넥션 자원을 정상적으로 획득 할시
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false; // 커넥션 자원을 획득하지 못한 경우.
	}
	public void insert(IdeaDTO ideadto) {
		// 커넥션, 쿼리, 매핑, 전송, 리턴값처리
		if(conn()) {
			try {
				String sql ="insert into ideabank values"
						+" (ideabank_seq.nextval, ?,?,?, default)";
				PreparedStatement psmt = conn.prepareStatement(sql);
				psmt.setString(1, ideadto.getTitle());
				psmt.setString(2, ideadto.getContent());
				psmt.setString(3, ideadto.getWriter());
				//쿼리 실행
				//psmt.executeUpdate();
				//쿼리 실행 리턴을 받아서 다음 처리 작업 정의
				int resultInt = psmt.executeUpdate();
				if(resultInt > 0 ) {
					conn.commit();
				}else {
					conn.rollback();
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	public ArrayList<IdeaDTO> selectAll(){
		ArrayList<IdeaDTO> flist = new ArrayList<IdeaDTO>();
		if(conn()) {
			try {
				String sql="select * from ideabank";
				PreparedStatement psmt = conn.prepareStatement(sql);
				ResultSet rs =psmt.executeQuery();
				//Resultset은 테이블 형식으로 가져온다고 이해합니다.
				while(rs.next()) {  //next()메서드는 rs에서 참조하는 테이블에서
					                // 튜플을 순차적으로 하나씩 접근하는 메서드
					IdeaDTO iTemp = new IdeaDTO();
					iTemp.setTitle(rs.getString("title"));
					iTemp.setNum(rs.getInt("num"));
					iTemp.setContent(rs.getString("content"));
					iTemp.setWriter(rs.getString("writer"));
					iTemp.setIndate(rs.getString("indate"));
					flist.add(iTemp);
				}
			} catch (SQLException e) {e.printStackTrace();}
		}		
		return flist;
	}
	public ArrayList<IdeaDTO> select(String searchW){
		ArrayList<IdeaDTO> flist = new ArrayList<IdeaDTO>();
		if(conn()) {
			try {
				String sql="select * from ideabank where "+
						"title like '%"+searchW+"%'";
				System.out.println(sql);
				PreparedStatement psmt = conn.prepareStatement(sql);
				ResultSet rs =psmt.executeQuery();
				//Resultset은 테이블 형식으로 가져온다고 이해합니다.
				while(rs.next()) {  //next()메서드는 rs에서 참조하는 테이블에서
					                // 튜플을 순차적으로 하나씩 접근하는 메서드
					IdeaDTO iTemp = new IdeaDTO();
					iTemp.setTitle(rs.getString("title"));
					iTemp.setNum(rs.getInt("num"));
					iTemp.setContent(rs.getString("content"));
					iTemp.setWriter(rs.getString("writer"));
					iTemp.setIndate(rs.getString("indate"));
					flist.add(iTemp);
				}
			} catch (SQLException e) {e.printStackTrace();}
		}		
		return flist;
	}	
	
	public void delete(int delId) {
		if(conn()) {
			try {
				String sql = "delete from ideabank where num=?";
				PreparedStatement psmt = conn.prepareStatement(sql);
				psmt.setInt(1, delId);
				psmt.executeUpdate();
			} catch (Exception e) {
				// TODO: handle exception
			} finally {
				try {
					if(conn !=null) conn.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		}
	}
	public IdeaDTO selectOne(int findId){
		if(conn()) {
			try {
				String sql="select * from ideabank where num=?";
				PreparedStatement psmt = conn.prepareStatement(sql);
				psmt.setInt(1, findId);
				ResultSet rs =psmt.executeQuery();
				if(rs.next()) {  // 쿼리 결과가 튜플 하나일 경우는 이렇게 해도 됨
					IdeaDTO iTemp = new IdeaDTO();
					iTemp.setNum(rs.getInt("num"));
					iTemp.setTitle(rs.getString("title"));
					iTemp.setContent(rs.getString("content"));
					iTemp.setWriter(rs.getString("writer"));
					iTemp.setIndate(rs.getString("indate"));
					return iTemp;
				}
			} catch (Exception e) {			} //finally에 conn 자원 반납코드 추가
		}
		return null;
	}
	public void update(IdeaDTO fdto) {
		if(conn()) {
			try {
				String sql ="update ideabank set"+
			                " title=?,content=? where num=?";
				PreparedStatement psmt = conn.prepareStatement(sql);
				psmt.setString(1, fdto.getTitle());
				psmt.setString(2, fdto.getContent());
				psmt.setInt(3, fdto.getNum());
				psmt.executeUpdate();
			} catch (Exception e) {
				// TODO: handle exception
			} finally {
				//각자 채워 주시길
			}
			
		}
	}
}
