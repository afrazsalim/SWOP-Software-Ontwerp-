package shapes;

import java.awt.Shape;
import java.awt.geom.GeneralPath;

import diagramViews.DiagramView;

/**
 * A class representing the shape of the partyobejct in sequence view.
 * @author Afraz Salim
 *
 */
public class PartyObjectSequenceView extends SequenceViewShape {

	/**
	 * A constructor to create the intance of the actor shape in communication view.
	 * @param diagram
	 *        The diagram of the partyShape.
	 * @param x
	 *        The x-Coordinate of the partyShape.
	 * @param y
	 *        The y-coordinate of the partyShape.
	 * @param width
	 *        The width of the party's shape.
	 * @param height
	 *        The height of the party's shape.
	 */

	public PartyObjectSequenceView(DiagramView diagram,int x, int y, int width, int height) {
		super(diagram,x, y, width, height);
		this.setLabelX(this.getX());
		this.setLabelHeight(20);
		this.setLabelY(this.getY()-this.getLabelHeight());
		this.setLabelWidth(20);
		this.setLabelWidth(this.getWidth());
		this.setLabel(new Label(this.getLabelX(),this.getLabelY(),this.getLabelWidth(),this.getLabelHeight(),""));
		this.setLine(new StraightLineShape(this.getX()+this.getWidth()/2,this.getY()+this.getHeight(),5,3*this.getHeight()+this.getHeight()));
	}
	
	
	

	
	

	/**
	 * A getter to get the shape of actor in sequence view.
	 */
	@Override
	public Shape getShape() {
        GeneralPath path = new GeneralPath();
        path.moveTo(this.getX(), this.getY());
        path.lineTo(this.getX()+this.getWidth(), this.getY());
        path.lineTo(this.getX()+this.getWidth(), this.getY()+this.getHeight());
        path.lineTo(this.getX(),this.getY()+this.getHeight());
        path.lineTo(this.getX(), this.getY());
		return path;
	}

	
	/**
	 * A function to move the shape to the new coordinates.
	 * @param x
	 *        The new x-coordinate of the shape.
	 * @param y
	 *        The new y-coordinate of the shape.
	 */
	@Override
	public void move(int x, int y) {
          this.setX(x);
          this.setY(y);
          this.setLabelX(this.getX());
  		  this.setLineX(this.getX()+this.getWidth()/2);
  		  this.setLabelY(this.getY()-this.getLabelHeight());
		  this.setLineY(this.getY()+this.getHeight());
		  this.getLabel().setX(this.getLabelX());
		  this.getLabel().setY(this.getLabelY());
          this.getLine().updatePoints(this.getLineX(), this.getLineY());
	}

}
