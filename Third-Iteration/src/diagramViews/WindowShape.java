package diagramViews;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

import Exceptions.IllegalOperationExcetion;
import Factories.ViewFactory;
import box.DialogBox;
import box.DialogBoxElement;
import box.SimpleDialogBox;
import button.Button;
import button.RadioButton;
import button.SimpleButton;
import domainObjects.Interaction;
import domainObjects.Message;
import domainObjects.Party;
import shapes.PartyShape;
/**
 * A class representing the window's shape.
 * @author Afraz Salim
 *
 */
public class WindowShape extends ObjectShape implements ActionListener {
	

	/**
	 * A variable representing the x-coordinate of the title bar.
	 */
	private int titleBarX;
	/**
	 * A variable representing the y-coordinate of the title bar.
	 */
	private int titleBarY;
	/**
	 * A variable representing the width of the title bar.
	 */
	private int titleBarWidth;
	/**
	 * A variable representing the height of the title bar.
	 */
	private int titleBarHeight;
	/**
	 * A variable represeting where the wndow is active or no.
	 */
	private boolean isActive;
	/**
	 * A private variable to store the diagram.
	 */
	private DiagramView diagram;
	
	/**
	 * An arraylist to store the shapes of all the windows.
	 */
	private ArrayList<ObjectShape> childShape;
	
	
	/**
	 * A constructor for creating the window shape arround the diagram.
	 * @param interaction
	 *        The controller of the diagram.
	 * @param view
	 *        The view of the diagram.
	 * @param x
	 *        The given x-corodinate of the window's shape.
	 * @param y
	 *        The given y-coordiante of the window's shape.
	 * @param width
	 *        Width of the windows shape.
	 * @param height
	 *        Height of the windows shape.
	 */
	public WindowShape(Interaction interaction, View view, int x, int y, int width, int height) {
      super(interaction,view,x,y,width,height);
      this.setChildShape(new ArrayList<>());
      this.setTitleBarX(this.getX()+this.getStrokedBound()/2);
      this.setTitleBarWidth(this.getWidht()-this.getStrokedBound());
      this.setTitleBarY(this.getY()+this.getStrokedBound()/2);
      this.setTitleBarHeight(40);
      this.setDiagram(ViewFactory.getInstance().createDiagramView(view, interaction, this.getTitleBarX(), this.getTitleBarY()+this.getTitleBarHeight()+3,this.getTitleBarWidth(),this.getHeight()-this.getTitleBarHeight()-this.getStrokedBound()));;
	  this.setActive(true);
	  this.setxCloseButtion((this.getTitleBarX()+this.getTitleBarWidth())-30);
	  this.setDifference(Math.abs(this.getxCloseButtion()-(this.getTitleBarX()+this.getTitleBarWidth())));
	  this.setSizeButtonX((this.getTitleBarX()+this.getTitleBarWidth()-30)-this.getDifference());
	  this.setDialogBoxList(new ArrayList<>());
	}
	
	
	/**
	 * A variable to store the x-coordiante of the closed button.
	 */
	private int sizeButtonX;
	
	/**
	 * A private variable to store the reference of the active diagram/
	 */
	private static DiagramView activeDagram;
	


	/**
	 * A reference to the parent window shape.
	 */
    private WindowShape parentShape;	
	
    /**
     * Adds a new child in the list.
     * @param child
     *        The child to be added.
     */
	protected void addChild(ObjectShape child) {
		this.childShape.add(child);
	}

	/**
	 * Removes the child from the list.
	 * @param shape
	 *        The given child to be removed.
	 */
	protected void removeChild(ObjectShape shape) {
		this.childShape.remove(shape);
	}
	
	
	/**
	 * A paint function, which is used to paint the window and it's diagram.
	 */
	public void paint(Graphics g) {
		float[] dashes = {1,1,1};
     	BasicStroke stroke = new BasicStroke(2, BasicStroke.CAP_SQUARE, BasicStroke.CAP_SQUARE, 10, dashes, 10);
		Graphics2D g2 = (Graphics2D)g;
	    this.paintAllFrames(g2);
	    g2.setColor(Color.RED);
        Shape line = new Line2D.Double(this.getTitleBarX(), this.getTitleBarY()+this.getTitleBarHeight(),this.getTitleBarX()+this.getTitleBarWidth(),this.getTitleBarY()+this.getTitleBarHeight());
	    Shape stroked = stroke.createStrokedShape(line);
	    g2.draw(stroked);
	    g2.setColor(Color.WHITE);
	    g2.draw(this.getCloseButtonShape());
	    g2.draw(this.getSizeButtonShape());
	    g2.setColor(Color.WHITE);
	    this.getDiagram().paint(g2);
	    this.getDialogBoxList().stream().forEach(e -> e.draw(g2));
	    if(this.getSelectedBox() != null)
	    	this.getSelectedBox().draw(g2);
	
	}
	
