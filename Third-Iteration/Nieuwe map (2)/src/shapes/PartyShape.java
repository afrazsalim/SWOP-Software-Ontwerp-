package shapes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.stream.Collectors;

import box.DialogBox;
import box.DialogBoxElement;
import box.SimpleDialogBox;
import box.TextField;
import button.Button;
import button.RadioButton;
import diagramViews.DiagramView;
import domainObjects.Actor;
import domainObjects.Party;
import domainObjects.PartyObject;

/**
 * A class representing the partyshape.
 * @author Afraz Salim
 *
 */
public abstract class PartyShape implements ActionListener {
	
	/**
	 * A private variable which stores the x-coordinate.
	 */
	private int x;
	/**
	 * A private variable which store the y-coordiante.
	 */
	private int y;
	/**
	 * A private variable whcih stores the width of the shape.
	 */
	private int width;
	/**
	 * A private variable which stores the height of the shape.
	 */
	private int height;
	/**
	 * A private variable which stores the line of the shape.
	 */
	private LineShape line;
	/**
	 * A private variable which stores the label.
	 */
	private Label label;
	private int lineX;
	private int lineY;
	private int lineWidth;
	private int lineHeight;
	private int labelX;
	private int labelY;
	private int labelWidth;
	private int labelHeight;
	private DiagramView diagram;
	private boolean setIsSelected;
	private ArrayList<DialogBox> dialogBoxList;
	
	/**
	 * A constructor to create the instance of the actor shape.
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
	public PartyShape(DiagramView diagram,int x, int y, int width, int height) {
		this.setX(x);
		this.setY(y);
		this.setWidth(width);
		this.setHeight(height);
		this.setDiagram(diagram);
		this.setDialogBoxList(new ArrayList<>());
	}
	
	
	
	/**
	 * A function to check if the shape contains the points.
	 * @param x
	 *        The given x-coordinate to be checked.
	 * @param y
	 *        The given y-coordiante to be checked.
	 * @return
	 *        Returns true if the shape contains the given points.
	 */
	public boolean contains(int x, int y) {
		if((x >= this.getX() && x <= this.getX()+this.getWidth() && y >= this.getY() && y <= this.getY()+this.getHeight())) 
			return true;
		return false;
	}
	
	/**
	 * A private variable to store the reference to the real party.
	 */
	private Party source;
	
	/**
	 * A getter to get the x-coordiante of the party.
	 * @return
	 *       Returns the x-coordinate of the party.
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * A setter to set the x-coordinate of the shape.
	 * @param x
	 *        The x-coordinate which will be set as shape's coordiante.
	 */
	protected void setX(int x) {
		this.x = x;
	}
	
	/**
	 * A function to move the shape.
	 * @param x
	 *        The given x-coordiante on which the shape will be moved.
	 * @param y
	 *        The given y-coordiante on whcih the shape will be moved.
	 */
	public abstract void move(int x, int y);
	

	/**
	 * A getter to get the y-coordiante of the shape.
	 * @return
	 *       Returns the y-coordinate of the shape.
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * A setter to set the y-coordinate of the shape.
	 * @param y
	 *       The given y-coordiante which will be set.
	 */
	protected void setY(int y) {
		this.y = y;
	}

	/**
	 * A getter to get the width of the shape.
	 * @return
	 *      Returns the width of the shape.
	 */
	public int getWidth() {
		return width;
	}
	/**
	 * A stter to set the width of the party's shape.
	 * @param width
	 *         The given width which will replaced the old width.
	 */
	protected void setWidth(int width) {
		this.width = width;
	}
	/**
	 * A getter to get the height of the party's shape.
	 * @return
	 *        Returns the height of the party's shape.
	 */
	public int getHeight() {
		return height;
	}
	/**
	 * A setter to set the height of the party's shape.
	 * @param height
	 *        The new height of the party's shape.
	 */
	protected void setHeight(int height) {
		this.height = height;
	}
	

	/**
	 * A function to get the shape of the party.
	 * @return
	 *       Returns the shape of the party.
	 */
	public abstract Shape getShape();




	

	/**
	 * A getter to get the real party associated with this shape.
	 * @return
	 *     returns the party associated with this shape.
	 */
	public Party getSource() {
		return source;
	}



