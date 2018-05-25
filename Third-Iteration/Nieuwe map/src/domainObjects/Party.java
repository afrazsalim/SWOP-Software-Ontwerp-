package domainObjects;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

import Exceptions.IllegalObjectException;
import Exceptions.IllegalOperationExcetion;


/**
 * 
 * @author Afraz Salim
 *
 */
public abstract class Party {
	
	
	/**
	 * Text of party label.
	 */
	private String labelText;
	
	
	private boolean hasActiveLabel;
	/**
	 * An arraylist to store characters of the label.
	 */
	private ArrayList<Character> labelList;
	/**
	 * An instance of Propertychange listener.
	 */
    private PropertyChangeSupport pcs;
    /**
     * An instance of window in which party resides.
     */
    private Interaction window;
    
    /**
     * List of messages which are sent.
     */
    private ArrayList<Message> senderList;
    /**
     * List of message which are received.
     */
    private ArrayList<Message> recieverList;
    /**
     * A list of Associated partie.
     **/
    private ArrayList<Party> assciationList;
    /**
     * A string which indicates that the label of the party has been changed and will be used by Propertychange event.
     */
    private static final String LabelEdited = "LabelEdited";
    /**
     * A string which indicates that the label has been verified.
     */
    private static final String labelVerified = "LabelVerified";

    /**
     * A string which indicates that a message has been created. 
     */
    private static final String MessageCreated = "MessageCreated";
    /**
     * A string indicates that message second point has been updated and it will be used by PropertyChange event.
     */
    private static final String MessageUpdated = "MessageUpdatedSecondPoint";
    /**
     * A string indicates that the message has been created.
     */
    private static final String MessageFinished = "MessageFinished";
    /**
     * A string which indicates that the message has been removed.
     */
    private static final String RemoveMessage = "RemoveMessage";


	
/**
 * A constructor to construct the party.
 * @param text
 *        The label text of the party.
 * @param interaction
 *        The window of the party in which party resides.
 * @throws
 *        Throws illegal object exception if the interaction is not a valid interaction.
 */
	protected Party(String text, Interaction interaction)  {
		if(interaction == null)
			throw new IllegalObjectException("Window is not valid, Unresolveable error");
		pcs = new PropertyChangeSupport(this);
		this.setSenderList(new ArrayList<>());
		this.setRecieverList(new ArrayList<>());
		this.setLabelList(new ArrayList<>());
		this.setLabelText(text);
		this.addToTheList(text);
		this.setWindow(interaction);		
	}

	
	





	/**
	 * Adds the text to label.
	 * @param text
	 *        The text to be added in the label text.
	 */
	private void addToTheList(String text) {
		if(text.length() >  0) {
		char[] arr = text.toCharArray();
		for(int i = 0; i < arr.length;i++)
         this.getLabelList().add(arr[i]);	
		}
	}




   




	/**
	 * @return 
	 *       Returns the label text.
	 */
	public String getLabelText() {
		return labelText;
	}



    /**
     * Sets the label text of the party.
     * @param labelText
     *        The new label text of the party will be equal to this label text.
     */
	public void setLabelText(String labelText) {
		this.labelText = labelText;
	}




    /**
     * A boolean setter to check whether the label is active.
     * @param b
     *        New boolean value of this checker will be equal to this parameter.
     */
	protected void setHasActiveLabel(boolean b) {
         this.hasActiveLabel = b;		
	}


	/**
	 * A checker to check whether a party has an active label.
	 * @return
	 *       Return true if and only if the label of the party is active.
	 */
	public boolean getHasActiveLabel() {
		return this.hasActiveLabel;
	}









	


	
	
	/**
	 * Sends the notification to the subscribers.
	 * @param text
	 *        Define the property name of the operation.
	 * @param oldValue
	 *        Returns the old value of the object whose value has been changed.
	 * @param newValue
	 *        Returns the new value of the object.
	 */
	private void sendNotifications(String text,Object oldValue, Object newValue) {
        PropertyChangeEvent event = new PropertyChangeEvent(this,text,oldValue,newValue);
        this.pcs.firePropertyChange(event);
	}



	

	/**
	 * Provides a function for all listener to subscribe for updates.
	 * @param listener
	 *        The new listener which will be added to subscribers list.
	 */
	public void subscribe(PropertyChangeListener listener) {
		if(listener == null) 
			throw new IllegalOperationExcetion("Null can't be subscribed for notifications, Unreslveable Error");
		this.pcs.addPropertyChangeListener(listener);
	}









	/**
	 * A list to store the characters of a label.
	 * @return
	 *       Returns the characters list of the label.
	 */
	private ArrayList<Character> getLabelList() {
		return labelList;
	}





	/**
	 * A setter to initialize the labellist of the party.
	 * @param labelList
	 *        The character list will be initialized with this instance.
	 */
	private void setLabelList(ArrayList<Character> labelList) {
		this.labelList = labelList;
	}





