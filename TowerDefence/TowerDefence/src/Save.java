/*
여섯번째 클래스 입니다.
*/
import java.io.File;
import java.util.*;
//java.util.*에서 Scanner 클래스를 가져올 수 있다. 외부 텍스트를 컴퓨터가 처리가능한 데이터로 치환할 수 있다.
public class Save {
	public void pathSave(File loadPath) {
		//스태이지 파일 주소를 받아 온다.
		try{
		Scanner loadScanner = new Scanner(loadPath);
		//스케너 클래스를 객체로 선언하려면 예외 처리를 해 주어야 한다.
		
		while(loadScanner.hasNext()){
			//다음 글자가 있으면 반복문을 계속 진행한다.
			Screen.killsToWin = loadScanner.nextInt();
			for(int y=0;y<Screen.room.block.length;y++){
				for(int x=0;x<Screen.room.block[0].length;x++){
					Screen.room.block[y][x].earthID = loadScanner.nextInt();
					//Screen.room.block[y][x]내에 있는 earthID 변수에 받은 글자 값을 저장한다.
					//배열 하나에 입력 받고 나면 그 다음 값을 입력 받고, 그 다음 값을 입력받고 하게 된다.
				}
			}
			//앞의 for문에서 earthID값을 다 받았기 때문에 그 다음 값을 받게 된다.
			for(int y=0;y<Screen.room.block.length;y++){
				for(int x=0;x<Screen.room.block[0].length;x++){
					Screen.room.block[y][x].buildID = loadScanner.nextInt();
					//Screen.room.block[y][x]내에 있는 airID 변수에 loadScanner 한 값을 입력하는 것이다.
					//배열 하나에 입력 받고 나면 그 다음 값을 입력 받고, 그 다음 값을 입력받고 하게 된다.
				}
			}
		}
		loadScanner.close();
		//현재 스케너를 클로즈한다. 곧, nextInt()로 다음 값, 다음 값 받던 것도 끝내게 된다.

		}catch(Exception e){
			
		}
	}
}
