/***************************************
 두 번째 클래스입니다.
 
 이 프로그램의 실행 순서는
 Frame객체 ->
 Frame객체의 생성자 ->
 생성자내 init()메서드 ->
 init()메서드 안의 screen객체 호출의 순서이다.
 
 Screen 클래스에서 Save 클래스를 호출한다.
 Screen 클래스에서 tower 클래스를 호출한다. 
  
 ****************************************/

import javax.swing.*;
//java.swing으로 착각하기 쉬운데 javax.swing임을 기억하자. 
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
//Graphics타입을 선언해 주기 위한 것.
//Graphics는 컴퍼넌트나 이미지 상에 그림을 그릴 수 있게 하는 추상 베이스 클래스이다.
import java.awt.Image;
import java.awt.Point;
//이미지를 불러오는데 필요한 여러 정보가 들어 있다.
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.io.File;
import java.util.ArrayList;


public class Screen extends JPanel implements Runnable {
	//이 클래스에서 스레드를 구현해 준다.
	
	public static Image[] img_info_earth = new Image[100];
	public static Image[] img_info_build = new Image[100];
	public static Image[] img_info_res = new Image[100];
	public static Image[] img_info_mob = new Image[100];
	public static Image[] img_info_tower = new Image[Tower.towerWidth];
	public static Image[] gameBackground = new Image[1];
	
	public Thread thread = new Thread(this);
	
	public static int scrnWidth, scrnHeight;
	
	public static int gold, myHP;
	//코인과 자기 HP point를 설정
	
	public static int killed = 0, killsToWin = 0, level = 1, maxLvl = 3;
	
	public static int winTime = 150000, winFrame = 0;
	
	public static boolean first = true;
	//초기값을 설정한다.
	
	public static boolean isDebug = false; 
	//유닛의 공격 범위를 확인할 수 있다.
	
	public static Point mse = new Point(0, 0);
	
	//참조변수를 Point 클래스로 하였다. java.awt.Point에서 가져왔다.포인트 클래스는 정수 정밀도로 지정된,(x, y)좌표 공간내의 위치를 나타내는 점이다. 
	//x좌표와  y좌표값을 가지고 있으며 마우스에 값을 가져다 주는 것이 아니라, 좌표 값을 여기에 입력해 주어야 한다.
	//x는 이Point의 X좌표. X좌표가 설정되어 있지 않은 경우는 디폴트로 0이 됩니다. y는 이Point의 Y좌표. Y좌표가 설정되어 있지 않은 경우는 디폴트로 0이 됩니다.
	//keyHandle 클래스 만들어 준 이후에 만들어 주었다. Point 클래스는 import java.awt.Point에서 가져와 썼다.
	//좌표 공간 내에 지정되었다.(x,y)의 위치에 점을 구축해 초기화 한다.
	//Point(int x, int y) 좌표 공간내의 지정되었다(x, y)의 위치에 점을 구축해 초기화합니다. 파라미터:x - 새롭게 구축되는 다음에 대한 X좌표: Point, y - 새롭게 구축되는 다음에 대한 Y좌표: Point
 	
	
	public static Room room = new Room();
	//room 객체는 define()메서드에서 사용한다.
	
	public static Save save;
	//save 객체는 define()메서드에서 사용한다.
	
	public static Tower tower;
	//tower 객체는 define()메서드에서 사용한다.
	
