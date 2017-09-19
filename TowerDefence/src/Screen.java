/***************************************
 �� ��° Ŭ�����Դϴ�.
 
 �� ���α׷��� ���� ������
 Frame��ü ->
 Frame��ü�� ������ ->
 �����ڳ� init()�޼��� ->
 init()�޼��� ���� screen��ü ȣ���� �����̴�.
 
 Screen Ŭ�������� Save Ŭ������ ȣ���Ѵ�.
 Screen Ŭ�������� tower Ŭ������ ȣ���Ѵ�. 
  
 ****************************************/

import javax.swing.*;
//java.swing���� �����ϱ� ��� javax.swing���� �������. 
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
//GraphicsŸ���� ������ �ֱ� ���� ��.
//Graphics�� ���۳�Ʈ�� �̹��� �� �׸��� �׸� �� �ְ� �ϴ� �߻� ���̽� Ŭ�����̴�.
import java.awt.Image;
import java.awt.Point;
//�̹����� �ҷ����µ� �ʿ��� ���� ������ ��� �ִ�.
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.io.File;


public class Screen extends JPanel implements Runnable {
	//�� Ŭ�������� �����带 ������ �ش�.
	
	public static Image[] img_info_ground = new Image[100];
	public static Image[] img_info_air = new Image[100];
	public static Image[] img_info_res = new Image[100];
	public static Image[] img_info_mob = new Image[100];
	public static Image[] gameBackground = new Image[1];
	
	public Thread thread = new Thread(this);
	
	public static int scrnWidth, scrnHeight;
	
	public static int gold, myHP;
	//���ΰ� �ڱ� HP point�� ����
	
	public static int killed = 0, killsToWin = 0, level = 1, maxLvl = 3;
	
	public static int winTime = 100000, winFrame = 0;
	
	public static boolean first = true;
	//�ʱⰪ�� �����Ѵ�.
	
	public static boolean isDebug = false; 
	//������ ���� ������ Ȯ���� �� �ִ�.
	
	public static Point mse = new Point(0, 0);
	
	//���������� Point Ŭ������ �Ͽ���. java.awt.Point���� �����Դ�.����Ʈ Ŭ������ ���� ���е��� ������,(x, y)��ǥ �������� ��ġ�� ��Ÿ���� ���̴�. 
	//x��ǥ��  y��ǥ���� ������ ������ ���콺�� ���� ������ �ִ� ���� �ƴ϶�, ��ǥ ���� ���⿡ �Է��� �־�� �Ѵ�.
	//x�� ��Point�� X��ǥ. X��ǥ�� �����Ǿ� ���� ���� ���� ����Ʈ�� 0�� �˴ϴ�. y�� ��Point�� Y��ǥ. Y��ǥ�� �����Ǿ� ���� ���� ���� ����Ʈ�� 0�� �˴ϴ�.
	//keyHandle Ŭ���� ����� �� ���Ŀ� ����� �־���. Point Ŭ������ import java.awt.Point���� ������ ���.
	//��ǥ ���� ���� �����Ǿ���.(x,y)�� ��ġ�� ���� ������ �ʱ�ȭ �Ѵ�.
	//Point(int x, int y) ��ǥ �������� �����Ǿ���(x, y)�� ��ġ�� ���� ������ �ʱ�ȭ�մϴ�. �Ķ����:x - ���Ӱ� ����Ǵ� ������ ���� X��ǥ: Point, y - ���Ӱ� ����Ǵ� ������ ���� Y��ǥ: Point
 	
	
	public static Room room = new Room();
	//room ��ü�� define()�޼��忡�� ����Ѵ�.
	public static Save save;
	//save ��ü�� define()�޼��忡�� ����Ѵ�.
	public static Tower tower;
	//tower ��ü�� define()�޼��忡�� ����Ѵ�.
	
	public static Mob[] mobs = new Mob[1000];
	//�ִ� ���� ������ ���� ���� 1000������ ������ �ξ���.
	
	public Screen(Frame frame){	
		//������ �κ��̴�. ȣ�� ��� ������ �ȴ�.
		setBackground(Color.pink);
		//������ �����Ѵ�.
		frame.addMouseListener(new KeyHandle());
		frame.addMouseMotionListener(new KeyHandle());
		
		thread.start();
		//������ ��ü �ȿ� ��� �ִ� start()�޼���� thread�� ������ �����Ѵ�.
	}
	
	
	public void define() {
		room = new Room();
		save = new Save();
		tower = new Tower();
		
		gold = 10;
		//�ʱ� ��带 ������ �ش�.
		myHP = 20;
		//�÷��̾� HP�� ������ �ش�.
		
		//�ϳ��� �̹����� �ɰ��� �迭ȭ �Ͽ� ���� �� �� �ִ�.
		for(int i=0;i<img_info_ground.length;i++){
			img_info_ground[i] = new ImageIcon("res/img_info_ground.png").getImage();
			//�̹����� �����ͼ� �迭�� �����Ѵ�.
			img_info_ground[i] = createImage(new FilteredImageSource(img_info_ground[i].getSource(), new CropImageFilter(0, 26*i, 26, 26)));
			//�̹����� 26�ȼ��� ���η� ������  ���� ��ǥ�� 26�ȼ� * i��°�� �ִ� �̹����� �����´�.
			}
		
		//
		for(int i=0;i<img_info_ground.length;i++){
			img_info_air[i] = new ImageIcon("res/img_info_air.png").getImage();
			img_info_air[i] = createImage(new FilteredImageSource(img_info_air[i].getSource(), new CropImageFilter(0, 26*i, 26, 26)));
		}
		
			img_info_res[0] = new ImageIcon("res/ball.png").getImage();
			//��ũ���� ��ư�� ���� �ϱ� ���� ��.
			
			img_info_res[1] = new ImageIcon("res/heart.gif").getImage();
			img_info_res[2] = new ImageIcon("res/coin.png").getImage();
			img_info_mob[0] = new ImageIcon("res/mob1.gif").getImage();
			gameBackground[0] =  new ImageIcon("res/background.png").getImage();
			
			save.pathSave(new File("save/stage.map"));
			//SaveŬ������ pathSave() �޼��忡 ���� �ּҸ� �Է��Ѵ�.

			for(int i=0;i<mobs.length; i++){
				mobs[i] = new Mob();//�� ��ü�� ��� �����Ѵ�.
			}			
		}
	
