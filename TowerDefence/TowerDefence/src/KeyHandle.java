import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener; 


public class KeyHandle implements MouseMotionListener, MouseListener{

	public void mouseClicked(MouseEvent e) {
		//누른 마우스를 떼어 주면 이 이벤트가 발생하는데 mouseReleased 다음에 발동합니다.
	}
	public void mousePressed(MouseEvent e) {
		//마우스를 누른 상태에서 이 이벤트가 실행이 됩니다.
		Screen.tower.click(e.getButton());
		//마우스를 누르면 버튼을 가지게 됩니다.
	}
	public void mouseReleased(MouseEvent e) {
		//마우스를 누른 후 떼면 이 이벤트가 발생합니다.
	}
	public void mouseEntered(MouseEvent e) {
		//마우스가 게임 안으로 들어오면 이 이벤트가 실행됩니다.
	}
	public void mouseExited(MouseEvent e) {
		//마우스가 설정된 게임의 창 밖으로 나가면 이 이벤트가 실행이 된다.
	}
	public void mouseDragged(MouseEvent e) {	
		Screen.mse = new Point( (e.getX()) - ((Frame.size.width - Screen.scrnWidth)/2)  , (e.getY() - ((Frame.size.height - (Screen.scrnHeight)) - (Frame.size.width - Screen.scrnWidth)/2)));
	}
	public void mouseMoved(MouseEvent e) {
		Screen.mse = new Point( (e.getX()) - ((Frame.size.width - Screen.scrnWidth)/2)  , (e.getY() - ((Frame.size.height - (Screen.scrnHeight)) - (Frame.size.width - Screen.scrnWidth)/2)));
	}	
}
