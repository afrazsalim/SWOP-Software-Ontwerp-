package tests;

import static org.junit.Assert.*;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import org.junit.Test;

import diagramViews.View;

public class AddPartyTest {

	createNewInteractionTest test;
	
	/**
	 * Add party test in sequence view.
	 */
	@Test
	public void addParty_LegalCase() {
		test = new createNewInteractionTest();
		test.addNewInteraction_LegalCase(); //created new Interaction
		assertEquals(test.context.getAllWindows().size() ,1); // Assertion that a new interaction has been added to the list.
		test.context.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 50, 150, 1); //create party event.
		assertEquals(test.context.getAllWindows().get(0).getDiagram().getShapeList().size(),1); //  A partyShape associated with the party has been created.
	}
	
	
	/**
	 * Add party test in communication view.
	 */
	@Test
	public void addPartyCommview_LegalCase() {
		test = new createNewInteractionTest();
		test.addNewInteraction_LegalCase(); //created new Interaction
		assertEquals(test.context.getAllWindows().size() ,1); // Assertion that a new interaction has been added to the list.
		assertTrue(test.context.getAllWindows().get(0).getView().equals(View.SEQUENCE_View)); // The given initial view is sequence view
		// Converting the view starts here.
		test.context.handleKeyEvent(0, KeyEvent.VK_TAB, ' ');
		// verify the new view
	    assertTrue(test.context.getAllWindows().get(0).getView().equals(View.COMM_View)); // View has been switched to comm view
		// create the new party
	    test.context.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 50, 150, 1); //create party event.
		// verify that the new party has been created.
	    assertEquals(test.context.getAllWindows().get(0).getDiagram().getShapeList().size(),1); //  A partyShape associated with the party has been created.
	}
	
	
	
	
	@Test
	public void addParty_ILLegalCase() {
		test = new createNewInteractionTest();
		test.addNewInteraction_LegalCase();
		assertEquals(test.context.getAllWindows().size() ,1);
		test.context.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 50, 150, 1); //created a party
		assertEquals(test.context.getAllWindows().get(0).getDiagram().getShapeList().size(),1);
		test.context.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 200, 150, 1); // attempt to create new party while previous one has active label.
		assertEquals(test.context.getAllWindows().get(0).getDiagram().getShapeList().size(),1);
	}
	
	

}
