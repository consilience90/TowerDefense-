import java.awt.color.*;
import java.awt.*;


public class Tower implements twStaus{
	public static int towerWidth = 8;
	static Room room = new Room();
	public static int buttonSize = room.rectSize - 5;
	
	//twStaus�� ��� �޴� �κ�
	public static int iconSize = 20;
	public static int iconSpace = 3;
	public static int iconText = 13;
	public static int priceID = 0;
	public static int item = 4;
	public static int holdID = -1;
	public static int realID = -1;
	//�� ��° ��ư������ ���� ��.
	
	public static int [] buttonID = {Value.buildTower, Value.buildTower, Value.buildTower, Value.buildTower, Value.buildTower, Value.buildTower, Value.buildTower, Value.buildFlagTrash };
	public static int[] buttonPrice = {10, 40, 65, 95, 140, 185, 235, 0};
	
	
	
	public Rectangle[] button = new Rectangle[towerWidth];
	
	public Rectangle buttonHP;
	public Rectangle buttonGold;
	
	public boolean holdsItem = false;
	
	public Color color;
	
	public Tower() {
		define();
	}
	
	public void click (int mouseButton) {
		if(mouseButton == 1){
			for(int i =0; i<button.length;i++){
				//Ÿ���� Rectangle�� button�� Ȱ���Ѵ�. button�� �ε���  ���� towerWidth�̹Ƿ�  Ÿ�� ������ �����̴�.
				if(button[i].contains(Screen.mse)) {
				//���콺 ��ǥ�� ��ư �ȿ� �ִ��� Ȯ���Ѵ�.
					if(buttonID[i] != Value.buildNone) {
						//���� �Ǽ����� �ʾҴٸ�
						if(buttonID[i] == Value.buildFlagTrash) { //Delete item.
							//��ư ���̵� ��� ���� �����Ѵٸ�
							holdsItem = false;
							//��� �ִ� Ÿ���� ������ �� �ִ�.
						} else {
							holdID = buttonID[i];
							//Ÿ���� ���¸� ��Ÿ���� ����
							//�Ǽ� �� �� �ִ°�? ����� Ÿ���� ���� �� �ִ°�?
							realID = i;
							//��ư�� ����
							holdsItem = true;
							//���콺�� Ÿ���� ������ �ϰ� �ִ� ���°� �ȴ�.
						}
					}
				}
			}
			if(holdsItem) {
				//holdsItem �� �κ��� ���콺�� �������� ������ �ִ� �����ΰ� �ƴѰ��� üũ�ϴ� ��.
				//���콺�� Ÿ���� ������ �ִ� ���¶�� �� �κ��� true�� �� ��.
				if(Screen.gold >= buttonPrice[realID]) {
					//������ ��ư�� ���� ���ݺ��� Ŭ ���� ������ �����ϴ�.
					for(int y = 0; y < Screen.room.block.length; y++) {
						for(int x = 0; x < Screen.room.block[0].length; x++) {
							if(Screen.room.block[y][x].contains(Screen.mse))
								//���� ���õ� �迭 ������ ���콺 ��ǥ�� �ִٸ� true�� ��ȯ�ϰ�, �׷��� �ʴٸ� false�� ��ȯ�Ѵ�.
								if(Screen.room.block[y][x].earthID != Value.earthRoad && Screen.room.block[y][x].buildID == Value.buildNone) {
									//earthID�� �濡 �ο��� ���� ���� ���� ��� �Ǽ������ϵ��� �Ѵ�. �� �ܵ� ���� �Ǽ� �ǵ��� �Ѵ�. �׸��� build�� �ο��� ���� �ش��ϴ� ��� ���� �Ǽ��� �� �ְ� �Ѵ�.
									//��������� ���� ���ؼ��� �Ǽ� �ǵ��� �� ���̴�.
									Screen.room.block[y][x].buildID = realID;
									//block[y][x]�� ������ buildID�� holdID���� �Է��� �ش�. �׷��� ���� ���´� ���ϰ� �ȴ�.
									//�� realID�� �ش��ϴ� ���ϸ� Ÿ���� �Ǽ��ϰ� �ȴ�.
									//�� ������ �Ǹ�, ��ư�� �ش��ϴ� ��ŭ�� ������ �����Ѵ�.
									Screen.gold -= buttonPrice[realID];
								}
						}
					}
				}
			}
		}
	}
	
