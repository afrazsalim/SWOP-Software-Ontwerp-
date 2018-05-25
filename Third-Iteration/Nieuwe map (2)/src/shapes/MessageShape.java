package shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.GeneralPath;
import java.io.Serializable;
import java.util.ArrayList;

import Exceptions.IllegalOperationExcetion;
import box.DialogBox;
import box.ListBox;
import box.SimpleDialogBox;
import box.TextField;
import button.Button;
import button.SimpleButton;
import domainObjects.InvocationMessage;
import domainObjects.Message;
import domainObjects.ResultingMessage;

/**
 * A class representing the shape of the message.
 * @author Afraz Salim
 *
 */
public class MessageShape implements Serializable {

	
	private static final long serialVersionUID = 2105201620226894435L;
	private int x;
	private int y;
	private int xSecond;
	private int ySecond;
	private PartyShape senderShape;
	private PartyShape recieverShape;
	private String messageText;
	private ArrayList<DialogBox> dialogBoxList;
	/**
	 * A constructor to create a new instance of the message shape.
	 * @param sender
	 *        The sender shape of the message shape.
	 * @param reciever
	 *        The reciver shape of the message shape.
	 * @param x
	 *        The x-coordinate of the start point of the message shape.
	 * @param y
	 *        The y-coordinate of the begin point of the message shape.
	 * @param secondX
	 *        The second x-coordinate of the end point of the message shape.
	 * @param secondY
	 *        The y-coordinate of the message shap's end point.
	 */
	public MessageShape(PartyShape sender, PartyShape reciever,int x, int y, int secondX, int secondY) {
	   this.setX(x);
	   this.setY(y);
	   this.setxSecond(secondX);
	   this.setySecond(secondY);
       this.setSenderShape(sender);
       this.setRecieverShape(reciever);
       this.setDialogBoxList(new ArrayList<>());
       this.setMessageText("");
	}
	
	
	/**
	 * A getter to get the shape of the message.
	 * @return
	 *       Returns the shape of the message.
	 */
	public Shape getMessageShape() {
		GeneralPath line = new GeneralPath();
		line.moveTo(this.getX(), this.getY());
		line.lineTo(this.getxSecond(), this.getySecond());
		line.moveTo(this.getxSecond()-this.getIndex()*10, this.getySecond()-5);
		line.lineTo(this.getxSecond(), this.getySecond());
		line.moveTo(this.getxSecond()-this.getIndex()*10, this.getySecond()+5);
		line.lineTo(this.getxSecond(), this.getySecond());
        line.closePath();
        if(this.isStroked())
        	return this.strokedLine(line);
		return line;
	}
	
	private Shape strokedLine(GeneralPath line) {
		float[] dashes = {8,8,8};
     	BasicStroke stroke = new BasicStroke(1, BasicStroke.CAP_SQUARE, BasicStroke.CAP_SQUARE, 10, dashes, 1);
		Shape shape = stroke.createStrokedShape(line);
     	return shape;
	}

	private int getIndex() {
		if(this.getX() > this.getxSecond())
			return -1;
		return 1;
	}

	/**
	 * A getter to get the x-coordinate of the message's shape.
	 * @return
	 *      Returns the x-coordinat of start point of message's shape.
	 */
	public int getX() {
		return x;
	}
	
	protected void setX(int x) {
		this.x = x;
	}
	
	/**
	 * A getter to get the y-coordinate of the message's shape.
	 * @return
	 *      Returns the y-coordinat of start point of message's shape.
	 */
	public int getY() {
		return y;
	}
	
	protected void setY(int y) {
		this.y = y;
	}
	/**
	 * A getter to get the y-coordinat eof the end point of the message's shape.
	 * @return
	 *        Returns the y-coordinate of the end point of the message's hape.
	 */
	public int getySecond() {
		return ySecond;
	}
	protected void setySecond(int ySecond) {
		this.ySecond = ySecond;
	}
	/**
	 * A getter to get the x-coordinate of the end point of the message's shape.
	 * @return
	 *        Returns the x-coordinate of teh end point of the message's shape.
	 */
	public int getxSecond() {
		return xSecond;
	}
	protected void setxSecond(int xSecond) {
		this.xSecond = xSecond;
	}