	public void paintComponent(Graphics g){
		//paintComponent()�޼���� JPanel�� ��� �޴� Ŭ������ Container�� ��ġ�� �޼��带 ��ӹ޴´�.
		//�� �޼��带 �̿��Ͽ� ȭ�鿡 �׸��� �׸� �� �ִ� ������ �����. �� �޼���� ���� ������ ���� �ʾƵ� �ڵ������� ����ȴ�.
		
		if(first){
			scrnWidth = getWidth();
			scrnHeight = getHeight();
			//â�� �ʺ�� ���� ���� ������ �����Ѵ�. �ѹ��� �������� �ǹǷ� �ʱⰪ�� true�� ���� first�� ����ϰ� ���� ���� false�� ���� ���״�.
			
			define(); // �ʱ� ������ �� �� ���� ������ �־�� �ϴ� �͵��� �����Ѵ�.
			
			first = false;
		}
		
		g.drawImage(gameBackground[0], 0, 0, Frame.size.width, Frame.size.height, null);
		//��� �̹����� �ִ´�.

		room.draw(g);
		//���� �����Ͽ� �����ش�.
		
		tower.draw(g);
		//�Ǽ��� Ÿ���� �����ϰ� �Ѵ�.
		
		for(int i=0; i<mobs.length;i++){
			if(mobs[i].inGame){
				mobs[i].draw(g);
				//���� �����Ǿ��ٸ� �� �� ��ü�� �̹��� ������ ����Ѵ�.
			}
		}
		
		if(myHP < 1) {
			//������ ���� �Ǿ��� �� ȭ���� ��Ÿ����.
			int fontSize = 100;
			g.setColor(new Color(240,50,50));
			g.fillRect(0,0, scrnWidth, scrnHeight );
			g.setColor(new Color(255,255,255));
			g.setFont(new Font("3D", Font.BOLD,fontSize));
			g.drawString("Game over", Frame.size.width/2 - fontSize, Frame.size.height/2 - fontSize/2);
		}
		//����� HP�� ���� �ʾ� ������ ������ ���̴�.
		
		if(winFrame >= winTime){
			//�����ð� �̻� ��Ƽ�� �¸� �޽����� ���.
			int fontSize = 80;
			g.fillRect(0, 0, getWidth(), getHeight());
			g.setColor(new Color(140, 210, 255));
			g.setFont(new Font("3D", Font.BOLD, fontSize));
			g.drawString("�¸�", Frame.size.width/2 - fontSize, Frame.size.height/2 - fontSize/2);
		}else{
			//�¸����� ���� �ð��� ����Ѵ�.
			int currentTime = (int)(winTime/1000)-(int)(Math.floor((winFrame/1000)));
			int fontSize = 30;	
			g.setColor(new Color(255, 255, 255));
			g.setFont(new Font("3D", Font.BOLD, fontSize));
			g.drawString("�¸����� ���� �ð� : " + currentTime/60 +"��" + currentTime%60+"��", 50, Frame.size.height - fontSize*2);
		}


		
	}
	
	
	public int spawnTime = 2500, spawnFrame = 0;
	//spawnTime�� ���� �ð��� �����Ѵ�. ������ ���۵ǰ�, �� �Ŀ� spawn�� �� �������� �����ϰ� ����
	
	public void mobSummoned() {
		if(spawnFrame >= spawnTime){//�ð� ������ �����ǰ�
			for(int i=0; i<mobs.length; i++) {
				if(!mobs[i].inGame){//���� �����ϰ� �ִٸ�
					mobs[i].spawnMob(Value.mobLizad);//���� ��ȯ�Ѵ�. ���� ���� � ���� ��ȯ������ ���Ѵ�.
					break;
				}
			}
			spawnFrame = 0;
		} else {
			spawnFrame += 1;
		}
	}
	
	public void run() {
		//�����带 �����Ѵ�.
		while(true) {			
			if(!first && myHP > 0){
				//�ʱ� ������ �ƴϰ�, ����� HP�� 0 �ʰ��̰�, �¸������� �������� �ʾ��� �� �����Ѵ�.
				room.physic();
				//���� �����Ѵ�.
				mobSummoned();
				//���� ��ȯ�Ѵ�.
				for(int i=0; i<mobs.length;i++){
					if(mobs[i].inGame){
						//���� ���� ���� �����ϸ�
						mobs[i].physic();
						//���� �����δ�.
					}
				}
			}
			
		
			if(winFrame >= winTime + 3000) {
				System.exit(0);
			} else {
				winFrame++;
			}
			
			repaint();
			
			try{
				Thread.sleep(1);
			}catch(Exception e){//Exception e�� ���ڷ� �޴� ���� ��� ���ܸ� catch ���� ������ ��Ƴ��ڴٴ� ��.
				
			}
		}
		
	}
}