	/**
	 * Store the difference between the close button and the end of the title bar.
	 */
	private int difference;
	
	/**
	 * A getter to get the shape of the closed button.
	 * @return
	 *       Returns the shape of the close button.
	 */
	private Shape getCloseButtonShape() {
		GeneralPath path = new GeneralPath();
		path.moveTo(this.getxCloseButtion(), this.getTitleBarY());
		path.lineTo(this.getxCloseButtion()+this.getDifference(), this.getTitleBarY());
		path.lineTo(this.getxCloseButtion()+this.getDifference(), this.getTitleBarY()+this.getTitleBarHeight()-2);
		path.lineTo(this.getxCloseButtion(), this.getTitleBarY()+this.getTitleBarHeight()-2);
		path.lineTo(this.getxCloseButtion(), this.getTitleBarY());
		path.lineTo(this.getxCloseButtion()+this.getDifference(), this.getTitleBarY()+this.getTitleBarHeight()-2);
        path.moveTo(this.getxCloseButtion(), this.getTitleBarY()+this.getTitleBarHeight()-2);
		path.lineTo(this.getxCloseButtion()+this.getDifference(), this.getTitleBarY());
		path.closePath();
		return path;
	}
	
	/**
	 * A getter to get the size f the button.
	 * @return
	 *       Returns the size of the button.
	 */
	private Shape getSizeButtonShape() {
		Shape rect = new Rectangle2D.Double(this.getSizeButtonX(),this.getTitleBarY(),28,this.getTitleBarHeight()-2);
		return rect;
	}
	
	/**
	 * A private variable to store the x-coordinate of the close button.
	 */
	private int xCloseButtion;
	
	/**
	 * A paint function to paint the outer frame.
	 * @param g2
	 *        The given Graphics reference to paint.
	 */
	private void paintAllFrames(Graphics2D g2) {
        if(this.isActive()) 
			g2.setColor(Color.RED);
		else
		   g2.setColor(Color.WHITE);
		g2.draw(this.getShape());
		g2.setColor(Color.blue);
		g2.draw(this.getTitleBar());
		g2.fill(this.getTitleBar());
		g2.setColor(Color.BLACK);
		g2.setFont(new Font("TimesRoman", Font.ROMAN_BASELINE, 20));
		g2.drawString(this.getTitleBarText(), (this.getTitleBarX()+this.getTitleBarWidth()/8)-this.getTitleBarText().length(), this.getTitleBarY()+this.getTitleBarHeight()/2);
	    this.getChildShape().stream().forEach(e -> e.paint(g2));
	}



	/**
	 * A getter to get the stroke bound of paint.
	 * @return
	 *       Returns the stroke bound of the window.
	 */
	private int getStrokedBound() {
		return 10;
	}
	
	/**
	 * A string which will be printed on the top of the window.
	 * @return
	 *        Returns the string to be painted on the top of the window.
	 */
	private String getTitleBarText() {
		return "Diagram view : " + this.getView();
	}
	
	
	/**
	 * A getter to get the shape of the title bar.
	 * @return
	 *       Returns the shape of the title bar.
	 */
	Shape getTitleBar() {
		Shape titleBar = new Rectangle2D.Double(this.getTitleBarX(),this.getTitleBarY(),this.getTitleBarWidth(),this.getTitleBarHeight());
		return titleBar;
	}
	
	
	
	
	/**
	 * A getter to get the shape of the Window's outer frame.
	 * @return
	 *       Returns the outer frame of the window.
	 */
	private Shape getShape() {
		float[] dashes = {1,1,1};
     	BasicStroke stroke = new BasicStroke(this.getStrokedBound(), BasicStroke.CAP_SQUARE, BasicStroke.CAP_SQUARE, 10, dashes, 10);
		Shape rectangle = new Rectangle2D.Double(this.getX(),this.getY(),this.getWidht(),this.getHeight());
		Shape stroked = stroke.createStrokedShape(rectangle);
		return stroked;
	}

