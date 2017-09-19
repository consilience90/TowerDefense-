
import java.awt.*;

public class Mob extends Rectangle implements mbStatus{
	
	public int xC,yC;
	public int myHP;
	
	//mbStatus의 상속 받는 부분
	public int mobSize = 52;
	public int mobMoving = 0;
	public int upward = 0, downward = 1, right =2, left = 3;
	public int direction = right;
	public int mobID = Value.mobbuild;
	public int myHPSpace = 3, myHPHeight = 6;
	
	public boolean inGame = false;
	public boolean hasUpward = false;
	public boolean hasDownward = false;
	public boolean hasLeft = false;
	public boolean hasRight = false;

	
	
	public Mob(){
		
		}

	
	public void spawnMob(int mobID) {
		for(int y=0; y<Screen.room.block.length;y++){
			if(Screen.room.block[y][0].earthID == Value.earthRoad){
				//각각에 저장된 earthID값을 불러온다. 이 값은 save로 인하여 맵에서 받아온 수치 값이 저장되어 있다.
				//block[y][0]로 한 값을 불러오는 것은 맵에서 첫번째 열의 값들을 차례로 불러오는 것이라 할 수 있다. 첫번째 열은 시작위치가 있는 지점이고,
				//첫번째 열의 값이 길(road)가 되는 값인 1이 될 경우는 첫번째 열에 존재하는 시작지점의 위치가 된다.
				 
				setBounds(Screen.room.block[y][0].x, Screen.room.block[y][0].y, mobSize, mobSize);
				//이제는 몹을 넣을 위치를 지정해 준다. x좌표와 y좌표는 이미 들어가 있다.
			
				xC = 0;
				yC = y;
			}
		}
		
		
		this.mobID = mobID;
		//입력 받은 몹 아이디 값을 해당 몹 객체에 저장을 해 준다.
		this.myHP = mobSize;
		
		inGame = true;
		//게임 내에서 몹이 살아있다는 의미이다.
				
	}
	
	public void deleteMob() {
		inGame = false;
		
		direction = right; 
		mobMoving = 0;
		xC = 0;
		yC = y;
		//이 부분을 안 설정해 주면 유닛이 죽을 때, 다른 유닛들의 궤도가 이탈하는 기현상이 발생함
		
	}

	
	public void loosemyHP () {
		Screen.myHP -= 1;
		//몹 체력을 깍는 메서드
	}
	
	public int movingFrame = 0, movingSpeed = 35; 
	//movingSpeed 값이 작을 수록 속도가 빠르다.
	
	public void physic() {
		
		if(movingFrame >= movingSpeed){
			if(direction == right){
				x += 1;
			}else if(direction == upward){
				y -= 1;
			}else if(direction == downward) {
				y += 1;
			}else if(direction == left) {
				x -= 1;
			}
			
			mobMoving += 1;
			
			///////////////////////////////////////////////////////////////////////////////////////////
			if(mobMoving == Screen.room.rectSize){
				// 이 부분은 코너에 관련된 부분 같다.
				if(direction == right){
					xC += 1;
					hasRight = true;
				}else if(direction == upward){
					yC -= 1;
					hasUpward = true;
				}else if(direction == downward) {
					yC += 1;
					hasDownward = true;
				}else if(direction == left) {
					xC -= 1;
					hasLeft = true;
				}
				
				if(!hasUpward){
					try {
						if(Screen.room.block[yC+1][xC].earthID == Value.earthRoad){
							direction = downward;
						}
					}catch (Exception e) {}					
				}
				if(!hasDownward){
					try {
						if(Screen.room.block[yC-1][xC].earthID == Value.earthRoad){
							direction = upward;
						}
					} catch (Exception e) {}
				}
				
				if(!hasLeft){
					try {
						if(Screen.room.block[yC][xC+1].earthID == Value.earthRoad){
							direction = right;
						}
					} catch (Exception e) {}
				}
				
				if(!hasRight){
					try {
						if(Screen.room.block[yC][xC-1].earthID == Value.earthRoad){
							direction = left;
						}
					} catch (Exception e) {}
				}
				
				
///////////////////////////////////////////////////////////////////////////////////////////
				
				if(Screen.room.block[yC][xC].buildID == Value.buildFlag){
					deleteMob();
					loosemyHP();
					
				}
				
				
///////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
				hasUpward = false;
				hasDownward = false;
				hasLeft = false;
				hasRight = false;
				
				mobMoving = 0;

					
			}
			movingFrame = 0;
				
			} else {
			
			movingFrame += 1;
			}
		} 
///////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

		public void loseMyHP(int amo){
			myHP -= amo;
		//losemyHP메서드를 호출할 때 넣어진 값만큼 체력이 깍이도록 만들었다.
			checkDeath();
			//몹 체력이 0이면 제거한다.
		}
		
		
		public void checkDeath() {
			if(myHP == 0){
				deleteMob();
			}
		}
		//몹 체력이 0이하인지 판별한다.
		
		public boolean isDead() {
			if(inGame) {
				return false;
			}
			else{
				return true;
			}
		}
		//몹이 죽었는지 살았는지 판별한다.
	
	public void draw(Graphics g) {
			g.drawImage(Screen.img_info_mob[mobID], x, y, width, height, null);
			//몹 이미지를 출력한다.
		
		//myHP bar
		g.setColor(new Color(180, 50, 50));
		g.fillRect(x, y - myHPSpace - myHPHeight, mobSize, myHPHeight);
			
		//에너지 바를 설정한다.
		g.setColor(Color.red);
		g.fillRect(x, y - myHPSpace - myHPHeight, myHP, myHPHeight);

		//에너지 바의 윤곽을 설정한다.
		g.setColor(new Color(0, 0, 0));
		g.drawRect(x, y - myHPSpace - myHPHeight, myHP - 1, myHPHeight - 1);
		
	}

}
