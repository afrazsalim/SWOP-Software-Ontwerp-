package button;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 * A class representing the simple button.
 * @author Afraz Salim
 *
 */
public class SimpleButton extends Button {

	/**
	 * A constructor for creating a new button.
	 * @param x
	 *        The x-coordinate of the button.
	 * @param y
	 *        The y-coordinate of the button.
	 * @param text
	 *        The label of the button.
	 */
	public SimpleButton(int x, int y, String text) {
		super(x, y, text);
		this.setListener(new ArrayList<>());
	}


	/**
	 * A getter to get the shape of the button.
	 */
	@Override
	protected Shape getShape() {
		Rectangle2D ellipse = new Rectangle2D.Double(this.getX(), this.getY(), this.getDimension(), this.getDimension()/2);
	     return ellipse;
	}
	

	@Override
	protected Shape getInnerShape() {
		return null;
	}

	/**
	 * 
	 * A function to draw the graphics of the button.
	 */
	public void draw(Graphics2D g2) {
		super.draw(g2);
		g2.setColor(Color.LIGHT_GRAY);
		if(this.getIsActive())
			g2.setColor(Color.BLUE);
		g2.fill(new Rectangle2D.Double(this.getX()+1,this.getY()+1,this.getDimension()-1,this.getDimension()/2 -1));
		g2.setColor(Color.BLACK);
		g2.setFont(new Font("SERIF", Font.TRUETYPE_FONT, 13));
		g2.drawString(this.getLabel(), this.getX()+2, this.getY()+this.getDimension()/2 -2);
	}
    
	/**
	 * A function which check if the button's shape contains the given point.
	 */
	public boolean contains(int x, int y) {
		return this.getShape().contains(x,y);
	}


	/**
	 * A function to change the size of the button.
	 */
	@Override
	protected void changeSize(int x, int y, int previousX, int previousY) {
       this.setWidth(this.getWidth()+(x-previousX));
       this.setHeight(this.getHeight()+(y-previousY));
       this.setX(this.getX()+(x-previousX));
	}
	
	/**
	 * A function to draw the selected button.
	 */
    public void drawSelected(Graphics2D g2) {
		float[] dashes = {1,1,1};
     	BasicStroke stroke = new BasicStroke(2, BasicStroke.CAP_SQUARE, BasicStroke.CAP_SQUARE, 10, dashes, 10);
    	g2.setColor(Color.RED);
		g2.draw(stroke.createStrokedShape(new Rectangle2D.Double(this.getX()-2,this.getY()-2,this.getDimension()+4,this.getDimension()/2 +4)));
	}
	
	
}