	/**
	 * A getter to get the x-coordinate of the title bar.
	 * @return
	 *       Returns the x-coordinate of the title bar.
	 */
	private int getTitleBarX() {
		return titleBarX;
	}

	
	/**
	 * A setter to set the x-coordinate of the titlebar.
	 * @param titleBarX
	 *        The new x-coordinate of teh titlebar.
	 */
	private void setTitleBarX(int titleBarX) {
		this.titleBarX = titleBarX;
	}

	/**
	 * A getter to get the y-coordinate of the titlebar.
	 * @return
	 *      Returns the y-coordinate of the title bar.
	 */
	private int getTitleBarY() {
		return titleBarY;
	}

	/**
	 * A setter to set the y-coordinat of the titlebar.
	 * @param titleBarY
	 *        The new y-coordinate of the titlebar.
	 */
	private void setTitleBarY(int titleBarY) {
		this.titleBarY = titleBarY;
	}
	


	/**
	 * A getter to get the width of titlebar.
	 * @return
	 *        Returns the width of the titlebar.
	 */
	private int getTitleBarWidth() {
		return titleBarWidth;
	}

	/**
	 * A setter to set the width of the title bar.
	 * @param titleBarWidth
	 *        The new width of the titlebar.
	 */
	private void setTitleBarWidth(int titleBarWidth) {
		this.titleBarWidth = titleBarWidth;
	}

	private int getTitleBarHeight() {
		return titleBarHeight;
	}

	private void setTitleBarHeight(int titleBarHeight) {
		this.titleBarHeight = titleBarHeight;
	}


	public DiagramView getDiagram() {
		return diagram;
	}


	private void setDiagram(DiagramView diagram) {
		this.diagram = diagram;
	}


	/**
	 * A propertyChange function which catches an event and acts according to the propertyNameValue.
	 * @param event
	 *        The new event which will be catched by this function.
	 */
	@Override
	public void propertyChange(PropertyChangeEvent event) {
		String value = event.getPropertyName();
		switch(value){
		      case "ChangedWindowCoords" : this.updateCoords(event);
		           break;
		}
          		
	}


	/**
	 * A function which updates the coordinates this windowSHape.
	 * @param event
	 *        The event which contains coordinates and different information about event.
	 */
	private void updateCoords(PropertyChangeEvent event) {
		if(this.isActive()) {
         this.setX((int) event.getOldValue());
         this.setY((int) event.getNewValue());
         this.setTitleBarX(this.getX()+this.getStrokedBound()/2);
         this.setTitleBarY(this.getY()+this.getStrokedBound()/2);
         this.getDiagram().move(this.getTitleBarX(),this.getTitleBarY()+this.getTitleBarHeight());
		}
	}


	private ArrayList<ObjectShape> getChildShape() {
		return childShape;
	}

    private void setChildShape(ArrayList<ObjectShape> childShape) {
		this.childShape = childShape;
	}

    /**
     * A function to check if the window shape contains the given coordinates.
     */
	@Override
	protected boolean contains(int x, int y) {
		return this.getTitleBar().contains(x,y);
	}

	private int previousX;
	private int previousY;
	
	
	private DialogBox selectedBox;
	
	/**
	 * A function which indicates that the mouse action has been performed.
	 */
	@Override
	public void performMouseAction(int id, int x, int y, int clickCount) {
		 if(this.canForwardRequestToDiagram(x,y))
	    	  this.getDiagram().performMouseAction(id, x, y, clickCount);
		 else 
			 this.getEvent(id).performAction(this,x,y);
		
		
		
      this.setPreviousX(x);
      this.setPreviousY(y);
	}
	

	private boolean canForwardRequestToDiagram(int x, int y) {
	    if(this.contains(x, y))
	    	return false;
		if(this.getSelectedBox() != null)
			return false;
		if(this.hasAnyIntersectingDialogBox(x, y))
			return false;
		if(this.canResize(x, y))
			return false;
		return true;
	}

	private ClickEventEnum getEvent(int id) {
        if(id == MouseEvent.MOUSE_CLICKED)
        	return WindowShapeClickEvent.ClickEvent;
        else if(id == MouseEvent.MOUSE_DRAGGED)
        	return WindowShapeClickEvent.MouseDragged;
        else if(id ==MouseEvent.MOUSE_RELEASED)
        	return WindowShapeClickEvent.MouseReleased;
		return NullEnum.NullEnum;
	}


