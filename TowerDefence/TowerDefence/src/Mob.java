
import java.awt.*;

public class Mob extends Rectangle implements mbStatus{
	
	public int xC,yC;
	public int myHP;
	public int fullHp;
	
	//mbStatus의 상속 받는 부분
	public int mobSize = 52;
	public int mobMoving = 0;
	public int upward = 0, downward = 1, right =2, left = 3;
	public int wardSign = right;
	public int mobID = Value.mobbuild;
	public int myHPSpace = 3, myHPHeight = 6;
	public int mobReward = 0;
	
	public boolean passMob = false;
	public boolean inGame = false;
	public boolean hasUpward = false;
	public boolean hasDownward = false;
	public boolean hasLeft = false;
	public boolean hasRight = false;

	
	
	public Mob(){
		
		}

	
	public void spawnMob(int mobID, int hp, int speed, int reward) {
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
		this.myHP = hp;
		this.fullHp = this.myHP;
		this.movingSpeed=speed;
		this.mobReward = reward;
		
		inGame = true;
		//게임 내에서 몹이 살아있다는 의미이다.
				
	}
	
	public void deleteMob() {
		inGame = false;
		
		wardSign = right;
		mobMoving = 0;
		xC = 0;
		yC = y;
		//이 부분을 안 설정해 주면 유닛이 죽을 때, 다른 유닛들의 궤도가 이탈하는 기현상이 발생함
		
	}
	
	public void pass() {
		passMob = true;
		
		wardSign = right;
		mobMoving = 0;
		xC = 0;
		yC = y;
		//이 부분을 안 설정해 주면 유닛이 죽을 때, 다른 유닛들의 궤도가 이탈하는 기현상이 발생함
		
	}

	
	public void looseMyHP () {
		Screen.myHP -= 1;
		//몹 체력을 깍는 메서드
	}
	
	public int movingFrame = 0, movingSpeed = 35; 
	//movingSpeed 값이 작을 수록 속도가 빠르다.
	
