package diagramViews;

import java.awt.Graphics;
import java.beans.PropertyChangeListener;

import Exceptions.IllegalObjectException;
import domainObjects.Interaction;

/**
 * A super class for the diagram and it's window.
 * @author Afraz Salim
 *
 */
public abstract class ObjectShape implements PropertyChangeListener{

	private int x;
	private int y;
	private int widht;
	private int height;
	
	private Interaction interaction;
	
	private View view;
	/**
	 * A constructor to initialize the diagram and it's window's data.
	 * @param interaction
	 *        The given controller of the diagram and it's window.
	 * @param view
	 *        The view of the diagram.
	 * @param x
	 *        The given x-coordinate of the window/
	 * @param y
	 *       The given y-coordinate of the window.
	 * @param width
	 *       The given width of the window.
	 * @param height
	 *        Given height of the window.
	 */
	public ObjectShape(Interaction interaction, View view, int x, int y, int width, int height) {
 	     if(interaction  == null)
 	    	  throw new IllegalObjectException("Can't create a window as the controller is null , System exit");
 	     if(!(this.isValidView(view)))
 	    	 throw new IllegalObjectException("View not supported , Fatal Error");
		 this.setX(x);
 	     this.setY(y);
 	     this.setWidht(width);
 	     this.setHeight(height);
 	     this.setInteraction(interaction);
 	     this.setView(view);
	}
	
	

	
	/**
	 * A checker to check if the window contains the given point.
	 * @param x
	 *        The given x-coordinate representing the point.
	 * @param y
	 *        The given y-coordinate representing the point.
	 * @return
	 *        Returns true if the window contains these coordinates.
	 */
	protected boolean contains(int x, int y) {
		if(x >= this.getX() && x<= this.getX()+this.getWidht() && y >= this.getY() && y <= this.getY()+this.getHeight())
			return true;
		return false;
	}
	
	/**
	 * A function to resize the window.
	 * @param width
	 *        The new width of the window.
	 * @param height
	 *        The new height of the window.
	 */
	protected abstract void resize(int width, int height);
	
	
	/**
	 * A checker to check if the view is valid.
	 * @param view
	 *        The current view of the diagram.
	 * @return
	 *        Returns true if the view is a valid view.
	 */
	private boolean isValidView(View view) {
		if(view  == View.SEQUENCE_View)
			return true;
		if(view == View.COMM_View)
			return true;
		return false;
	}

	
	/**
	 * A getter to get the x-coordinate.
	 * @return
	 *       Returns the x-coordinate of the diagram.
	 */
	public int getX() {
		return x;
	}
	/**
	 * A setter to set the x-coordinate.
	 * @param x
	 *        The new x-coordinate of the diagram.
	 */
	protected void setX(int x) {
		this.x = x;
	}
	
	/**
	 * A getter to get the y-coordinate of the diagram.
	 * @return
	 *       Returns the y-coordinate of the diagram.
	 */
	public int getY() {
		return y;
	}
	/**
	 * A setter to set the y.
	 * @param y
	 *        Sets the y coordinate equal to the new y coordinate.
	 */
	protected void setY(int y) {
		this.y = y;
	}
	/**
	 * A getter to get the width of the diagram.
	 * @return
	 *       Returns the width of the diagram.
	 */
	public int getWidht() {
		return widht;
	}
	/**
	 * A stter to set the width.
	 * @param widht
	 *         The width of the window.
	 */
	protected void setWidht(int widht) {
		this.widht = widht;
	}
	
	/**
	 * A getter to get the height of the window.
	 * @return
	 *       Returns the height of the window.
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * A setter to set the height of the window.
	 * @param height
	 *        The height of the newly created window.
	 */
	protected void setHeight(int height) {
		this.height = height;
	}

	/**
	 * A getter to get the controller.
	 * @return
	 *       Returns the controller.
	 */
	public Interaction getInteraction() {
		return interaction;
	}
	

	/**
	 * A setter to set the controller of the diagram.
	 * @param interaction
	 *        The controller which will be set.
	 */
	protected void setInteraction(Interaction interaction) {
		this.interaction = interaction;
	}

	/**
	 * A getter to get view of the diagram.
	 * @return
	 *       Returns the view of the diagram.
	 */
	public View getView() {
		return view;
	}

	/**
	 * A setter to set the view of the diagram.
	 * @param view
	 *        The new view of the diagram.
	 */
	protected void setView(View view) {
		this.view = view;
	}


	/**
	 * A function to paint the diagram.
	 * @param g
	 *        The given instance of the graphics with which a diagram paints itself.
	 */
	protected abstract void paint(Graphics g);


	/**
	 * A function which performs mouse action according to some event.
	 * @param id
	 *        The id of the mouse event.
	 * @param x2
	 *        X-coordinate of the mouseevent.
	 * @param y2
	 *        y-coordinate of the mouse Event.
	 * @param clickCount
	 *        Number of counts clicks.
	 */
	protected abstract void performMouseAction(int id, int x2, int y2, int clickCount);


	/**
	 * A function to move all the sub-objects
	 * @param x
	 *        The x-coordinate where object will be moved.
	 * @param y
	 *        The y-coordinate where object will be moved.
	 */
	protected void move(int x, int y) {
		
	}

}
