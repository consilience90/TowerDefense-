import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener; 


public class KeyHandle implements MouseMotionListener, MouseListener{

	public void mouseClicked(MouseEvent e) {
		//���� ���콺�� ���� �ָ� �� �̺�Ʈ�� �߻��ϴµ� mouseReleased ������ �ߵ��մϴ�.
	}
	public void mousePressed(MouseEvent e) {
		//���콺�� ���� ���¿��� �� �̺�Ʈ�� ������ �˴ϴ�.
		Screen.tower.click(e.getButton());
		//���콺�� ������ ��ư�� ������ �˴ϴ�.
	}
	public void mouseReleased(MouseEvent e) {
		//���콺�� ���� �� ���� �� �̺�Ʈ�� �߻��մϴ�.
	}
	public void mouseEntered(MouseEvent e) {
		//���콺�� ���� ������ ������ �� �̺�Ʈ�� ����˴ϴ�.
	}
	public void mouseExited(MouseEvent e) {
		//���콺�� ������ ������ â ������ ������ �� �̺�Ʈ�� ������ �ȴ�.
	}
	public void mouseDragged(MouseEvent e) {	
		Screen.mse = new Point( (e.getX()) - ((Frame.size.width - Screen.scrnWidth)/2)  , (e.getY() - ((Frame.size.height - (Screen.scrnHeight)) - (Frame.size.width - Screen.scrnWidth)/2)));
	}
	public void mouseMoved(MouseEvent e) {
		Screen.mse = new Point( (e.getX()) - ((Frame.size.width - Screen.scrnWidth)/2)  , (e.getY() - ((Frame.size.height - (Screen.scrnHeight)) - (Frame.size.width - Screen.scrnWidth)/2)));
	}	
}
