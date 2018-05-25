package box;

import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeEvent;

/**
 * A class representing the simply dialogbox.
 * @author Afraz Salim
 *
 */
public class SimpleDialogBox extends DialogBox {

	/**
	 * A constructor to create new instnace of the dialogbox.
	 * @param x
	 *        The x-coordinate of the dialogbox which will be created.
	 * @param y
	 *        They-coodinate of the dialogbox which will be created.
	 * @param width
	 *        The with of the newly to be created dialogbox.
	 * @param height
	 *        The height of the newly to created dialogbox.
	 */
	public SimpleDialogBox(int x, int y, int width, int height) {
		super(x, y, width, height);
		super.setTitleBarHeight(10);
		super.setTitleBarX(this.getX());
		super.setTitleBarY(this.getY()-this.getTitleBarHeight());
		super.setTitleBarWidth(this.getWidth());
	}
	

	
	/**
	 * A function to get the shape of the dialogbox/
	 */
	@Override
	public Shape getShape() {
		Rectangle2D rect = new Rectangle2D.Double(this.getX(),this.getY(),this.getWidth(),this.getHeight());
		return rect;
	}


	/**
	 * A function to move the dialogbox to new place.
	 */
	@Override
	public void moveTo(int x, int y, int previousX, int previousY) {
	  this.setX(this.getX()+(x-previousX));
  	  this.setY(this.getY()+(y-previousY));
  	  this.setTitleBarX(this.getTitleBarX()+(x-this.getTitleBarX()));
  	  this.setTitleBarY(this.getY()+(y-this.getTitleBarY()));		
	}





	

}
