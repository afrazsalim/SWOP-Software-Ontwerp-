package shapes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import diagramViews.DiagramView;

/**
 * A class reprseting all the shapes in sequence view.
 * @author Afraz Salim
 
 *
 */
public abstract class SequenceViewShape extends PartyShape{

	/**
	 * A constructor to initialize the seuqence view shapes.
	 * @param diagram
	 *         The diagram of the shapes in sequence view.
	 * @param x
	 *        The x-coordinate of the sequence view shapes.
	 * @param y
	 *        The give y-coordinate of the sequence view shapes.
	 * @param width
	 *       The given width of the shape to becreated.
	 * @param height
	 *        The given height of the shape to be created.
	 */
	public SequenceViewShape(DiagramView diagram, int x, int y, int width, int height) {
		super(diagram, x, y, width, height);
	}
	

	/**
	 * A fucntion whcih responds if the mouse is dragged over a shapes line.
	 * It creates a new message if the coordinates lie on some lineshape.
	 */
	@Override
	public void drag(int x, int y) {
		   super.drag(x, y);
		   if(this.getLine().contains(x, y) ||((StraightLineShape) this.getLine()).getActivationBarAt(x,y) != null) {
			   this.getDiagram().createMessage(this,x,y);
		   }
		}
	
	/**
	 * A function to draw the graphics.
	 */
	@Override
	public void draw(Graphics2D g2) {
		super.draw(g2);
		g2.fill(this.getLine().getShape());
		this.drawMessage(g2);
	}
	
	/**
	 * A function to draw the message.
	 */
	protected void drawMessage(Graphics2D g2) {
		 this.getLine().getBarList().stream().forEach(e ->{
	        	e.draw(g2);
	        	e.getSenderList().stream().forEach(message -> {
	        		    message.draw(g2);
	        	});
	        });		
	}
}
