package diagramViews;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.stream.Collectors;

import Exceptions.IllegalOperationExcetion;
import box.DialogBox;
import box.DialogBoxElement;
import box.ListBox;
import box.SimpleDialogBox;
import box.TextField;
import button.Button;
import button.SimpleButton;
import domainObjects.Interaction;
import domainObjects.InvocationMessage;
import domainObjects.Message;
import domainObjects.Party;
import domainObjects.ResultingMessage;
import shapes.MessageShape;
import shapes.PartyShape;


/**
 * A class representing the Diagram view.
 * @author Afraz Salim
 *
 */
public abstract class DiagramView extends ObjectShape implements ActionListener{

	
	Random random;
	/**
	 * A private arraylist to store the party list.
	 */
	private ArrayList<Party> partyList;
	
	private HashMap<MessageShape,Message> shapeMessageMap;
	
	private HashMap<MessageShape,DialogBox> messageToDialog;
	
	

	/**
	 * A private hashMap to store the partyShape with party.
	 */
	private HashMap<Party,PartyShape> associationList; 
	/**
	 * A private ArrayList which stores the party shape.
	 */
	private ArrayList<PartyShape> shapeList;

	/**
	 * A private hashmap to store an invocation message with resulting messagE.
	 */
	private static HashMap<Message,Message> messageAssociationMap = new HashMap<>();
	
	private HashMap<PartyShape, DialogBox> partyDialogMap;

	
	private HashMap<String, ActionPerformedEventEnum> stringEnum;
	
	
	/**
	 * A constructor to initialize the data in diagram view.
	 * @param view
	 *        The given view of the diagram.
	 * @param interaction
	 *        The given controller of the diagram.
	 * @param x
	 *        The given x-coordinate of the diagram.
	 * @param y
	 *        The given y-coordinate of the diagram.
	 * @param width
	 *        The given width of the diagram.
	 * @param height
	 *        The given height of the diagram.
	 */
	public DiagramView(View view, Interaction interaction, int x, int y, int width,int height) {
	         super(interaction,view,x,y,width,height);
	         random = new Random();
	         this.setMessageShapeMap(new HashMap<>());
	         this.setShapeMessageMap(new HashMap<>());
	         this.setPartyList(new ArrayList<>());
	         this.setAssociationList(new HashMap<>());
	         this.setShapeList(new ArrayList<>());
	         this.setDialogBoxList(new ArrayList<>());
	         this.getInteraction().subscribe(this);
	         this.setPartyDialogMap(new HashMap<>());
	         this.setStringEnum(new HashMap<>());
	         this.setDomainEventMap(new HashMap<>());
	         this.setMessageToDialog(new HashMap<>());
	         this.setDialogBoxToMessage(new HashMap<>());
	         this.setUpStringEnumMap();
	}
	
	private void setUpStringEnumMap() {
      this.addToStringEnumMap("x", ActionPerformedEventEnum.X);
      this.addToDomainEventMap("MessageCreated", DomainEventEnum.MessageCreated);
      this.addToDomainEventMap("CreatedParty", DomainEventEnum.CreatedParty);
      this.addToDomainEventMap("RemoveMessage", DomainEventEnum.RemoveMessage);
      this.addToDomainEventMap("RemoveParty", DomainEventEnum.RemoveParty);
      this.addToDomainEventMap("ConvertedParty", DomainEventEnum.ConvertedParty);
      this.addToDomainEventMap("MessageFinished", DomainEventEnum.MessageFinished);
      this.addToDomainEventMap("LabelVerified", DomainEventEnum.LabelVerified);
      this.addToDomainEventMap("labelEnabled", DomainEventEnum.LabelEnabled);
      this.addToDomainEventMap("MessageLabelVerified", DomainEventEnum.MessagelabelVerfied);
      this.addToDomainEventMap("MessageLabelEdited",DomainEventEnum.MessageLabelEdited);
	}
	
	
	private HashMap<DialogBox,Message> dialogBoxToMessage;


	private void addToDomainEventMap(String string, DomainEventEnum event) {
          this.getDomainEventMap().put(string,event);		
	}

	private void addToStringEnumMap(String string, ActionPerformedEventEnum add) {
      this.getStringEnum().put(string,add);		
	}

	/**
	 * A function which acts according to the event recieved.
	 * @param evt
	 *        A parameter which contains a specific event.
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		this.getValue(evt.getPropertyName()).performAction(this,evt.getSource(), evt.getOldValue(), evt.getNewValue());		
	}

	
	private DomainEventEnum getValue(String value) {
		if(this.getDomainEventMap().get(value) == null)
			return DomainEventEnum.NullEvent; //NullEvent for allowing polymorphism
		return this.getDomainEventMap().get(value);
		
	}
	
	protected void updateMessageShapeText() {
     this.getPartyList().stream().forEach(e -> {
    	 for(Message message : e.getSenderList()) {
    		 this.getMessageShapeMap().get(message).setMessageText(message.getLabelText());
    	 }
     });		
	}

	/**
	 * A checker to check if the give position is a valid position to create a party object.
	 */
	protected boolean isValidPositionToCreateObject(int x, int y) {
		if(x + this.getActorShapeWidth() > this.getX()+ this.getWidht())
			return false;
		if(y + this.getActorHeight() > this.getY()+this.getHeight())
			return false;
		 if(!(this.contains(x, y)))
	        	return false;
	        for(PartyShape shape : this.getShapeList()) {
	        	if(shape.contains(x, y) ||shape.getLine().hasAnyIntersectingMessage(x, y) != null || shape.getLabel().contains(x, y) || shape.getLine().contains(x, y) || shape.getLine().getIntersectingLabel(x, y) != null || shape.hasAnyIntersectingDialogBox(x,y) != null)
	        		return false;
	        }
		return true;
		
	}

	
	protected abstract int getActorHeight();
	protected abstract int getActorShapeWidth();

