package shapes;

import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.geom.GeneralPath;

import diagramViews.DiagramView;

/**
 * 
 * @author Afraz Salim
 *
 */
public class ActorShapeCommView extends CommViewShape {

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
	public ActorShapeCommView(DiagramView diagram,int x, int y, int width, int height) {
		super(diagram,x, y, width, height);
		this.setLabelX(this.getX());
		this.setLabelY(this.getY()+this.getHeight());
		this.setLabelWidth(this.getWidth());
		this.setLabelHeight(20);
		this.setLabel(new Label(this.getLabelX(),this.getLabelY(),this.getLabelWidth(),this.getLabelHeight(),""));
		this.setLine(new RectangleLineShape(this.getX()-5,this.getY(),this.getWidth()+10,this.getHeight()));
	}
	
	/**
	 * A setter to set the x-Coordinate of the Shape.
	 * @param x
	 *        The new x-coordinate of the shape.
	 */
	@Override
	protected void setX(int x) {
		super.setX(x);
		this.setLabelX(this.getX());
		this.setLineX(this.getX()-5);
	}
	
	/**
	 * A setter to set the y-coordinate of the shape.
	 */
	protected void setY(int y) {
		super.setY(y);
		this.setLabelY(this.getY()+this.getHeight());
		this.setLineY(this.getY());
	}
	
	/**
	 * A getter to get the width of the shape.
	 * @return
	 *        Returns the width of the shape.
	 */
	private int getWidthBound() {
		return 14;
	}
	
	
	/**
	 * A getter to get the height of the party's shape.
	 * @return
	 *       Returns the height of th party's shape.
	 */
	private int getHeightBound() {
		return 14;
	}


	/**
	 * A getter to get the shape of actor in communication view.
	 */
	@Override
	public Shape getShape() {
		GeneralPath path = new GeneralPath();
		path.moveTo(this.getX(), this.getY());
		path.lineTo(this.getX()+this.getWidth(), this.getY());
		path.lineTo(this.getX()+this.getWidth(), this.getY()+this.getHeight());
		path.lineTo(this.getX(), this.getY()+this.getHeight());
		path.lineTo(this.getX(), this.getY());
		path.moveTo(this.getX()+this.getWidthBound(), this.getY()+this.getHeightBound());
		path.lineTo(this.getX()+this.getWidth()-this.getWidthBound(), this.getY()+this.getHeightBound());
		path.lineTo(this.getX()+this.getWidth()-this.getWidthBound(),  this.getY()+this.getHeightBound()+this.getHeight()/3);
		path.lineTo(this.getX()+this.getWidthBound(),  this.getY()+this.getHeightBound()+this.getHeight()/3);
		path.lineTo(this.getX()+this.getWidthBound(), this.getY()+this.getHeightBound());
        path.moveTo((this.getX()+this.getWidthBound()+this.getX()+this.getWidth()-this.getWidthBound())/2, this.getY()+this.getHeightBound()+this.getHeight()/3);
		path.lineTo((this.getX()+this.getWidthBound()+this.getX()+this.getWidth()-this.getWidthBound())/2, this.getY()+2*this.getHeight()/3 + this.getHeightBound());
		path.lineTo(this.getX()+this.getWidth()-this.getWidthBound(),this.getY()+this.getHeight());
		path.moveTo((this.getX()+this.getWidthBound()+this.getX()+this.getWidth()-this.getWidthBound())/2, this.getY()+2*this.getHeight()/3 + this.getHeightBound());
		path.lineTo(this.getX()+this.getWidthBound(),this.getY()+this.getHeight());
		path.moveTo((this.getX()+this.getWidthBound()+this.getX()+this.getWidth()-this.getWidthBound())/2,this.getY()+this.getHeight()/2 + this.getHeightBound());
		path.lineTo(this.getX()+this.getWidth()-5, this.getY()+this.getHeight()/2 + this.getHeightBound()+10);
		path.moveTo((this.getX()+this.getWidthBound()+this.getX()+this.getWidth()-this.getWidthBound())/2,this.getY()+this.getHeight()/2 + this.getHeightBound());
		path.lineTo(this.getX()+5, this.getY()+this.getHeight()/2 + this.getHeightBound()+10);
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
	 		  this.setLineX(this.getX()-5);
	 		  this.setLabelY(this.getY()+this.getHeight());
			  this.setLineY(this.getY());
			  this.getLabel().setX(this.getLabelX());
			  this.getLabel().setY(this.getLabelY());
			  this.getLine().updatePoints(this.getLineX(), this.getLineY());
 	}

	
	

}
