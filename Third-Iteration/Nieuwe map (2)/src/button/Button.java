package button;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import box.DialogBoxElement;
/**
 * A class representing the button.
 * @author Afraz Salim
 *
 */
public abstract class Button extends DialogBoxElement {
	
	private ArrayList<ActionListener> listener;
	private int x;
	private int y;
	private String label;
	private int dimension;
	
	/**
	 * A constructor to initialize a new button.
	 * @param x
	 *        The x-coordiante of the button.
	 * @param y
	 *        The y-coordinate of the button.
	 * @param text
	 *        The label text of the button.
	 */
	public Button(int x , int y, String text) {
		super(x,y,0,0);
		this.setLabel(text);
	}
	
	
	
	/**
	 * A getter to get the shape of the button.
	 */
	protected abstract Shape getShape();
	
	protected abstract Shape getInnerShape();
	
	protected ArrayList<ActionListener> getListener() {
		return listener;
	}

	protected void setListener(ArrayList<ActionListener> listener) {
		this.listener = listener;
	}

	/**
	 * A function to get the x-coordinate of the button.
	 */
	public int getX() {
		return x;
	}

	/**
	 * A setter to set the x-coordinate of teh button.
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * A getter to get the y-coordinate of the button.
	 */
	public int getY() {
		return y;
	}

	/**
	 * A setter to set the y-coordinate of teh button.
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * A getter to get the label's text.
	 * @return
	 *      Returns the text of the label.
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * A setter to set the label's text.
	 * @param label
	 *        The new text of the button's label.
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * A getter to get the dimension of the button.
	 * @return
	 *        Returns the dimension of the button.
	 */
	public int getDimension() {
		return dimension;
	}
	
	/**
	 * A function to check if the button's shape contains the point.
	 */
	public abstract boolean contains(int x, int y);

	

	/**
	 * A setter to set the dimension of the button.
	 * @param dimension
	 *        The new dimension of the button.
	 */
	public void setDimension(int dimension) {
		this.dimension = dimension;
	}
	
	/**
	 * A function to remove the listener.
	 * @param listener
	 *         The listener which will be removed.
	 */
	public void removeListener(ActionListener listener) {
		this.getListener().remove(listener);
	}
	
	/**
	 * A function to add action listener to the button.
	 * @param actionListener
	 *        The listener which will be added.
	 */
	public void addActionListener(ActionListener actionListener) {
		this.getListener().add(actionListener);
	}

	/**
	 * A function indicating that a mouse event has occured.
	 */
	public void mouseClick(int x, int y) {
       if(this.contains(x, y)) {
    	   this.getListener().stream().forEach(e -> e.actionPerformed(new ActionEvent(this,1,this.getLabel())));
       }
	}

	/**
	 * A function to draw the graphics of the button.
	 */
	protected void draw(Graphics2D g2) {
		g2.draw(this.getShape());
		g2.setColor(Color.WHITE);
		g2.draw(this.getShape());

	}

	/**
	 * A function to move the button to new place.
	 */
	public void moveTo(int x, int y, int previousX, int previousY) {
		this.setX(this.getX()+(x-previousX));
		this.setY(this.getY()+(y-previousY));
	}

	

}