	protected void createMessageShape(Party source,Message newValue) {
	  newValue.subscribe(this);
      PartyShape shape = this.getAssociationList().get(source);
      MessageShape messageShape =   shape.getLine().createMessageShape(shape,null,newValue,this.getCurrentX(),getPreviousY(),getPreviousX(),getPreviousY());
	  this.getMessageShapeMap().put(newValue, messageShape);
	  this.getShapeMessageMap().put(messageShape, newValue);
	  this.setMessageUnderConstruction((Message) newValue);
	}

	/**
	 * Sets the label of the party off after being verified.
	 * @param evt
	 *        The event which contains the new and old text.
	 */
	protected void setLabelOff(Party party,String text) {
        PartyShape shape = this.getAssociationList().get(party);
        this.updateLabelText(party,text);
        shape.setLabelActive(false);     
        shape.updateAllDialogBox();
	}

	
	/**
	 * A function which updates the text of the label.
	 * @param evt
	 *        An event parameter which contains text of label.
	 */
	private void updateLabelText(Party party,String text) {
         PartyShape shape = this.getAssociationList().get(party);
         shape.getLabel().setText(text);
	}

	/**
	 * A function which enabled a label.
	 * @param evt
	 *        A parameter which contains the boolean value for activating and deactivatng the shape.
	 */
	protected void enabledLabel(Party newValue,boolean value) {
		PartyShape shape = this.getAssociationList().get(newValue);
		shape.setLabelActive(value);
	}

	/**
	 * A function to create the shape of the party.
	 * @param evt
	 *        An event which ontains the newly created party.
	 */
	protected void createPartyShape(Interaction interaction ,Party party) {
		if(!(this.hasAnyShapeWithSameSource(party))) {
			 party.subscribe(this);
			 this.getPartyList().add(party);
		     this.createShape(party,this.getX()+getObjectPositionX(), this.getY()+getObjectPositionY(),null);
		}
	}

	
	/**
	 * A checker to check if there is alread a partyShape with same party as source.
	 * @param evt
	 *        An event which contains the newly created party.
	 * @return
	 *        Returns true if there is no party with same partyShape.
	 */
	private boolean hasAnyShapeWithSameSource(Party party) {
		for(PartyShape shape : this.getShapeList()) {
			  if(shape.getSource().equals(party)) 
                 return true;
		}
		 return false;
	}

	/**
	 * A function to remove the message.
	 * @param evt
	 *        An evt parameter which contains the message value to be removed.
	 */
	protected void removeMessage(Party source,Message oldValue) {
       MessageShape messageShape = this.getMessageShapeMap().get(oldValue);
       PartyShape shape = this.getAssociationList().get(source);
       shape.getLine().removeMessage(messageShape);
       this.getMessageShapeMap().remove(oldValue);
       this.getShapeMessageMap().remove(messageShape);
       this.setMessageUnderConstruction(null);
	}
	

	/**
	 * A functionw which removes the party.
	 * @param evt
	 *       A parameter which contains the party to be removed.
	 */
	protected  void removePartyShape(Party old) {
       PartyShape shape = this.getAssociationList().get(old);
       this.getPartyList().remove(old);
       this.getShapeList().remove(shape);
       this.getAssociationList().remove(old);
	}


	/**
	 * A function to finish the message creation process.
	 * @param evt
	 *        A parameter which contains the value of the message.
	 */
	protected void finishMessageCreation(Party reciever,Party source) {
		if(source.equals(this.getMessageUnderConstruction().getSender())) {
		PartyShape shape = this.getAssociationList().get(reciever);
		shape.getLine().finishMessage(shape,this.getMessageShapeMap().get(getMessageUnderConstruction()),shape.getLine().getX(),shape.getLine().getY()+getPreviousY());
		  if(getMessageUnderConstruction() instanceof InvocationMessage) {
	   			this.createResultingMessage();	         
		  }
		}
		this.getShapeList().stream().forEach(e ->{
			e.move(e.getX(), e.getY());
		});
	}
	
	
	
	

	
	/**
	 * A function to convert the party's type.
	 * @param evt
	 *        An event which contains the converted party's new value and old value in terms of parties.
	 */
	protected void convertPartyType(Party oldValue,Party party) {
      PartyShape old = this.getAssociationList().get(oldValue);
      this.getPartyList().remove(oldValue);
      this.getAssociationList().remove(oldValue);
      if(old != null) {
      party.subscribe(this);
      this.getPartyList().add(party);
      this.createShape(party, old.getX(), old.getY(), old);
      this.getShapeList().remove(old);
      }
	}


	
	/**
	 * A private variable to store the previous x-clicked.
	 */
	private static int previousX;
	/**
	 * A private variable to store the previous y-coordinate.
	 */
	private static int previousY;
	/**
	 * A function which performs the mouse action.
	 */
	protected void performMouseAction(int id, int x, int y, int clickCount) {
		this.setCurrentX(x);
		if(this.canBlockEvent()) {}
		else {
			this.getCickEvent(id,clickCount).performAction(this, x, y);
		}
          setPreviousX(x);
          setPreviousY(y);
	}
	
	

	private int currentX;
	
	private int getCurrentX() {
		return this.currentX;
	}
	
