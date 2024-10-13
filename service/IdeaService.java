package service;

import java.util.ArrayList;
import java.util.Scanner;

import dao.IdeaDAO;
import dto.IdeaDTO;

public class IdeaService {
	// 서비스를 제공하는 기능(요청에 따라 데이터베이스 작업 후, 응답)
	// 역할 : 요청 처리, CRUD작업 요청, 응답화면
	

		// IdeaDAO 클래스를 싱글톤으로 디자인.
		// IdeaService에서는 객체를 만들지 않고 공용변수를 통해서 주소를 가져온다.
		private IdeaDAO ideadao = IdeaDAO.getInstance();

		// generate constructor from superclass (source우클릭 생성자 자동 생성)
		public IdeaService() {
			menu();
		}

		private void menu() {
			Scanner in = new Scanner(System.in);
			boolean flag = true;
			while (flag) {
				System.out.println("1. 등록 2. 삭제 3. 수정 4. 전체보기 5. 검색 6. 종료");
				int selNum = in.nextInt();
				in.nextLine();
				switch (selNum) {
				case 1:
					ideaAdd();
					break; // break는 switch문을 멈춰라
				case 2:
					ideaDel();
					break;
				case 3:
					ideaMod();
					break;
				case 4:
					ideaList();
					break;
				case 5:
					ideaSearch();
					break;
				case 6:
					flag = false;
					break;
				}

			}

		}

		private void ideaAdd() { // 추가 쿼리 >> insert
//			System.out.println("신규 아이디어 등록");		>> MainFrame에서 입력 받아 DB 업로드 기능을 추가하여 없어도 됨
//			Scanner in = new Scanner(System.in);
//			System.out.println("제목 입력");
//			String title = in.nextLine();
//			System.out.println("내용 입력");
//			String content = in.nextLine();
//			System.out.println("작성자 입력<작성자는 수정 불가>");
//			String writer = in.nextLine();
			IdeaDTO ideadto = new IdeaDTO();
//			ideadto.setTitle(title);
//			ideadto.setContent(content);
//			ideadto.setWriter(writer);
			// dao 객체에게 ideadto 객체의 주소를 전달
			// dao에게 insert 작업 요청, 매개변수값으로 ideadto
			ideadao.insert(ideadto);
//			english
//			korean
		}

		private void ideaDel() { // 전체보기 실행 후, 삭제 >> ideaTitleList() 호출 후, 삭제
			Scanner in = new Scanner(System.in);
			// 1. 번호와 제목을 보여준다.
			ideaTitleList();
			// 2. 삭제할 번호 선택
			System.out.println("삭제할 번호 선택");
			int delNo = in.nextInt();
			in.nextLine();
			// 3. DB에서 삭제
			ideadao.delete(delNo);

		}

		private void ideaMod() { // 전체보기 실행 후, 수정 >> ideaTitleList() 호출 후, 수정
			Scanner in = new Scanner(System.in);
			// 1. 번호와 제목 보여준다.
			ideaTitleList();
			// 2. 수정할 번호 선택
			System.out.println("수정할 번호 선택");
			int modNum = in.nextInt();
			in.nextLine();
			// 3. 수정할 정보 가져오기
			IdeaDTO moddto = ideadao.selectOne(modNum);
			System.out.println("현재 정보");
			System.out.println(moddto.toString());
			// 4. 수정할 데이터 입력하기
			System.out.println("수정할 제목 입력");
			String title = in.nextLine();
			moddto.setTitle(title);
			// 5. DB에 적용하기
			ideadao.update(moddto);
		}

		private void ideaList() { // 전체보기 >> select
			ArrayList<IdeaDTO> idealist = ideadao.selectAll();
			for (IdeaDTO t : idealist) {
				System.out.println(t.toString());
			}
		}

		private void ideaSearch() { // 검색 쿼리
			Scanner in = new Scanner(System.in);
			System.out.println("검색어를 입력 하세요");
			String sw = in.nextLine();
			ArrayList<IdeaDTO> idealist = ideadao.select(sw);
			for (IdeaDTO t : idealist) {
				System.out.println(t.toString());
			}
		}

		private void ideaTitleList() { // 제목과 번호보기
			ArrayList<IdeaDTO> idealist = ideadao.selectAll();
			for (IdeaDTO t : idealist) {
				// ArrayList로부터 가져와서 num과 title만 보여준다.
				System.out.println(t.getNum() + " : " + t.getTitle());
			}
		}

	}