	/**
	 * A stter to set the party as source of this shape.
	 * @param party
	 *       The party of the shape.
	 */
	public void setSource(Party party) {
		this.source = party;
	}



	/**
	 * A getter to get the lineShape of the arty's shape.
	 * @return
	 *       Returns the lineshape of the party's shape.
	 */
	public LineShape getLine() {
		return line;
	}


 
	/**
	 * A setter to set the lineshape of the partyShape.
	 * @param line
	 *        The line of the party.
	 */
	protected void setLine(LineShape line) {
		this.line = line;
	}



	/**
	 * A getter to get the label of the partyshape.
	 * @return
	 *       Returns the labelShape of the party shape.
	 */
	public Label getLabel() {
		return label;
	}



	/**
	 * A setter to set the label of the partyShape.
	 * @param label
	 *        The new label of the party shape.
	 */
	protected void setLabel(Label label) {
		this.label = label;
	}



	/**
	 * A getter to get the x-coordiante of the line
	 * @return
	 *       Returns the x-coordinate of the line.
	 */
	public int getLineX() {
		return lineX;
	}



	/**
	 * A setter to set the x-coordinate of the line.
	 * @param lineX
	 *        The x-coordinate of the lineShape.
	 */
	public void setLineX(int lineX) {
		this.lineX = lineX;
	}



	/**
	 * A getter to get the y-coordinate of the lineShape.
	 * @return
	 *       Returns the y-coordinate of the line.
	 */
	public int getLineY() {
		return lineY;
	}



	/**
	 * Sets the y-coordinate of the line.
	 * @param lineY
	 *        The new y-coordinate of the line.
	 */
	protected void setLineY(int lineY) {
		this.lineY = lineY;
	}



	/**
	 * A getter to get the height of the line shape.
	 * @return
	 *       Returns the height of the line shape.
	 */
	public int getLineHeight() {
		return lineHeight;
	}



	/**
	 * A setter to set the height of the line shape.
	 * @param lineHeight
	 *        The new height of the line shape.
	 */
	public void setLineHeight(int lineHeight) {
		this.lineHeight = lineHeight;
	}



	/**
	 * A getter to get the width of the line.
	 * @return
	 *       Returns the width of the line.
	 */
	public int getLineWidth() {
		return lineWidth;
	}



	/**
	 * A setter to set the width of the line.
	 * @param lineWidth
	 *        The new line widht of the party shape.
	 */
	public void setLineWidth(int lineWidth) {
		this.lineWidth = lineWidth;
	}



	/**
	 * A getter to get the x-coordinate of the label.
	 * @return
	 *        Returns the x-coordinate of the label.
	 */
	public int getLabelX() {
		return labelX;
	}



	/**
	 * A setter to set the x-coordinate of the label.
	 * @param labelX
	 *        The given x-coordinate of the label.
	 */
	protected void setLabelX(int labelX) {
		this.labelX = labelX;
	}



	/**
	 * A getter to get the y-coordinate of the label.
	 * @return
	 *       Returns the y-coordinate of the label.
	 */
	public int getLabelY() {
		return labelY;
	}



	/**
	 * A setter to set the y-coordinate of the label.
	 * @param labelY
	 *         The new y-coordinate of the label.
	 */
	protected void setLabelY(int labelY) {
		this.labelY = labelY;
	}



	/**
	 * A getter to get the width of the label.
	 * @return
	 *       Returns the width of the label.
	 */
	public int getLabelWidth() {
		return labelWidth;
	}



	/**
	 * A setter to set the width of the label.
	 * @param labelWidth
	 *        The new width of the label.
	 */
	protected void setLabelWidth(int labelWidth) {
		this.labelWidth = labelWidth;
	}



	/**
	 * A getter to get the height of the label.
	 * @return
	 *       Returns the height of the label.
	 */
	public int getLabelHeight() {
		return labelHeight;
	}



	/**
	 * A setter to set the height of the label.
	 * @param labelHeight
	 *        The new height of the label.
	 */
	protected void setLabelHeight(int labelHeight) {
		this.labelHeight = labelHeight;
	}



