package shapes;

import java.awt.Shape;
import java.awt.geom.GeneralPath;

import diagramViews.DiagramView;

/**
 * A class which represents the actor shape in sequence view.
 * @author Afraz Salim
 *
 */
public class ActorShapeSequenceView extends SequenceViewShape {

	/**
	 * A constructor to create the intance of the actor shape in Sequence view.
	 * @param view
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
	public ActorShapeSequenceView(DiagramView view,int x, int y, int width, int height) {
		super(view,x, y, width, height);
		this.setLabelX(this.getX());
		this.setLabelY(this.getY()+this.getHeight());
		this.setLabelHeight(20);
		this.setLabel(new Label(this.getLabelX(),this.getLabelY(),this.getWidth(),this.getLabelHeight(),""));
	    this.setLine(new StraightLineShape(this.getX()+this.getWidth()/2,this.getLabelY()+this.getLabelHeight(),5,3*this.getHeight()));
	}
	
	
	/**
	 * A getter to get the widthbound of shape.
	 */
	private int getWidthBound() {
		return 14;
	}
	
	/**
	 * A getter to get the height bound of the shape.
	 */
	private int getHeightBound() {
		return 14;
	}

	


	/**
	 * A getter to get the shape of the actor in sequence view.
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
	    path.closePath();
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
  		  this.setLabelY(this.getY()+this.getHeight());
		  this.setLineY(this.getLabelY()+this.getLabelHeight());
  		  this.setLabelX(this.getX());
  		  this.setLineX(this.getX()+this.getWidth()/2);
  		  this.setLabelY(this.getY()+this.getHeight());
		  this.getLabel().setX(this.getLabelX());
		  this.getLabel().setY(this.getLabelY());
		  this.getLine().updatePoints(this.getLineX(), this.getLineY());
	}


	

	
	
	
}
