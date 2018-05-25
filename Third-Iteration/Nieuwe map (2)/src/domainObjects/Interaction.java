package domainObjects;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

import Exceptions.IllegalOperationExcetion;

/**
 * A class which represents the interaction.
 * @author Afraz Salim
 *
 */
public class Interaction {

	
	
	/**
	 * The list of all the parties which are in same interaction.
	 */
	private ArrayList<Party> partyList;
	/**
	 * Represents the propertychange support.
	 */
    private PropertyChangeSupport pcs;
    /**
     * A string which represents the size of the interaction.
     */
    private static final String PropertySize  = "changedSize";
    /**
     * A string which indicates that the party has been created.
     */
    private static final String CreatedParty = "CreatedParty";
    /**
     * A string indicates that the party has been removed.
     */
    private static final String RemoveParty = "RemoveParty";
    /**
     * A string which indicates that the party has been converted.
     */
    private static final String ConvertedParty = "ConvertedParty";
    

	
    /**
     * A constructor which initializes the interaction.
     */
	public Interaction() {
		pcs = new PropertyChangeSupport(this);
		this.setPartyList(new ArrayList<>());
	}
	
	/**
	 * Provide a function for all listeners to subscribe for the updates.
	 * @param listener
	 *        The listener which will be subscribed.
	 */
	public void subscribe(PropertyChangeListener listener) {
		if(listener == null) 
			throw new IllegalOperationExcetion("Null can't be subscribed for notifications, Unreslveable Error");
		this.pcs.addPropertyChangeListener(listener);
	}

	
	




	
	
	
	




	




    /**
     * Creates a new Party object.
     * @param text
     *        The text of the newly created party.
     */
	public void createNewPartyObject(String text) {
		if(this.hasAnyPartyLabelEnabled())
			throw new IllegalOperationExcetion("A party has already label enabled, can't create a new Party");
		Party actor = createNewPartyInstance(text);
		this.addToPartyList(actor);
		this.sendNotification(CreatedParty,null,actor);
	}

	private Party createNewPartyInstance(String text) {
		return new PartyObject(text,this);
	}



	/**
	 * Creates a new Actor.
	 * @param text
	 *        The label text of the actor which will be created.
	 */
	public void createNewActor(String text) {
		if(this.hasAnyPartyLabelEnabled())
			throw new IllegalOperationExcetion("A pary has label enanled, can't create a new party");
		Party actor = createNewActorInstance(text);
		this.addToPartyList(actor);
		this.sendNotification(CreatedParty,null,actor);
	}



	private Party createNewActorInstance(String text) {
		return new Actor(text,this);
	}

	/**
	 * Sends the notifications.
	 * @param text
	 *        PropertyName of the event.
	 * @param first
	 *        The old value of the object whoms property will be changed.
	 * @param second
	 *        The new value of the obect whose property will be changed.
	 */
	private void sendNotification(String text, Object first, Object second) {
		PropertyChangeEvent event = new PropertyChangeEvent(this,text,first, second);
       this.pcs.firePropertyChange(event);		
	}



	
	/**
	 * Adds a party to the part list.
	 * @param party
	 *       The party which will be added.
	 */
	private void addToPartyList(Party party) {
      this.getPartyList().add(party);	
	}


	


	/**
	 * Returns the party list.
	 * @return
	 *       Returns the party list.
	 */
	private ArrayList<Party> getPartyList() {
		return partyList;
	}



	/**
	 * A setter to set the party list.
	 * @param partyList
	 *        The new list which will replace the old list.
	 */
	private void setPartyList(ArrayList<Party> partyList) {
		this.partyList = partyList;
	}



	/**
	 * A checker to check if any party has label enabled.
	 * @return
	 *       Returns true no party has active label.
	 */
	public boolean hasAnyPartyLabelEnabled() {
       for(Party party : this.getPartyList()) {
    	   if(party.getHasActiveLabel())
    		   return true;
           if(party.hasAnyMessageWithLabelEnabled())
        	   return true;
       }
		return false;
	}





	





/**
 * Converts the party.
 * @param party
 *        The party which will be converted.
 */
public void convertParty(Party party) {
	  if(party == null)
		  throw new IllegalOperationExcetion("Can't change the null instance");
	  if(this.hasAnyPartyInvalidLabel())
		  throw new IllegalOperationExcetion("A party has illegal Label");
	   Party actor = null;
	   this.getPartyList().remove(party);
	   if(party instanceof Actor) 
		  actor = this.createNewPartyInstance(party.getLabelText());
	     else if(party instanceof PartyObject) 
		 actor = this.createNewActorInstance(party.getLabelText());
	     this.sendNotification(ConvertedParty, party, actor);
	     this.switchMessages(party,actor);
		 actor.verifyText();
	     this.getPartyList().add(actor);
	     actor.setSenderList(party.getSenderList());
	     actor.setRecieverList(party.getRecieverList());
		 this.setAllPartiesLabelOff();
}




/**
 * A checker to check if any party has label invalid.
 * @return
 *       Returns true if no party has label enabled.
 */
private boolean hasAnyPartyInvalidLabel() {
	for(Party party : this.getPartyList())
		if(!(party.isValidText(party.getLabelText())))
			return true;
	return false;
}



/**
 * A switcher which transfers the message from one party to other.
 * @param party
 *        The first party whose messages will be transfered.
 * @param actor
 *        The second party which will get the messages.
 */
private void switchMessages(Party party, Party actor) {
	this.changeSender(party,actor);
    this.changeReciever(party,actor);
   
}



private void changeReciever(Party party, Party actor) {
	 for(Message message : party.getRecieverList()) {
	    	message.setReciever(actor);
	    }	
}

private void changeSender(Party party, Party actor) {
	for(Message message : party.getSenderList()) {
    	message.setSender(actor);
    }	
}

/**
 * Sets the label of all the parties off.
 */
private void setAllPartiesLabelOff() {
  for(Party party: this.getPartyList())
	  party.verifyText();
}




/**
 * Removes the party from the interaction.
 * @param party
 *        The given party which will be removed.
 */
public void removeParty(Party party) {
	this.getPartyList().remove(party);
	party.removeAllMessages();
	this.sendNotification(RemoveParty, party, null);
   }



}





	

