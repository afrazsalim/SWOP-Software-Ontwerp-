package shapes;

import java.awt.Graphics;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import domainObjects.Message;

/**
 * A super-class representing the line of the party shape.
 * @author Afraz Salim
 *
 */
public abstract class LineShape {
	/**
	 * The given x-coordinate of the line.
	 */
	private int x;
	/**
	 * The given y-coordinate of the line.
	 */
	private int y;
	/**
	 * The given width of the line.
	 */
	private int width;
	/**
	 * The given height of the line.
	 */
	private int height;
	
	private ArrayList<ActivationBarShape> barList;

	
	

	/**
	 * A constructor to initialize the line.
	 * @param x
	 *        The given x-coordinate of the line.
	 * @param y
	 *        The given y-coordinate of the line.
	 * @param width
	 *        The given width of the line.
	 * @param height
	 *        The given height of the line.
	 */
	public LineShape(int x, int y, int width, int height) {
		this.setX(x);
		this.setY(y);
		this.setWidth(width);
		this.setHeight(height);
		this.setBarList(new ArrayList<ActivationBarShape>());
	}
	

	
	
	/**
	 * A getter to get the shape of the line.
	 * @return
	 *      Returns the shape of the line.
	 */
	public Shape getShape() {
       Shape rect = new Rectangle2D.Double(this.getX(),this.getY(),this.getWidth(),this.getHeight());
		return rect;
	}
	

	/**
	 * A checker to check if there is any activationbar at the give coordinates.
	 * @param x
	 *        The given x-coordinate to be checked.
	 * @param y
	 *       The given y-cordinate to be checked.
	 * @return
	 *       Returns true if the coordinates lies in specific range.
	 */
/**   public boolean hasActivationBarAt(int x, int y) {
	   for(Coordinate temp : this.getActivationBarList()) {
		   if(x >= temp.getX() && x <= temp.getX()+this.getActivationBarWidth() && y >= temp.getY() && y<= temp.getY()+this.getActivationBarHeigth())
			   return true;
	   }
	   return false;
   }**/
	
   /**
    * A getter to get the x-coordinate.
    * @return
    *       Returns the x-coordinate of the line.
    */
	public int getX() {
		return x;
	}
	/**
	 * A setter to set the x-coordinate.
	 * @param x
	 *        The new x value.
	 */
	protected void setX(int x) {
		this.x = x;
	}
	/**
	 * A getter to get the y-coordinate of the line.
	 * @return
	 *       Returns the y-coordinate of the line.
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * A setter to set the y-coordinate of the line.
	 * @param y
	 *       The new value of line.
	 */
	protected void setY(int y) {
		this.y = y;
	}
	/**
	 * A getter to get the width of the line.
	 * @return
	 *      Returns the width of the line.
	 */
	public int getWidth() {
		return width;
	}
	/**
	 * A setter to set the width of the activation bar.
	 * @param width
	 *       The new width of the activation bar.
	 */
	protected void setWidth(int width) {
		this.width = width;
	}
	/**
	 * A getter to get the height of the activationbar.
	 * @return
	 *       Returns the height of the activation bar.
	 */
	public int getHeight() {
		return height;
	}
	/**
	 * A setter to set the height of the activation bar.
	 * @param height
	 *        The given new heigh of the activation bar.
	 */
	private void setHeight(int height) {
		this.height = height;
	}



	/**
	 * A checker to check if the line contrains a point.
	 * @param x
	 *       The given x-coordinate to be controlled.
	 * @param y
	 *       The given y-coordinate to be controlled.
	 * @return
	 *       Returns true if the line shape contains the given points.
	 */
	public boolean contains(int x, int y) {
		return this.getShape().contains(x,y);
	}











	

	/**
	 * A functionw which draw the line shape and all activationbars.
	 * @param g
	 *       The given graphics instance with which the line draws itself.
	 */
	public void draw(Graphics g) {
	}











	/**
	 * A function to get the list of activation bars.
	 * @return
	 *       Returns the activation bar list.
	 */
	public ArrayList<ActivationBarShape> getBarList() {
		return barList;
	}




	/**
	 * A setter to set the bar list of the line.
	 * @param barList
	 *        The new bar list of the line.
	 */
	protected void setBarList(ArrayList<ActivationBarShape> barList) {
		this.barList = barList;
	}




	/**
	 * A function which creates a new message shape.
	 * @param sender
	 *        The sender of the message shape.
	 * @param reciever
	 *        The reciever of the message's shape.
	 * @param message
	 *         The message for which shape will be created.
	 * @param x
	 *         The x-coordinate of the message's shape.
	 * @param y
	 *        The y-coordinate of the message's shape.
	 * @param previousX
	 *        The previously clicked x-coordinate.
	 * @param previousY
	 *       The previously click y-coordinate.
	 * @return
	 *       Returns a new message shape.
	 */
	public abstract MessageShape createMessageShape(PartyShape sender,PartyShape reciever,Message message, int x, int y,int previousX,int previousY);








	/**
	 * A function to update the points of the line shape.
	 * @param lineX
	 *        The new x-coordinate of the line.
	 * @param lineY
	 *       The new y-coordinate of the line.
	 */
	public abstract void updatePoints(int lineX, int lineY);




	/**
	 * A function to remove messageShape.
	 * @param messageUnderConstruction
	 *         The shape of the message which will be remvoed.
	 */
	public abstract void removeMessage(MessageShape messageUnderConstruction);




	/**
	 * A function which end the message creation process by interpolating end points.
	 * @param shape
	 *        The message shape which was being constructed.
	 * @param message
	 *        The message of the shape.
	 * @param x
	 *        The x-coordinate of the end point of the message's shape.
	 * @param y
	 *        The y-coordinate of the end point of the message's shape.
	 */
	public abstract void finishMessage(PartyShape shape, MessageShape message, int x, int y);




	/**
	 * A function which transforms line from one shape to other.
	 * @param shape
	 *        The new shape of this line.
	 */
	public abstract void updateOwnerShip(PartyShape shape);




	/***
	 * A function to get the intersecting message shape's label.
	 * @param x2
	 *        The x-coordinte to check if the message's shape intersect the point.
	 * @param y2
	 *        The y-coordinat eot check if the message's shape intersects the point.
	 * @return
	 *        Returns true if the message's shape intersects the point given by following coordinates.
	 */
	public abstract MessageShape getIntersectingLabel(int x2, int y2);




	/**
	 * A function check if any message's shapê intersects the point.
	 * @param x
	 *       The x-coorrdinte of the point to be checked.
	 * @param y
	 *       The y-coordinate of the point to be checked.
	 * @return
	 *       Returns true if teh message's shape intersects the coordinates.
	 */
	public abstract MessageShape hasAnyIntersectingMessage(int x, int y);




	/**
	 * A function to check if this line has any selected messge.
	 * @return
	 *       Returns true if the message is selected.
	 */
	public abstract MessageShape getSelectedMessage();




	/**
	 * A function which changes all the activation bars list.
	 * @param barList2
	 *       the new activation bar list of this line.
	 */
	public void changeBarList(ArrayList<ActivationBarShape> barList2) {
		setBarList(barList2);
	}




	
	
	
	
	
	
	

}
