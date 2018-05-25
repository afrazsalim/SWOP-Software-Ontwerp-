package box;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import Exceptions.IllegalObjectException;
import Exceptions.IllegalOperationExcetion;
import domainObjects.Party;
import button.Button;
import button.SimpleButton;
import diagramViews.DiagramView;
import diagramViews.ObjectShape;
/**
 * A class representing the Dialogbox.
 * @author Afraz Salim
 *
 */
public abstract class DialogBox {
	
	/**
	 * A private list variable to store the childs.
	 */
	private ArrayList<DialogBoxElement> childList;
	
	
	
	private int x;
	private int y;
	private int width;
	private int height;
	/**
	 * A constructor to create a new instance of the dialogbox.
	 * @param x
	 *        The x-coordinate of the new dialogbox which will be created.
	 * @param y
	 *        The y-coordinate of the new dialogbox which will be created.
	 * @param width
	 *        The width of the new dialogbox.
	 * @param height
	 *        The height of the new dialogbox.
	 */
	public DialogBox(int x, int y, int width, int height) {
		this.setX(x);
		this.setY(y);
		this.setWidth(width);
		this.setHeight(height);
		this.setChildList(new ArrayList<>());
		this.addCloseButton();	
	}
	
/**
 * A getter to get the x-coordinate of the dialogbox.
 * @return
 *        Returns the x-coordinate of the dialogbox.
 */
	public int getX() {
		return x;
	}
	
	/**
	 * A stter to set the c-coordinate of the dialogbox.
	 * @param x
	 *       The new x-coordinate of the dialogbox.
	 */
	protected void setX(int x) {
		this.x = x;
	}


	/**
	 * A getter to get the y-coordinate of the dialogbox.
	 * @return
	 *        Returns the y-coordinate of the dialogbox.
	 */
	public int getY() {
		return y;
	}


	/**
	 * A stter to set the y-coordinate of the dialogbox.
	 * @param y
	 *       The new y-coordinate of the dialogbox.
	 */
	public void setY(int y) {
		this.y = y;
	}


	/**
	 * A getter to get the width of the dialogbox.
	 * @return
	 *       Returns the width of the dialogbox.
	 */
	public int getWidth() {
		return width;
	}

	
	/**
	 * A stter to set the width of the dialogbox.
	 * @param width
	 *       The new width of the dialogbox.
	 */
	protected void setWidth(int width) {
		this.width = width;
	}


	/**
	 * A getter to get the height of the dialogbox.
	 * @return 
	 *        Returns the height of the dialogbox.
	 */
	public int getHeight() {
		return height;
	}


	/**
	 * A stter to set the height of the dialogbox.
	 * @param height
	 *        The new height of the dialogbox.
	 */
	protected void setHeight(int height) {
		this.height = height;
	}
	
	
	/**
	 * A function to add the close button to the dialogbox.
	 */
	private void addCloseButton() {
		   Button closeButton = new SimpleButton((this.getX() + this.getWidth()),this.getY(),"x");
		   closeButton.setDimension(20);
		   closeButton.setX(this.getX()+this.getWidth()-closeButton.getDimension());
		   this.addToChilds(closeButton);
		   this.setCloseButton(closeButton);
		   closeButton.setActive(true);
	}

	private Button closeButton;
	/**
	 * A setter to set the close button.
	 * @param button
	 *        The button which will  be set as close button.
	 */
	private void setCloseButton(Button button) {
       		this.closeButton = button;
	}
	
	/**
	 * A getter to get the close button.
	 * @return
	 *       Returns the close button of the dialogbox.
	 */
	public Button getCloseButton() {
		return this.closeButton;
	}


	/**
	 * A getter to get the shape of the dialogbox.
	 * @return
	 *        Returns the shape of the dialogbox.
	 */
	public abstract Shape getShape();

	

	private int titleBarX;
	private int titleBarY;
	private int titleBarHeight;
	private int titleBarWidth;
	
	/**
	 * A getter to get the shape of titlebar of the dialogbox.
	 * @return
	 *        Returns the title bar shape of the dialogbxo.
	 */
	protected Shape getTitleBarShape() {
		Rectangle2D rect = new Rectangle2D.Double(this.getX(),this.getY(),this.getTitleBarWidth(),this.getTitleBarHeight());
		return rect;
	}

