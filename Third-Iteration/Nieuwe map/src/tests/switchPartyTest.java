package tests;

import static org.junit.Assert.*;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import org.junit.Test;

import shapes.PartyShape;

public class switchPartyTest {

	createNewInteractionTest test;
	
	
	@Test
	public void switchPartyType_LegalCase() {
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
		assertFalse(test.context.getAllWindows().get(0).getDiagram().getShapeList().get(0).getLabel().isActive()); // Label of the party has been deactivated.
		PartyShape shape = test.context.getAllWindows().get(0).getDiagram().getShapeList().get(0); // Store the initial partyShape.
		System.out.println("Shaaaaaaaaaapes   is  " + shape);
		test.context.handleMouseEvent(MouseEvent.MOUSE_CLICKED, shape.getX()+5, shape.getY()+5, 1); //Select the shape of the party.
        assertTrue(test.context.getAllWindows().get(0).getDiagram().getShapeList().get(0).isSetIsSelected());
		
		test.context.handleMouseEvent(MouseEvent.MOUSE_CLICKED, shape.getX()+5, shape.getY()+5, 2); // double click Event on the party's shape.
	   
		PartyShape converted = test.context.getAllWindows().get(0).getDiagram().getShapeList().get(0); // Store the converted party Shape.
	    System.out.println("Converted is  " + converted);

	    assertFalse(shape.equals(converted));
	}

	
}
