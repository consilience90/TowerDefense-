/*
������° Ŭ���� �Դϴ�.
*/
import java.io.File;
import java.util.*;
//java.util.*���� Scanner Ŭ������ ������ �� �ִ�. �ܺ� �ؽ�Ʈ�� ��ǻ�Ͱ� ó�������� �����ͷ� ġȯ�� �� �ִ�.
public class Save {
	public void pathSave(File loadPath) {
		//�������� ���� �ּҸ� �޾� �´�.
		try{
		Scanner loadScanner = new Scanner(loadPath);
		//���ɳ� Ŭ������ ��ü�� �����Ϸ��� ���� ó���� �� �־�� �Ѵ�.
		
		while(loadScanner.hasNext()){
			//���� ���ڰ� ������ �ݺ����� ��� �����Ѵ�.
			Screen.killsToWin = loadScanner.nextInt();
			for(int y=0;y<Screen.room.block.length;y++){
				for(int x=0;x<Screen.room.block[0].length;x++){
					Screen.room.block[y][x].earthID = loadScanner.nextInt();
					//Screen.room.block[y][x]���� �ִ� earthID ������ ���� ���� ���� �����Ѵ�.
					//�迭 �ϳ��� �Է� �ް� ���� �� ���� ���� �Է� �ް�, �� ���� ���� �Է¹ް� �ϰ� �ȴ�.
				}
			}
			//���� for������ earthID���� �� �޾ұ� ������ �� ���� ���� �ް� �ȴ�.
			for(int y=0;y<Screen.room.block.length;y++){
				for(int x=0;x<Screen.room.block[0].length;x++){
					Screen.room.block[y][x].buildID = loadScanner.nextInt();
					//Screen.room.block[y][x]���� �ִ� airID ������ loadScanner �� ���� �Է��ϴ� ���̴�.
					//�迭 �ϳ��� �Է� �ް� ���� �� ���� ���� �Է� �ް�, �� ���� ���� �Է¹ް� �ϰ� �ȴ�.
				}
			}
		}
		loadScanner.close();
		//���� ���ɳʸ� Ŭ�����Ѵ�. ��, nextInt()�� ���� ��, ���� �� �޴� �͵� ������ �ȴ�.

		}catch(Exception e){
			
		}
	}
}