	private boolean setActiveToMove;
	void setActiveToMove(boolean b) {
      	this.setActiveToMove = b;
	}
	
	boolean getActiveToMove() {
		return this.setActiveToMove;
	}

	protected DialogBox getDialogBoxBarAt(int x, int y) {
		for(DialogBox box: this.getDialogBoxList())
			if(box.canActivate(x, y))
				return box;
		return null;
	}

	private boolean hasAnyIntersectingDialogBox(int x, int y) {
		for(DialogBox box : this.getDialogBoxList())
			if(box.canActivate(x, y))
				return true;
		return false;
	}

	private int getIndex(int x) {
		if(x <= this.getX()+this.getWidht()/2)
			return -1;
		return 1;
	}

	protected void setDimensionMax(int x, int y) {
		try {
			this.resize(800, 200);
		}catch(IllegalOperationExcetion exc) {
			System.out.println(exc.toString());
		}
	}


	boolean sizeButtonContains(int x, int y) {
		return (x >= this.getSizeButtonX() && x <= this.getSizeButtonX()+30 && y >= this.getTitleBarY() && y <= this.getTitleBarY()+this.getTitleBarHeight());
	}


	void closeWindow() {
      getContext().remove(this);		
	}


	boolean closeButtonContains(int x, int y) {
		if(this.containsX(x) && this.containsY(y))
			return true;
		return false;
	}

	

	private boolean containsY(int y) {
		return (y >= this.getTitleBarY() && y <= this.getTitleBarY()+this.getTitleBarHeight());
	}

	private boolean containsX(int x) {
		return (x >= this.getxCloseButtion() && x <= this.getxCloseButtion()+this.getDifference());
	}

	protected void translate(int x, int y) {
		assert(this.isActive() && this.getActiveToMove());
			     this.getDialogBoxList().stream().forEach(e -> e.move(x, y, this.getPreviousX(), this.getPreviousY()));
			     this.setX(this.getX()+(x-this.getPreviousX()));
		         this.setY(this.getY()+(y-this.getPreviousY()));
		         this.setTitleBarX(this.getX()+this.getStrokedBound()/2);
		         this.setTitleBarY(this.getY()+this.getStrokedBound()/2);
		         this.getDiagram().move(this.getTitleBarX(),this.getTitleBarY()+this.getTitleBarHeight());
		         this.setxCloseButtion(this.getxCloseButtion()+(x-this.getPreviousX()));
		         this.setSizeButtonX(this.getxCloseButtion()-30);
		}


	private int previousCode;

	
	protected void handleKeyEvent(int id, int keyCode, char keyChar) {
		if(this.getSelectedBox() != null) 
			this.getSelectedBox().performKey(id, keyCode, keyChar);
		else {
			this.getKeyEvent(keyCode).performAction(this, this.getPreviousX(), this.getPreviousY());
		    this.getDiagram().handleKeyEvent(id,keyCode,keyChar);	
		}
		this.setPreviousCode(keyCode);
	}

	
	protected boolean canCreateNewDialogBox() {
		if(this.getDiagram().getSelectedShape() != null)
			return false;
		if(this.getDiagram().hasAnyPartySelectedMessage() != null)
			return false;
		if(this.getPreviousCode() != 17)
			return false;
		return true;
	}
	

	private ClickEventEnum getKeyEvent(int keyCode) {
       	if(keyCode == 9)
       		return KeyAction.Tab;
       	if(keyCode == 17)
       		return KeyAction.Controrl;
       	if(keyCode == 10)
       		return KeyAction.Enter;
       	if(keyCode == 68)
       		return KeyAction.D;
		return NullEnum.NullEnum;
       	
	}

	protected void createNewDialogBox() {
		   DialogBox box = new SimpleDialogBox(this.getX()+this.getWidht()/2,this.getY()+this.getTitleBarHeight()+10,140,70);
		   Button button = new RadioButton(box.getX() + 15,box.getY()+box.getHeight()/3,"CommView");
	       button.setDimension(20);
		   box.addToChilds(button);
		   Button secondButton = new RadioButton(box.getX() + 80,box.getY()+box.getHeight()/3,"SeqView");
		   secondButton.setDimension(20);
		   box.addToChilds(secondButton);
		   button.addActionListener(this);
		   secondButton.addActionListener(this);
		   box.getCloseButton().addActionListener(this);
		   this.switchButtons(button,secondButton);
		   this.getDialogBoxList().add(box);
	}

	
	private void switchButtons(Button button, Button secondButton) {
		if(this.getView() == View.SEQUENCE_View)
			   secondButton.setActive(true);
		   else
			   button.setActive(true);		
	}


