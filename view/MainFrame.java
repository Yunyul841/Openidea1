package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import dao.IdeaDAO;
import dto.IdeaDTO;



	// is a 관점으로 상속 받음
	// 콘솔로 나타내는 것 말고 view를 따로 분리하는 것.
	// service단에 있는 화면에 관련된 것만 이곳으로 분리한다. 이를 위해 package를 분리하였다.
	// 화면에 관련된 기능이 JFrame. JFrame을 상속받아서 이 프로그램의 view를 만든다.

	// 인터페이스 : implement >> 기능을 구현하기 위해서 사용한다(ActionListener라는 기능을 구현받았다)
	public class MainFrame extends JFrame implements ActionListener {
		// 인터페이스 구현 - 기능을 처리하기 위해. 콤마 찍고 뒤에 다른 리스너 추가 가능

		// 글자 객체
		private JLabel title = new JLabel("IdeaBank");

		// 글자 입력 공간
		private JTextField input = new JTextField();

		// 리스트 >> false로 입력 시, 하나만 선택 가능하게 됨
		private List wordList = new List(5, true);

		// 버튼
		private JButton btn = new JButton("Save");

		private JButton btn1 = new JButton("버튼1");
		private JButton btn2 = new JButton("버튼2");

		private JPanel centerP = new JPanel();
		// panel은 컨테이너이면서 컴포넌트이다. 기본 레이아웃은 flow Layout

		private IdeaDAO ideaDao = IdeaDAO.getInstance(); // DB등록 위해
//		private IdeaService is = new IdeaService();		// IdeaService로 가서 dao로 가도록

		public MainFrame() {

			// this 대신 super 가능
			this.setBounds(100, 100, 300, 500); // 위치, 위치, 가로, 세로

			// 컨테이너는 컴포넌트를 배치시킨다. 컨테이너는 레이아웃이 있다.
			// JFrame은 컨테이너이고, 기본 레이아웃은 border layout이다.
			// border layout은 하나의 공간에 하나의 컴포넌트만 가능하다. >> cencter를 두 번 하면 먼저 한건 없어짐. >> 패널을
			// 이용해서 동시 두 개 배치 가능
			// title을 North자리에, input을 South자리에 wordList를 Center자리에 배치한다.
			this.add(title, "North");
			this.add(btn, "South");
			// 가운데 패널
			centerP.setLayout(new BorderLayout());
			// 꽉 차게 만들기 위해서 Layout을 BorderLayout으로 변환
			centerP.setBackground(Color.ORANGE);
			centerP.add(wordList, "Center");
			centerP.add(input, "South");
			this.add(centerP, "Center");
//			this.add(wordList, "Center");

//			this.add(btn1,"East");
//			this.add(btn2,"West");

			// 리스너 등록
			btn.addActionListener(this);
			input.addActionListener(this);

			// true면 화면에 보이게, false는 안보이게(프로그램 종료는 아님)
			this.setVisible(true);
			// 창을 닫으면 프로그램을 자동 종료해라(없으면 x눌러서 창 닫아도 실행중인 상태)
			this.setDefaultCloseOperation(EXIT_ON_CLOSE);

			loadDB();

		}

		// *** 실행시 view에 DB 데이터 출력위한 메서드
		private void loadDB() {
			ArrayList<IdeaDTO> ideadto = ideaDao.selectAll();
			for (IdeaDTO i : ideadto) {
				wordList.add(i.toString());
				// wordList.add(i.getNum() + " : " + i.getTitle());
			}
		}

		// 객체를 보면 component와 container를 구분.
		// component >> 화면을 구성하는 요소(JLabel, JTextField, List)
		// container >> 요소를 배치하는 기능(JFrame)

		@Override // *** 메서드 재정의(add unimplemented methods) >> 액션 리스너가 이 메서드를 처리. 개발하고자 하는 동작을 구현
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == btn || e.getSource() == input) { // 발생한 이벤트가 btn이라면 아래를 실행하라
//				System.out.println("버튼이 클릭 됨");
				String t = input.getText();
//				System.out.println("입력하신 글은 : " + t);

				input.setText("");

				// 리스트에 내용 등록
//				wordList.add(t);

				// *** DB 등록 위해
				IdeaDTO dto = new IdeaDTO();
				dto.setTitle(t);
				ideaDao.insert(dto);
			}

		}

		// view를 gui로 구현
	}


