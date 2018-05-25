package domainObjects;

import java.util.ArrayList;

/**
 * A class representing the Invocation Message.
 * @author afraz
 *
 */
public class InvocationMessage extends Message {
	

	/**
	 * A constructor to create the instance of the InvocationMessage.
	 *
	 * @param sender
	 *        The sender of the message.
	 * @param text
	 *        The given intital text of the message.
	 */
	public InvocationMessage(Party sender, String text) {
		super(sender,text);
		setNumber(getNumber()+1);
		super.setActive(true);
	}
    
  


	
	

	@Override
	protected boolean isValidStringInMessageLabel(String string) {
		if(!string.contains("(") && !string.contains(")"))
			return false;
    	ArrayList<Character> list  = new ArrayList<>();
    	char [] array = this.stringToCharacter(string);
    	for(int i  = 0; i< array.length ;i++) {
    		list.add(array[i]);
    	}
    	if(this.isValidMethodName(list) && this.isValidParameterList(list)) 
    		return true;
		return false;
	}







	@Override
	protected boolean isValidMethodName(ArrayList<Character> list) {
		if(!list.contains(')'))
			return false;
		if(!list.contains(')'))
			return false;
		if(list.size() <= 0)
			return false;
		if(this.parseMethodName(list).length() > 0 && Character.isUpperCase(this.parseMethodName(list).charAt(0)))
			return false;
		return true;
	}







	@Override
	protected boolean isValidMethodName(String text) {
			return text.length() > 0 && Character.isLowerCase(text.charAt(0));

	}



}