	public void physic() {
		//Screen 객체의 스레드 안에서 실행된다.
	
		
		if(movingFrame >= movingSpeed){
			//무빙 프레임은 스레드 안에서 반복문이 돌아갈 때마다 증가한다. 그 반복 횟수가 movingSpeed에서 정한 횟수를 넘길 때 실행한다.
			//곧 어느 정도의 시간에 정해진 값만큼 움직이게 하려는지를 결정한다.
			//정해진 방향을 따라서 움직이도록 한다.
			//네 방향 중 한 방향으로만 이동하게 한다.
			
			if(wardSign == right){
				//wardSign에 부여된 방향이 오른쪽이면
				x += 1;
				//오른쪽 방향으로 1픽셀 이동
			}else if(wardSign == upward){
				//wardSign에 부여된 방향이 왼쪽이면
				y -= 1;
				//위쪽 방향으로 1픽셀 이동
			}else if(wardSign == downward) {
				////wardSign에 부여된 방향이 아래쪽이면
				y += 1;
				//아래쪽 방향으로 1픽셀 이동
			}else if(wardSign == left) {
				//wardSign에 부여된 방향이 왼쪽이면
				x -= 1;
				//왼쪽 방향으로 1픽셀 이동
			}
			
			mobMoving += 1;
			//이 값 증가는 스레드 내의 반복문 시행 간격에 따라 증가한다.
			
			//위에서 wardSign이 가진 방향은 한 방향 뿐이다.
			if(mobMoving == Screen.room.rectSize){
			//사각형의 갈로 길이 만큼 이동하면
			//바로 앞 부분에서 정한 wardSign 
				if(wardSign == right){
					//오른쪽으로 가야 한다면
					xC += 1;
					//다음 배열으로 가야할 배열의 위치를 탐색할 때 오른쪽 공간을 탐색하는 값으로 바꿔주고
					hasRight = true;
					//오른쪽으로 가도 된다고 허용한다.
				}else if(wardSign == upward){
					//위로 가야 한다면
					yC -= 1; 
					//다음 배열으로 가야할 배열의 위치를 탐색할 때 위쪽 공간을  탐색하는 값으로 바꿔주고
					hasUpward = true;
					//위로 가도 된다고 허용한다.
				}else if(wardSign == downward) {
					//아래로 가야 한다면
					yC += 1;
					//다음 배열으로 가야할 배열의 위치를 탐색할 때 아래쪽 공간을  탐색하는 값으로 바꿔주고
					hasDownward = true;
					//아래로 가도 된다고 허용한다.
				}else if(wardSign == left) {
					//왼쪽으로 가야 한다면
					xC -= 1;
					//다음 배열으로 가야할 배열의 위치를 탐색할 때 아래쪽 공간을  탐색하는 값으로 바꿔주고
					hasLeft = true;
					//왼쪽으로 가도 된다고 허용한다.
				}
				//곧 이 부분은 가도되는 방향을 설정한다. 이동하고 나서 다음 부분이 가도 되는 부분인지 확인 하기 위해서 다음 부분을 탐색하게끔 하는 것.
				
				
				//방향을 부여한다.
				if(!hasUpward){
				//위로 가도 된다는 허가를 받지 못하였다면
					try {
						if(Screen.room.block[yC+1][xC].earthID == Value.earthRoad){
							//기준위치 아래쪽이 길이면
							wardSign = downward;
							//아래쪽이란 지시를 부여한다.							
						}
					}catch (Exception e) {}					
				}
				
				if(!hasDownward){
				//아래로 가도 된다는 허락을 받지 못하였다면
					try {
						if(Screen.room.block[yC-1][xC].earthID == Value.earthRoad){
							//기준위치 위쪽이 길이라면
							wardSign = upward;
							//위쪽이란 지시를 부여한다.
						}
					} catch (Exception e) {}
				}
				
				if(!hasLeft){
				//왼쪽으로 가도 된다는 허락을 받지 못하였다면
					try {
						if(Screen.room.block[yC][xC+1].earthID == Value.earthRoad){
							//기준 위치 오른쪽이 길이라면
							wardSign = right;
							//오른쪽이란 지시를 부여한다.
						}
					} catch (Exception e) {}
				}
				
				if(!hasRight){
				//오른쪽으로 가도 된다는 허락을 받지 못하였다면
					try {
						if(Screen.room.block[yC][xC-1].earthID == Value.earthRoad){
							//기준 위치 왼쪽이 길이라면
							wardSign = left;
							//왼쪽이란 지시를 부여한다.
						}
					} catch (Exception e) {}
				}
				
				
				if(Screen.room.block[yC][xC].buildID == Value.buildFlag){
					pass();
					deleteMob();
					looseMyHP();
					
				}
				
				
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

		public void loseEnemyHP(int amo){
			myHP -= amo;
			//losemyHP메서드를 호출할 때 넣어진 값만큼 체력이 깍이도록 만들었다.
			checkDeath();
			//몹 체력이 0이면 제거한다.
		}
		
		
		public void checkDeath() {
			if(myHP <= 0){
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
		Image img = Screen.img_info_mob[mobID];
			g.drawImage(img, x, y, width, height, null);
			//몹 이미지를 출력한다.
		
		//에너지바 벡그라운드
		g.setColor(new Color(180, 50, 50));
		g.fillRect(x, y - myHPSpace - myHPHeight, mobSize, myHPHeight);
			
		//에너지 바를 설정한다.
		g.setColor(Color.red);
		//g.fillRect(x, y - myHPSpace - myHPHeight, Screen.room.rectSize, myHPHeight);
		g.fillRect(x, y - myHPSpace - myHPHeight, (myHP*mobSize)/fullHp, myHPHeight);

		//에너지 바의 윤곽을 설정한다.
		g.setColor(new Color(0, 0, 0));
		g.drawRect(x, y - myHPSpace - myHPHeight, mobSize, myHPHeight - 1);
		
	}

}