	private void setCurrentX(int x) {
        this.currentX =x;		
	}

	private boolean canBlockEvent() {
		if(getMessageUnderConstruction() == null) {
		if(this.getPartyWithLabelEnabled() != null)
			return true;
		if(this.hasAnyPartyEnabledMessage())
			return true;
		}
		return false;
	}

	private boolean hasAnyPartyEnabledMessage() {
		for(Party party : this.getPartyList()) {
			if(party.hasAnyActiveMessage())
				return true;
		}
		return false;
	}

	protected boolean canDeSelectedBox(int x, int y) {
		 if(this.getSelectedBox() == null)
			 return false;
		 if(this.getSelectedBox().contains(x, y))
			 return false;
		return true;
	}

	protected void deSelectBox() {
		this.getSelectedBox().setActive(false);
		this.setSelectedBox(null);
	}

	
	

	
	private DiagramEnum getCickEvent(int id, int clickCount) {
       if(id == MouseEvent.MOUSE_CLICKED && clickCount == 1)
    	   return DiagramEnum.Clicked;
       if((id == MouseEvent.MOUSE_CLICKED ||id == MouseEvent.MOUSE_RELEASED) && clickCount == 2)
    	   return DiagramEnum.doubleClicked;
       if (id == MouseEvent.MOUSE_DRAGGED)
    	   return DiagramEnum.dragged;
       if(id == MouseEvent.MOUSE_RELEASED)
    	   return DiagramEnum.released;
	return DiagramEnum.DummyValue;
	}

	private DialogBox getDialogBoxIntersects(int x, int y) {
		for(DialogBox box : this.getDialogBoxList()) {
			if(box.intersects(x,y))
				return box;
		}
		return null;
	}

	/**
	 * A function which acts when the mouse is released.
	 * @param x
	 *        The x-coordinate on which the mouse is released.
	 * @param y
	 *        The y-coordinate on which the mouse is released.
	 */
	protected void mouseReleased(int x, int y) {
        for(PartyShape shape :this.getShapeList()) {
        	shape.releasedButtion(x, y);
        }
        if(getMessageUnderConstruction() != null) {
        	getMessageUnderConstruction().getSender().finishMessageCreationProcess(null);
            this.setMessageUnderConstruction(null);
        }
	}


	/**
	 * A function to resize the diagram.
	 */
	@Override
	protected void resize(int width, int height) {
		
	}






	/**
	 * A setter to set the message label active.
	 * @param message
	 *        The message whose label gets activated.
	 */
	public void setMessageLabelActive(Message message) {
       message.setlabelEnabled();
	}
	

	
	
	
	private int getRandomNumber() {
	    return random.nextInt((1 - 0) + 1) + 0;
	}


	protected void createNewObject(int x, int y) {
 		if(this.getRandomNumber() == 0)
 			this.createActorObject(x,y);
 		else
 			this.createPartyObject(x,y);
	}



	private static int objectPositionX;
	private static int objectPositionY;
	private void createActorObject(int x, int y) {
		setObjectPositionX(x-this.getX());
		setObjectPositionY(y-this.getY());
		try {
			this.getInteraction().createNewActor("");
		}catch(IllegalOperationExcetion exc) {
			System.out.println("Exception : " + exc.toString());
		}
	}
	
	private Shape getShape() {
		return new Rectangle2D.Double(this.getX(),this.getY(),this.getWidht(),this.getHeight());
	}