	/**
	 * A function which if the shape contains the given coordiante , will ask the diagram to move it.
	 * @param x
	 *        The given x-coordiante of to be checked.
	 * @param y
	 *        The given y-coordinate to be checked.
	 */
	public void drag(int x, int y) {
		 if(this.isSetIsSelected() && (this.hasAnyIntersectingDialogBox(x,y) == null)) 
			   this.getDiagram().move(x,y,this.getSource());
	}


	

    /**
     * A getter to get the dialogbox box which intersects the point.
     * @param x
     *        The x-coordinat eof the point which will be checked.
     * @param y
     *        The y-coordinate of the point which will be checked.
     * @return
     *        Returns the box if the it contains the given point.
     */
	public DialogBox hasAnyIntersectingDialogBox(int x, int y) {
		for(DialogBox box: this.getDialogBoxList())
			if(box.contains(x,y))
				return box;
		return null;
	}



	/**
	 * A getter to get the diagram of the partyshape.
	 * @return
	 *       Returns the diagram of the partyshape.
	 */
	public DiagramView getDiagram() {
		return diagram;
	}



	
	private void setDiagram(DiagramView diagram) {
		this.diagram = diagram;
	}


	

	/**
	 * The coordinates on which the mouse is cliked.
	 * @param x
	 *        The given x-coordinate on which the mouse is clicked.
	 * @param y
	 *        The given y-coordinate on which the mouse is clicked.
	 */
	public void mouseClicked(int x, int y) {
		if(this.hasAnyIntersectingDialogBox(x, y) != null) {
			this.hasAnyIntersectingDialogBox(x, y).mouseClick(x, y);
			return;
		}
		if(this.getLine().hasAnyIntersectingMessage(x,y) != null && this.getDiagram().partyWithActiveMessage() == null) {
			this.getLine().hasAnyIntersectingMessage(x, y).setSelected(true);
			this.setSelectedMessage(this.getLine().hasAnyIntersectingMessage(x, y));
		}
      	if(this.getLabel().contains(x,y))
      		this.getLabel().setLabelActive(true);
      	if(this.getLine().getIntersectingLabel(x,y) != null)
      		this.getDiagram().enableMessageLabel(this.getLine().getIntersectingLabel(x,y));
      	if(this.contains(x, y)) {
      		if(this.getDiagram().deSelectParty(x,y)) {
      	      this.getDiagram().setAllSelectedFalse();
      		this.setSetIsSelected(true);
      		}
      	}
      	this.getDialogBoxList().stream().forEach(box ->{
      		box.mouseClick(x,y);
      	});
	}
	


	
	private MessageShape selecteMessage ;

	/**
	 * A setter to set the selected message shape.
	 * @param message
	 *        The message shape which will be set as selected.
	 */
	public void setSelectedMessage(MessageShape message) {
       	this.selecteMessage = message;
	}

	
    /**
     * A getter to get the selected.
     * @return
     *       Returns the selected shape of the message.
     */
	public MessageShape getSelectedMessage() {
		return this.selecteMessage;
	}


	/**
	 * A function which acts when mouse is double clicked.
	 * @param x
	 *        The x-coordinate on which the mouse is double clicked.
	 * @param y
	 *        The y-coordinate on which the mouse is double clicked.
	 */
	public void doubleClicked(int x, int y) {
 		if(this.isSetIsSelected() && this.contains(x, y))
 			this.getDiagram().convertPartyType(this.getX(),this.getY(),this.getSource());
	}



	/**
	 * A function which acts when the mouse button is released.
	 * @param x
	 *        The given x-coordinate on which the mouse is released.
	 * @param y
	 *        The given y-coordinate on which the mouse is released.
	 */
	public void releasedButtion(int x, int y) {
        if(this.getLine().contains(x, y)) {
        	this.getDiagram().endMessageProcess(this,x,y);
        }
	}



	/**
	 * A function to draw Shapes.
	 * @param g2
	 *       The given instance of the graphics with which it draws shapes.
	 */
	public void paint(Graphics2D g2) {
		this.getDialogBoxList().stream().forEach(e -> {
		   e.draw(g2);
		});
		this.getDialogBoxList().stream().forEach(e -> {
			e.draw(g2);
			});
	}

	


	

	protected abstract void drawMessage(Graphics2D g2);


	/**
	 * A fucntion to check if the label is active.
	 * @return
	 *        Return true if the label is active.
	 */
	public boolean getHasActiveLabel() {
		return this.getLabel().isActive();
	}



