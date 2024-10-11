package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MainFrame extends JFrame  implements ActionListener{
	private JLabel title = new JLabel("IdeaBank");
	private JTextField input = new JTextField();
	private List wordList = new List(5,true);
	private JButton btn = new JButton("Sane");
	private JButton btn1 = new JButton("East");
	private JButton btn2 = new JButton("West");
	private JPanel centerP = new JPanel();
	// Panel은 컨테이너이면서 컴포넌트이다. 기본 레이아웃이 flowLayout이다.
	
	
	
	
	public MainFrame() {

		this.setBounds(100, 100, 300, 500);
		// 컨테이너는 컴포넌트를 배치시킨다, 컨테이너는 레이아웃이 있다.
		// JFrame은 컨테이너이고, 기본 레이아웃은 border layout 이이다.
		// border layout 은 하나의 공간에 하나의 컴포넌트만 가능하다.
		this.add(title,"North");
		this.add(btn,"South");
		// 가운데 패널
		centerP.setLayout(new BorderLayout());
		centerP.setBackground(Color.red);
		centerP.add(wordList,"Center");
		centerP.add(input,"South");
		this.add(centerP,"Center");
		//this.add(btn,"Sane");
		//this.add(btn1,"East");
		//this.add(btn2,"West");
		
		btn.addActionListener(this);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}




	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btn) {
			System.out.println("버튼이 클릭 됨");
			String t = input.getText();
			System.out.println("입력하신 글은 : " +t);
		}
	}
}
