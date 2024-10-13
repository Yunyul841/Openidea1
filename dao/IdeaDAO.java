package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dto.IdeaDTO;
public class IdeaDAO {

	

		// 멤버변수 지정
		private String username = "system";
		private String password = "11111111";
		private String url = "jdbc:oracle:thin:@localhost:1521:orcl";
		private String driverName = "oracle.jdbc.driver.OracleDriver";
		private Connection conn = null;

		// ** 싱글톤 디자인 코딩 시작
		public static IdeaDAO ideadao = null;

		// 생성자를 private로 막는다.
		private IdeaDAO() {
			init(); // 드라이버 로드
		}

		public static IdeaDAO getInstance() {
			if (ideadao == null) {
				ideadao = new IdeaDAO(); // 자기자신의 객체 생성
			}
			return ideadao;
		}
		// 싱글톤 디자인 코딩 끝 **

		private void init() { // 드라이버 로드, 최초 1회만
			try {
				Class.forName(driverName);
				System.out.println("오라클 드라이버 로드 성공");
				// 드라이버 로드가 제대로 됐다면(빌드가 제대로 됐다면), 오라클 드라이버 로드 성공 메세지 출력
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

		private boolean conn() { // 커넥션 가져오는 공통 코드를 메서드로 정의
			try {
				conn = DriverManager.getConnection(url, username, password);
//				System.out.println("커넥션 자원 획득 성공");
				return true; // 커넥션 자원을 정상적으로 획득할 시
			} catch (Exception e) {
			}
			return false; // 획득 실패시
		}

		public void insert(IdeaDTO ideadto) {
			// 커넥션, 쿼리, 매핑, 전송, 리턴값처리
			if (conn()) {
				try {
					// 쿼리문 작성
					// 4-1 제안하기 insert into ideabank values(ideabank_seq.nextval,???,defaullt
					String sql = "insert into ideabank values (ideabank_seq.nextval,?,?,?,default)";
					PreparedStatement psmt = conn.prepareStatement(sql);
					// Mapping
					psmt.setString(1, ideadto.getTitle()); 
					psmt.setString(2, ideadto.getContent());
					psmt.setString(3, ideadto.getWriter());

					/*
					 * 쿼리 실행 >> DB로부터 리턴값은 받지 않는다. 등록할 때, 이런 커리문을 집어넣겠다.라는 의미 psmt.executeUpdate();
					 */
					// 쿼리 실행 >> 리턴을 받아서 다음 처리 작업 정의. 제대로 들어갔는지 확인. commit과 rollback은 트렌젝션처리
					int resultInt = psmt.executeUpdate(); // 결과가 성공한 수로 나오므로
					if (resultInt > 0) {
						conn.commit();
					} else {
						conn.rollback();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				} finally { // conn 자원 반납
					try {
						if (conn != null)
							conn.close();
					} catch (Exception e2) {
					}
				}
			} else {
				System.out.println("데이터베이스 커넥션 실패");

			}
 
		}

		public ArrayList<IdeaDTO> selectAll() {
			ArrayList<IdeaDTO> ilist = new ArrayList<IdeaDTO>();
			if (conn()) {
				try {
					// 4-5 전체보기 select * from ideabank
					String sql = "select * from ideabank";
					PreparedStatement psmt = conn.prepareStatement(sql);
					ResultSet rs = psmt.executeQuery();;
					// ResultSet은 테이블 형식으로 가져온다고 이해
					while (rs.next()) { // next() 메서드는 rs에서 참조하는 테이블에서
										// 튜플을 순차적으로 하나씩 접근하는 메서드
						IdeaDTO iTemp = new IdeaDTO();
						// rs.getString("id") >> rs가 접근한 튜플에서 id 컬럼의 값을 가져옴
						iTemp.setTitle(rs.getString("title"));
						iTemp.setNum(rs.getInt("num"));
						iTemp.setContent(rs.getString("content"));
						iTemp.setWriter(rs.getString("writer"));
						iTemp.setIndate(rs.getString("indate"));
						ilist.add(iTemp);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				} finally { // conn 자원 반납
					try {
						if (conn != null)
							conn.close();
					} catch (Exception e2) {

					}
				}
			}
			return ilist;
		}

		public void delete(int delNo) {
			if (conn()) {
				try {
					// 4-3 삭제하기 delete from ideabank where num=?
					String sql = "delete from ideabank where num=?";
					PreparedStatement psmt = conn.prepareStatement(sql);
					psmt.setInt(1, delNo);
					psmt.executeUpdate();
				} catch (Exception e) {
				} finally { // conn 자원 반납
					try {
						if (conn != null)
							conn.close();
					} catch (Exception e2) {
					}
				}
			}
		}

		public IdeaDTO selectOne(int modNum) {
			if (conn()) {
				try {
					String sql = "select * from ideabank where num = ?";
					PreparedStatement psmt = conn.prepareStatement(sql);
					psmt.setInt(1, modNum);
					ResultSet rs = psmt.executeQuery();
					if (rs.next()) {
						IdeaDTO iTemp = new IdeaDTO();
						iTemp.setTitle(rs.getString("title"));
						iTemp.setNum(rs.getInt("num"));
						iTemp.setContent(rs.getString("content"));
						iTemp.setWriter(rs.getString("writer"));
						iTemp.setIndate(rs.getString("indate"));
						return iTemp;
					}
				} catch (Exception e) {
				} finally { // conn 자원 반납
					try {
						if (conn != null)
							conn.close();
					} catch (Exception e2) {

					}
				}
			}
			return null;
		}

		public void update(IdeaDTO idto) {
			if (conn()) {
				try {
					// 4-2 수정하기 update ideabank set title = ? content=? where num=?
					String sql = "update ideabank set title=?, content=? where num=? ";
					PreparedStatement psmt = conn.prepareStatement(sql);
					psmt.setString(1, idto.getTitle());
					psmt.setString(2, idto.getContent());
					psmt.setInt(3, idto.getNum());
					psmt.executeUpdate();
				} catch (Exception e) {
				} finally {
					try {
						if (conn != null)
							conn.close();
					} catch (Exception e2) {
					}
				}
			}
		}

		public ArrayList<IdeaDTO> select(String searchW) {
			ArrayList<IdeaDTO> ilist = new ArrayList<IdeaDTO>();
			if (conn()) {
				try {
					// 4-4 검색하기 select * from ideabank where title like '&?%'
					String sql = "select * from ideabank where " + "title like '%" + searchW + "%'";
					System.out.println(sql);
					// psmt.setString(1,searchW)는 안되고 위처럼 직접 입력해야 됨
					PreparedStatement psmt = conn.prepareStatement(sql);
					ResultSet rs = psmt.executeQuery();
					// Resultset은 테이블 형식으로 가져온다고 이해합니다.
					while (rs.next()) { // next()메서드는 rs에서 참조하는 테이블에서
										// 튜플을 순차적으로 하나씩 접근하는 메서드
						IdeaDTO iTemp = new IdeaDTO();
						iTemp.setTitle(rs.getString("title"));
						iTemp.setNum(rs.getInt("num"));
						iTemp.setContent(rs.getString("content"));
						iTemp.setWriter(rs.getString("writer"));
						iTemp.setIndate(rs.getString("indate"));
						ilist.add(iTemp);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			return ilist;
		}

	}