	/**
	 * A function to verify to text of the party's label.
	 */
	public void verifyText() {
		char [] list = this.getLabelText().toCharArray();
		ArrayList<Character> temp = new ArrayList<>();
		for(int i = 0; i <list.length;i++)
			temp.add(list[i]);
		this.setLabelList(temp);
      if(this.hasValidLabel(temp)) {
  		  this.sendNotifications(labelVerified, this.getLabelText(), this);
          this.setHasActiveLabel(false);
      }
	}



	





	

	/**
	 * Checks whether the given list contains a valid label
	 * @param list
	 *        A list which contains the characters of party's label.
	 * @return
	 *        Returns true if the list contains a valid label.
	 */
	private boolean hasValidLabel(ArrayList<Character> list) {
		if(list.size() < 1)
			return false;
		if(!list.contains(':'))
			return false;
		int index = list.indexOf(':');
		if(index >0 && !Character.isLowerCase(list.get(0)))
			return false;
		if(list.size() <= index+1)
			return false;
		if(!Character.isUpperCase(list.get(index+1)))
			return false;
		return true;
	}


	








	/**
	 * A getter to get the interaction of the party.
	 * @return 
	 *        Returns the interaction of the party.
	 */
	private Interaction getWindow() {
		return window;
	}




    /**
     * A setter to set the interaction of the party.
     * @param window
     *        The interaction of the party.
     */
	private void setWindow(Interaction window) {
		this.window = window;
	}





	/**
	 * Stores the instance of the message under construction.
	 */
	private Message messageUnderInvocation;



	/**
	 * Creates a new Message with given text.
	 * @param text
	 *        The text of the message which will be created.
	 */
	public void createMessage(String text) {
		Message message = new InvocationMessage(this,text);
		this.setMessageUnderInvocation(message);
		this.getSenderList().add(message);
		this.sendNotifications(MessageCreated, null, message);
	}
	
	
	/**
	 * Creates a resulting message.
	 * @param sender
	 *        The sender party of the message.
	 * @param reciever
	 *        The recieve party of the message.
	 * @param text
	 *        The text of the resulting message.
	 * @return
	 *        Returns an instance of newly created Resulting message.
	 * @throws IllegalOperationExcetion
	 *        Throw IllegalOperationExcetion if the sender party is null. 
	 */       
	public Message createResultingMessage(Party sender, Party reciever, String text) throws IllegalOperationExcetion {
		if(sender == null)
			 throw new IllegalOperationExcetion("Send can't be null");
		if(reciever == null)
			throw new IllegalOperationExcetion("Reciever of a message can't be null");
		Message message = new ResultingMessage(this,text);
		message.setSender(this);
		message.setReciever(reciever);
		this.setMessageUnderInvocation(message);
		this.getSenderList().add(message);
		reciever.getRecieverList().add(message);
		return message;
	}

	

	



	


	/**
	 * Finishes the message creation process by adding a reciever to the message which is underconstruction.
	 * @param reciever
	 *        The reciever of the message.
	 *        
	 *        Catches the IllegalOperationException if the reciever is not a valid reciever and removes the message from sender list.
	 */
	public void finishMessageCreationProcess(Party reciever) {
		try {
			this.recieveMessage(reciever);
		}catch(IllegalOperationExcetion exc) {
			System.out.println(exc.toString() + "All the operations executed so far, will be undone"); 
			this.sendNotifications(RemoveMessage, this.getMessageUnderConstruction(), null);
			this.getSenderList().remove(this.getMessageUnderConstruction());
			Message.reducNumber();
			this.setMessageUnderInvocation(null);
		}
		
	}
	



	/**
	 * Recieves the message
	 * @param reciever
	 *        The reciever of the message.
	 * @throws
	 *        If reciever is null then an IllegalOperationException will be thrown.
	 * @throws  
	 *        If sender of the message is equal to the reciever of the message then an IllegalOperationException will be thrown.
	 */
	private void recieveMessage(Party reciever) {
		if(reciever == null)
			throw new IllegalOperationExcetion("Reciever was null, Message will be deleted again");
		if(this.getMessageUnderConstruction().getSender().equals(reciever))
			throw new IllegalOperationExcetion("Sending a message to same party is not supported ");
       	this.sendNotifications(MessageUpdated,null,this.getMessageUnderConstruction());
       	this.getMessageUnderConstruction().setReciever(reciever);
       	reciever.recieve(this.getMessageUnderConstruction());
       	this.sendNotifications(MessageFinished, this.getMessageUnderConstruction(),reciever);
       	this.setMessageUnderInvocation(null);
	}


	


	







	




	
/**
 * Recieves the message.
 * @param messageUnderConstruction
 *        Message which is under construction.
 */
	private void recieve(Message messageUnderConstruction) {
        this.getRecieverList().add(messageUnderConstruction);	
	}




	


    /**
     * Returns the clone list of all the messages whicha re sent.
     * @return
     *       Returns the list of message in terms of clone, sent by this party.
     */
	public ArrayList<Message> getSenderList() {
		return senderList;
	}






	/**
	 * A setter to set the list of messages which are sent.
	 * @param senderList
	 *        The List of the messages sent by this party.
	 */
	protected void setSenderList(ArrayList<Message> senderList) {
		this.senderList = senderList;
	}