	/**
	 * A fucntion to update the second point of the message's shape.
	 * @param x2
	 *        The x-coordinate of the end point of the message's shape.
	 * @param y2
	 *        The y-coordinate of the end point of the messag's shape.
	 */
	public void updateSecondPoint(int x2, int y2) {
       this.setxSecond(x2);
       this.setySecond(y2);
	}

	/**
	 * A function to draw the graphics.
	 * @param g
	 *         The instance of the graphics with which shape will be drawn.
	 */
	public void draw(Graphics2D g) {
		if(this.getSourceMessage() instanceof ResultingMessage)
			this.setStroked(true);
		if(this.isSelected())
			g.setColor(Color.RED);
		else
		    g.setColor(Color.MAGENTA);
		g.draw(this.getMessageShape());
		g.setColor(Color.WHITE);
		this.drawLabel(g);
	}
	
	private boolean isSelected;

	

	


	/**
	 * A function which returns the shape of the party which recievers message.
	 * @return
	 *       Returns the shape of the party which recieves message.
	 */
	public PartyShape getRecieverShape() {
		return recieverShape;
	}


	
	protected void setRecieverShape(PartyShape recieverShape) {
		this.recieverShape = recieverShape;
	}

	/**
	 * A function which returns the shape of the party which sends message.
	 * @return
	 *       Returns the shape of the party which sends message.
	 */
	public PartyShape getSenderShape() {
		return senderShape;
	}


	/**
	 * A setter to set the sender party's shape.
	 * @param senderShape
	 *        The shape of the sender party.
	 */
	protected void setSenderShape(PartyShape senderShape) {
		this.senderShape = senderShape;
	}


	protected boolean isStroked() {
		return stroked;
	}


	/**
	 * A setter to set the shape stroked or not.
	 * @param stroked
	 *        The new boolean value which indicates the state of the shape.
	 */
	public void setStroked(boolean stroked) {
		this.stroked = stroked;
	}


	private boolean stroked;
	
	/**
	 * A checker to check if message's label intersects the point.
	 * @param x
	 *        The x-coordinate of the point.
	 * @param y
	 *       The y-coordinate of the point.
	 * @return
	 *        Returns true if the label intersects the given point.
	 */
	public boolean hasLabelPlaceIntersecting(int x, int y) {
			return ((x >= this.getLabelX() && (x <= this.getLabelX() + 20)) && (y >=this.getLabelY()-10 && (y <= this.getLabelY())));
	}


	private boolean doesIntersectsX(int x) {
		return (x >= this.getLabelX() && (x <= this.getLabelX() + 20));
	}


	private boolean doesIntersectsY(int y) {
		return (y >=this.getLabelY()) && (y <= this.getLabelY()+20);
	}
	
	
	
	/**
	 * A function to draw the graphics.
	 * @param g2
	 *        The instance of graphis with which shapes will be drawn.
	 */
	public void drawLabel(Graphics g2) {
		g2.setColor(this.getColor());
		if(this.getSourceMessage().isActive()) {
			this.drawLabelInActive(g2);
			
		}
		else
			this.drawInActiveLabel(g2);
        this.setLabelX(getMessageLabelX());
        this.setLabelY(getMessageLabelY());
        g2.setColor(Color.WHITE);
	}
	
	private void drawInActiveLabel(Graphics g2) {
		if(this.getNumberVisible())
	 	  g2.drawString("<<" +this.getSourceMessage().getMessageNumbers()+ this.getSourceMessage().getLabelText() + ">>", this.getMessageLabelX(), this.getMessageLabelY());		
		else
		 	  g2.drawString("<<" +this.getSourceMessage().getLabelText() + ">>", this.getMessageLabelX(), this.getMessageLabelY());		
	}


	private void drawLabelInActive(Graphics g2) {
  	       g2.setColor(Color.red);
          if(this.getNumberVisible()) {
  			g2.drawString("<<" + this.getSourceMessage().getMessageNumbers()+this.getSourceMessage().getLabelText() + "|"+ ">>", this.getMessageLabelX(), this.getMessageLabelY());
          }
          else
    			g2.drawString("<<" + this.getSourceMessage().getLabelText() + "|"+ ">>", this.getMessageLabelX(), this.getMessageLabelY());
	}


	private Color getColor() {
		return this.color;
	}

