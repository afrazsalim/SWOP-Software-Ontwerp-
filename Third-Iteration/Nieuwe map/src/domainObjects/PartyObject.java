package domainObjects;

/**
 * 
 * @author Afraz Salim
 *
 */
public class PartyObject extends Party {
	
    /**
     * 
     * @param text
     *        The text of the party.
     * @param window
     *        Interaction of the party.
     */
	protected PartyObject(String text,Interaction window) {
		super(text,window);
	}

}
