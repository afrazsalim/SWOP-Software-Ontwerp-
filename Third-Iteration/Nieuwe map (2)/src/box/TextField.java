package box;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 * A class representing the textfield of the dialogbox.
 * @author Afraz Salim
 *
 */
public class TextField extends DialogBoxElement {
	

	private String text;
	
	/**
	 * A constructor to create a new textfield.
	 * @param x
	 *        The x-coordinat eof the textfield.
	 * @param y 
	 *        The y-coordinate of the textfield.
	 * @param width
              The width of the text field.
	 * @param height
	 *        The height of the text field.
	 */
	public TextField(int x, int y , int width, int height) {
		super(x,y,width,height);
		this.setListenerList(new ArrayList<>());
		this.setText("");
		this.setLabel("");
		this.setEditAble(true);
	}
	
	/**
	 * A getter to get the shape of the textfield.
	 */
	public Shape getShape() {
		Rectangle2D rect = new Rectangle2D.Double(this.getX(),this.getY(),this.getWidth(),this.getHeight());
		return rect;
	}
	
	/**
	 * A function to check if the textfield contains the given point.
	 */
	public boolean contains(int x, int y) {
		return this.getShape().contains(x,y);
	}
	
  
	/**
	 * A function to remove the text from text field
	 */
     protected void removeText() {
		char [] list = this.getText().toCharArray();
   	    this.setText("");
		for(int i = 0 ; i < list.length-1;i++) {
			this.setText(this.getText()+ Character.toString(list[i]));
		}
		this.getListenerList().stream().forEach(e -> e.actionPerformed(new ActionEvent(this,1,this.getText())));
	  }
	
     /**
      * A function which indicates that a mouse click has occured.
      */
	public void mouseClick(int x , int y) {
		if(this.contains(x, y) && getSelectedElement() == null) {
			this.performActionOnClick(x,y);
		}
	}

	private void performActionOnClick(int x, int y) {
		setSelectedElement(this);
	    this.setActive(true);
		this.getListenerList().stream().forEach(e -> e.actionPerformed(new ActionEvent(this, 1, this.getText())));		
	}

	

	protected void draw(Graphics2D g2) {
      g2.setColor(Color.BLACK);
      g2.draw(this.getShape());
      g2.fill(new Rectangle2D.Double(this.getX()+1,this.getY()+2,this.getWidth()-2,this.getHeight()-2));
      g2.setColor(Color.WHITE);
      g2.setFont(new Font("SERIF", Font.TRUETYPE_FONT, this.getHeight()));
      if(this.getIsActive() && this.getIsEditable()) {
    	  g2.setColor(Color.RED);
    	  g2.drawString(this.getText()+"|" ,this.getX(),this.getY()+this.getHeight()/2 + this.getHeight()/3);
      }
      else {
    	  g2.setColor(Color.WHITE);
    	  g2.drawString(this.getText() ,this.getX(),this.getY()+this.getHeight()/2 + this.getHeight()/3);
      }
      g2.setColor(Color.WHITE);
	  g2.setFont(new Font("SERIF", Font.TRUETYPE_FONT, this.getHeight()));
	  g2.drawString(this.getLabel(), this.getX()+4, this.getY()-1);
	}
	
	
	private ArrayList<ActionListener> listenerList;

	/**
	 * A function to add listener to the textfield for the event.
	 * @param actionListner
	 *         The new listener which listens to the events of this field.
	 */
	public void addActionListener(ActionListener actionListner) {
		this.getListenerList().add(actionListner);
	}

	protected ArrayList<ActionListener> getListenerList() {
		return listenerList;
	}

	protected void setListenerList(ArrayList<ActionListener> listenerList) {
		this.listenerList = listenerList;
	}

	private String label;
	/**
	 * A setter to set the label of the text field.
	 * @param string
	 *        The new label of the text field.
	 */
	public void setLabel(String string) {
      this.label = string;		
	}
	
	/**
	 * A getter to get the label text of the textfield.
	 * @return
	 *      Returns the label text of the textfield.
	 */
	public String getLabel() {
		return this.label;
	}

	private boolean isActive;
	/**
	 * A setter to set the textfield active
	 */
	public void setActive(boolean b) {
		this.isActive = b;
		if(b)
		   this.getListenerList().stream().forEach(e -> e.actionPerformed(new ActionEvent(this, 1, "Activated")));
		else if(!b)
			this.getListenerList().stream().forEach(e -> e.actionPerformed(new ActionEvent(this,1,"deactivated")));
	}
	
	/**
	 * A function to check if the field is active.
	 */
	public boolean getIsActive() {
		return this.isActive;
	}

	/**
	 * A getter to get the text inside the field.
	 * @return
	 *       Returns the text of the textfield.
	 */
	public String getText() {
		return text;
	}

	/**
	 * A setter to set the text of the field.
	 * @param text
	 *        The new text which will be inserted into the texfield.
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * A function to add the text to textfield.
	 */
	protected void addText(char keyChar) {
	 if(this.getText().length() < 16 && this.getIsEditable()) {
       this.setText(this.getText()+Character.toString(keyChar));	
       this.getListenerList().stream().forEach(e -> e.actionPerformed(new ActionEvent(this, 1, this.getText())));
	 }
	}

	/**
	 * A function to move the texfield to the new place.
	 */
	public void moveTo(int x, int y, int previousX, int previousY) {
        this.setX(this.getX() + (x- previousX));
        this.setY(this.getY()+(y-previousY));
	}

	/**
	 * A function to change the size of the textfield.
	 */
	@Override
	protected void changeSize(int x, int y, int previousX, int previousY) {
		if(!(this.getIsEditable()))
		 this.setX(this.getX()+(x-previousX));
		this.setY(this.getY()+(y-previousY));
	}

	private boolean isEditable;
	protected void setEditAble(boolean b) {
 		this.isEditable = b;
	}
	
	protected boolean getIsEditable() {
		return this.isEditable;
	}

	/**
	 * A function which indicates that an action has been performed.
	 */
	@Override
	public void performKey(int id, int keyCode, char keyChar) {
          		if(keyCode == 8  && getSelectedElement().equals(this)) {
          			getSelectedElement().removeText();
          		}
          		if(Character.isAlphabetic(keyChar) && getSelectedElement().equals(this))
          			getSelectedElement().addText(keyChar);
	}
	
	/**
	 * A function to draw the selected shape.
	 */
	@Override
	 public void drawSelected(Graphics2D g2) {
			float[] dashes = {1,1,1};
	     	BasicStroke stroke = new BasicStroke(2, BasicStroke.CAP_SQUARE, BasicStroke.CAP_SQUARE, 10, dashes, 10);
	    	g2.setColor(Color.RED);
			g2.draw(stroke.createStrokedShape(new Rectangle2D.Double(this.getX(),this.getY()-2,this.getWidth()+2,this.getHeight()+2)));
		}
		

}
