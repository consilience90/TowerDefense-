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
import java.util.ArrayList;


public class Screen extends JPanel implements Runnable {
	//�� Ŭ�������� �����带 ������ �ش�.
	
	public static Image[] img_info_earth = new Image[100];
	public static Image[] img_info_build = new Image[100];
	public static Image[] img_info_res = new Image[100];
	public static Image[] img_info_mob = new Image[100];
	public static Image[] img_info_tower = new Image[Tower.towerWidth];
	public static Image[] gameBackground = new Image[1];
	
	public Thread thread = new Thread(this);
	
	public static int scrnWidth, scrnHeight;
	
	public static int gold, myHP;
	//���ΰ� �ڱ� HP point�� ����
	
	public static int killed = 0, killsToWin = 0, level = 1, maxLvl = 3;
	
	public static int winTime = 150000, winFrame = 0;
	
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
		for(int i=0;i<img_info_earth.length;i++){
			img_info_earth[i] = new ImageIcon("res/img_info_ground.png").getImage();
			//�̹����� �����ͼ� �迭�� �����Ѵ�.
			img_info_earth[i] = createImage(new FilteredImageSource(img_info_earth[i].getSource(), new CropImageFilter(0, 26*i, 26, 26)));
			//�̹����� 26�ȼ��� ���η� ������  ���� ��ǥ�� 26�ȼ� * i��°�� �ִ� �̹����� �����´�.
			}
		
		for(int i=0;i<img_info_earth.length;i++){
			img_info_build[i] = new ImageIcon("res/img_info_air.png").getImage();
			img_info_build[i] = createImage(new FilteredImageSource(img_info_build[i].getSource(), new CropImageFilter(0, 26*i, 26, 26)));
		}
		
			img_info_res[0] = new ImageIcon("res/ball.png").getImage();
			//��ũ���� ��ư�� ���� �ϱ� ���� ��.			
			
			img_info_res[1] = new ImageIcon("res/heart.gif").getImage();
			img_info_res[2] = new ImageIcon("res/coin.png").getImage();
			
			img_info_res[4] = new ImageIcon("res/openball.png").getImage();
			
		for(int i=0;i<img_info_earth.length;i++){
			img_info_mob[i] = new ImageIcon("res/mob/mob"+(i+1)+".gif").getImage();
		}
		
		for(int i=0;i<img_info_tower.length;i++){
			img_info_tower[i] = new ImageIcon("res/tower/tower"+(i+1)+".png").getImage();
		}
			
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
		//�Ǽ��� Ÿ���� �����ϰ� �Ѵ�. �� ��ü�� ��ġ�Ǵ� Ÿ���� �ƴϴ�.
		
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
		}else if( myHP>0 ){
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
	public int checkLvl = 1;
	
	public void mobSummoned() {
		if(spawnFrame >= spawnTime){//�����ð� ������ �����ǰ�
			
			switch (checkLvl){
			case 1:
				spawnTime = 2500;
				for(int i=0; i<mobs.length; i++) {
					if(!mobs[i].inGame){//���� �����ϰ� �ִٸ�
						mobs[i].spawnMob(Value.mobPika, 50, 35, 2);//��ī�� ��ȯ�Ѵ�.
						break;
					}
				}
				break;
			case 2:
				spawnTime = 3000;
				for(int i=0; i<mobs.length; i++) {
					if(!mobs[i].inGame){//���� �����ϰ� �ִٸ�
						mobs[i].spawnMob(Value.mobGora, 70, 35, 2);//����Ĵ��� ��ȯ�Ѵ�.
						break;
					}
				}
				break;
				
			case 3:
				spawnTime = 2500;
				for(int i=0; i<mobs.length; i++) {
					if(!mobs[i].inGame){//���� �����ϰ� �ִٸ�
						mobs[i].spawnMob(Value.mobPipi, 120, 50, 3);//�߻߸� ��ȯ�Ѵ�.
						break;
					}
				}
				break;
			case 4:
				spawnTime = 2000;
				for(int i=0; i<mobs.length; i++) {
					if(!mobs[i].inGame){//���� �����ϰ� �ִٸ�
						mobs[i].spawnMob(Value.mobLapla, 100, 35, 3);//���ö󽺸� ��ȯ�Ѵ�.
						break;
					}
				}
				break;
			case 5:
				spawnTime = 1500;
				for(int i=0; i<mobs.length; i++) {
					if(!mobs[i].inGame){//���� �����ϰ� �ִٸ�
						mobs[i].spawnMob(Value.mobNasi, 50, 25, 3);//���ø� ��ȯ�Ѵ�
						break;
					}
				}
				break;
			case 6:
				spawnTime = 400;
				for(int i=0; i<mobs.length; i++) {
					if(!mobs[i].inGame){//���� �����ϰ� �ִٸ�
						mobs[i].spawnMob(Value.mobTobuk, 40, 15, 3);//�ѹ��ʸ� ��ȯ�Ѵ�.
						break;
					}
				}
				break;
			case 7:
				spawnTime = 1000;
				for(int i=0; i<mobs.length; i++) {
					if(!mobs[i].inGame){//���� �����ϰ� �ִٸ�
						mobs[i].spawnMob(Value.mobLeesang, 150, 30, 4);//�̻��ؾ��� ��ȯ�Ѵ�.
						break;
					}
				}
				break;
			case 8:
				spawnTime = 700;
				for(int i=0; i<mobs.length; i++) {
					if(!mobs[i].inGame){//���� �����ϰ� �ִٸ�
						mobs[i].spawnMob(Value.mobNeru, 200, 50, 4);//�׷�̸� ��ȯ�Ѵ�.
						break;
					}
				}
				break;
			case 9:
				spawnTime = 300;
				for(int i=0; i<mobs.length; i++) {
					if(!mobs[i].inGame){//���� �����ϰ� �ִٸ�
						mobs[i].spawnMob(Value.mobButter, 120, 15, 4);//�������� ��ȯ�Ѵ�.
						break;
					}
				}
				break;
			case 10:
				spawnTime = 100;
				for(int i=0; i<mobs.length; i++) {
					if(!mobs[i].inGame){//���� �����ϰ� �ִٸ�
						mobs[i].spawnMob(Value.mobKeishi,  25, 1, 5);//���̽ø� ��ȯ�Ѵ�.
						break;
					}
				}
				break;
			}
			spawnFrame = 0;
		} else {
			spawnFrame += 1;
		}
	}
	
	public int nextLevel = 15000, nextFrame = 0;
	public void levelUp() {
		if(nextFrame>=nextLevel){
			nextFrame=0;
			checkLvl++;
		}else{
			nextFrame++;
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
				levelUp();
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
			}catch(Exception e){
				
			}
		}
		
	}
}