	private ArrayList<DialogBox> dialogBoxList;
	/**
	 * A function to switch the diagram view.
	 */
	@SuppressWarnings("unchecked") 
	protected void switchView() {
		View temp = null;
		if(this.getView() == View.SEQUENCE_View)
			temp = View.COMM_View;
		else
			temp = View.SEQUENCE_View;
     	DiagramView diagram = ViewFactory.getInstance().createDiagramView(temp, this.getInteraction(), this.getTitleBarX(), this.getTitleBarY()+this.getTitleBarHeight(),this.getTitleBarWidth(),this.getHeight()-this.getTitleBarHeight()-this.getStrokedBound());
     	diagram.setPartyList((ArrayList<Party>) this.getDiagram().getPartyList().clone());
     	diagram.createShapesForAllParties((HashMap<Party, PartyShape>) this.getDiagram().getAssociationList().clone());
     	diagram.subscribeToAllParties();
     	diagram.subscribeToAllMessages();
     	this.setDiagram(null);
     	setActiveDagram(diagram);
     	this.setDiagram(diagram);
     	this.setView(diagram.getView());
     	this.turnOffAllButtons();
     	this.switchButton();
	}
	

	

	private void switchButton() {
		DialogBoxElement first ;
		DialogBoxElement second ;
		if(this.getDialogBoxList().size() > 0) {
			for(DialogBox element : this.getDialogBoxList()) {
				second = element.getCommViewButton();
				first = element.getSecViewButton();
				this.switchButtons((Button)second, (Button)first);
				}
			}
		}
	     	 

	private void turnOffAllButtons() {
     	this.getDialogBoxList().stream().forEach(e -> {
     		e.getChildList().stream().forEach(element -> {
     			if(element instanceof Button)
     				((Button) element).setActive(false);
     		});
     	});
	}

	/**
	 * A function which creates new diagram.
	 */
	@SuppressWarnings("unchecked") 
	protected void createNewDiagram() {
      WindowShape shape  = new WindowShape(this.getInteraction(), this.getView(), this.getX()+20+this.getWidht(), this.getY(), this.getWidht(), this.getHeight());
      shape.getDiagram().setPartyList((ArrayList<Party>) this.getDiagram().getPartyList().clone());
	  shape.getDiagram().adjustParameter((HashMap<Party, PartyShape>) this.getDiagram().getAssociationList().clone());
	  getContext().add(shape);
	  this.setActive(false);
	}

	



	


	protected int getPreviousCode() {
		return previousCode;
	}


	private void setPreviousCode(int previousCode) {
		this.previousCode = previousCode;
	}


	private static DiagramView getActiveDagram() {
		return activeDagram;
	}


	private static void setActiveDagram(DiagramView activeDagram) {
		WindowShape.activeDagram = activeDagram;
	}


	private WindowShape getParentShape() {
		return parentShape;
	}


	private void setParentShape(WindowShape parentShape) {
		this.parentShape = parentShape;
	}


	int getPreviousY() {
		return previousY;
	}


	void setPreviousY(int previousY) {
		this.previousY = previousY;
	}

   int getPreviousX() {
		return previousX;
	}


	void setPreviousX(int previousX) {
		this.previousX = previousX;
	}

	
	private static WindowContext context;

	protected void setWindowContext(WindowContext windowContext) {
      		WindowShape.context = windowContext;
	}


	private static WindowContext getContext() {
		return context;
	}


	private boolean isActive() {
		return isActive;
	}