	public static Mob[] mobs = new Mob[1000];
	//최대 생성 가능한 몹의 수를 1000마리로 설정해 두었다.
	
	
	public Screen(Frame frame){	
		//생성자 부분이다. 호출 즉시 실행이 된다.
		setBackground(Color.pink);
		//배경색을 설정한다.
		frame.addMouseListener(new KeyHandle());
		frame.addMouseMotionListener(new KeyHandle());
		
		thread.start();
		//스레드 객체 안에 들어 있는 start()메서드로 thread의 실행을 개시한다.
	}
	
	
	public void define() {
		room = new Room();
		save = new Save();
		tower = new Tower();
		
		gold = 10;
		//초기 골드를 설정해 준다.
		myHP = 20;
		//플레이어 HP를 설정해 준다.
		
		//하나의 이미지를 쪼개어 배열화 하여 저장 할 수 있다.
		for(int i=0;i<img_info_earth.length;i++){
			img_info_earth[i] = new ImageIcon("res/img_info_ground.png").getImage();
			//이미지를 가져와서 배열에 저장한다.
			img_info_earth[i] = createImage(new FilteredImageSource(img_info_earth[i].getSource(), new CropImageFilter(0, 26*i, 26, 26)));
			//이미지를 26픽셀씩 세로로 나누어  세로 좌표가 26픽셀 * i번째에 있는 이미지만 가져온다.
			}
		
		for(int i=0;i<img_info_earth.length;i++){
			img_info_build[i] = new ImageIcon("res/img_info_air.png").getImage();
			img_info_build[i] = createImage(new FilteredImageSource(img_info_build[i].getSource(), new CropImageFilter(0, 26*i, 26, 26)));
		}
		
			img_info_res[0] = new ImageIcon("res/ball.png").getImage();
			//스크린에 버튼을 띄우게 하기 위한 것.			
			
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
			//Save클래스의 pathSave() 메서드에 파일 주소를 입력한다.

			for(int i=0;i<mobs.length; i++){
				mobs[i] = new Mob();//몹 객체를 계속 생성한다.
			}			
		}
	
	public void paintComponent(Graphics g){
		//paintComponent()메서드는 JPanel이 상속 받는 클래스인 Container에 위치한 메서드를 상속받는다.
		//이 메서드를 이용하여 화면에 그립을 그릴 수 있는 공간을 만든다. 이 메서드는 따로 실행해 주지 않아도 자동적으로 실행된다.
		
		if(first){
			scrnWidth = getWidth();
			scrnHeight = getHeight();
			//창의 너비와 높이 값을 가져와 저장한다. 한번만 가져오면 되므로 초기값이 true인 변수 first를 사용하고 나서 값을 false로 변경 시켰다.
			
			define(); // 초기 실행할 때 한 번은 실행해 주어야 하는 것들을 실행한다.
			
			first = false;
		}
		
		g.drawImage(gameBackground[0], 0, 0, Frame.size.width, Frame.size.height, null);
		//배경 이미지를 넣는다.

		room.draw(g);
		//맵을 구현하여 보여준다.
		
		tower.draw(g);
		//건설할 타워를 선택하게 한다. 맵 자체에 배치되는 타워는 아니다.
		
		for(int i=0; i<mobs.length;i++){
			if(mobs[i].inGame){
				mobs[i].draw(g);
				//몹이 생성되었다면 그 몹 객체의 이미지 정보를 출력한다.
			}
		}
		
		if(myHP < 1) {
			//게임이 종료 되었을 때 화면을 나타낸다.
			int fontSize = 100;
			g.setColor(new Color(240,50,50));
			g.fillRect(0,0, scrnWidth, scrnHeight );
			g.setColor(new Color(255,255,255));
			g.setFont(new Font("3D", Font.BOLD,fontSize));
			g.drawString("Game over", Frame.size.width/2 - fontSize, Frame.size.height/2 - fontSize/2);
		}
		//사용자 HP가 남지 않아 게임이 끝났을 때이다.
		
		if(winFrame >= winTime){
			//일정시간 이상 버티면 승리 메시지가 뜬다.
			int fontSize = 80;
			g.fillRect(0, 0, getWidth(), getHeight());
			g.setColor(new Color(140, 210, 255));
			g.setFont(new Font("3D", Font.BOLD, fontSize));
			g.drawString("승리", Frame.size.width/2 - fontSize, Frame.size.height/2 - fontSize/2);
		}else if( myHP>0 ){
			//승리까지 남은 시간을 출력한다.
			int currentTime = (int)(winTime/1000)-(int)(Math.floor((winFrame/1000)));
			int fontSize = 30;	
			g.setColor(new Color(255, 255, 255));
			g.setFont(new Font("3D", Font.BOLD, fontSize));
			g.drawString("승리까지 남은 시간 : " + currentTime/60 +"분" + currentTime%60+"초", 50, Frame.size.height - fontSize*2);
		}		
	}
	
	
	public int spawnTime = 2500, spawnFrame = 0;
	//spawnTime은 스폰 시간을 조절한다. 게임이 시작되고, 얼마 후에 spawn이 될 것인지를 결정하고 있음
	public int checkLvl = 1;
	