	protected void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(Color.BLACK);
		g2.draw(this.getShape());
		g2.fill(getShape());
		g2.setColor(Color.WHITE);
		this.getDialogBoxList().stream().forEach(e -> e.draw(g2));
			 for(PartyShape shape : this.getShapeList()) {
				    shape.draw(g2);		           
		        }
			 if(this.getSelectedBox() != null)
	             this.getSelectedBox().draw(g2);
	   }
	
	protected void createNewCoordinates(Message message) {
		PartyShape sender = this.getAssociationList().get(message.getSender());
		PartyShape reciever = this.getAssociationList().get(message.getReciever());
		int firstY = random.nextInt(((sender.getLine().getY()+sender.getLine().getHeight()-15) - (sender.getLine().getY()+15)) + (sender.getLine().getY()+15));
		int secondY = random.nextInt(((reciever.getLine().getY()+reciever.getLine().getHeight()-15) - (reciever.getLine().getY()+15)) + (reciever.getLine().getY()+15));
	}


	
	private void createPartyObject(int x, int y) {
	   setObjectPositionX(x-this.getX());
	   setObjectPositionY(y-this.getY());
	   try {
		   this.getInteraction().createNewPartyObject("");
	   }catch(IllegalOperationExcetion exc) {
		   System.out.println("Error occured at" + exc.toString());
	   }
	}


	

	private DialogBox selectedBox;


	private int previousKeyCode;
	
	
	/**
	 * A boolean checker to check if a messagedialogbox can be created.
	 * @return
	 *       Returns true if a message dialogbox can be created.
	 */
	public boolean canCreateMessageDialogBox() {
		if(this.getSelectedBox() != null)
			return false;
		if(this.getPreviousKeyCode() != 17)
			return false;
		if(this.getSelectedShape() != null)
			return false;
		if(this.hasAnyPartySelectedMessage() == null)
			return false;
		return true;
	}

	protected void handleKeyEvent(int id, int keyCode, char keyChar) {
		if(this.getSelectedBox() != null)
			this.getSelectedBox().performKey(id, keyCode, keyChar);
		else 
			this.getKeyEvent(keyCode,keyChar).performAction(this,keyChar);
		this.setPreviousKeyCode(keyCode);
}
	protected void verifyMessageLabel() {
		this.verifyMessageLabel(this.messageWithLabelActive());
	}

	protected void verifyPartyLabel() {
		this.verifyLabel(getPartyWithLabelEnabled());
	}



	
	

	protected boolean canCreatePartyDialogBox() {
		return (this.getPreviousKeyCode() == 17 && this.getSelectedShape() != null);
	}

	

	private KeyEventDiagram getKeyEvent(int keyCode, char keyChar) {
          if(keyCode == 10)
        	  return KeyEventDiagram.Enter;
          else if((Character.isAlphabetic(keyChar) ||keyChar == ':') && this.getPartyWithLabelEnabled() != null)
        	  return KeyEventDiagram.EnterTextParty;
          else if(keyCode == 127)
        	  return KeyEventDiagram.RemoveParty;
          else if(keyCode == 8)
        	  return KeyEventDiagram.RemoveText;
          else if(this.isValidChracterForMessage(keyChar))
        	  return KeyEventDiagram.EnterMessageText;
		return KeyEventDiagram.DummyValue;
	}

	
	
	private boolean isValidChracterForMessage(char keyChar) {
		return this.messageWithLabelActive() != null;
	}

	protected PartyShape hasAnyPartySelectedMessage() {
		for(PartyShape shape : this.getShapeList()) {
			if(shape.getSelectedMessage() != null)
				return shape;
		}
		return null;
	}

	protected void createDialogBoxForParty() {
		assert(this.getSelectedShape() != null);
		this.getDialogBoxList().add(this.getSelectedShape().createNewDialogBox());		
	}

	

	/**
	 * Removes the last character from the message by calling the operations from domain.
	 * @param message
	 *        A message with an active label.
	 */
	public void removeMessageLabelText(Message message) {
		try {
			message.removeText();		
		}catch(IllegalOperationExcetion exc) {
			System.out.println(exc.toString());
		}
	}

	
	
	private void verifyMessageLabel(Message message) {
        message.verifyText();
	}

	
	
	
	/**
	 * A function which enters message's label by calling the controller.
	 * @param message
	 *        A message which has active label.
	 * @param keyChar
	 *        The character which needs to be inserted.
	 */
	public void enterMessageLabelText(Message message, char keyChar) {
		this.getMessageShapeMap().get(message).changeMessageText(keyChar);
		message.enterText(this.getMessageShapeMap().get(message).getMessageText());		
	}
	
	

	protected Message messageWithLabelActive() {
		for(Party party : this.getPartyList()) {
			for(Message message  : party.getSenderList())
				if(message.isActive())
					return message;
		}
		return null;
	}

	private void removeParty(Party party) {
      this.getInteraction().removeParty(party);	
	}

	private void removeText(Party party) {
       PartyShape shape = this.getAssociationList().get(party);
       shape.removeText();
	}

	private void verifyLabel(Party party) {
		PartyShape shape = this.getAssociationList().get(party);
		party.setLabelText(shape.getLabel().getText());
		party.verifyText();
	}

	private void enterLabel(char keyChar, Party party) {
		if(Character.isAlphabetic(keyChar) ||keyChar == ':') {
		PartyShape shape = this.getAssociationList().get(party);
		shape.getLabel().setText(shape.getLabel().getText()+ Character.toString(keyChar));
		}
	}

	protected Party getPartyWithLabelEnabled() {
        for(PartyShape party : this.getShapeList())
        	if(party.getHasActiveLabel())
        		return party.getSource();
		return null;
	}

	protected ArrayList<Party> getPartyList() {
		return partyList;
	}



	protected void setPartyList(ArrayList<Party> partyList) {
		this.partyList = partyList;
	}



	protected HashMap<Party,PartyShape> getAssociationList() {
		return associationList;
	}



	protected void setAssociationList(HashMap<Party,PartyShape> associationList) {
		this.associationList = associationList;
	}




	protected abstract void createShape(Party party, int x, int y, PartyShape shape);




	protected abstract boolean canCreate(int x, int y);




	/**
	 * Moves the party to the given point.
	 * @param x
	 *        The x-coordinate of the point.
	 * @param y
	 *        The y-coordinate of the point.
	 * @param source
	 *        Party which will be moved.
	 */
	public abstract void move(int x, int y, Party source);




	/**
	 * A getter to get the list of all the shapes.
	 * @return
	 *       Returns the list of all the shapes.
	 */
	public ArrayList<PartyShape> getShapeList() {
		return shapeList;
	}




	private void setShapeList(ArrayList<PartyShape> shapeList) {
		this.shapeList = shapeList;
	}


	/**
	 * A function to convert the party's type.
	 * @param x
	 *        The x-coordinate which needs to be converted.
	 * @param y
	 *        The y-coordinate which needs to be converted.
	 * @param source
	 *        The given party which will be converted.
	 */
	public void convertPartyType(int x, int y, Party source) {
		System.out.println("Asked for conversion");
		this.getInteraction().convertParty(source);
	}

	/**
	 * A getter to get the previous x-coordinate.
	 * @return
	 *       Returns the previously click x-coordinate.
	 */
	public static int getPreviousX() {
		return previousX;
	}

	protected void setPreviousX(int x) {
		previousX = x;
	}
	
	private int messageUnderConsX;
	private  int messageUnderConsY;
	private Message messageUnderConstruction;

	
	

	/**
	 * A getter to get y-coordinate of the message.
	 * @return
	 *       Returns the y-coordinate of the message which is under construction.
	 */
	protected int getMessageUnderConsY() {
		return messageUnderConsY;
	}

	

	/**
	 * A getter to get the message coordinate which is under construction.
	 * @return
	 *       Returns the x-coordinate of the message which is under construction.
	 */
	protected int getMessageUnderConsX() {
		return messageUnderConsX;
	}

	
	/**
	 * A geter to get the message which is under construction.
	 * @return
	 *       Returns the message under construction.
	 */
	protected  Message getMessageUnderConstruction() {
		return messageUnderConstruction;
	}

	/**
	 * A setter to set the message which is under construction.
	 * @param message
	 *        The message which is under construction.
	 */     
	protected void setMessageUnderConstruction(Message message) {
		messageUnderConstruction = message;
	}

	


	/**
	 * Adjusts the parameter of the diagram and all the parties in it.
	 * @param map
	 *        A map containing the Shap for each party.
	 */
	public void adjustParameter(HashMap<Party, PartyShape> map) {
		for(Party party : this.getPartyList()) {
			PartyShape shape = map.get(party);
			party.subscribe(this);
			if(shape != null) {
            int x = shape.getX() - shape.getDiagram().getX();
            int y = shape.getY() - shape.getDiagram().getY();
			this.createShape(party, this.getX()+x, this.getY()+y, null);
			}
		}
			this.createCopyMessage(map);
	}
	

	/**
	 * Ends the message creation process.
	 * @param shape
	 *        Shape of the party which detects a mouse release on it's lifeline and ends message.
	 * @param x
	 *        The x-coordinate of the shape's lifeline.
	 * @param y
	 *        The y-coordinate of the shape's lifeline.
	 */
	public void endMessageProcess(PartyShape shape, int x, int y) {
		 this.setPreviousX(x-shape.getX());
		 setPreviousY(y-shape.getLine().getY());
         if(getMessageUnderConstruction() != null) {
           if(shape != null) {
        	   getMessageUnderConstruction().getSender().finishMessageCreationProcess(shape.getSource());
           }
         }
	}
	

	
	

	/**
	 * A function which creates a resulting message by calling the controller.
	 * @param shape
	 *        The shape of the party which creates the message.
	 * @param message
	 *        The message for which a resulting message is created.
	 * @return
	 *       Returns a new resulting message.
	 */
	private void createResultingMessage() {
		  PartyShape senderShape = this.getAssociationList().get(getMessageUnderConstruction().getSender());
		  PartyShape reciever = this.getAssociationList().get(getMessageUnderConstruction().getReciever());
		  MessageShape invocationShape = this.getMessageShapeMap().get(getMessageUnderConstruction());
		  Message message = reciever.getSource().createResultingMessage(reciever.getSource(), senderShape.getSource(), "");
          MessageShape resultingShape = reciever.getLine().createMessageShape(reciever, senderShape, message,invocationShape.getxSecond(), invocationShape.getySecond()+8,getPreviousX(),getPreviousY());
          resultingShape.setStroked(true);
          senderShape.getLine().finishMessage(senderShape, resultingShape, invocationShape.getX(),invocationShape.getY()+8); 
          this.getMessageShapeMap().put(message, resultingShape);
          this.getShapeMessageMap().put(resultingShape, message);
          getMessageAssociationMap().put(message,getMessageUnderConstruction());
          this.setMessageUnderConstruction(null);
	}

	

	/**
	 * A getter to get the previous y-coordinate.
	 * @return
	 *        Returns the previous y-coordinates.
	 */
	public static int getPreviousY() {
		return previousY;
	}

	/**
	 * A setter to set the previous y-cordinates.
	 * @param previousY
	 *        The previous y-coordinate where mouse was clicked.
	 */
	protected static void setPreviousY(int y) {
		previousY = y;
	}
	

	/**
	 * A function which moves the party's shape at the given points.
	 */
	@Override
	public void move(int x, int y) {
		this.getShapeList().stream().forEach(e -> e.move(x,this.getX(),y,this.getY()));
		this.setX(x);
		this.setY(y);
	}
	
	

	
	/**
	 * A getter to get the to be created object's position.
	 * @return
	 *       Returns the position of the object which will be created.
	 */
	protected static int getObjectPositionX() {
		return objectPositionX;
	}

	/**
	 * A setter to set the objects positon.
	 * @param x
	 *        The new x-coordinate.
	 */
	protected static void setObjectPositionX(int x) {
		objectPositionX = x;
	}

	/**
	 * A getter to get the obejcts y-coordinate.
	 * @return
	 *       Returns the y-coordinate of the object.
	 */
	protected static int getObjectPositionY() {
		return objectPositionY;
	}

	/**
	 * A setter to set the object's positions.
	 * @param y
	 *        The y-coordinate on which the object will be created.
	 */
	protected static void setObjectPositionY(int y) {
		objectPositionY = y;
	}

	/**
	 * A function to create shapes for all of parties.
	 * @param map
	 *        The map which contains party shapes with each party.
	 */
	protected void createShapesForAllParties(HashMap<Party,PartyShape> map) {
         for(Party party : this.getPartyList()) {
        	 PartyShape shape = map.get(party);
        	 if(shape != null)
        	  this.createShape(party, shape.getX(), shape.getY(), null);
         }
         for(Party party : this.getPartyList()) {
        	 PartyShape shape = map.get(party);
        	 if(shape != null)
        	   this.transferMessage(party, shape);
			 this.editLabelByCopy(this.getAssociationList().get(party), shape);
         }
         this.getShapeList().stream().forEach(e -> e.move(e.getX(), e.getY()));
	}
	
	
	/**
	 * A function to transfer one party's messages to other party shape.
	 * @param party
	 *        The party from which messages will be transferred.
	 * @param shape
	 *        The party's shape to which messages will be transfered.
	 */
	protected abstract void transferMessage(Party party, PartyShape shape);


	/**
	 * A getter to get the messaga association.
	 * @return
	 *       Returns the message association map.
	 */
	protected static HashMap<Message,Message> getMessageAssociationMap() {
		return messageAssociationMap;
	}
	private HashMap<Message, MessageShape> messageShapeMap;


	/**
	 * A checker to check if the diagram can be resized.
	 * @param width
	 *        The new width of the diagram.
	 * @param height
	 *        The new height of the diagram.
	 * @return
	 *       Returns true if the diagram can be resized.
	 */
	public boolean canResize(int width, int height) {
		if(this.getSelectedBox() != null)
			return false;
		for(PartyShape shape : this.getShapeList()) {
			if(shape.getX()+shape.getWidth() > this.getX()+this.getWidht()+width)
				return false;
			if(shape.getLine().getY()+shape.getLine().getHeight() > this.getY()+this.getHeight()+height)
				return false;
		}
		return true;
	}

	/**
	 * A function to create the message.
	 * @param shape
	 *        Party's shape which creates a new message.
	 * @param x
	 *        The x-coordinate on which the message will be created.
	 * @param y
	 *        The y-coordiante on which message will be created.
	 */
	public void createMessage(PartyShape shape, int x, int y) {
		setPreviousX(x);
		setPreviousY(y);
        shape.getSource().createMessage("");
	}

	/**
	 * A getter to get the map which contains message and the shape of the messagE.
	 * @return
	 *        Returns the shape of the message associated with each message.
	 */
	protected HashMap<Message, MessageShape> getMessageShapeMap() {
		return messageShapeMap;
	}

	/**
	 * A setter to set the map of the message shap for each message.
	 * @param messageShapeMap
	 *        The new map with which the map will be initialized.
	 */
	protected void setMessageShapeMap(HashMap<Message, MessageShape> messageShapeMap) {
		this.messageShapeMap = messageShapeMap;
	}

	/**
	 * A getter to get the message shape's map.
	 * @return
	 *       Returns the map containing messageshape and their messageS.
	 */
	protected HashMap<MessageShape,Message> getShapeMessageMap() {
		return shapeMessageMap;
	}

	/**
	 * A setter to set the message shape of the message.
	 * @param shapeMessageMap
	 *        The new map with which message's shape will be initialized.
	 */
	protected void setShapeMessageMap(HashMap<MessageShape,Message> shapeMessageMap) {
		this.shapeMessageMap = shapeMessageMap;
	}

	/**
	 * A fucntion to create the copy of the message.
	 * @param map
	 *        The map which contians the shapes of the old message.
	 */
	protected abstract void createCopyMessage(HashMap<Party, PartyShape> map);

	/**
	 * A function to edit the label by copy.
	 * @param shape
	 *        The given shape from which messages will be copied.
	 * @param shapes
	 *        The shape to which message will be copied.
	 */
	protected abstract void editLabelByCopy(PartyShape shape, PartyShape shapes);

	
	
	/**
	 * A function to enabled the message's shape's label.
	 * @param shape
	 *        The shape of the message whoms label will be enabled.
	 */
	public void enableMessageLabel(MessageShape shape) {
		this.getShapeMessageMap().get(shape).setlabelEnabled();
	}

	/**
	 * A function to set all the shapes false.
	 */
	public void setAllSelectedFalse() {
       this.getShapeList().stream().forEach(e -> e.setSetIsSelected(false));		
	}
	

	/**
	 * A getter to get the selected shape.
	 * @return
	 *       Returns the selected shape .
	 */
	protected PartyShape getSelectedShape() {
		for(PartyShape shapes : this.getShapeList())
			if(shapes.isSetIsSelected())
				return shapes;
		return null;
	}

	/**
	 * A function which returns the previous keyCode.
	 * @return
	 *       Returns the previous keyCode.
	 */
	public int getPreviousKeyCode() {
		return previousKeyCode;
	}

	/**
	 * A function to set the previous keyCode.
	 * @param previousKeyCode
	 *        The previous keyCode which will be set.
	 */
	private void setPreviousKeyCode(int previousKeyCode) {
		this.previousKeyCode = previousKeyCode;
	}

	/**
	 * A private variable to store dialogBoxes.
	 */
	private ArrayList<DialogBox> dialogBoxList;
	/**
	 * A private variable to store the domain event.
	 */
	private HashMap<String, DomainEventEnum> domainEvent;
	
	/**
	 * A function to create the message dialog's box.
	 * @param message
	 *        The message shape for which dialogbox will be created.
	 */
	protected void createMessageDialogBox(MessageShape message) {
		DialogBox box = message.createDialogBox();
		this.getDialogBoxToMessage().put(box, this.getShapeMessageMap().get(message));
        box.getCloseButton().addActionListener(this);
		message.setSelected(false);	    
		this.getDialogBoxList().add(box);
	}

	/**
	 * Populate the dialogbox.
	 * @param box
	 *        The box which will be populated.
	 * @param message
	 *        Shape of the message whom's dialogbox will be populated.
	 */


	
