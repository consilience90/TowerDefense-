import java.awt.color.*;
import java.awt.*;


public class Tower implements twStaus{
	public static int towerWidth = 8;
	static Room room = new Room();
	public static int buttonSize = room.rectSize - 5;
	
	//twStaus의 상속 받는 부분
	public static int iconSize = 20;
	public static int iconSpace = 3;
	public static int iconText = 13;
	public static int priceID = 0;
	public static int item = 4;
	public static int holdID = -1;
	public static int realID = -1;
	//몇 번째 버튼인지에 대한 것.
	
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
				//타입을 Rectangle로 button을 활용한다. button의 인덱스  수는 towerWidth이므로  타워 종류의 갯수이다.
				if(button[i].contains(Screen.mse)) {
				//마우스 좌표가 버튼 안에 있는지 확인한다.
					if(buttonID[i] != Value.buildNone) {
						//아직 건설되지 않았다면
						if(buttonID[i] == Value.buildFlagTrash) { //Delete item.
							//버튼 아이디가 취소 값과 부합한다면
							holdsItem = false;
							//쥐고 있는 타워를 제거할 수 있다.
						} else {
							holdID = buttonID[i];
							//타워의 상태를 나타내는 정보
							//건설 할 수 있는가? 들었던 타워를 지울 수 있는가?
							realID = i;
							//버튼의 순서
							holdsItem = true;
							//마우스가 타워를 가지게 하고 있는 상태가 된다.
						}
					}
				}
			}
			if(holdsItem) {
				//holdsItem 이 부분은 마우스가 아이템을 가지고 있는 상태인가 아닌가를 체크하는 것.
				//마우스가 타워를 가지고 있는 상태라면 이 부분이 true가 될 것.
				if(Screen.gold >= buttonPrice[realID]) {
					//코인이 버튼이 가진 가격보다 클 때만 시행이 가능하다.
					for(int y = 0; y < Screen.room.block.length; y++) {
						for(int x = 0; x < Screen.room.block[0].length; x++) {
							if(Screen.room.block[y][x].contains(Screen.mse))
								//각각 선택된 배열 내에서 마우스 좌표가 있다면 true를 반환하고, 그렇지 않다면 false를 반환한다.
								if(Screen.room.block[y][x].earthID != Value.earthRoad && Screen.room.block[y][x].buildID == Value.buildNone) {
									//earthID가 길에 부여한 값과 같지 않은 경우 건설가능하도록 한다. 곧 잔디 위에 건설 되도록 한다. 그리고 build가 부여된 값에 해당하는 모든 곳에 건설할 수 있게 한다.
									//결과적으로 길을 통해서만 건설 되도록 한 것이다.
									Screen.room.block[y][x].buildID = realID;
									//block[y][x]의 각각의 buildID에 holdID값을 입력해 준다. 그러면 땅의 상태는 변하게 된다.
									//곧 realID에 해당하는 포켓몬 타워를 건설하게 된다.
									//다 실행이 되면, 버튼에 해당하는 만큼의 코인이 감소한다.
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
		//타워 건설 버튼의 위치를 지정해 줍니다.
		}
		
		buttonHP = new Rectangle(Screen.room.block[0][0].x-1,button[0].y, iconSize, iconSize);
		//하트의 위치를 지정해 주었다.
		buttonGold = new Rectangle(Screen.room.block[0][0].x-1,button[0].y + button[0].height-iconSize, iconSize, iconSize);
		//골드의 위치를 지정해 주었다.
	}
	
	
	public void draw(Graphics g){
		for(int i=0;i<button.length; i++){
			if(buttonID[i] != Value.buildFlagTrash){
				g.drawImage(Screen.img_info_res[0], button[i].x, button[i].y, button[i].width ,button[i].height, null);
			//버튼을 띄워준다.
			}
			if(button[i].contains(Screen.mse)){
				//마우스를 올려두는 이벤트가 발생하면 
				g.setColor(new Color(100,255,255,150));
				g.fillRect(button[i].x, button[i].y, button[i].width ,button[i].height);
				//버튼 색이 변하도록 하였다.
			}
		
			
			if(buttonID[i] == Value.buildTower){
				//버튼에 타워 이미지를 추가한다.
				g.drawImage(Screen.img_info_tower[i+1], button[i].x + item, button[i].y + item, button[i].width - (item*2), button[i].height - (item*2), null);		
			}
			
			//취소버튼
			if(buttonID[i] == Value.buildFlagTrash) {
				//버튼에 취소 이미지를 추가한다.
				g.drawImage(Screen.img_info_res[4], button[i].x + item, button[i].y + item, button[i].width - (item*2), button[i].height - (item*2), null);
			}else if(buttonID[i] == Value.buildFlag){
					
			}
			
			if(buttonPrice[i] > 0) {
				g.setColor(Color.MAGENTA);
				g.setFont(new Font("3D", Font.BOLD, 10));
				g.drawString("Gold" + buttonPrice[i], button[i].x + 10, button[i].y + item + 50);
				//가격 정보를 표시한다.
			}
			
		}
		
		g.setColor(new Color(255,255,255,0));
		g.drawImage(Screen.img_info_res[1], buttonHP.x, buttonHP.y, buttonHP.width, buttonHP.height, null);
		//생명력 이미지를 표시한다.
		g.drawImage(Screen.img_info_res[2], buttonGold.x, buttonGold.y, buttonGold.width, buttonGold.height, null);
		//골드 이미지를 표시한다.
		
		g.setFont(new Font("3D New", Font.BOLD, 14));
		g.setColor(new Color(255,255,255));
		g.drawString("" + Screen.myHP, buttonHP.x + buttonHP.width + iconSpace, buttonHP.y + iconText);
		g.drawString("" + Screen.gold, buttonGold.x + buttonGold.width + iconSpace, buttonGold.y + iconText);
		//가지고 있는 HP와 골들의 양을 표시한다.
		
		if(holdsItem) {
			g.drawImage(Screen.img_info_res[0], Screen.mse.x - ((button[0].width - (item*2))/2) + item, Screen.mse.y - ((button[0].width - (item*2))/2) + item, button[0].width - (item*2), button[0].height - (item*2), null);		
		//마우스가 몬스터 볼을 가진 상태를 나타낸다.
			
		}
	}

}