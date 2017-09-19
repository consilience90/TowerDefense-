
/***************************************
 �� ��° Ŭ�����Դϴ�.
 
 �� ���α׷��� ���� ������
 Frame��ü ->
 Frame��ü�� ������ ->
 �����ڳ� init()�޼��� ->
 init()�޼��� ���� screen��ü ȣ���� �����̴�.
 
 �׸��� Screen ��ü�� ���� �޼���� �߿� ������ define()�޼��带 ����Ͽ� Room Ŭ������ ��üȭ �Ͽ� �ҷ��´�.
 ���� Screen ��ü�� paintComponent()�޼��忡�� Room.draw()�޼��带 ȣ���Ͽ� �׸��� �׸���.
Room.draw()�޼��尡 ȣ��Ǹ� ������ ������ Room�� define()�޼��带 �����ϰ� �Ǹ� define()�޼���� Block Ŭ������ ��ü�� �Ѵ�.
Room ���� ���ǵ� block �迭������ Block Ŭ������ �����ϰ� �Ͽ� Room.draw()���� block�� ����Ͽ� ����ϴ� ����� �����Ѵ�. 

*****************************************/


import java.awt.Graphics;

public class Room {
	public int widthRect = 12;
	public int heightRect = 8;
	//�迭�� ���� ĭ�� ���� ĭ���� �����Ѵ�.
	public int rectSize = 52;
	public Block[][] block;
	//�ٵ��ǰ� ���� ���� ����� ���ؼ� 2���� �迭�� �����Ͽ���.
	//block Ŭ������ �迭ȭ�� Ÿ������ block ������ �����Ͽ���.
	
	
	
	public Room(){
		define();
	}
	
	public void define() {
		block = new Block[heightRect][widthRect];
		
		for(int y=0;y<block.length;y++){
			//heightRect �ε��� ����
			for(int x=0;x<block[0].length;x++){
				//widthRect �ε��� ����		
				block[y][x] = new Block((Screen.scrnWidth/2) - ((widthRect*rectSize)/2)+ (x*rectSize), y*rectSize, rectSize, rectSize, Value.earthGrass, Value.buildNone);
				//�ʿ� ǥ��Ǵ� ������ �簢���� ��ġ�� ���̸� �����Ѵ�. ���� � �Ӽ��� ������ �ִ��� �Է��Ѵ�.
			}
		}
	}
	
	public void physic() {
		for (int y = 0; y < block.length; y++) {
			for (int x = 0; x < block[0].length; x++) {
				block[y][x].physic();
				//Ÿ�� ���� �޼��尡 ��� ����ȴ�. �ֳ��ϸ� �����忡�� ����Ǳ� �����̴�.
			}
		}
	}
	
	
	
	public void draw(Graphics g){
	
		for(int y=0;y<block.length;y++){
			for(int x=0;x<block[0].length;x++){
				block[y][x].draw(g);
				//������ block �迭�� �Էµ� �����͸� �������� �׷�����. �簢���� �׷�����.
			}
		}
		for(int y=0;y<block.length;y++){
			for(int x=0;x<block[0].length;x++){
				block[y][x].range(g);
				//���⿡ ������ �����ϴ� �ڵ带 �θ� ���� ��ü �׸� ��ü�� ��Ÿ����.
			}
		}
		
	}
	
}