/**
 * A function which triggers action performed event.
 */
	@Override
	public void actionPerformed(ActionEvent e) {
		this.getStringEnum().get(e.getActionCommand()).performAction(this, (DialogBoxElement) e.getSource());
	}




	/**
	 * A getter to get the list of the dialogbox.
	 * @return
	 *       Returns the a list containing dialogbox.
	 */
	public ArrayList<DialogBox> getDialogBoxList() {
		return dialogBoxList;
	}

	protected void setDialogBoxList(ArrayList<DialogBox> dialogBoxList) {
		this.dialogBoxList = dialogBoxList;
	}

	public DialogBox getSelectedBox() {
		return selectedBox;
	}

	public void setSelectedBox(DialogBox selectedBox) {
		this.selectedBox = selectedBox;
	}

	/**
	 * A getter to get the message shape which is active.
	 * @return
	 *       Returns a messageshape if it is active.
	 */
	public abstract MessageShape partyWithActiveMessage();

	private HashMap<PartyShape, DialogBox> getPartyDialogMap() {
		return partyDialogMap;
	}

	private void setPartyDialogMap(HashMap<PartyShape, DialogBox> partyDialogMap) {
		this.partyDialogMap = partyDialogMap;
	}

	protected boolean canActivateABox(int x, int y) {
        if(this.getDialogBoxIntersects(x, y) == null)
        	return false;
        if(this.getSelectedBox() != null)
        	return false;
		return this.getDialogBoxIntersects(x, y).canActivate(x,y);
	}

	protected void activateBox(int x, int y) {
		this.setSelectedBox(this.getDialogBoxIntersects(x, y));
		this.getSelectedBox().setActive(true);
	}

	protected boolean canDeselectShape(int x, int y) {
 		return (this.getSelectedShape() != null && !this.getSelectedShape().contains(x, y));
	}

	protected void deSelectShape() {
	 assert(this.getSelectedShape() != null);
   	 this.getSelectedShape().setSetIsSelected(false);
	}

	protected void forwarRequestToShapes(int x, int y) {
		   for(PartyShape shape : this.getShapeList())
	        	 shape.mouseClicked(x, y);		
	}

	protected void forwarRequestToShapeDoubleClick(int x, int y) {
		ArrayList<PartyShape> temp = new ArrayList<PartyShape>();
		temp.addAll(getShapeList());
       for(PartyShape shape : temp)
    	   shape.doubleClicked(x, y);
	}

	protected void forwarDragRequestToBox(int x, int y) {
		this.getSelectedBox().drag(x,y,getPreviousX(),getPreviousY());		
	}

	protected void updateMessageSecondPoint(int x, int y) {
		assert(this.getMessageUnderConstruction() != null);
		this.getMessageShapeMap().get(getMessageUnderConstruction()).updateSecondPoint(x, y);		
	}

	protected void forwardDragRequestToShapes(int x, int y) {
		for(PartyShape shape : this.getShapeList())
	        shape.drag(x, y);		
	}

	protected void createMessageBox() {
		assert(this.hasAnyPartySelectedMessage().getSelectedMessage() != null);
		createMessageDialogBox(this.hasAnyPartySelectedMessage().getSelectedMessage());
		this.hasAnyPartySelectedMessage().getSelectedMessage().setSelected(false);
		this.hasAnyPartySelectedMessage().setSelectedMessage(null);
	}

	protected boolean canForwardRequestToBox(int x, int y) {
		if(this.getSelectedBox() == null)
			return false;
		if(!this.getSelectedBox().contains(x, y))
			return false;
		return true;
	}

	private HashMap<String, ActionPerformedEventEnum> getStringEnum() {
		return stringEnum;
	}

	private void setStringEnum(HashMap<String, ActionPerformedEventEnum> stringEnum) {
		this.stringEnum = stringEnum;
	}

	
	private HashMap<String, DomainEventEnum> getDomainEventMap() {
		return this.domainEvent;
	}

	private void setDomainEventMap(HashMap<String, DomainEventEnum> stringEnum) {
		this.domainEvent = stringEnum;
	}


	