	public void mobSummoned() {
		if(spawnFrame >= spawnTime){//리젠시간 조건이 충족되고
			
			switch (checkLvl){
			case 1:
				spawnTime = 2500;
				for(int i=0; i<mobs.length; i++) {
					if(!mobs[i].inGame){//몹이 존재하고 있다면
						mobs[i].spawnMob(Value.mobPika, 50, 35, 2);//피카츄를 소환한다.
						break;
					}
				}
				break;
			case 2:
				spawnTime = 3000;
				for(int i=0; i<mobs.length; i++) {
					if(!mobs[i].inGame){//몹이 존재하고 있다면
						mobs[i].spawnMob(Value.mobGora, 70, 35, 2);//고라파덕을 소환한다.
						break;
					}
				}
				break;
				
			case 3:
				spawnTime = 2500;
				for(int i=0; i<mobs.length; i++) {
					if(!mobs[i].inGame){//몹이 존재하고 있다면
						mobs[i].spawnMob(Value.mobPipi, 120, 50, 3);//삐삐를 소환한다.
						break;
					}
				}
				break;
			case 4:
				spawnTime = 2000;
				for(int i=0; i<mobs.length; i++) {
					if(!mobs[i].inGame){//몹이 존재하고 있다면
						mobs[i].spawnMob(Value.mobLapla, 100, 35, 3);//라플라스를 소환한다.
						break;
					}
				}
				break;
			case 5:
				spawnTime = 1500;
				for(int i=0; i<mobs.length; i++) {
					if(!mobs[i].inGame){//몹이 존재하고 있다면
						mobs[i].spawnMob(Value.mobNasi, 50, 25, 3);//나시를 소환한다
						break;
					}
				}
				break;
			case 6:
				spawnTime = 400;
				for(int i=0; i<mobs.length; i++) {
					if(!mobs[i].inGame){//몹이 존재하고 있다면
						mobs[i].spawnMob(Value.mobTobuk, 40, 15, 3);//뚜벅초를 소환한다.
						break;
					}
				}
				break;
			case 7:
				spawnTime = 1000;
				for(int i=0; i<mobs.length; i++) {
					if(!mobs[i].inGame){//몹이 존재하고 있다면
						mobs[i].spawnMob(Value.mobLeesang, 150, 30, 4);//이상해씨를 소환한다.
						break;
					}
				}
				break;
			case 8:
				spawnTime = 700;
				for(int i=0; i<mobs.length; i++) {
					if(!mobs[i].inGame){//몹이 존재하고 있다면
						mobs[i].spawnMob(Value.mobNeru, 200, 50, 4);//네루미를 소환한다.
						break;
					}
				}
				break;
			case 9:
				spawnTime = 300;
				for(int i=0; i<mobs.length; i++) {
					if(!mobs[i].inGame){//몹이 존재하고 있다면
						mobs[i].spawnMob(Value.mobButter, 120, 15, 4);//버터플을 소환한다.
						break;
					}
				}
				break;
			case 10:
				spawnTime = 100;
				for(int i=0; i<mobs.length; i++) {
					if(!mobs[i].inGame){//몹이 존재하고 있다면
						mobs[i].spawnMob(Value.mobKeishi,  25, 1, 5);//케이시를 소환한다.
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
		//스레드를 구현한다.
		while(true) {			
			if(!first && myHP > 0){
				//초기 설정이 아니고, 사용자 HP가 0 초과이고, 승리조건이 충족되지 않았을 때 실행한다.
				room.physic();
				//몹을 공격한다.
				mobSummoned();
				//몹을 소환한다.
				levelUp();
				for(int i=0; i<mobs.length;i++){
					if(mobs[i].inGame){
						//몹이 게임 내에 존재하면
						mobs[i].physic();
						//몹을 움직인다.
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