	/**
	 * A function to change the label text of the message' shape.
	 * @param keyChar
	 *        The new character which will be inserted.
	 */
	public void changeMessageText(char keyChar) {
		char [] temp = this.getMessageText().toCharArray();
		ArrayList<Character> list = new ArrayList<>();
		for(int i  = 0; i < temp.length; i++)
			list.add(temp[i]);
		list.add(keyChar);
		this.setMessageText(this.builStrngFromCharacter(list));
	}

	private String builStrngFromCharacter(ArrayList<Character> list) {
		StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < list.size();i++) {
        	stringBuilder.append(list.get(i));
        }
		return stringBuilder.toString();
	}

	private int labelX;
	private int labelY;
	private Color color;
	

	private int getMessageLabelY() {
		return this.getY()+(this.getySecond()-this.getY())/2;
	}




	private int getMessageLabelX() {
		return this.getX()+(this.getxSecond()-this.getX())/2;
	}


	
	protected int getLabelX() {
		return labelX;
	}


	protected void setLabelX(int labelX) {
		this.labelX = labelX;
	}


	protected int getLabelY() {
		return labelY;
	}


	protected void setLabelY(int labelY) {
		this.labelY = labelY;
	}


	protected void setColor(Color color) {
       this.color = color;		
	}


	protected boolean hasPoint(int x, int y) {
		int dxc = x - this.getX();
		int dyc = y - this.getY();
		int dxl = this.getxSecond() - this.getX();
		int dyl = this.getySecond() - this.getY();
		int cross = dxc * dyl - dyc * dxl;
	    return (cross > -300 && cross < 300);
	}


	/**
	 * A checker to check if the message's shape is selected.
	 * @return
	 *       Returns true if the message's shape is selected.
	 */
	public boolean isSelected() {
		return isSelected;
	}


	/**
	 * A setter to set the shape as selected.
	 * @param isSelected
	 *         The new boolean status of the message's shape.
	 */
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}


	/**
	 * A getter to get the text of the message's shape.
	 * @return
	 *       Returns the text of the message's shape.
	 */
	public String getMessageText() {
		return messageText;
	}


	/**
	 * A setter to set the text of the message's shape.
	 * @param messageText
	 *         The new text of the messag's shape.
	 */
	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}


	/**
	 * A function to  change the text of the messag's shape.
	 * @param newValue
	 *        The new value of the text.
	 */
	public void changeMessageText(String newValue) {
        this.setMessageText(newValue);		
	}


	private Message sourceMessage;
	
	protected void setSource(Message message) {
		this.sourceMessage = message;
	}


	protected Message getSourceMessage() {
		return sourceMessage;
	}


	/**
	 * A function to draw the dialogbox's shape.
	 * @return
	 *       Returns a newly created dialogbox.
	 */
	public DialogBox createDialogBox() {
		DialogBox box = new SimpleDialogBox(this.getX(),this.getY()+2,250,100);
		TextField field = new TextField(box.getX(),box.getY()+box.getTitleBarHeight()+15,80,15);
		field.setLabel("new arg");
		Button button = new SimpleButton(field.getX()+field.getWidth()+2,field.getY(),"Add");
		button.setDimension(40);
		box.addToChilds(button);
		button.setActive(true);
		box.addToChilds(field);
		this.AddActionListener(this.getSourceMessage(),field,button,box);
		ListBox listBox = new ListBox(button.getX()+button.getDimension()+2, button.getY(), field.getWidth()+2, field.getHeight()*5-2,"arguments");
		box.addToChilds(listBox);
		Button up = new SimpleButton(listBox.getX()+listBox.getWidth()+2,listBox.getY()-10,"up");
		up.setDimension(40);
		box.addToChilds(up);
		Button down = new SimpleButton(up.getX(),up.getY()+up.getDimension()/2+1,"down");
		down.setDimension(40);
		box.addToChilds(down);
		Button delete = new SimpleButton(down.getX(),down.getY()+down.getDimension()/2+1,"delete");
		delete.setDimension(40);
		box.addToChilds(delete);
		listBox.addListener(up);
		listBox.addListener(down);
		listBox.addListener(delete);
        this.addListenerToUpButton(up,this.getSourceMessage(),listBox);
		down.addActionListener(listBox);
		this.addDeleteButtonListener(delete,this.getSourceMessage(),listBox);
		TextField methodField = new TextField(field.getX(),field.getY()+field.getHeight()*2,80,15);
		methodField.setLabel("methodName");
        box.addToChilds(methodField);
        Button enter = new SimpleButton(button.getX(),methodField.getY(),"Enter");
        this.addEnterButtonActionListern(enter,methodField,this.getSourceMessage());
        enter.setDimension(40);
        enter.setActive(true);
        box.addToChilds(enter);
        this.getDialogBoxList().add(box);
        this.populateMessageBox(box);
		return box;
	}


	


	private void addListenerToUpButton(Button up, Message message,ListBox box) {
         	up.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
           		 if(box.getselectedField() != null)
           			 try {
               			 message.moveArgumentBackWar(box.getselectedField().getText());
           			 }catch(IllegalOperationExcetion exc) {
           				 System.out.println(exc.toString());
           			 }
				}
         	});
	}


	private void addDeleteButtonListener(Button delete, Message message, ListBox listBox) {
              delete.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if(listBox.getselectedField() != null) {
						TextField field = listBox.getselectedField();
                     message.removeArgument(field.getText());
                     listBox.setSelectedFieldAsNull(field);
					}
				}
            	  
              });		
	}


	private void addEnterButtonActionListern(Button enter, TextField methodField, Message message) {
          enter.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
                  message.enterMethodName(methodField.getText());				
			}
        	  
          });		
	}


	private void AddActionListener(Message message, TextField field, Button button, DialogBox box) {
		 button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(field.getIsActive()) {
					message.enterParameter(field.getText());
				}
				
			}
			 
		 });
	}

	private boolean visibleNumber;

	/**
	 * A function which set if the number should be set visible.
	 * @param b
	 *        The new boolean value of the message's state.
	 */
	public void setNumbersVisible(boolean b) {
		visibleNumber = b;
	}

	private boolean getNumberVisible() {
		return this.visibleNumber;
	}


	public ArrayList<DialogBox> getDialogBoxList() {
		return dialogBoxList;
	}


	public void setDialogBoxList(ArrayList<DialogBox> dialogBoxList) {
		this.dialogBoxList = dialogBoxList;
	}


	/**
	 * A function to populate MessageDialogBox with given parameters string.
	 * @param text
	 *        The tex of the parameters.
	 */
	public void populateDialogBox(String text) {
		char [] array = text.toCharArray();
		StringBuilder builder = new StringBuilder();
		for(DialogBox box : this.getDialogBoxList()) {
			for(int i = 0; i < array.length;i++) {
				   if(array[i] == ',') {
					   box.enterFields(builder.toString());
					   builder = new StringBuilder();
				   }
				   if(array[i] != ',')
				     builder = builder.append(array[i]);
			   }
			   box.enterFields(builder.toString());
		}
	}


	/**
	 * A function which clears all the field from the list.
	 */
	public void clearAllField() {
		this.getDialogBoxList().stream().forEach(e -> {
			e.getListBox().clearAllField();		
		});
		
	}


	/**
	 * A function which enters the method name in the dialogbox.
	 * @param methodName
	 *        The new method name.
	 */
	public void enterMethodName(String methodName) {
		this.getDialogBoxList().stream().forEach(e -> {
			e.enterMethodName(methodName);
		});
	}


	/**
	 * A function which populates a message box.
	 */
	public void populateMessageBox() {
		this.getDialogBoxList().stream().forEach(e -> {
		    this.populateMessageBox(e);		
		});
        this.setMessageText(this.getSourceMessage().getLabelText());
	}
	
	private void populateMessageBox(DialogBox box) {
		this.clearAllField();
        this.populateDialogBoxParameter(this.getSourceMessage().getParametersList());
        this.populateDialogBoxMethodName(this.getSourceMessage().getMethodName());
	}
	
	
	/**
	 * A function to populate dialog's boxes parameters field.
	 * @param message
	 *        The shape of the message whom's dialogbox will be populated.
	 * @param text
	 *        The given text with which dialog box will be populated.
	 */
	private void populateDialogBoxParameter(String text) {
       	this.populateDialogBox(text);
	}
	

	/**
	 * Populate's dialogbox's methodField.
	 * @param message
	 *        The shape of the message whom's dialogbox will be populated.
	 * @param methodName
	 *       The method name which will be written to field of methodName field.
	 */
	private void populateDialogBoxMethodName(String methodName) {
		this.enterMethodName(methodName);
	}

}
