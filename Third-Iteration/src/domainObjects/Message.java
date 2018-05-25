package domainObjects;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.stream.Collectors;

import Exceptions.IllegalOperationExcetion;

/**
 * 
 * @author Afraz Salim
 *
 */
public abstract class Message {
	/**
	 * The label text of the Message.
	 */
	private String labelText;
	/**
	 * Sender of the message.
	 */
	private Party sender;
	/**
	 * Reciever of the message.
	 */
	private Party reciever;
	/**
	 * Message number.
	 */
	private static int number = 1;
	/**
	 * Propertychange support.
	 */
    private PropertyChangeSupport pcs;
    /**
     * A string which indicates that the message label has been edited.
     */
    private static final String MessageLabelEdited = "MessageLabelEdited";
    /**
     * A string which indicates that the message label has been verified.
     */
    private static final String MessageLabelVerified = "MessageLabelVerified";
    /**
     * A list of Listeners.
     */
    private ArrayList<PropertyChangeListener> listenerList;
    /**
     * Chracter list of the message label.
     */
    private ArrayList<Character> charactersList;
    /**
     * Number of the message.
     */
    private int messageNumber;
    
    
    /**
     * A constructor to initialize the message.
     * @param sender
     *        Sender of the message.
     * @param text
     *        Label's text of the message.
     * @throws IllegalOperationExcetion
     *        Throws illegalOperationException if the the sender of the message is null.
     */
	public Message(Party sender,String text) {
		if(sender == null)
			throw new IllegalOperationExcetion("Every Message must have a sender party");
		this.setSender(sender); 
		setListenerList(new ArrayList<>());
		 pcs = new PropertyChangeSupport(this);
		this.setCharactersList(new ArrayList<>());
		if(!(this instanceof ResultingMessage))
		    this.setActive(false);
		this.setMessageNumber(getNumber());
		this.setLabelText("");
		this.setMethodName("");
	}
	
	
	/**
	 * Provides a function to subscribe.
	 * @param listener
	 *        A listener to be added.
	 */
    public void subscribe(PropertyChangeListener listener) {
    	if(!(this.getListenerList().contains(listener))) {
		 this.pcs.addPropertyChangeListener(listener);
		 this.getListenerList().add(listener);
    	}
    }


    /**
     * A getter to get the label text.
     * @return
     *        Returns the label's text.
     */
	public String getLabelText() {
		return labelText;
	}
	
	/**
	 * A string representing the message number.
	 */
	private String messageNumbers;
	
	/**
	 * A setter to set the message label's text.
	 * @param labelText
	 *        The new text which will be set.
	 */
	protected void setLabelText(String labelText) {
		this.computeStringNumber(labelText);
		this.labelText =  labelText ;
	}

	private void computeStringNumber(String labelText2) {
		StringBuilder stringBuilder = new StringBuilder();
		for(int i  = 0; i < this.getMessageNumber();i++) {
			stringBuilder.append("" + i+":");
		} 
		this.setMessageNumbers(stringBuilder.toString());
	}

	
	/**
	 * A function to enter the message parameter.
	 * @param text
	 *        The text of the parameter which will be inserted.
	 */
	  public void enterParameter(String text) {
	    	if(this.parseParametersList(this.getCharactersList()).length() <= 1) {
	    		
	    	}
	    	if(this.getCharactersList().contains(')') && this.getCharactersList().contains('(')) {
	    		this.addParameterAtEnd(text);
	    	}
		}
	  

		private void addParameterAtEnd(String text) {
			if(this.parseParametersList(this.getCharactersList()).length() <= 2) {
				this.getCharactersList().remove(this.getCharactersList().size()-1);
		    	this.addCharacterToList(text,this.getCharactersList());
		    	this.getCharactersList().add(')');
		      }
			else if (this.parseParametersList(this.getCharactersList()).length() > 2) {
			       this.getCharactersList().remove(this.getCharactersList().size()-1);
			       this.getCharactersList().add(',');
	               this.addCharacterToList(text, this.getCharactersList());
	               this.getCharactersList().add(')');
			}
			this.verifyText();
		}
		
		

		private void addCharacterToList(String text, ArrayList<Character> list) {
	        char [] array = text.toCharArray();
	        for(int i = 0; i < array.length;i++) {
	        	list.add(array[i]);
	        }
		}
		