	protected void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	/**
	 * A boolean checker to check if the outerframe contains the given coordinate.
	 * @param x
	 *        The given x-coordinate to be checked.
	 * @param y
	 *       The given y-coordinate to be checked.
	 * @return
	 *       returns true if the outer frame contains the given coordinates.
	 */
    private boolean hasIntersectingBoundingBox(int x, int y) {
    	if(x > this.getX()+this.getWidht() - 5 && x <= this.getX()+this.getWidht()+5 && y >= this.getY() && y <= this.getY()+this.getHeight())
    		return true;
    	if(x > this.getX() && x <= this.getX()+this.getWidht() && y > this.getY()+this.getHeight()-5 && y < this.getY()+this.getHeight()+5)
    		return true;
    	if(x >= this.getX()-5  && x < this.getX()+5 && y > this.getY() && y <= this.getY()+this.getHeight())
    		return true;
		return false;
		
	}
    /**
     * A function to resize the window.
     * @param width
     *         The new width of the outer window.
     * @param height
     *         The new height of the outer window.
     */
	@Override
	protected void resize(int width, int height) {
		if(!(this.getDiagram().canResize(width,height)))
			throw new IllegalOperationExcetion("Can't resize window , move elements first");
		this.setWidht(this.getWidht()+width);
		this.setHeight(this.getHeight()+height);
		this.setxCloseButtion((this.getTitleBarX()+this.getTitleBarWidth())-30);
		this.setSizeButtonX((this.getTitleBarX()+this.getTitleBarWidth()-30)-this.getDifference());
		this.setTitleBarWidth(this.getWidht()-this.getStrokedBound());
        this.getDiagram().setWidht(this.getTitleBarWidth());
        this.getDiagram().setHeight(this.getHeight()-this.getTitleBarHeight()-this.getStrokedBound());
	}


	/**
	 * A getter to get the x-coordinate of the close button.
	 * @return
	 *       Returns the x-coordinate of the close button.
	 */
	private int getxCloseButtion() {
		return xCloseButtion;
	}


	/**
	 * A setter to set the x-coordinat of the close button.
	 * @param xCloseButtion
	 *        The new x-coordinate of the close button.
	 */
	private void setxCloseButtion(int xCloseButtion) {
		this.xCloseButtion = xCloseButtion;
	}


	/**
	 * A getter to get the difference.
	 * @return
	 *       Returns the difference between close button and resize button.
	 */
	private int getDifference() {
		return difference;
	}


	/**
	 * A setter to set the difference between close buton and resize button.
	 * @param difference
	 *        The new difference between close button and resize button.
	 */
	private void setDifference(int difference) {
		this.difference = difference;
	}


	/**
	 * A getter to get the size of the buttons.
	 * @return
	 *      Returns the size of the buttons.
	 */
	private int getSizeButtonX() {
		return sizeButtonX;
	}


	/**
	 * A setter to set the size of the button.
	 * @param sizeButtonX
	 *        The new size of the button.
	 */
	private void setSizeButtonX(int sizeButtonX) {
		this.sizeButtonX = sizeButtonX;
	}

	/**
	 * A function which performs differnt actionPerformed events.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(this.getView() == View.COMM_View && e.getActionCommand().equals("SeqView"))
			this.switchView();
		else if(this.getView() ==  View.SEQUENCE_View && e.getActionCommand().equals("CommView"))
			this.switchView();
		else if(e.getActionCommand().equals("x"))
			this.removeDialogBox((Button)e.getSource());
		
	}

	/**
	 * A function to remove the dialogbox from the list.
	 * @param button
	 *         Button whom's dialogbox will be removed.
	 */
	private void removeDialogBox(Button button) {
		ArrayList<DialogBox> temp = (ArrayList<DialogBox>) this.getDialogBoxList().stream().filter(e -> e.getChildList().contains(button)).collect(Collectors.toList());
		this.setSelectedBox(null);
		this.getDialogBoxList().removeAll(temp);
	}

	/**
	 * A getter to get the dialogbox list of this windowshape.
	 * @return
	 *       Returns the dialogbox list of this window shape.
	 */
	public ArrayList<DialogBox> getDialogBoxList() {
		return dialogBoxList;
	}

	/**
	 * A function to set the list of the dialogbox in this windowshape.
	 * @param dialogBoxList
	 *        The new dialogbox list which will be set.
	 */
	protected void setDialogBoxList(ArrayList<DialogBox> dialogBoxList) {
		this.dialogBoxList = dialogBoxList;
	}

	public DialogBox getSelectedBox() {
		return selectedBox;
	}

	/**
	 * A setter to set the selected dialogbox.
	 * @param selectedBox
	 *        The dialogBox which will be set as selected.
	 */
	protected void setSelectedBox(DialogBox selectedBox) {
		this.selectedBox = selectedBox;
	}