	public void define() {
		for(int i=0;i<button.length;i++){
		
		button[i] = new Rectangle (((Screen.scrnWidth/2) - ((room.widthRect*room.rectSize)/2)) + (room.rectSize)*(i+2)+3 , 8*room.rectSize+3 ,buttonSize,buttonSize);	
		//Ÿ�� �Ǽ� ��ư�� ��ġ�� ������ �ݴϴ�.
		}
		
		buttonHP = new Rectangle(Screen.room.block[0][0].x-1,button[0].y, iconSize, iconSize);
		//��Ʈ�� ��ġ�� ������ �־���.
		buttonGold = new Rectangle(Screen.room.block[0][0].x-1,button[0].y + button[0].height-iconSize, iconSize, iconSize);
		//����� ��ġ�� ������ �־���.
	}
	
	
	public void draw(Graphics g){
		for(int i=0;i<button.length; i++){
			if(buttonID[i] != Value.buildFlagTrash){
				g.drawImage(Screen.img_info_res[0], button[i].x, button[i].y, button[i].width ,button[i].height, null);
			//��ư�� ����ش�.
			}
			if(button[i].contains(Screen.mse)){
				//���콺�� �÷��δ� �̺�Ʈ�� �߻��ϸ� 
				g.setColor(new Color(100,255,255,150));
				g.fillRect(button[i].x, button[i].y, button[i].width ,button[i].height);
				//��ư ���� ���ϵ��� �Ͽ���.
			}
		
			
			if(buttonID[i] == Value.buildTower){
				//��ư�� Ÿ�� �̹����� �߰��Ѵ�.
				g.drawImage(Screen.img_info_tower[i+1], button[i].x + item, button[i].y + item, button[i].width - (item*2), button[i].height - (item*2), null);		
			}
			
			//��ҹ�ư
			if(buttonID[i] == Value.buildFlagTrash) {
				//��ư�� ��� �̹����� �߰��Ѵ�.
				g.drawImage(Screen.img_info_res[4], button[i].x + item, button[i].y + item, button[i].width - (item*2), button[i].height - (item*2), null);
			}else if(buttonID[i] == Value.buildFlag){
					
			}
			
			if(buttonPrice[i] > 0) {
				g.setColor(Color.MAGENTA);
				g.setFont(new Font("3D", Font.BOLD, 10));
				g.drawString("Gold" + buttonPrice[i], button[i].x + 10, button[i].y + item + 50);
				//���� ������ ǥ���Ѵ�.
			}
			
		}
		
		g.setColor(new Color(255,255,255,0));
		g.drawImage(Screen.img_info_res[1], buttonHP.x, buttonHP.y, buttonHP.width, buttonHP.height, null);
		//����� �̹����� ǥ���Ѵ�.
		g.drawImage(Screen.img_info_res[2], buttonGold.x, buttonGold.y, buttonGold.width, buttonGold.height, null);
		//��� �̹����� ǥ���Ѵ�.
		
		g.setFont(new Font("3D New", Font.BOLD, 14));
		g.setColor(new Color(255,255,255));
		g.drawString("" + Screen.myHP, buttonHP.x + buttonHP.width + iconSpace, buttonHP.y + iconText);
		g.drawString("" + Screen.gold, buttonGold.x + buttonGold.width + iconSpace, buttonGold.y + iconText);
		//������ �ִ� HP�� ����� ���� ǥ���Ѵ�.
		
		if(holdsItem) {
			g.drawImage(Screen.img_info_res[0], Screen.mse.x - ((button[0].width - (item*2))/2) + item, Screen.mse.y - ((button[0].width - (item*2))/2) + item, button[0].width - (item*2), button[0].height - (item*2), null);		
		//���콺�� ���� ���� ���� ���¸� ��Ÿ����.
			
		}
	}

}