	/**
	 * A function to add the child to list.
	 * @param element
	 *        The element which will be added to child list.
	 */
	public void addToChilds(DialogBoxElement element) {
		this.getChildList().add(element);
	}
	
	
    /**
     * A function which indicates that a mouseClick was happend.
     * @param x
     *        The x-coordinate on which mouse click happend.
     * @param y
     *        The y-coordinate on mous click happend.
     */
	public void mouseClick(int x, int y) {
		List<DialogBoxElement> list  = this.getChildList().stream().filter(e -> e instanceof Button).collect(Collectors.toList()) ;
		list.stream().forEach(e -> e.mouseClick(x, y));
		if(this.getShape().contains(x,y) && isValidMoveToUndoEffect(x,y)) {
			DialogBoxElement.getSelectedElement().setActive(false);
			DialogBoxElement.setSelectedElement(null);
			return;
		}
		if(DialogBoxElement.getSelectedElement() != null) {
			DialogBoxElement.getSelectedElement().mouseClick(x, y);
			return;
		  }
		else
	      this.getChildList().stream().forEach(e -> e.mouseClick(x,y));
	}
	
	
	
	private int selectionNumber;
	/**
	 * A function which indicates that a keyEvent happend.
	 * @param id
	 *        The id of the keyEvent which occured.
	 * @param keyCode
	 *        The KeyCode of the keyEvent which occured.
	 * @param keyChar
	 *        The keyChar of the keyEvent which happend.
	 */
	public void keyPressed(int id, int keyCode, char keyChar) {
		if(DialogBoxElement.getSelectedElement() != null)
			DialogBoxElement.getSelectedElement().performKey(id,keyCode,keyChar);
		else if(keyCode == 9) {
		  int index = this.getChildList().indexOf(DialogBoxElement.getSelectedChild());
		  if(index+1 < this.getChildList().size() && DialogBoxElement.getSelectedChild() != null) {
			  DialogBoxElement.setSelectedChild(this.getChildList().get(index+1));
		  }
		  else
		  {
			  DialogBoxElement.setSelectedChild(this.getChildList().get(0));
		  }
		}
		else if(keyCode == 32) {
			this.mouseClick(DialogBoxElement.getSelectedChild().getX()+2, DialogBoxElement.getSelectedChild().getY()+1);
		}
	}
	
	/**
	 * A function which indicates that a keyEvent happend.
	 * @param id
	 *        The id of the keyEvent which occured.
	 * @param keyCode
	 *        The KeyCode of the keyEvent which occured.
	 * @param keyChar
	 *        The keyChar of the keyEvent which happend.
	 */
	public void performKey(int id, int keyCode, char keyChar) {
  		this.keyPressed(id, keyCode, keyChar);
    }

	





	/**
	 * A funcntion which checks if it is possible to undo the effect.
	 * @param x
	 *        The x-coordinate of the point to be checked.
	 * @param y
	 *        The y-coordinate of the point to be checked.
	 * @return
	 *        Returns true if an effect can be undone.
	 */
	private boolean isValidMoveToUndoEffect(int x, int y) {
		if(DialogBoxElement.getSelectedElement() == null)
			return false;
		for(DialogBoxElement element: this.getChildList())
			if(element.getShape().contains(x,y))
				return false;
		return true;
	}


	protected int getTitleBarX() {
		return titleBarX;
	}

	protected void setTitleBarX(int titleBarX) {
		this.titleBarX = titleBarX;
	}

	protected int getTitleBarY() {
		return titleBarY;
	}

	protected void setTitleBarY(int titleBarY) {
		this.titleBarY = titleBarY;
	}

	/**
	 * A getter to get the height of the title bar.
	 * @return
	 *       Returns the height of the titlebar.
	 */
	public int getTitleBarHeight() {
		return titleBarHeight;
	}

	protected void setTitleBarHeight(int titleBarHeight) {
		this.titleBarHeight = titleBarHeight;
	}

	protected int getTitleBarWidth() {
		return titleBarWidth;
	}

	protected void setTitleBarWidth(int titleBarWidth) {
		this.titleBarWidth = titleBarWidth;
	}

	
	/**
	 * A function check if the dialogbox contains the point.
	 * @param x
	 *        The x-coordinate of the point to be checked.
	 * @param y
	 *        The y-coordinate of the point to be checked.
	 * @return
	 *       Retursn true if the shape contains the given point.
	 */
	public boolean contains(int x, int y) {
		return this.getTitleBarShape().contains(x,y) ||this.getShape().contains(x,y);
	}

	/**
	 * A function to mvoe the dialogbox to new point.
	 * @param x
	 *        The x-coordinate of the point where box will be moved.
	 * @param y
	 *        The y-coordinate of the point where box will be moved.
	 * @param previousX
	 *        The x-coordinate of the previously selected point.
	 * @param previousY
	 *        The x-coordinate of the previously selected point.
	 */
	public void move(int x, int y, int previousX, int previousY) {
		  this.moveTo(x, y, previousX, previousY);
    	  this.getChildList().stream().forEach(e -> e.moveTo(x,y,previousX,previousY));    	  
	}