protected void createMessageShape(PartyShape shape, MessageShape message, PartyShape shapes) {
	Party sender = message.getSenderShape().getSource();
	PartyShape senderShape = this.getAssociationList().get(sender);
	MessageShape messageShape  =senderShape.getLine().createMessageShape(senderShape, null, shapes.getDiagram().getShapeMessageMap().get(message),this.getXCoordinateOfSender(senderShape,message,shapes),getYCoordinateOfSender(senderShape,message,shapes),getPreviousX(),getPreviousY());
	messageShape.updateSecondPoint((message.getxSecond()-message.getX())+messageShape.getX(), message.getySecond());
	PartyShape reciever = shapes.getDiagram().getAssociationList().get(message.getRecieverShape().getSource());
	PartyShape recieverShape = this.getAssociationList().get(reciever.getSource()) ;
    recieverShape.getLine().finishMessage(recieverShape,messageShape, this.getXCoordinateOfReciever(recieverShape,message,shapes),this.getYcoordinateOfReciver(recieverShape,message,shapes));
    Message mes  = shapes.getDiagram().getShapeMessageMap().get(message);
    this.getMessageShapeMap().put(mes, messageShape);
    this.getShapeMessageMap().put(messageShape, mes);
    if(this.getShapeMessageMap().get(messageShape) instanceof ResultingMessage)
    	messageShape.setStroked(true);
}
	

