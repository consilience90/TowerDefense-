
import java.awt.*;

public class Mob extends Rectangle implements mbStatus{
	
	public int xC,yC;
	public int myHP;
	public int fullHp;
	
	//mbStatus�� ��� �޴� �κ�
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
		this.myHP = hp;
		this.fullHp = this.myHP;
		this.movingSpeed=speed;
		this.mobReward = reward;
		
		inGame = true;
		//���� ������ ���� ����ִٴ� �ǹ��̴�.
				
	}
	
	public void deleteMob() {
		inGame = false;
		
		wardSign = right;
		mobMoving = 0;
		xC = 0;
		yC = y;
		//�� �κ��� �� ������ �ָ� ������ ���� ��, �ٸ� ���ֵ��� �˵��� ��Ż�ϴ� �������� �߻���
		
	}
	
	public void pass() {
		passMob = true;
		
		wardSign = right;
		mobMoving = 0;
		xC = 0;
		yC = y;
		//�� �κ��� �� ������ �ָ� ������ ���� ��, �ٸ� ���ֵ��� �˵��� ��Ż�ϴ� �������� �߻���
		
	}

	
	public void looseMyHP () {
		Screen.myHP -= 1;
		//�� ü���� ��� �޼���
	}
	
	public int movingFrame = 0, movingSpeed = 35; 
	//movingSpeed ���� ���� ���� �ӵ��� ������.
	
	public void physic() {
		//Screen ��ü�� ������ �ȿ��� ����ȴ�.
	
		
		if(movingFrame >= movingSpeed){
			//���� �������� ������ �ȿ��� �ݺ����� ���ư� ������ �����Ѵ�. �� �ݺ� Ƚ���� movingSpeed���� ���� Ƚ���� �ѱ� �� �����Ѵ�.
			//�� ��� ������ �ð��� ������ ����ŭ �����̰� �Ϸ������� �����Ѵ�.
			//������ ������ ���� �����̵��� �Ѵ�.
			//�� ���� �� �� �������θ� �̵��ϰ� �Ѵ�.
			
			if(wardSign == right){
				//wardSign�� �ο��� ������ �������̸�
				x += 1;
				//������ �������� 1�ȼ� �̵�
			}else if(wardSign == upward){
				//wardSign�� �ο��� ������ �����̸�
				y -= 1;
				//���� �������� 1�ȼ� �̵�
			}else if(wardSign == downward) {
				////wardSign�� �ο��� ������ �Ʒ����̸�
				y += 1;
				//�Ʒ��� �������� 1�ȼ� �̵�
			}else if(wardSign == left) {
				//wardSign�� �ο��� ������ �����̸�
				x -= 1;
				//���� �������� 1�ȼ� �̵�
			}
			
			mobMoving += 1;
			//�� �� ������ ������ ���� �ݺ��� ���� ���ݿ� ���� �����Ѵ�.
			
			//������ wardSign�� ���� ������ �� ���� ���̴�.
			if(mobMoving == Screen.room.rectSize){
			//�簢���� ���� ���� ��ŭ �̵��ϸ�
			//�ٷ� �� �κп��� ���� wardSign 
				if(wardSign == right){
					//���������� ���� �Ѵٸ�
					xC += 1;
					//���� �迭���� ������ �迭�� ��ġ�� Ž���� �� ������ ������ Ž���ϴ� ������ �ٲ��ְ�
					hasRight = true;
					//���������� ���� �ȴٰ� ����Ѵ�.
				}else if(wardSign == upward){
					//���� ���� �Ѵٸ�
					yC -= 1; 
					//���� �迭���� ������ �迭�� ��ġ�� Ž���� �� ���� ������  Ž���ϴ� ������ �ٲ��ְ�
					hasUpward = true;
					//���� ���� �ȴٰ� ����Ѵ�.
				}else if(wardSign == downward) {
					//�Ʒ��� ���� �Ѵٸ�
					yC += 1;
					//���� �迭���� ������ �迭�� ��ġ�� Ž���� �� �Ʒ��� ������  Ž���ϴ� ������ �ٲ��ְ�
					hasDownward = true;
					//�Ʒ��� ���� �ȴٰ� ����Ѵ�.
				}else if(wardSign == left) {
					//�������� ���� �Ѵٸ�
					xC -= 1;
					//���� �迭���� ������ �迭�� ��ġ�� Ž���� �� �Ʒ��� ������  Ž���ϴ� ������ �ٲ��ְ�
					hasLeft = true;
					//�������� ���� �ȴٰ� ����Ѵ�.
				}
				//�� �� �κ��� �����Ǵ� ������ �����Ѵ�. �̵��ϰ� ���� ���� �κ��� ���� �Ǵ� �κ����� Ȯ�� �ϱ� ���ؼ� ���� �κ��� Ž���ϰԲ� �ϴ� ��.
				
				
				//������ �ο��Ѵ�.
				if(!hasUpward){
				//���� ���� �ȴٴ� �㰡�� ���� ���Ͽ��ٸ�
					try {
						if(Screen.room.block[yC+1][xC].earthID == Value.earthRoad){
							//������ġ �Ʒ����� ���̸�
							wardSign = downward;
							//�Ʒ����̶� ���ø� �ο��Ѵ�.							
						}
					}catch (Exception e) {}					
				}
				
				if(!hasDownward){
				//�Ʒ��� ���� �ȴٴ� ����� ���� ���Ͽ��ٸ�
					try {
						if(Screen.room.block[yC-1][xC].earthID == Value.earthRoad){
							//������ġ ������ ���̶��
							wardSign = upward;
							//�����̶� ���ø� �ο��Ѵ�.
						}
					} catch (Exception e) {}
				}
				
				if(!hasLeft){
				//�������� ���� �ȴٴ� ����� ���� ���Ͽ��ٸ�
					try {
						if(Screen.room.block[yC][xC+1].earthID == Value.earthRoad){
							//���� ��ġ �������� ���̶��
							wardSign = right;
							//�������̶� ���ø� �ο��Ѵ�.
						}
					} catch (Exception e) {}
				}
				
				if(!hasRight){
				//���������� ���� �ȴٴ� ����� ���� ���Ͽ��ٸ�
					try {
						if(Screen.room.block[yC][xC-1].earthID == Value.earthRoad){
							//���� ��ġ ������ ���̶��
							wardSign = left;
							//�����̶� ���ø� �ο��Ѵ�.
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
			//losemyHP�޼��带 ȣ���� �� �־��� ����ŭ ü���� ���̵��� �������.
			checkDeath();
			//�� ü���� 0�̸� �����Ѵ�.
		}
		
		
		public void checkDeath() {
			if(myHP <= 0){
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
		Image img = Screen.img_info_mob[mobID];
			g.drawImage(img, x, y, width, height, null);
			//�� �̹����� ����Ѵ�.
		
		//�������� ���׶���
		g.setColor(new Color(180, 50, 50));
		g.fillRect(x, y - myHPSpace - myHPHeight, mobSize, myHPHeight);
			
		//������ �ٸ� �����Ѵ�.
		g.setColor(Color.red);
		//g.fillRect(x, y - myHPSpace - myHPHeight, Screen.room.rectSize, myHPHeight);
		g.fillRect(x, y - myHPSpace - myHPHeight, (myHP*mobSize)/fullHp, myHPHeight);

		//������ ���� ������ �����Ѵ�.
		g.setColor(new Color(0, 0, 0));
		g.drawRect(x, y - myHPSpace - myHPHeight, mobSize, myHPHeight - 1);
		
	}

}
