
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Block extends Rectangle{
	//Rectangle은  좌표 공간 내의 REctangle 오브젝트의 좌상의 점(x,y), 그 폭, 및 그 높이로 둘러싸인 좌표 공간 내의 area를 지정한다.
	
	
	public int earthID;
	public int buildID;
	//유닛의 아이디를 저장한다.
	
	public Rectangle towerSpace;
	public static int towerSpaceSize = 100; //In Mission
	
	public int atk = -1;
	//몹을 공격하는데 관여된 변수
	public boolean aim = false;
	//탓겟 조준에 관한 불리언 변수

	public int aimTime =100, aimFrame = 0;

	
	//초기에 틀을 잡아 주었던 형식
	public Block(int x, int y, int width, int height, int earthID, int buildID ){		
		
		setBounds(x,y,width,height);
		//Rectangle 경계의 크기를 지정하는 것으로 x,y,width,height값에 따라 달라진다.
		
		towerSpace = new Rectangle(x - (towerSpaceSize/2), y - (towerSpaceSize/2), width + towerSpaceSize, height + towerSpaceSize);
		//이것은 타워의 공격 사정거리 범위를 설정하는 부분이다.		
		
		this.earthID = earthID;
		this.buildID = buildID;
		
		}

	public void draw(Graphics g){
		g.drawImage(Screen.img_info_ground[earthID], x, y, width, height, null);
		g.setColor(new Color(255,150,200));
		g.drawRect(x,y,width,height);
		//분홍색의 격자가 생기게 하였다.
		
		if(buildID  != Value.build){
			g.drawImage(Screen.img_info_air[buildID], x, y, width, height, null);
		}
		//격자의 한 칸 안에 놓인 몹의 이미지를 출력한다.
	}
		
	public void physic() {
		//몹을 공격하는 움직임을 구현한다.
		if (atk != -1 && towerSpace.intersects(Screen.mobs[atk])) {
			aim = true;
		} else {
			aim = false;
		}
		//이 부분은 사정거리 내에서 표적을 선정하도록 하는 메서드이다.
		
		if(!aim) {
			if (buildID == Value.buildTower) {
				for (int i = 0; i < Screen.mobs.length; i++) {
					//몹이 스폰되면 될 수록 몹 객체가 저장된 mobs[]배열은 점점 더 늘어난다.

					if (Screen.mobs[i].inGame) {
						//몹 객체가 죽지 않고 생성되어 있는지를 파악한다.						
						if (towerSpace.intersects(Screen.mobs[i])) {
						//해당 towerSquar안에 객체로 지정된 몹이 위치하면 공격하게끔 한다.
							aim = true;
							atk = i;
							//i 값을 충력해 보면몹의 갯수에 따라 0부터 몹 갯수까지의 수가 출력이 된다.
							//그래서 atk의 값에 해당하는 몹을 선택해서 추 후에 그 몹에 해당하는 대상의 x좌표와 y좌표 값을 이용해서 어디서 부터 어디까지 aim 하라고 한다.
						}
					}
				}
			}
		}
		
		
		
		if(aim) {
			if(aimFrame >= aimTime) {
				Screen.mobs[atk].loseMyHP(1);
				//겨냥된 몹의 체력을 깍는다.
				aimFrame = 0;
				//체력을 한번 깍고 공격을 중지하게 한다.
			} else {
				aimFrame += 1;
				//다시 프레임이 늘어나면 공격할 수 있게 한다.
			}
			
			if(Screen.mobs[atk].isDead()) {
				//몹 상태가 죽어 있으면
				getMoney(Screen.mobs[atk].mobID);
				//돈을 얻고
				aim = false;
				//조준하지 않는다.
				atk = -1;
				Screen.killed++;//킬 카운트를 한다.
			}
		}
	}
	
	
	public void getMoney(int mobID) {
			Screen.gold += (Value.mobReward[mobID]);
			//각각의 몹이 가진 보상 만큼의 값을 얻는다.
	}
		
	public void range(Graphics g) {
		g.setColor(Color.YELLOW);
		if(Screen.isDebug) {
			 if(buildID == Value.buildTower) {
				g.drawRect(towerSpace.x, towerSpace.y, towerSpace.width, towerSpace.height);
			}	
		}	
		//공격 범위를 확인할 때 사용한다.
		
		if(aim) {
			g.setColor(new Color(255,0,0));
			g.drawLine(x + (width/2), y + (height/2), Screen.mobs[atk].x + (Screen.mobs[atk].width/2), Screen.mobs[atk].y + (Screen.mobs[atk].height/2));
			//시작 지점과 끝지점을 정하고 선을 그린다.
		}
	}
		 

		
	
}
