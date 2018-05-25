package tests;

import static org.junit.Assert.*;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import org.junit.Test;

import diagramViews.WindowContext;
import shapes.PartyShape;

public class createMessageTest {

	createNewInteractionTest test;

	/**
	 * A test-case to test the creation of the message in sequence diagram.
	 */
	@Test
	public void createMessageTest_LegalCase() {
		test = new createNewInteractionTest();
		test.addNewInteraction_LegalCase(); //created new Interaction
		assertEquals(test.context.getAllWindows().size() ,1); // Assertion that a new interaction has been added to the list.
		test.context.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 156, 91, 1); //create party event.
		assertEquals(test.context.getAllWindows().get(0).getDiagram().getShapeList().size(),1); //  A partyShape associated with the party has been created.
		assertTrue(test.context.getAllWindows().get(0).getDiagram().getShapeList().get(0).getLabel().isActive()); // label of the party is active
		test.context.handleKeyEvent(0, 0, 'a'); //Entering a valid label starts here
		test.context.handleKeyEvent(0,0, ':');
		test.context.handleKeyEvent(0, 0,'L');
		test.context.handleKeyEvent(KeyEvent.VK_ENTER, 10, ' '); // Entered for verification of label
		assertFalse(test.context.getAllWindows().get(0).getDiagram().getShapeList().get(0).getLabel().isActive());
		
		//Create a second Party
		
		test.context.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 300, 91, 1); //create party event.
		assertEquals(test.context.getAllWindows().get(0).getDiagram().getShapeList().size(),2); //  A NEW partyShape associated with the party has been created.
		assertTrue(test.context.getAllWindows().get(0).getDiagram().getShapeList().get(1).getLabel().isActive()); // label of the party is active
		test.context.handleKeyEvent(0, 0, 'a'); //Entering a valid label starts here
		test.context.handleKeyEvent(0,0, ':');
		test.context.handleKeyEvent(0, 0,'L');
		test.context.handleKeyEvent(KeyEvent.VK_ENTER, 10, ' '); // Entered for verification of label
		assertFalse(test.context.getAllWindows().get(0).getDiagram().getShapeList().get(1).getLabel().isActive()); // Label of the second party has been deactivated.
	    //create message
		
		PartyShape first = test.context.getAllWindows().get(0).getDiagram().getShapeList().get(0);
		PartyShape second = test.context.getAllWindows().get(0).getDiagram().getShapeList().get(1);
		test.context.handleMouseEvent(MouseEvent.MOUSE_DRAGGED, first.getLine().getX(), first.getLine().getY()+10, 1); // drag event to create message on first party's line.
	    test.context.handleMouseEvent(MouseEvent.MOUSE_RELEASED, second.getLine().getX(), second.getLine().getY()+10, 1); // released mouse on second objects life line
	    assertEquals(first.getSource().getSenderList().size(),1);
	    assertEquals(second.getSource().getRecieverList().size(),1);
	    assertTrue(first.getSource().getSenderList().get(0).isActive());
	}
	
	/**
	 * A test-case to test the creation of the message but illegal case. Mouse will NOT be released on the life line of the second shape.(sequence view)
	 */
	@Test
	public void createMessageTest_ILLegalCase() {
		test = new createNewInteractionTest();
		test.addNewInteraction_LegalCase(); //created new Interaction
		assertEquals(test.context.getAllWindows().size() ,1); // Assertion that a new interaction has been added to the list.
		test.context.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 156, 91, 1); //create party event.
		assertEquals(test.context.getAllWindows().get(0).getDiagram().getShapeList().size(),1); //  A partyShape associated with the party has been created.
		assertTrue(test.context.getAllWindows().get(0).getDiagram().getShapeList().get(0).getLabel().isActive()); // label of the party is active
		test.context.handleKeyEvent(0, 0, 'a'); //Entering a valid label starts here
		test.context.handleKeyEvent(0,0, ':');
		test.context.handleKeyEvent(0, 0,'L');
		test.context.handleKeyEvent(KeyEvent.VK_ENTER, 10, ' '); // Entered for verification of label
		assertFalse(test.context.getAllWindows().get(0).getDiagram().getShapeList().get(0).getLabel().isActive());
		
		//Create a second Party
		
		test.context.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 300, 91, 1); //create party event.
		assertEquals(test.context.getAllWindows().get(0).getDiagram().getShapeList().size(),2); //  A NEW partyShape associated with the party has been created.
		assertTrue(test.context.getAllWindows().get(0).getDiagram().getShapeList().get(1).getLabel().isActive()); // label of the party is active
		test.context.handleKeyEvent(0, 0, 'a'); //Entering a valid label starts here
		test.context.handleKeyEvent(0,0, ':');
		test.context.handleKeyEvent(0, 0,'L');
		test.context.handleKeyEvent(KeyEvent.VK_ENTER, 10, ' '); // Entered for verification of label
		assertFalse(test.context.getAllWindows().get(0).getDiagram().getShapeList().get(1).getLabel().isActive()); // Label of the second party has been deactivated.
	    //create message
		
		PartyShape first = test.context.getAllWindows().get(0).getDiagram().getShapeList().get(0);
		PartyShape second = test.context.getAllWindows().get(0).getDiagram().getShapeList().get(1);
		test.context.handleMouseEvent(MouseEvent.MOUSE_DRAGGED, first.getLine().getX(), first.getLine().getY()+10, 1); // drag event to create message on first party's line.
	    test.context.handleMouseEvent(MouseEvent.MOUSE_RELEASED, second.getLine().getX(), 10, 1); // released mouse on second objects life line
	    assertEquals(first.getSource().getSenderList().size(),0);   // NO message was created.
	    assertEquals(second.getSource().getRecieverList().size(),0); // No message was created.
	}

	
	
	
	
	
	
	
	
}
