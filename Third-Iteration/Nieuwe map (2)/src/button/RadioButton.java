package button;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.List;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
/**
 * A class representing the RadioButton.
 * @author Afraz Salim
 *
 */
public class RadioButton extends Button {

	
	private static int MAX_SIZE  = 40;
	
	/**
	 * A constructor to create a new radio button.
	 * @param x
	 *        The x-coordinate of the button.
	 * @param y
	 *        The y-coordinate of the button.
	 * @param text
	 *        The label of the button.
	 */
	public RadioButton(int x, int y, String text) {
		super(x, y, text);
		this.setListener(new ArrayList<>());
	}

	protected void draw(Graphics2D g2) {
		super.draw(g2);
		g2.setColor(Color.BLACK);
		g2.fill(this.getShape());
		g2.setColor(Color.LIGHT_GRAY);
		g2.fill(this.getInnerShape());
		if(this.getIsActive()) {
			g2.setColor(Color.GREEN);
			g2.fill(this.getInnerShape());
		}
		g2.draw(this.getInnerShape());
		g2.setColor(Color.CYAN);
		g2.setFont(new Font("SERIF", Font.TRUETYPE_FONT, 13));
		g2.drawString(this.getLabel(), this.getX()-(this.getWidth()/3), this.getY()-1);
	}
	  
	/**
	 * A function to check if the radiobutton contains the coordinates.
	 */
	public boolean contains(int x, int y) {
		return (isInXBoundry(x) && isInYBoundry(y));
	}

	private boolean isInXBoundry(int x) {
		return (x >= this.getX() && x <= this.getX()+this.getDimension());
	}

	private boolean isInYBoundry(int y) {
		return (y >= this.getY() && y <= this.getY()+this.getDimension());
	}

	@Override
	public Shape getShape() {
		Ellipse2D ellipse = new Ellipse2D.Double(this.getX(), this.getY(), this.getDimension(), this.getDimension());
	     return ellipse;
	}

	@Override
	protected Shape getInnerShape() {
		Ellipse2D inner = new Ellipse2D.Double(this.getX()+5/2, this.getY()+5/2, this.getDimension()-5, this.getDimension()-5);
		return inner;
	}

	@Override
	protected void changeSize(int x, int y, int previousX, int previousY) {
		if(this.getDimension() < this.getMaxDimension()) {
         this.setDimension(this.getDimension()+(x-previousX));
		}
	}

	private int getMaxDimension() {
		return MAX_SIZE;
	}
	
	/**
	 * A function to draw the selected element.
	 */
    public void drawSelected(Graphics2D g2) {
    	g2.setColor(Color.BLACK);
		g2.setFont(new Font("SERIF", Font.TRUETYPE_FONT, 10));
    	g2.drawString("Target", this.getX(), this.getY()+this.getDimension()+10);
	}
	
	
	

}
