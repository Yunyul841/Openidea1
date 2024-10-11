package service;

import java.util.ArrayList;
import java.util.Scanner;

import dao.IdeaDAO;
import dto.IdeaDTO;

public class IdeaService {
	
	private IdeaDAO ideadao=IdeaDAO.getInstance();
	
		//IdeaDAO 클래스를 싱클톤으로 디자인. 
		//IdeaService에서는 객체를 만들지 않고 공용변수를 통해서 주소를 가져온다.
		
		public IdeaService() {
			menu();
		}
		
		private void menu() {	
			Scanner in = new Scanner(System.in);
			boolean flag = true;
			while(flag) {
				System.out.println("1.등록 2.삭제 3.수정 4.전체 5.검색 6.종료");
				int selNum = in.nextInt();
				in.nextLine();
				switch(selNum) {
					case 1: ideaAdd(); break;  // break는 switch문을 멈춰라
					case 2: ideaDel(); break;
					case 3: ideaMod(); break;
					case 4: ideaList(); break;
					case 5: ideaSearch(); break;
					case 6: flag=false; break;
				}
			}
			in.close();
		}
		private void ideaAdd() {	
			System.out.println("신규 아이디어 등록 하세요");
			Scanner in = new Scanner(System.in);
			System.out.println("제목입력하세요");
			String title = in.nextLine();
			System.out.println("내용입력하세요");
			String content = in.nextLine();
			System.out.println("작성자입력<작성자는 안되요>");
			String writer = in.nextLine();
			IdeaDTO ideadto = new IdeaDTO();
			ideadto.setTitle(title);
			ideadto.setContent(content);
			ideadto.setWriter(writer);
			//dao 객체에게 ideadto객체의 주소를 전달
			ideadao.insert(ideadto);
			
		}
		private void ideaDel() {	
			Scanner in = new Scanner(System.in);
			// 번호와 제목을 보여준다..
			ideaTitleList();
			// 삭제할 번호 선택
			System.out.println("삭제할 번호를 선택하세요");
			int delNo = in.nextInt();
			in.nextLine();
			// 디비에서 삭제	
			ideadao.delete(delNo);
		}
		private void ideaMod() {
			Scanner in = new Scanner(System.in);
			//  번호와 제목 보여주기
			ideaTitleList();
			//  수정 번호 선택
			int modNum = in.nextInt();
			in.nextLine();
			// 수정할 정보 가져오기
			IdeaDTO moddto = ideadao.selectOne(modNum);		
			System.out.println("현재 정보");
			System.out.println(moddto.toString());
			// 수정할 데이터 입력하기
			System.out.println("제목수정하세요");
			String title = in.nextLine();
			moddto.setTitle(title);
			// 디비에 적용하기
			//System.out.println(moddto.toString());
			ideadao.update(moddto);
			
		}
		private void ideaList() {  // 전체보기		
			ArrayList<IdeaDTO> idealist = ideadao.selectAll();
			for(IdeaDTO t : idealist) {
				System.out.println(t.toString());
			}
		}
		private void ideaSearch() {	
			Scanner in = new Scanner(System.in);
			System.out.println("검색어를 입력 하세요");
			String sw = in.nextLine();
			ArrayList<IdeaDTO> idealist = ideadao.select(sw);
			for(IdeaDTO t : idealist) {
				System.out.println(t.toString());
			}
		}
		
		private void ideaTitleList() { // 제목과 번호보기	
			ArrayList<IdeaDTO> idealist = ideadao.selectAll();
			for(IdeaDTO t : idealist) {
				System.out.println(t.getNum() +" : "+t.getTitle());
			}
		}
	}


