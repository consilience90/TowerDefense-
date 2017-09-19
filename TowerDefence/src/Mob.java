
import java.awt.*;

public class Mob extends Rectangle implements mbStatus{
	
	public int xC,yC;
	public int myHP;
	
	//mbStatus�� ��� �޴� �κ�
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
				//������ ����� earthID���� �ҷ��´�. �� ���� save�� ���Ͽ� �ʿ��� �޾ƿ� ��ġ ���� ����Ǿ� �ִ�.
				//block[y][0]�� �� ���� �ҷ����� ���� �ʿ��� ù��° ���� ������ ���ʷ� �ҷ����� ���̶� �� �� �ִ�. ù��° ���� ������ġ�� �ִ� �����̰�,
				//ù��° ���� ���� ��(road)�� �Ǵ� ���� 1�� �� ���� ù��° ���� �����ϴ� ���������� ��ġ�� �ȴ�.
				 
				setBounds(Screen.room.block[y][0].x, Screen.room.block[y][0].y, mobSize, mobSize);
				//������ ���� ���� ��ġ�� ������ �ش�. x��ǥ�� y��ǥ�� �̹� �� �ִ�.
			
				xC = 0;
				yC = y;
			}
		}
		
		
		this.mobID = mobID;
		//�Է� ���� �� ���̵� ���� �ش� �� ��ü�� ������ �� �ش�.
		this.myHP = mobSize;
		
		inGame = true;
		//���� ������ ���� ����ִٴ� �ǹ��̴�.
				
	}
	
	public void deleteMob() {
		inGame = false;
		
		direction = right; 
		mobMoving = 0;
		xC = 0;
		yC = y;
		//�� �κ��� �� ������ �ָ� ������ ���� ��, �ٸ� ���ֵ��� �˵��� ��Ż�ϴ� �������� �߻���
		
	}

	
	public void loosemyHP () {
		Screen.myHP -= 1;
		//�� ü���� ��� �޼���
	}
	
	public int movingFrame = 0, movingSpeed = 35; 
	//movingSpeed ���� ���� ���� �ӵ��� ������.
	
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
				// �� �κ��� �ڳʿ� ���õ� �κ� ����.
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
		//losemyHP�޼��带 ȣ���� �� �־��� ����ŭ ü���� ���̵��� �������.
			checkDeath();
			//�� ü���� 0�̸� �����Ѵ�.
		}
		
		
		public void checkDeath() {
			if(myHP == 0){
				deleteMob();
			}
		}
		//�� ü���� 0�������� �Ǻ��Ѵ�.
		
		public boolean isDead() {
			if(inGame) {
				return false;
			}
			else{
				return true;
			}
		}
		//���� �׾����� ��Ҵ��� �Ǻ��Ѵ�.
	
	public void draw(Graphics g) {
			g.drawImage(Screen.img_info_mob[mobID], x, y, width, height, null);
			//�� �̹����� ����Ѵ�.
		
		//myHP bar
		g.setColor(new Color(180, 50, 50));
		g.fillRect(x, y - myHPSpace - myHPHeight, mobSize, myHPHeight);
			
		//������ �ٸ� �����Ѵ�.
		g.setColor(Color.red);
		g.fillRect(x, y - myHPSpace - myHPHeight, myHP, myHPHeight);

		//������ ���� ������ �����Ѵ�.
		g.setColor(new Color(0, 0, 0));
		g.drawRect(x, y - myHPSpace - myHPHeight, myHP - 1, myHPHeight - 1);
		
	}

}
