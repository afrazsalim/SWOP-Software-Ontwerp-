package shapes;

import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
/**
 * A class which represents the label of the party.
 * @author Afraz Salim
 *
 */
public class Label {
	/**
	 * The given x-coordinate of the label.
	 */
	private int x;
	/**
	 * The given y-coordinate of the label.
	 */
	private int y;
	/**
	 * The given width of the label.
	 */
	private int width;
	/**
	 * The given height of the label.
	 */
	private int height;
	/**
	 * The given string of the label.
	 */
	private String text;
	
	/**
	 * A constructor to initialize the label's data.
	 * @param x
	 *        The given x-Coordinate of the label.
	 * @param y
	 *        The given y-coordinate of the label.
	 * @param width
	 *        The given width of the window.
	 * @param height
	 *        The given height of the label.
	 * @param text
	 *        The given text of the label.
	 */
	public Label(int x, int y , int width, int height, String text) {
		this.setX(x);
		this.setY(y);
		this.setWidth(width);
		this.setHeight(height);
		this.setText(text);
		this.setLabelActive(true);
	}
	
	/**
	 * A getter to get the x-coordinate of the label.
	 * @return
	 *       Returns the current x-coordinate of the label.
	 */
	public int getX() {
		return x;
	}
	/**
	 * @param x
	 *       Sets the x-Coordinate equal to the given x-coordinate.
	 */
	protected void setX(int x) {
		this.x = x;
	}
	/**
	 * A getter to get the y-coordinate of the label.
	 * @return
	 *        Returns the y-coordinate of the label.
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * 
	 * @param y
	 *        Sets the y-coordinate of the label.
	 */
	protected void setY(int y) {
		this.y = y;
	}
	/**
	 * A getter to get the width of the label.
	 * @return
	 *       Returns the width of the label.
	 */
	public int getWidth() {
		return width;
	}
	/**
	 * A setter to set the width of the label.
	 * @param width
	 *        The new width of the label.
	 */
	protected void setWidth(int width) {
		this.width = width;
	}
	/**
	 * A getter to get the height of the label.
	 * @return
	 *        Returns the height of the label.
	 */
	public int getHeight() {
		return height;
	}
	/**
	 * A setter to set the height of the label.
	 * @param height
	 *        The new height of the label.
	 */
	protected void setHeight(int height) {
		this.height = height;
	}

	/**
	 * Text of the label.
	 * @return
	 *        Returns the text of the label.
	 */
	public String getText() {
		return text;
	}

	/**
	 * A setter to set the text of the label.
	 * @param text
	 *        The new text of the label.
	 */
	public void setText(String text) {
		this.text = text;
	}
	
	
	/**
	 * A getter ot get the shape of the label.
	 * @return
	 *        Returns the shape of the label.
	 */
	public Shape getShape() {
		Shape rect = new Rectangle2D.Double(this.getX(),this.getY(),this.getWidth(),this.getHeight());
		return rect;
	}

	/**
	 * A checker to check if the label contains a specific coordinate.
	 * @param x
	 *        The given x-coordinate which will be checked.
	 * @param y
	 *        The given y-coordinate to be checked.
	 * @return
	 *        Returns true of the label contains the given coordinates.
	 */
	public boolean contains(int x, int y) {
		return (x >= this.getX() && x <= this.getX()+this.getWidth() && y >= this.getY() && y <= this.getY()+this.getHeight());
	}

	/**
	 * A boolean variable which checks if the label is active.
	 */
	private boolean isActive;
	
	/**
	 * A setter to set the label status accordingly.
	 * @param value
	 *        The new value which will replace the old status.
	 */
	protected void setLabelActive(boolean value) {
		this.isActive = value;
	}
	/**
	 * A checker to check if the label is an active label.
	 * @return
	 *        Returns true if label is an active label.
	 */
	public boolean isActive() {
		return isActive;
	}

	/**
	 * Removes the text from the label.
	 */
	public void removeText() {
      String text = this.getText();
      char [] arr = text.toCharArray();
      ArrayList<Character> list = new ArrayList<>();
      for(int i = 0; i <arr.length-1;i++)
    	  list.add(arr[i]);
      if(list.size() == 0)
    	  this.text = "";
      if(list.size() > 0)
		 this.text = list.stream().map(e->e.toString()).reduce((acc, e) -> acc + e).get();
	}
	
	
	

}
