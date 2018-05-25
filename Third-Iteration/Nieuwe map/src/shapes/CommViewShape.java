package shapes;

import java.awt.Graphics2D;

import diagramViews.DiagramView;
import domainObjects.InvocationMessage;

/**
 * A class which represents a common interface for all the shapes in communication view.
 * @author Afraz Salim
 *
 */
public abstract class CommViewShape extends PartyShape {

	/**
	 * A constructor to initialize the coordinates of the shapes.
	 * @param diagram
	 *        The diagram of the shape.
	 * @param x
	 *        The new-Coordinate of the shape.
	 * @param y
	 *        The new y-coordinate of the shape.
	 * @param width
	 *        The given width of the shape.
	 * @param height
	 *        The given height of the shape.
	 */
	public CommViewShape(DiagramView diagram, int x, int y, int width, int height) {
		super(diagram, x, y, width, height);
	}

	/**
	 * If the mouse is dragged then this function will ask the digram to start creating message at given points.
	 * @param x
	 *        The given x-coordinate on which the message will be created.
	 * @param y
	 *        The given y-coordinate on which the message will be created.     
	 */
	@Override
	public void drag(int x, int y) {
       super.drag(x, y);
       if(this.getLine().contains(x, y) && !(this.contains(x, y))) {
		   this.getDiagram().createMessage(this,x,y);
	   }
	}
	/**
	 * A function to draw the shapes in comm view.
	 */
	@Override
	public void draw(Graphics2D g2) {
		super.draw(g2);
		this.drawMessage(g2);
	}
	
	/**
	 * A function to draw the message shapes.
	 */
	protected void drawMessage(Graphics2D g2) {
		((RectangleLineShape) this.getLine()).getSenderList().stream().forEach(e -> {
    		if(e.getSourceMessage() instanceof InvocationMessage) {
    			e.draw(g2);
    		}
    	});
	}

}