	/**
	 * A fucntion to change the status of the label.
	 * @param value
	 *        The new boolean value of the label.
	 */
	public void setLabelActive(boolean value) {
        this.getLabel().setLabelActive(value);		
	}



	/**
	 * A function to remove the text from the label shape.
	 */
	public void removeText() {
       this.getLabel().removeText();		
	}



	/**
	 * A function to clear all bars from the line.
	 */
	public void clearBars() {
	}



	/**
	 * A checker to check if the shape is seleceted.
	 * @return
	 *       Returns true if the shape is selected.
	 */
	public boolean isSetIsSelected() {
		return setIsSelected;
	}



	/**
	 * A function to set the shape as seleceted.
	 * @param setIsSelected
	 *        The new boolean value which will be set.
	 */
	public void setSetIsSelected(boolean setIsSelected) {
		this.setIsSelected = setIsSelected;
	}


	

	

	/**
	 * A function to create dialogbox for a party's shape.
	 * @return
	 *       Returns the new dialogbox for party's shape.
	 */
	public DialogBox createNewDialogBox() {
	   DialogBox box = new SimpleDialogBox(this.getX(),this.getY()-75-this.getLabelHeight(),180,100);
	   Button button = new RadioButton(box.getX() + 15,box.getY()+box.getHeight()/3,"Actor");
       button.setDimension(20);
	   this.addListenerToButton(button,this.getSource(),this.getDiagram(),this);
	   box.addToChilds(button);
	   Button secondButton = new RadioButton(box.getX() + 80,box.getY()+box.getHeight()/3,"Party");
	   secondButton.setDimension(20);
	   this.addListenerToButton(secondButton, this.getSource(),this.getDiagram(),this);
	   box.addToChilds(secondButton);
	   TextField field = new TextField(button.getX()-13,box.getY()+box.getHeight()-20,70,18);
	   box.addToChilds(field);
	   this.addListnerToInstanceField(this.getSource(),field);
	   field.setText(this.getSource().getInstaceName());
	   field.setLabel("instance");
	   this.getDialogBoxList().add(box);
	   TextField fieldClass = new TextField(field.getX()+field.getWidth()+5,field.getY(),field.getWidth(),field.getHeight());
	   fieldClass.setText(this.getSource().getClassName());
	   fieldClass.setLabel("ClassName");
	   box.addToChilds(fieldClass);
	   this.addListenerToClassField(this.getSource(),fieldClass);
	   if(this.getSource() instanceof PartyObject)
		   secondButton.setActive(true);
	   else if(this.getSource() instanceof Actor)
		   button.setActive(true);
	   box.getCloseButton().addActionListener(this);
	   box.getCloseButton().addActionListener(this.getDiagram());
	return box;
	}


	
	/***
	 * A function for adding listener to the add button.
	 * @param button
	 *        The button to which the listener will be added.
	 * @param source
	 *        The party of thsi partyShape.
	 * @param diagram
	 *        The diagram of this partyShape.
	 * @param shape
	 *        Shape of this object.
	 */
	private void addListenerToButton(Button button, Party source, DiagramView diagram, PartyShape shape) {
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
               diagram.convertPartyType(shape.getX(),shape.getY(), source);				
			}
			
		});
	}


	/**
	 * A function for adding listener to the close button of the dialogbox.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
	 if(e.getActionCommand().equals("x")) {
			ArrayList<DialogBox> temp = (ArrayList<DialogBox>) this.getDialogBoxList().stream().filter(box -> box.getChildList().contains(e.getSource())).collect(Collectors.toList());
			this.getDialogBoxList().removeAll(temp);
		}
	}


	/**
	 * A function to add listener to classfield of the party's shape.
	 * @param source
	 *        The soource paryt whose shape is this.
	 * @param fieldClass
	 *         The field class from which the text will be retrieved.
	 */
	private void addListenerToClassField(Party source, TextField fieldClass) {
		fieldClass.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
                source.updateClassName(((TextField) e.getSource()).getText());				
			}
			
		});
	}



	/**
	 * A function to add listener to the button.
	 * @param source
	 *        The party whose shape is this.
	 * @param field
	 *        The field from which the text will be retrieved.
	 */
	private void addListnerToInstanceField(Party source, TextField field) {
		 field.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
		          	  source.updateInstanceName(((TextField) e.getSource()).getText());
				}
			   });		
	}



	/**
	 * A getter to get the dialogbox list.
	 * @return
	 *       Returns the dialogbox list of the this shape.
	 */
	public ArrayList<DialogBox> getDialogBoxList() {
		return dialogBoxList;
	}



	/**
	 * A setter to set the dialogbox list.
	 * @param dialogBoxList
	 *        The new dialogbox list with which the dialogbox instance will be initialized.
	 */
	public void setDialogBoxList(ArrayList<DialogBox> dialogBoxList) {
		this.dialogBoxList = dialogBoxList;
	}



	/**
	 * A function which switched button of the dialogbox.
	 */
	public void switchButtons() {
      this.getDialogBoxList().stream().forEach(e -> {
    	  e.getChildList().stream().forEach(button -> {
    		  if(button instanceof Button)
    		    ((Button) button).setActive(false);
    	  });
      });	
      this.getDialogBoxList().stream().forEach(e -> {
    	  e.getChildList().stream().forEach(button -> {
    		  if(button instanceof Button) {
    		  if(this.getSource() instanceof PartyObject && ((Button) button).getLabel().equals("Party"))
    		    ((Button) button).setActive(true);
    		  else if(this.getSource() instanceof Actor && ((Button) button).getLabel().equals("Actor"))
    			  ((Button) button).setActive(true);
    		  }
    	  });
      });	
	}



	/**
	 * A checker to check if there is any active field.
	 * @return
	 *       Returns true if the texfield of the dialogbox of this party is active.
	 */
	public TextField hasActiveField() {
		for(DialogBox box : this.getDialogBoxList()) {
			 for(DialogBoxElement field : box.getChildList()) {
				 if(field instanceof TextField && ((TextField) field).getIsActive())
					 return (TextField) field;
			 }
		}
		return null;
	}


	