protected int getXCoordinateOfSender(PartyShape senderShape, MessageShape message, PartyShape shapes) {
	if(shapes.getDiagram().getShapeMessageMap().get(message) instanceof InvocationMessage) 
		return (senderShape.getLine().getX()+(message.getX()-message.getSenderShape().getLine().getX()));
	return this.getMessageShapeMap().get(getMessageAssociationMap().get(shapes.getDiagram().getShapeMessageMap().get(message))).getxSecond()+10;
}


protected int getXCoordinateOfReciever(PartyShape recieverShape, MessageShape message, PartyShape shapes) {
	if(shapes.getDiagram().getShapeMessageMap().get(message) instanceof InvocationMessage) {
	return (recieverShape.getLine().getX()+(message.getxSecond()-message.getRecieverShape().getLine().getX()));
	}
	return this.getMessageShapeMap().get(getMessageAssociationMap().get(shapes.getDiagram().getShapeMessageMap().get(message))).getX();
}


protected int getYcoordinateOfReciver(PartyShape recieverShape, MessageShape message, PartyShape shapes) {
	if(shapes.getDiagram().getShapeMessageMap().get(message) instanceof InvocationMessage) {
		return (recieverShape.getLine().getY()+(message.getySecond()-message.getRecieverShape().getLine().getY()));
	}
	return this.getMessageShapeMap().get(getMessageAssociationMap().get(shapes.getDiagram().getShapeMessageMap().get(message))).getY()+10;
}








