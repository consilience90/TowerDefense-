
/***************************************
 세 번째 클래스입니다.
 
 이 프로그램의 실행 순서는
 Frame객체 ->
 Frame객체의 생성자 ->
 생성자내 init()메서드 ->
 init()메서드 안의 screen객체 호출의 순서이다.
 
 그리고 Screen 객체의 여러 메서드들 중에 정의한 define()메서드를 사용하여 Room 클래스를 객체화 하여 불러온다.
 또한 Screen 객체의 paintComponent()메서드에서 Room.draw()메서드를 호출하여 그림을 그린다.
Room.draw()메서드가 호출되면 생성자 내에서 Room의 define()메서드를 실행하게 되며 define()메서드는 Block 클래스를 객체와 한다.
Room 내에 정의된 block 배열변수에 Block 클래스를 참조하게 하여 Room.draw()에서 block을 사용하여 출력하는 양상을 조절한다. 

*****************************************/


import java.awt.Graphics;

public class Room {
	public int widthRect = 12;
	public int heightRect = 8;
	//배열의 가로 칸수 세로 칸수를 지정한다.
	public int rectSize = 52;
	public Block[][] block;
	//바둑판과 같은 맵을 만들기 위해서 2차원 배열을 지정하였다.
	//block 클래스를 배열화한 타입으로 block 변수를 지정하였다.
	
	
	
	public Room(){
		define();
	}
	
	public void define() {
		block = new Block[heightRect][widthRect];
		
		for(int y=0;y<block.length;y++){
			//heightRect 인덱스 길이
			for(int x=0;x<block[0].length;x++){
				//widthRect 인덱스 길이		
				block[y][x] = new Block((Screen.scrnWidth/2) - ((widthRect*rectSize)/2)+ (x*rectSize), y*rectSize, rectSize, rectSize, Value.earthGrass, Value.buildNone);
				//맵에 표기되는 각각의 사각형의 위치와 길이를 설정한다. 또한 어떤 속성을 가지고 있는지 입력한다.
			}
		}
	}
	
	public void physic() {
		for (int y = 0; y < block.length; y++) {
			for (int x = 0; x < block[0].length; x++) {
				block[y][x].physic();
				//타겟 공격 메서드가 계속 실행된다. 왜냐하면 스레드에서 실행되기 때문이다.
			}
		}
	}
	
	
	
	public void draw(Graphics g){
	
		for(int y=0;y<block.length;y++){
			for(int x=0;x<block[0].length;x++){
				block[y][x].draw(g);
				//각각의 block 배열에 입력된 데이터를 바탕으로 그려낸다. 사각형이 그려진다.
			}
		}
		for(int y=0;y<block.length;y++){
			for(int x=0;x<block[0].length;x++){
				block[y][x].range(g);
				//여기에 범위를 푷현하는 코드를 두면 범위 전체 네모 전체가 나타난다.
			}
		}
		
	}
	
}
