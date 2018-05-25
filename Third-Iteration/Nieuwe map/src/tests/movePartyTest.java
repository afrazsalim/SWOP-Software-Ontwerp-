package tests;

import static org.junit.Assert.*;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import org.junit.Test;

public class movePartyTest {

	createNewInteractionTest test;
	
	@Test
	public void movePartyTest_LegalCase() {
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
		assertFalse((test.context.getAllWindows().get(0).getDiagram().getShapeList().get(0).getLabel().isActive()));
	    test.context.handleMouseEvent(MouseEvent.MOUSE_CLICKED, test.context.getAllWindows().get(0).getDiagram().getShapeList().get(0).getX()+1, test.context.getAllWindows().get(0).getDiagram().getShapeList().get(0).getY()+1,1);
	    int x = test.context.getAllWindows().get(0).getDiagram().getShapeList().get(0).getX(); // Store the previous coordinates
		int y = test.context.getAllWindows().get(0).getDiagram().getShapeList().get(0).getY(); // Store the previous coordinates
		test.context.handleMouseEvent(MouseEvent.MOUSE_DRAGGED,test.context.getAllWindows().get(0).getDiagram().getShapeList().get(0).getX()+3,95, 1); // Moved Shape with two point in X direction
	   int changedX = test.context.getAllWindows().get(0).getDiagram().getShapeList().get(0).getX();
	   int changedY = test.context.getAllWindows().get(0).getDiagram().getShapeList().get(0).getY();
	   System.out.println("Valus are " + changedX);
	   assertEquals(changedX,(x+2));
	   assertEquals(y,changedY);
	}

}