	/**
	 * Returns the Reciever list of the message.
	 * @return
	 *       Returns the list of the messages sent by this party.
	 */
	public ArrayList<Message> getRecieverList() {
		return recieverList;
	}






	/**
	 * Sets the reciever list of the party's messages.
	 * @param recieverList
	 *        List of messages, recieved by this party.
	 */
	protected void setRecieverList(ArrayList<Message> recieverList) {
		this.recieverList = recieverList;
	}






	/**
	 * Returns the message under construction
	 * @return
	 *      Returns the message under construction.
	 */
	private Message getMessageUnderConstruction() {
		return messageUnderInvocation;
	}





	/**
	 *  Sets the message underconstruction.
	 * @param messageUnderInvocation
	 *        Message underconstruction will be equal to the message instance give in parameter.
	 */
	private void setMessageUnderInvocation(Message messageUnderInvocation) {
		this.messageUnderInvocation = messageUnderInvocation;
	}
	
	
	/**
	 * Removes the message from the recieve list.
	 * @param message
	 *        The message which will be removed from the list.
	 */
	protected void removeMessageFromRecieverList(Message message) {
		this.getRecieverList().remove(message);
		this.sendNotifications(RemoveMessage, message, null);
	}

	





	/**
	 * Removes all the messages from the senderlist as well from the reciever list.
	 */
 protected void removeAllMessages() {
    for(Message message : this.getSenderList()) {
    	 message.getReciever().getRecieverList().remove(message);
    	 message.getReciever().sendNotifications(RemoveMessage, message, null);
    	 this.sendNotifications(RemoveMessage, message, null);
      }
    for(Message message : this.getRecieverList()) {
    	message.getSender().getSenderList().remove(message);
    	message.getSender().sendNotifications(RemoveMessage, message, null);
    	this.sendNotifications(RemoveMessage, message, null);
    }
	 this.setSenderList(new ArrayList<>());
	 this.setRecieverList(new ArrayList<>());
  }







/**
 * Removes the message from sender list.
 * @param message
 *        Message which will be removed.
 */
protected void removeMessageFromSenderList(Message message) {
 this.getSenderList().remove(message);	
 this.sendNotifications(RemoveMessage, message, null);
 }













/**
 * A checker to check if any message has active label.
 * @return
 *        Returns true if any message has an active label.
 */   
protected boolean hasAnyMessageWithLabelEnabled() {
     for(Message message : this.getSenderList())
    	 if(message.isActive())
    		 return true;
	return false;
    }







/**
 * A checker to check whether a label is a legal label or not.
 * @param string
 *        The given label to be checked for the correction.
 * @return
 *        Returns true if the string contains a valid label.
 */
protected boolean isValidText(String string) {
    char[] list = string.toCharArray();
    ArrayList<Character> label = new ArrayList<>();
    for(int i = 0; i < list.length;i++)
    	label.add(list[i]);
	return this.hasValidLabel(label);
}







/**
 * A getter to get the instance name of the paryt.
 * @return
 *       Returns the instance name of the party.
 */
public String getInstaceName() {
	String temp  = new String();
	for(int i = 0; i < this.getLabelList().size() && !this.getLabelList().get(i).equals(':');i++)
		temp = temp+this.getLabelList().get(i);
	return temp;
 }


/**
 * A getter to get the class name of the party.
 * @return
 *         Returns the class name of the party.
 */
public String getClassName() {
	String temp = new String();
	if(!this.getLabelList().contains(':'))
		return "";
	for(int i = this.getLabelList().indexOf(':')+1; i < this.getLabelList().size();i++)
		temp = temp+this.getLabelList().get(i);
	return temp;
}



/**
 * A function to update the instance name of the party.
 * @param text
 *         The new text of party's intance name.
 */
public void updateInstanceName(String text) {
	if(this.isValidInstance(text)) {
		this.setLabelText(text+":"+this.getClassName());
		this.updateLabelList(text+":"+this.getClassName());
		this.sendNotifications(labelVerified,this.getLabelText(),this);
	}
  }








private void updateLabelList(String string) {
  this.setLabelList(new ArrayList<>());
  this.addToTheList(string);
}








private boolean isValidInstance(String text) {
	if(text.length() <= 0)
		return true;
	return Character.isLowerCase(text.charAt(0));
}







/**
 * A function to update the class name of party.
 * @param text
 *        The class name of the party.
 */
public void updateClassName(String text) {
  if(this.isValidClassName(text)) {
	  this.setLabelText(this.getInstaceName()+":"+text);
	  this.updateLabelList(this.getInstaceName()+":"+text);
	  this.sendNotifications(labelVerified, this.getLabelText(), this);
  }
}








private boolean isValidClassName(String text) {
	if(text.length() <= 0)
		return false;
	if(!Character.isUpperCase(text.charAt(0)))
		return false;
	return true;
}







/**
 * A checker to check if any message is active.
 * @return
 *        Returns true is message is active
 */
public boolean hasAnyActiveMessage() {
	for(Message message : this.getSenderList())
		if(message.isActive())
			return true;
	return false;
}

}

	
	
	
	
 