	/**
	 * A function which check if the windowShape can be selected to move.
	 * @param x
	 *        The x-coordinate wher emouse event happend.
	 * @param y
	 *        The y-coordinate where mouse event happend.
	 * @return
	 *        Returns true if the shape can be selected to move.
	 */
	protected boolean canSetActiveToMove(int x, int y) {
		if(!(this.getTitleBar().contains(x,y)))
			return false;
		if(this.getActiveToMove() != false)
			return false;
		if(this.getCloseButtonShape().contains(x,y))
			return false;
		if(this.getDialogBoxBarAt(x, y) != null)
			return false;
		return true;
	}

	/**
	 * A function to check if teh window can be resize.
	 * @param x
	 *        The x-coordinate wher emouse event happend.
	 * @param y
	 *        The y-coordinate where mouse event happend.
	 * @return
	 *        Returns true if and only if the shape can be resize.
	 */
	protected boolean canResize(int x, int y) {
		return this.hasIntersectingBoundingBox(x, y) && this.isActive();
	}

	/**
	 * A function which initiates the resizing of thewindowShape/
	 * @param x
	 *        The x-coordinate where mouse click took placE.
	 * @param y
	 *        The y-coordinate where mouse click took place.
	 */
	protected void startResizing(int x, int y) {
		try{
  		  this.resize(this.getIndex(x)*(x-this.getPreviousX()), (y-this.getPreviousY()));
  	  }catch(IllegalOperationExcetion exc) {
  	  System.out.println(exc.toString());
  	  }
  	   if(this.getIndex(x) < 0)
  		  this.translate(x, y);		
	}

	/**
	 * A function to deselected this windowShape.
	 */
	protected void setOffSelectedBox() {
       this.getSelectedBox().setActive(false);
		this.setSelectedBox(null);		
	}

	/**
	 * A function to check if teh windowShape can be moved.
	 * @param x
	 *        The new x-coordiante where mouse event happpend.
	 * @param y
	 *       The y-coordiante where mouse event happend.
	 * @return
	 *       Returns true if the windowShape can be moved.
	 */
	protected boolean canMove(int x, int y) {
       if(!(this.getActiveToMove()))
    	   return false;
       if(!(this.getDiagram().canResize(x, y)))
    	   return false;
       if(this.getTitleBar().contains(this.getPreviousX(),this.getPreviousY()))
    	   return true;
		return true;
	}

	/**
	 * A function which removes the allowance for a dialogbox to move.
	 * @param x
	 *        The x-coordinate where the mouse click happend.
	 * @param y
	 *        The y-coordiante where the mouse click happend.
	 * @return
	 *        Returns true if window shape allowance for moving can be removed.
	 */
	protected boolean canRemoveMovingAllowance(int x, int y) {
		return (!(this.getTitleBar().contains(this.getPreviousX(),this.getPreviousY())));
	}

	/**
	 * A function which selects the box and also forwards the request to dialogbox.
	 * @param x
	 *        The x-coordinate where mouse click happend.
	 * @param y
	 *        The y-coordinate where mouse click happend.
	 */
	protected void selectBox(int x, int y) {
		  this.setSelectedBox(this.getDialogBoxBarAt(x, y));
		  this.getSelectedBox().setActive(true);
	}

	
	
	
	/**
	 * A checker to check if the request can forwarded to the dialogbox.
	 * @param x
	 *        The x-coordinate of the event.
	 * @param y
	 *        The y-coordiante of the event.
	 * @return
	 *        Returns true if mouse/key event can be forwarded to dialogbox/
	 */
	protected boolean canForwarRequestToDialogBox(int x, int y) {
		return  this.getSelectedBox() != null && this.getSelectedBox().contains(x, y);
	}

	/**
	 * A function which forwards request to the dialogbox.
	  * @param x
	 *        The x-coordinate where mouse click occured.
	 * @param y 
	 *        The y-coordinate of the point where mouse click event occured.
	 */
	public void forwarRequestToDialogBox(int x, int y) {
       this.getSelectedBox().mouseClick(x, y);		
	}

	/**
	 * A boolean checker to check if the dialog box can be activated.
	 * @param x
	 *        The x-coordinate where mouse click occured.
	 * @param y 
	 *        The y-coordinate of the point where mouse click event occured.
	 * @return
	 *       Returns true if the box can be activated.
	 */
	public boolean canActivateADialogBox(int x, int y) {
		return this.getDialogBoxBarAt(x, y) != null;
	}

	



}