protected int getYCoordinateOfSender(PartyShape senderShape, MessageShape message, PartyShape shapes) {
	if(shapes.getDiagram().getShapeMessageMap().get(message) instanceof InvocationMessage) {
		return  (senderShape.getY()+(message.getY()-message.getSenderShape().getLine().getY()));
	}
	return this.getMessageShapeMap().get(getMessageAssociationMap().get(shapes.getDiagram().getShapeMessageMap().get(message))).getySecond()+10;
}






       protected void enterMessageText() {
		Message message = this.getDialogBoxToMessage().get(this.getSelectedBox());
		assert(message != null);
		message.enterMethodName(this.getSelectedBox().getTextFieldWithMethodLabel().getText());		
	}

	
	protected void closeDialogBox(DialogBoxElement element) {
		ArrayList<DialogBox> temp = (ArrayList<DialogBox>) this.getDialogBoxList().stream().filter(button -> button.getChildList().contains(element)).collect(Collectors.toList());
		this.getDialogBoxList().removeAll(temp);
		DialogBoxElement.setSelectedElement(null);
		this.setSelectedBox(null);		
	}

	protected boolean canEditPartyLabel() {
		return getPartyWithLabelEnabled() != null;
	}

	protected void addTextToParty(char keyChar) {
		this.enterLabel(keyChar,getPartyWithLabelEnabled());
	}

	protected boolean canEditMessage() {
		return this.messageWithLabelActive() != null;
	}

	protected void addTextToMessage(char keyChar) {
		if(this.isValidCharacter(keyChar))
		this.enterMessageLabelText(this.messageWithLabelActive(),keyChar);		
	}

	protected boolean isValidCharacter(char keyChar) {
		return Character.isAlphabetic(keyChar) || keyChar == '_' || Character.isDigit(keyChar) || keyChar == '(' || keyChar == ')' || keyChar == ',';
	}

	protected void removeParty() {
		this.removeParty(getPartyWithLabelEnabled());
	}

	protected void removePartyLabelText() {
		this.removeText(getPartyWithLabelEnabled());		
	}

	protected void verifyMessageLabel(Message source, String newValue) {
          this.getMessageShapeMap().get(source).changeMessageText(newValue);
          this.populateBox(source);
	}

	private HashMap<MessageShape,DialogBox> getMessageToDialo() {
		return messageToDialog;
	}

	private void setMessageToDialog(HashMap<MessageShape,DialogBox> messageToDialog) {
		this.messageToDialog = messageToDialog;
	}

	/**
	 * A function whihc populated a message's diaogbox
	 * @param source
	 *        The source message whose dialog box will be populated.
	 */
	protected void populateBox(Message source) {
		this.getMessageShapeMap().get(source).populateMessageBox();
     
	}



   /**
    * A function which returns a map which maps from dialogbox to message.
    * @return
    *        Retuns the dialogbox of the message.
    */
	private HashMap<DialogBox, Message> getDialogBoxToMessage() {
		return dialogBoxToMessage;
	}

	private void setDialogBoxToMessage(HashMap<DialogBox, Message> dialogBoxToMessage) {
		this.dialogBoxToMessage = dialogBoxToMessage;
	}

	/**
	 * A function to forward request to dialogbox.
	 * @param x
	 *        The x-coordinate whihc will be forwarded.
	 * @param y
	 *        The y-coordinate which will be forwarded.
	 */
	protected void forwarRequestToDialogBox(int x, int y) {
      assert(this.getSelectedBox() != null);
      getSelectedBox().mouseClick(x, y);
	}

	/**
	 * A function with which diagram subscribes to all parties.
	 */
	protected void subscribeToAllParties() {
        this.getPartyList().stream().forEach(e -> {
        	e.subscribe(this);
        });		
	}

	/**
	 * A function to subscribe to all message.
	 */
	protected void subscribeToAllMessages() {
        this.getPartyList().stream().forEach(e -> {
        	e.getSenderList().stream().forEach(m -> {
        		m.subscribe(this);
        	});
        });		
	}

	/**
	 * A cheker to check if party can be deslected.
	 * @param x
	 *        The x-coordinate to check.
	 * @param y
	 *        The y-coordinate to check.
	 * @return
	 *        Returns true of the parytshape can be deselected.
	 */
	public boolean deSelectParty(int x, int y) {
		return this.getSelectedShape() == null && this.getPartyWithLabelEnabled() == null;
	}
	
	
	

}