	/**
	 * A function to move all the child of the dialogbox to new place.
	 * @param x2
	 *        The x-coordinate of the point on which mouse click happend.
	 * @param y2
	 *        The y-coordinate on which mouse click happend.
	 * @param previousX
	 *        The x-coordinate of the point on which last time was clicked.
	 * @param previousY
	 *        The y-coordinate of the poiint on which last time was clicked.
	 */
	protected abstract void moveTo(int x2, int y2, int previousX, int previousY);

	/**
	 * A function to draw the dialogbox.
	 * @param g2
	 *        An instance of graphics with which shapes will be drawn.
	 */
	public void draw(Graphics2D g2) {
		if(this.getIsActive()) {
			g2.setColor(Color.CYAN);
			g2.draw(this.createStrokedBorder());
		}
		g2.setColor(Color.GREEN);
		g2.draw(this.getShape());
		g2.setColor(Color.MAGENTA);
		g2.fill(new Rectangle2D.Double(this.getX()+1, this.getY()+1, this.getWidth()-1, this.getHeight()-1));
		g2.setColor(Color.BLUE);
		g2.fill(this.getTitleBarShape());
		g2.setColor(Color.WHITE);
		this.getChildList().stream().forEach(element -> 
		element.draw(g2));
		if(DialogBoxElement.getSelectedChild() != null)
			DialogBoxElement.getSelectedChild().drawSelected(g2);
	    
	}

	/**
	 * A function to create a strioked broder.
	 * @return
	 *      Returnsa stroked borded of the dialogbox.
	 */
	private Shape createStrokedBorder() {
		float[] dashes = {1,1,1};
     	BasicStroke stroke = new BasicStroke(1, BasicStroke.CAP_SQUARE, BasicStroke.CAP_SQUARE, 10, dashes, 10);
		Shape shape  = new Rectangle2D.Double(this.getX()-3, this.getY()-3, this.getWidth()+3, this.getHeight()+3);
		return stroke.createStrokedShape(shape);
	}



	

	

	/**
	 * A function which handle the drag event.
	 * @param x
	 *        The x-coordiante of teh point where click event happend.
	 * @param y
	 *       The y-coordinate of the point where click event happend.
	 * @param previousX
	 *       The previous x-coordinate where clickevent happend.
	 * @param previousY
	 *       The previous y-coordinate where clickEvent happend.
	 */
	public void drag(int x, int y,int previousX, int previousY) {
		if(this.getTitleBarShape().contains(previousX,previousY))
    	  this.move(x, y, previousX, previousY);
		else if (this.isValidMoveToResize(x,y,previousX,previousY))
			this.changeSize(x,y,previousX,previousY);
	}

	/**
	 * A function which changes the size of the dialogBox.
	 * @param x
	 *        The x-coordiante of teh point where click event happend.
	 * @param y
	 *       The y-coordinate of the point where click event happend.
	 * @param previousX
	 *       The previous x-coordinate where clickevent happend.
	 * @param previousY
	 *       The previous y-coordinate where clickEvent happend.
	 */
	protected void changeSize(int x, int y, int previousX, int previousY) {
		this.setWidth(this.getWidth()+(x-previousX));
		this.setHeight(this.getHeight()+(y-previousY));
		this.getChildList().stream().forEach(e ->{
			e.changeSize(x,y,previousX,previousY);
		});
		this.setTitleBarWidth(this.getTitleBarWidth()+(x-previousX));
	}


	

	/**
	 * A checker to check if the box can be resized.
	 * @param x
	 *        The x-coordiante of teh point where click event happend.
	 * @param y
	 *       The y-coordinate of the point where click event happend.
	 * @param previousX
	 *       The previous x-coordinate where clickevent happend.
	 * @param previousY
	 *       The previous y-coordinate where clickEvent happend.
	 * @return
	 *       Returns true if the box can be resized.
	 */
	private boolean isValidMoveToResize(int x, int y,int previousX, int previousY) {
		if(this.getIsActive()) {
			if(this.getTitleBarShape().contains(previousX,previousY))
				return false;
			if(previousX <= this.getX()+this.getWidth() && x > this.getX()+this.getWidth() && y > this.getY() && y <= this.getY()+this.getHeight())
				return true;
			if(previousY <= this.getY()+this.getHeight() && y >= this.getY()+this.getHeight() && x > this.getX() && x <= this.getX()+this.getWidth())
                return true;
			if(previousX >= this.getX()+this.getWidth() && x < this.getX()+this.getWidth() && y > this.getY() && y <= this.getY()+this.getHeight())
				return true;
			if(previousY > this.getY()+this.getHeight() && y <= this.getY()+this.getHeight() && x > this.getX() && x <= this.getX()+this.getWidth())
                return true;
           }
		return false;
	}

	
	private boolean active;

