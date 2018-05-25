package shapes;

import java.awt.Shape;
import java.awt.geom.GeneralPath;

import diagramViews.DiagramView;

/**
 * A class representng the PartyObject's shape in communcation view.
 * @author Afraz Salim
 *
 */
public class PartyObjectCommView extends CommViewShape {

	/**
	 * A constructor to create the new instance of the PartyObject's Shape in communication view.
	 * @param diagram
	 *        The diagram of the shape.
	 * @param x
	 *        The given x-coordinate of the shape.
	 * @param y
	 *        The given y-coordinate of the shape.
	 * @param width
	 *        The given width of the party's shape.
	 * @param height
	 *        The given height of the party's shape.
	 */
	public PartyObjectCommView(DiagramView diagram,int x, int y, int width, int height) {
		super(diagram,x, y, width, height);
		this.setLabelX(this.getX());
		this.setLabelHeight(20);
		this.setLabelY(this.getY()-this.getLabelHeight());
		this.setLabelWidth(20);
		this.setLabelWidth(this.getWidth());
		this.setLabel(new Label(this.getLabelX(),this.getLabelY(),this.getLabelWidth(),this.getLabelHeight(),""));
		this.setLine(new RectangleLineShape(this.getX()-5,this.getY(),this.getWidth()+10,this.getHeight()));
	}

	/**
	 * A getter to get the shape of the partyObject's in communcation view.
	 */
	@Override
	public Shape getShape() {
       GeneralPath path = new GeneralPath();
       path.moveTo(this.getX(), this.getY());
       path.lineTo(this.getX()+this.getWidth(), this.getY());
       path.lineTo(this.getX()+this.getWidth(), this.getY()+this.getHeight());
       path.lineTo(this.getX(), this.getY()+this.getHeight());
       path.lineTo(this.getX(), this.getY());
		return path;
	}

	/**
	 * A function to move the shape at given position.
	 * @param x
	 *        The given x-coordinate on which the object will be moved.
	 * @param y
	 *        The given y-coordinate on which the object will be moved.
	 */
	@Override
	public void move(int x, int y) {
		   this.setX(x);
          this.setY(y);
 		  this.setLabelX(this.getX());
 		  this.setLineX(this.getX()-5);
 		  this.setLabelY(this.getY()-this.getLabelHeight());
		  this.setLineY(this.getY());
		  this.getLabel().setX(this.getLabelX());
		  this.getLabel().setY(this.getLabelY());
          this.getLine().updatePoints(this.getLineX(), this.getLineY());
	}

	


}