		public void enterMethodName(String text) {
			if(isValidMethodName(text)) {
				this.enterText(text + this.parseParametersList(this.getCharactersList()));
			}
		}
		


		protected abstract boolean isValidMethodName(String text);

	/**
	 * A getter to get the sender of the message.
	 * @return
	 *       Returns the sender of the message.
	 */
	public Party getSender() {
		return sender;
	}

	/**
	 * A setter to set the sender of the message.
	 * @param sender
	 *        The new sender of the message.
	 */
	protected void setSender(Party sender) {
		this.sender = sender;
	}

	/**
	 * A getter ot get the reciever of the message.
	 * @return
	 *       Returns the reciever of the message.
	 */
	public Party getReciever() {
		return reciever;
	}

	/**
	 * A setter to set the reciever of the message.
	 * @param reciever
	 *         The new reciever of the messagE.
	 */
	protected void setReciever(Party reciever) {
		this.reciever = reciever;
	}



	




	/**
	 * A list of all the listeners.
	 * @return
	 *       Returns the list of the all listeners.
	 */
	private ArrayList<PropertyChangeListener> getListenerList() {
		return listenerList;
	}



	/**
	 * Sets the list of all the listeners.
	 * @param listenerList
	 *         The new list of the listeners.
	 */
	private void setListenerList(ArrayList<PropertyChangeListener> listenerList) {
		this.listenerList = listenerList;
	}


	/**
	 * A boolean variable to check if the message label is active.
	 */
	private boolean isActive;

	/**
	 * A checker to check if any message has active label.
	 * @return  
	 *       Returns true if the message is active.
	 */
	public boolean isActive() {
		return this.isActive;
	}
	
	/**
	 * A setter to change the active status of the message's label.
	 * @param value
	 *        The new value with which the old value will be replaced.
	 */
	protected void setActive(boolean value) {
		this.isActive = value;
	}


	

	/**
	 * Verfies and turns off the label if label is valid.
	 */
	public void verifyText() {
	 if(this.isValidStringInMessageLabel(this.converMethodNameAndParamterToString())) {
		 this.setLabelText(this.parseMethodName(this.getCharactersList()) + this.parseParametersList(this.getCharactersList()));
      this.setActive(false);
      this.sendNotifications(MessageLabelVerified, "", this.parseMethodName(this.getCharactersList())+ this.parseParametersList(this.getCharactersList()));
	 }
	}



	private String converMethodNameAndParamterToString() {
		return this.parseMethodName(this.getCharactersList()) + this.parseParametersList(this.getCharactersList());
	}


	/**
	 * A function to enter the characters into the message's label text.
	 * @param string
	 *        The keyChar which will be inserted at the end of the label.
	 */
	public void enterText(String string) {
			this.addStringToCharacterList(string);
			this.setMethodName(this.parseMethodName(this.getCharactersList()));
        	this.setLabelText(this.getMethodName()+this.parseParametersList(this.getCharactersList()));
            this.sendNotifications(MessageLabelEdited, "", this.getLabelText());
	}


    private void addStringToCharacterList(String string) {
       char [] array = string.toCharArray();
       ArrayList<Character> list = new ArrayList<>();
       for(int i = 0; i < array.length;i++) {
    	   list.add(array[i]);
       }
       this.setCharactersList(list);
	}
    
    protected String parseParametersList(ArrayList<Character> list) {
    	if(!list.contains('(') && !(list.contains(')')))
    		return "";
    	else if(list.contains('(') && !list.contains(')'))
    		return this.leftHalfOfParametersList(list);
    	else return this.fullparametersList(list);
    }


	private String fullparametersList(ArrayList<Character> list) {
		StringBuilder builder = new StringBuilder();
    	builder.append('(');
    		for(int i = list.indexOf('(')+1; i < list.size();i++) {
    			if(list.get(i) == ')')
    				break;
    			builder.append(list.get(i));
    		}
    		builder.append(')');
		return builder.toString();
		
	}


	private String leftHalfOfParametersList(ArrayList<Character> list) {
		StringBuilder builder = new StringBuilder();
    	builder.append('(');
    		for(int i = list.indexOf('(')+1; i < list.size();i++) {
    			if(list.get(i) == ')')
    				break;
    			builder.append(list.get(i));
    		}
		return builder.toString();
	}


