package box;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.KeyEvent;

import button.Button;
/**
 * A class representing the elements of dialogbox.
 * @author Afraz Salim
 *
 */
public abstract class DialogBoxElement {

	private int x;
	private int y;
	private int width;
	private int height;
	
	private static DialogBoxElement selectedElement;
	/**
	 * A constructor to initialize the dialogBox elements.
	 * @param x
	 *        The x-coordinate of the dialogbox element.
	 * @param y
	 *        The y-coordinate of the dialogbox element.
	 * @param width
	 *        The width of the dialog box element.
	 * @param height
	 *        The height of the dialogbox element.
	 */
	public DialogBoxElement(int x, int y, int width, int height) {
		this.setX(x);
		this.setY(y);
		this.setWidth(width);
		this.setHeight(height);
	}
	
	/**
	 * A getter to get the x-coordinate of the dialogbox.
	 * @return
	 *        Returns the x-coordnate of the dialog box.
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * A setter to set the x-coordinate of the dialogbox.
	 * @param x
	 *        The new x-coordinate of the dialogbox.
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * A getter to get the y-coordinate of the dialogbox.
	 * @return
	 *        Returns the y-coordnate of the dialog box.
	 */
	public int getY() {
		return y;
	}



	/**
	 * A setter to set the y-coordinate of the dialogbox.
	 * @param y
	 *        The new y-coordinate of the dialogbox.
	 */
	protected void setY(int y) {
		this.y = y;
	}

	/**
	 * A getter to get the width of the dialogbox.
	 * @return
	 *        Returns the widthof the dialog box.
	 */
	public int getWidth() {
		return width;
	}

     /**
      * A function to set the widht of the dialogbox element.
      * @param width
      *        Thenew width of the dialogbox.
      */
	protected void setWidth(int width) {
		this.width = width;
	}


	/**
	 * A function to get the heightof the dialogbox element.
	 * @return
	 *       Returns the height of the dialogbox.
	 */
	public int getHeight() {
		return height;
	}


	/**
	 * A setter to set the height of the dialogbox.
	 * @param height
	 *        The new height of the dialogbox.
	 */
	protected void setHeight(int height) {
		this.height = height;
	}

	private boolean isActive;
	
	/**
	 * A getter to get if the dialogbox element is active.
	 * @return
	 *       Returns true if dialogbox element is active.
	 */
	public boolean getIsActive() {
		return this.isActive;
	}
	/**
	 * A function to set the dialogbox element active.
	 * @param b
	 *        The new state of the dialogbox element.
	 */
	public void setActive(boolean b) {
       	this.isActive = b;
	}


	/**
	 * A function which indicates that a mouse click has occured.
	 * @param x
	 *        The x-coordinate of the point on which mouse click happend.
	 * @param y
	 *        The y-coordiante of the point on which mouse click occured.
	 */
	public abstract void mouseClick(int x, int y);


	/**
	 * A function to move the dialogboxelement to new place.
	 * @param x
	 *        The new x-coordinate coordinate on which mouse click happend.
	 * @param y
	 *        The new y-coordinate on which mouse click occured.
	 * @param previousX
	 *        The previous x-coordinate on which mouse click occured.
	 * @param previousY
	 *        The prevoious y-coordinate on which mouse click occured.
	 */
	public abstract void moveTo(int x, int y, int previousX, int previousY);


	/**
	 * A function which is used by dialogbox elements to draw themselves.
	 * @param g2 
	 *        The instance of the graphics with which the dialogbox will be drawn.
	 */
	protected abstract void draw(Graphics2D g2);


	/**
	 * A function which returns the slected dialogbox element.
	 * @return
	 *       Returns the selected dialogbox eleemnt.
	 */
	public static DialogBoxElement getSelectedElement() {
		return selectedElement;
	}


	/**
	 * A setter to set the selected element.
	 * @param element
	 *        The element which will be set as selected element.
	 */
	public static void setSelectedElement(DialogBoxElement element) {
		selectedElement = element;
	}


	/**
	 * A checker to if the dialogbox element contains the given coordinates.
	 * @param x
	 *        The x-coordinate to be checked.
	 * @param y
	 *        The y-coordinate to be checked.
	 * @return
	 *        Returns true if the dialogbox element contains the coordinates.
	 */
	public boolean contains(int x, int y) {
		return this.getShape().contains(x,y);
	}

/**
 * A function to get the shape of the dialogbox element.
 * @return
 *      Returns the shape of the dialogbox element.
 */
	protected abstract Shape getShape();


	/**
	 * A function indicating that a key action has been performed.
	 * @param id
	 *        The id of the key event.
	 * @param keyCode
	 *        The key code of the key event.
	 * @param keyChar
	 *        The char of the key event.
	 */
	public void performKey(int id, int keyCode, char keyChar) {
  
	}


	/**
	 * A function whcih adds char to textfield.
	 * @param keyChar
	 *        Character which will be added to textfield.
	 */
	protected void addText(char keyChar) {
		
	}


	/**
	 * A function which removes the text from label.
	 */
	protected void removeText() {
		
	}


	/**
	 * A function to change the size of the dialogbox eelement.
	 * @param x
	 *        The x-coordinate on which mouse click event happend.
	 * @param y
	 *        The y-coordinate on which mouse click event happepnd.
	 * @param previousX
	 *        The previously clicked x-coordinate ofa point.
	 * @param previousY
	 *         The previously clicked y-coordinate ofa point.
	 */
	protected abstract void changeSize(int x, int y, int previousX, int previousY);


	/**
	 * A function to draw the selected element.
	 * @param g2
	 *        The instance of graphics to draw the selected shape.
	 */
	public void drawSelected(Graphics2D g2) {
		
	}
	/**
	 * A variable to store the element as selected.
	 */
	private static DialogBoxElement element;
	
	/**
	 * A function to set the dialogbox element as selected.
	 * @param e
	 *       The element which will be set as selected element.
	 */
	protected static void setSelectedChild(DialogBoxElement e) {
		element = e;
	}
	
	/**
	 * A function which returns the slected dialogbox element.
	 * @return
	 *       Returns the selected dialogbox eleemnt.
	 */
	protected static DialogBoxElement getSelectedChild() {
		return element;
	}



	
	
}
