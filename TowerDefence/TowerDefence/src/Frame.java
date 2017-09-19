/***************************************
 첫번째 클래스입니다.
 ****************************************/
import javax.swing.*;
//extends JFrame을 지원하기 위한 것. JFrame에는  윈도우 창을 띄우는 것에 대한 정보가 들어가 있다.
import java.awt.*;
//Dimension클래스를 사용하기 위해 import한 것. 2D GUI를 구성하기 위해서는 Dimension을 활용해 주도록 한다.

public class Frame extends JFrame {
	public static Dimension size = new Dimension(934,585);
	//창의 크기 지정
	
	public Frame() {
			setTitle("타워디펜스");
			//창의 타이틀 바 제목 설정
			setSize(size);
			//창의 사이즈를 구현
			setResizable(false);
			//창의 사이즈 못 변경 하게 함
			setLocationRelativeTo(null);
			//윈도우 창 배치를  중앙으로 함
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			//창을 종료할 때 VM에서도 완전히 제거한다.
			start();
		}
	
	public static void main(String args[]){
	Frame frame = new Frame();

	}
	
	
	public void start() {
		setLayout(new GridLayout(1,1,0,0));
		//행이 1개 열이 1개인 네모난 레이아웃 하나를 만들어 준다.
		
		Screen screen = new Screen(this);
		add(screen);
		
		setVisible(true);
		//윈도우에 창을 표시한다.
		
		
	}
	
	

}