	/**
	 * A boolean getter to get if the box is active.
	 * @return
	 *       Returns true if the box is active.
	 */
	public boolean getIsActive() {
		return this.active;
	}

	
	/**
	 * A boolean setter to set the box as active.
	 * @param active
	 *        The new state of the box.
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * A function which returns the list of all it's childs.
	 * @return
	 *       Returns the child list of all the dialogbox elements.
	 */
	public ArrayList<DialogBoxElement> getChildList() {
		return childList;
	}


	/**
	 * A setter to set child list of the dialogbox.
	 * @param childList
	 *        The chilList with which the childList will be initialized.
	 */
	private void setChildList(ArrayList<DialogBoxElement> childList) {
		this.childList = childList;
	}


	
/**
 * A function which creates a new textfield for list.
 * @param box
 *        A box for which textfield will be created.
 * @return
 *       Returns a new text field.
 */
   private TextField createNewField(ListBox box) {
		  if(box.getLastField() != null)
	       return  new TextField(box.getX()+2,box.getLastField().getY()+box.getLastField().getHeight(),80,15);	
		  else
			return new TextField(box.getX()+2,box.getY()+2,80,15);
   }
	
	/**
	 * A function which returns the list box of dialog box.
	 * @return
	 *       Returns the listbox of the dialog box.
	 */
	public ListBox getListBox() {
		for(DialogBoxElement element : this.getChildList()) {
			if(element instanceof ListBox)
				return (ListBox) element;
		}
		return null;
	}
	

	/**
	 * A function which returns the sequence view button of dialogbox.
	 * @return
	 *        Returns the sequence view button of dialogbox.
	 */
	public DialogBoxElement getSecViewButton() {
		for(DialogBoxElement element : this.getChildList()) {
			if(element instanceof Button && ((Button) element).getLabel().equals("SeqView"))
				return element;
		}
		return null;
	}


	/**
	 * A function which returns the comm view button of dialogbox.
	 * @return
	 *        Returns the comm view button of dialogbox.
	 */
	public DialogBoxElement getCommViewButton() {
		for(DialogBoxElement element : this.getChildList()) {
			if(element instanceof Button && ((Button) element).getLabel().equals("CommView"))
				return element;
		}
		return null;
	}



	/**
	 * A function to enter a new field into the list box with given text.
	 * @param string
	 *        The given text of the newly field.
	 */
	public void enterFields(String string) {
		  ListBox box = this.getListBox();
		  assert(box != null);
		  TextField field = this.createNewField(box);
		  field.setText(string);
		  box.addNewField(field);
		  field.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand().equals("Activated")) {
					box.activateButton();
				} else if(e.getActionCommand().equals("deactivated"))
					box.deactivateButton();
			}
		  });
	}


	/**
	 * A function to enter the method name into method name field.
	 * @param methodName
	 *        The method name which will be added.
	 */
	public void enterMethodName(String methodName) {
        assert(this.getTextFieldWithMethodLabel()	!= null);
        this.getTextFieldWithMethodLabel().setText(methodName);
	}

/**
 * A function to gett the textfield whoms label is method.
 * @return
 *       Returns the field with method as labale.
 */
	public TextField getTextFieldWithMethodLabel() {
       for(DialogBoxElement element : this.getChildList()) {
    	   if(element instanceof TextField && ((TextField) element).getLabel().equals("methodName"))
    		   return (TextField) element;
       }
	return null;
	}


	/**
	 * A function to get the text of the argument field of the dialogbox.
	 * @return
	 *       Returns the text of the argument field of the dialog box.
	 */
	public String getArgFieldText() {
		return this.getArgField().getText();
	}


	/**
	 * A function to get the argument field of dialogbox.
	 * @return
	 */
	private TextField getArgField() {
		for(DialogBoxElement element : this.getChildList()) {
	    	   if(element instanceof TextField && ((TextField) element).getLabel().equals("new arg"))
	    		   return (TextField) element;
	       }
		return null;
		}

	/**
	 * A function to check if the dialogbox intersection a point.
	 * @param x
	 *        The x-coordinat eof the point to be checked.
	 * @param y
	 *        The y-coordinate of the point to be checked.
	 * @return
	 *        Returns true if the dialogbox contains the point.
	 */
	public boolean intersects(int x, int y) {
		if(this.getShape().contains(x,y) || this.getTitleBarShape().contains(x,y))
			return true;
		return false;
	}

	/**
	 * A boolean checker to check if the box can be activated.
	 * @param x
	 *        The x-coordinate of the box where mouse event happend.
	 * @param y
	 *        The y-coordinate of the box where mouse event happend.
	 * @return
	 *        Retuns true if the dialogbox's title bar contians.
	 */
	public boolean canActivate(int x, int y) {
		return getTitleBarShape().contains(x,y) ;
	}
	

}
	
	

	
