package domainObjects;

import java.util.ArrayList;

/**
 * A class representing the resulting message.
 * @author afraz
 *
 */
public class ResultingMessage extends Message {

	/**
	 * A constructor to create a new resulting message.
	 * @param sender
	 *        The sender of the resulting message.
	 * @param text
	 *        The initial text of the resulting message.
	 */
	public ResultingMessage(Party sender, String text) {
		super(sender,text);
	}

	@Override
	protected boolean isValidStringInMessageLabel(String string) {
		return true;
	}

	@Override
	protected boolean isValidMethodName(ArrayList<Character> list) {
		return true;
	}
	@Override
	protected boolean isValidMethodName(String text) {
			return true;

	}
	


}