/**
 * A function to move the party's shape.
 * @param x
 *        The new x-coordinate of the point on which party's shape will be moved.
 * @param xSecond
 *        The previous x-coordinate of the point on which party's shape will be moved.
 * @param y
 *        The new y-coordinate of the point on which party's shape will be moved.
 * @param ySecond
 *        The previous y-coordinate of the point on which party's shape will be moved.
 */
	public void move(int x, int xSecond, int y, int ySecond) {
		this.getDialogBoxList().stream().forEach(e -> e.move(x,y,xSecond,ySecond));
		this.move(this.getX()+ (x-xSecond), this.getY() + (y-ySecond));
	}


	

	/**
	 * A function to draw the shap's element.
	 * @param g2
	 *       The given instnace of the graphics with which partyShape's elements will be drawn.
	 */
	public void draw(Graphics2D g2) {
		if(this.isSetIsSelected())
	    	g2.setColor(Color.RED);
	    else
		g2.setColor(Color.GREEN);
    	g2.draw(this.getShape());
    	this.paint(g2);
    	g2.setColor(Color.LIGHT_GRAY);
    	g2.draw(this.getLine().getShape());
    	g2.setColor(Color.LIGHT_GRAY);
    	g2.setColor(Color.orange);
		g2.setFont(new Font("TimesRoman", Font.PLAIN, 15)); 
    	if(this.getHasActiveLabel()) {
    		g2.setColor(Color.RED);
        	g2.drawString(this.getLabel().getText()+"|", this.getLabel().getX(), this.getLabel().getY()+this.getLabel().getHeight()-2);
    	}
    	else
        	g2.drawString(this.getLabel().getText(), this.getLabel().getX(), this.getLabel().getY()+this.getLabel().getHeight()-2);
    	g2.draw(this.getLabel().getShape());
    	g2.setColor(Color.WHITE);
    	this.getLine().draw(g2);
    	g2.setColor(Color.WHITE);		
	}



	/**
	 * A function to update the dialogbox of the party
	 */
	public void updateAllDialogBox() {
      this.getDialogBoxList().stream().forEach(e -> {
    	  ((TextField) e.getChildList().get(3)).setText(this.getSource().getInstaceName());
    	  ((TextField) e.getChildList().get(4)).setText(this.getSource().getClassName());
      });
	}



	








}