	/**
	 * A function to check if the message's label is valid label.
	 * @param string
	 *        Label of the message.
	 * @return
	 *        True if and only if the message's label is valid label.
	 */
	protected abstract boolean isValidStringInMessageLabel(String string);
		





	protected char [] stringToCharacter(String string) {
		return string.toCharArray();
    }
	
	/**
	 * A function to check if the method name is a valid name.
	 * @param list
	 *        The list of characters which contains the method name.
	 * @return
	 *       Returns true if the method name is a valid method name.
	 */
	protected abstract boolean isValidMethodName(ArrayList<Character> list);
	
	/**
	 * A function which parse the method name.
	 * @param list
	 *        The list of character containing the method name.
	 * @return
	 *       Returns the method name.
	 */
	protected String parseMethodName(ArrayList<Character> list) {
		StringBuilder stringBuilder = new StringBuilder("");
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i) == ')' || list.get(i) == '(')
				break;
			stringBuilder.append(list.get(i));
		}
		return stringBuilder.toString();
	}


	protected boolean isValidParameterList(ArrayList<Character> list) {
		return true;
	}


	/**
     * A list of all characters.
     * @return
     *       Retruns the list of all the character of message's label.
     */
	protected ArrayList<Character> getCharactersList() {
		return charactersList;
	}



	/**
	 * A setter to set the characters list.
	 * @param charactersList
	 *        The new chracters list with which the old list will be replaced.
	 */
	private void setCharactersList(ArrayList<Character> charactersList) {
		this.charactersList = charactersList;
	}



	/**
	 * A function to remove the characters from the end of the Message's label.
	 */
	public void removeText() {
	   if(this.parseParametersList(this.getCharactersList()).length() > 2) 
		   this.removeLastCharacterFromParameters();
	   else
		   this.removeCharacterFromMethodName();	
       this.enterText(this.parseMethodName(this.getCharactersList())+this.parseParametersList(this.getCharactersList()));
	}



	
	private void removeCharacterFromMethodName() {
	  if(this.parseMethodName(this.getCharactersList()).length() > 0) {
		if(this.getCharactersList().contains('(') && this.getCharactersList().contains(')'))
         this.getCharactersList().remove(this.getCharactersList().size()-3);
		else if(this.getCharactersList().contains('(') && !this.getCharactersList().contains(')'))
			this.getCharactersList().remove(this.getCharactersList().size()-2);
		else
		 this.getCharactersList().remove(this.getCharactersList().size()-1);
	  }  
	}

	

	private void removeLastCharacterFromParameters() {
        this.getCharactersList().remove(this.getCharactersList().size()-1);
        if(this.getCharactersList().get(this.getCharactersList().size()-1) == ',')
        	this.getCharactersList().remove(this.getCharactersList().size()-1);
	}


	

	/**
	 * Sends the notifications to all listeners.
	 * @param propertyName
	 *        The propertyName of the event.
	 * @param oldText
	 *        Old text of message's label.
	 * @param labelText
	 *        New text of the message's label.
	 */
	private void sendNotifications(String propertyName, String oldText, String labelText) {
       PropertyChangeEvent event = new PropertyChangeEvent(this,propertyName,oldText,labelText);
       this.pcs.firePropertyChange(event);
	}


	

	/**
	 * Sets the label of the message enabled.
	 */
	public void setlabelEnabled() {
      this.setActive(true);	
      this.sendNotifications("","", this.getLabelText());
	}



	/**
	 * A getter to get the number of the message.
	 * @return
	 *        Returns the message's number.
	 */
	public static int getNumber() {
		return number;
	}



	/**
	 * A setter to set the message's number.
	 * @param number
	 *        The number of the message.
	 */
	protected static void setNumber(int number) {
		Message.number = number;
	}



	/**
	 * A getter to get the message's number.
	 * @return
	 *        Returns the string representation of the number.
	 */
	public String getMessageNumbers() {
		return messageNumbers;
	}



	/**
	 * A setter to set the message number in terms of string.
	 * @param messageNumbers
	 *        A new string reprsentation of the number.
	 */
	private void setMessageNumbers(String messageNumbers) {
		this.messageNumbers = messageNumbers;
	}



	/**
	 * A getter to get the message number.
	 * @return
	 *       Returns the message number.
	 */
	private int getMessageNumber() {
		return messageNumber;
	}



	/**
	 * A setter to set message number.
	 * @param messageNumber
	 *        The new number of the messagE.
	 */
	private void setMessageNumber(int messageNumber) {
		this.messageNumber = messageNumber;
	}

	private String methodName;

	/**
	 * A function to get the methodname of the messag's label.
	 * @return
	 *       Returns the method name of the message's label.
	 */
	public String getMethodName() {
		return methodName;
	}

	/**
	 * A setter to set the method name of the message's label.
	 * @param methodName
	 *        The new method name of the messsage's label.
	 */
	protected void setMethodName(String methodName) {
		this.methodName = methodName;
	}


	/**
	 * Reduces the message's number by 1.
	 */
	protected static void reducNumber() {
      if(getNumber() > 0)
    	  setNumber(getNumber()-1);
	}



	/**
	 * A getter to get the parameters list of teh message.
	 * @return
	 *        Returns the message's label's parameters list.
	 */
	public String getParametersList() {
		if(this.getLabelText().length() > 3 && this.getLabelText().contains("(") && this.getLabelText().contains(")"))
		 return this.getLabelText().substring(this.getLabelText().indexOf('(')+1, this.getLabelText().indexOf(')'));
		return " ";
	}


	/**
	 * A function which removes the text from the message's label.
	 * @param text
	 *        The given text which will be removed from the message's label.
	 */
	public void removeArgument(String text) {
      if(this.getLabelText().contains(text)) {
    	  this.enterText(this.removeTextFromLabel(text));
    	  this.rearrangeCommas(this.getCharactersList());
      }
	}


	private String removeTextFromLabel(String text) {
		  int i = this.search(text,this.getLabelText());
    	  String first = this.getLabelText().substring(0, i);
    	  String second = this.getLabelText().substring(i+text.length(), this.getLabelText().length());
    	  String finString = first+second;
    	  return finString;
	}


	/**
	 * Function which rearranges the commas in a list after removing a specific label element.
	 * @param list
	 *        The list from which element will be removed.
	 */
	private void rearrangeCommas(ArrayList<Character> list) {
		ArrayList<Character> removeIndex = new ArrayList<>();
      	for(int i = 0; i < list.size();i++) {
      		if(list.get(i) == '(' && i< list.size()-1 && list.get(i+1) == ',') {}
      	    else if(i< list.size()-1 && list.get(i)== ',' && list.get(i+1) == ')') {}
      	    else if(list.get(i) == ','&& i< list.size()-1 && list.get(i+1) == ',') {}
      	    else removeIndex.add(list.get(i));
      	}
      	this.setCharactersList(removeIndex);
      	this.enterText(this.parseMethodName(this.getCharactersList())+ this.parseParametersList(this.getCharactersList()));
	}



	//Algorithm from book Algorithms 4th edition for finidng the pattern of substring.
	private int search(String pat, String txt) {
        int m = pat.length();
        int n = txt.length();

        for (int i = 0; i <= n - m; i++) {
            int j;
            for (j = 0; j < m; j++) {
                if (txt.charAt(i+j) != pat.charAt(j))
                    break;
            }
            if (j == m) return i;            // found at offset i
        }
        return n;                            // not found
    }


	public void moveArgumentBackWar(String text) {
       if(!this.getLabelText().contains(text))
    	   throw new IllegalOperationExcetion("Message label does not contains the given text");
      int index = this.search(text,this.getLabelText());
      if(index > 0  && this.getCharactersList().get(index-1).equals(',')) {
    	  int indexOfPreviousText = this.getIndexOfPreviousText(index-1);
    	  System.out.println("Index ares  " + index  + " seocnd  " + indexOfPreviousText + " first "+ this.getCharactersList().get(index)  + " secind " + this.getCharactersList().get(indexOfPreviousText));
      }
	}


	private int getIndexOfPreviousText(int index) {
		for(int i = index-1; i >= 0; i--) {
			if(this.getCharactersList().get(i).equals(','))
				return i+1;
		}
		return 0;
	}

	


	
	
	
	
	
}
