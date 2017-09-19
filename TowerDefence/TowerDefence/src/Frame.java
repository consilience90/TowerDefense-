/***************************************
 ù��° Ŭ�����Դϴ�.
 ****************************************/
import javax.swing.*;
//extends JFrame�� �����ϱ� ���� ��. JFrame����  ������ â�� ���� �Ϳ� ���� ������ �� �ִ�.
import java.awt.*;
//DimensionŬ������ ����ϱ� ���� import�� ��. 2D GUI�� �����ϱ� ���ؼ��� Dimension�� Ȱ���� �ֵ��� �Ѵ�.

public class Frame extends JFrame {
	public static Dimension size = new Dimension(934,585);
	//â�� ũ�� ����
	
	public Frame() {
			setTitle("Ÿ�����潺");
			//â�� Ÿ��Ʋ �� ���� ����
			setSize(size);
			//â�� ����� ����
			setResizable(false);
			//â�� ������ �� ���� �ϰ� ��
			setLocationRelativeTo(null);
			//������ â ��ġ��  �߾����� ��
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			//â�� ������ �� VM������ ������ �����Ѵ�.
			start();
		}
	
	public static void main(String args[]){
	Frame frame = new Frame();

	}
	
	
	public void start() {
		setLayout(new GridLayout(1,1,0,0));
		//���� 1�� ���� 1���� �׸� ���̾ƿ� �ϳ��� ����� �ش�.
		
		Screen screen = new Screen(this);
		add(screen);
		
		setVisible(true);
		//�����쿡 â�� ǥ���Ѵ�.
		
		
	}
	
	

}
