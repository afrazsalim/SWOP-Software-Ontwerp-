package box;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;
import java.util.ArrayList;

import button.Button;

/**
 * A class representing the list box of the dialogbox.
 * @author Afraz Salim
 *
 */
public class ListBox extends DialogBoxElement implements ActionListener {
	
	private String Label;
	
	private ArrayList<TextField> fieldList;
	
	/**
	 * A constructor to create a new instance of the listbox.
	 * @param x
	 *        The x-coordinate of newly created listbox.
	 * @param y
	 *        The y-coordiante of the newly to be created listbox.
	 * @param width
	 *        The width of the newly to be created dialogbox.
	 * @param height
	 *        The height of the newly to be created dialogbox.
	 * @param label
	 *        The label of the listbox.
	 */
	public ListBox(int x, int y, int width, int height,String label) {
		super(x,y,width,height);
		this.setLabel(label);
		this.setFieldList(new ArrayList<>());
		this.setButtonList(new ArrayList<>());
		
	}


	/**
	 * A getter to get the label of the list box.
	 * @return
	 *       Returns the label of the listbox
	 */
	public String getLabel() {
		return Label;
	}

	/**
	 * A setter to set the label of the listbox.
	 * @param label
	 *        The new label of the listbox.
	 */
	private void setLabel(String label) {
		Label = label;
	}

	/**
	 * A getter to get the list of field which are in listbox.
	 * @return
	 *       Returns the list of fields.
	 */
	protected ArrayList<TextField> getFieldList() {
		return fieldList;
	}


	private void setFieldList(ArrayList<TextField> fieldList) {
		this.fieldList = fieldList;
	}
	
	/**
	 * A getter to get the shape of the listbox.
	 */
	public Shape getShape() {
		return new Rectangle2D.Double(this.getX(), this.getY(), this.getWidth(), this.getHeight());
	}

	/**
	 * A function to draw the graphics.
	 */
	public void draw(Graphics2D g2) {
		g2.drawString(this.getLabel(), this.getX()+2, this.getY()-2);
		g2.draw(this.getShape());
		g2.setColor(Color.GREEN);
		g2.fill(new Rectangle2D.Double(this.getX()+1, this.getY()+1, this.getWidth()-1, this.getHeight()-1));
	    this.getFieldList().stream().forEach(e -> e.draw(g2));
	}

	/**
	 * A function to move the listbox to the new position.
	 */
	public void moveTo(int x, int y, int previousX, int previousY) {
		this.getFieldList().stream().forEach(e -> e.moveTo(x, y, previousX, previousY));
		this.setX(this.getX() + (x-previousX));
		this.setY(this.getY()+(y-previousY));
	}

	/**
	 * A function which indicates that a clickevent has occured.
	 */
	@Override
	public void mouseClick(int x, int y) {
          this.getFieldList().stream().forEach(e -> e.mouseClick(x, y));
	}

	protected TextField getLastField() {
		if(this.getFieldList().size() > 0)
		  return this.getFieldList().get(this.getFieldList().size()-1);
		return null;
	}

	protected void addNewField(TextField field) {
         this.getFieldList().add(field);		
         field.setEditAble(false);
	}

	protected boolean hasAnyTextAs(String text) {
		for(TextField field : this.getFieldList()) {
			if(field.getText().equals(text))
				return true;
		}
		return false;
	}

	@Override
	protected void changeSize(int x, int y, int previousX, int previousY) {
        this.setX(this.getX()+(x-previousX));
        this.setHeight(this.getHeight()+(y-previousY));
        this.getFieldList().stream().forEach(e -> e.moveTo(x, e.getY(), previousX, e.getY()));
	}

	private ArrayList<Button> buttonList;
	
	/**
	 * A function which add a new listener to the list.
	 * @param button
	 *        The button which listens to this listbox.
	 */
	public void addListener(Button button) {
		this.getButtonList().add(button);
	}

	protected ArrayList<Button> getButtonList() {
		return buttonList;
	}

	protected void setButtonList(ArrayList<Button> buttonList) {
		this.buttonList = buttonList;
	}

	protected void activateButton() {
         this.getButtonList().stream().forEach(e -> e.setActive(true));		
	}

	/**
	 * A function to deactive a button.
	 */
	protected void deactivateButton() {
          this.getButtonList().stream().forEach(e -> e.setActive(false));		
	}

	/**
	 * A function that indicates that an action has been performed.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
         if(e.getActionCommand().equals("up"))
        	 this.moveFieldUp(getSelectedElement());
         else if(e.getActionCommand().equals("down"))
        	 this.moveDownField(getSelectedElement());
	}

	private void moveDownField(DialogBoxElement element) {
             assert(this.getFieldList().contains(element));
             if(this.getFieldList().indexOf(element) >= 0 && this.getFieldList().size() > this.getFieldList().indexOf(element)+1) {
            	 DialogBoxElement temp = this.getFieldList().get(this.getFieldList().indexOf(element)+1);
            	 int tempY = element.getY();
            	 int secondY = temp.getY();
            	 temp.setY(tempY);
            	 element.setY(secondY);
            	 this.getFieldList().remove(temp);
            	 this.getFieldList().remove(element);
            	 this.getFieldList().add((TextField) element);
            	 this.getFieldList().add((TextField) temp);
             }
	}

	
	private void removeField(DialogBoxElement element) {
		assert(this.getFieldList().contains(element));
		this.getFieldList().remove(element);
	}

	private void moveFieldUp(DialogBoxElement selectedElement) {
		
	}

	/**
	 * A function which clears all the field from the listbox.
	 */
	public void clearAllField() {
      this.setFieldList(new ArrayList<>());		
	}

	/**
	 * A function to get the selected field from the listbox.
	 * @return
	 *       Returns the selected field from the listbox.
	 */
	public TextField getselectedField() {
       for(TextField field : this.getFieldList()) {
    	   if(field.getIsActive())
    		   return field;
       }
		return null;
	}


	/**
	 * A function to set the selected field as a null fiedl.
	 * @param object
	 *        The new null object of the selected field.
	 */
	public void setSelectedFieldAsNull(TextField field) {
        field.setActive(false);
	}

	



	
	

}
