package tests;

import static org.junit.Assert.*;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import org.junit.Test;

import diagramViews.View;
import shapes.PartyShape;

public class createMessageCommview {


	    createNewInteractionTest test;
	    
		@Test
		public void createMessageCommView_LegalCase() {
			test = new createNewInteractionTest();
			test.addNewInteraction_LegalCase(); //created new Interaction
			assertEquals(test.context.getAllWindows().size() ,1); // Assertion that a new interaction has been added to the list.
			assertTrue(test.context.getAllWindows().get(0).getView().equals(View.SEQUENCE_View)); // The given initial view is sequence view
			// Converting the view starts here.
			test.context.handleKeyEvent(0, KeyEvent.VK_TAB, ' ');
			// verify the new view
		    assertEquals(test.context.getAllWindows().get(0).getView(),View.COMM_View); // View has been switched to comm view
			test.context.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 156, 200, 1); //create party event.
		    assertEquals(test.context.getAllWindows().get(0).getDiagram().getShapeList().size(),1); //  A partyShape associated with the party has been created.
			assertTrue(test.context.getAllWindows().get(0).getDiagram().getShapeList().get(0).getLabel().isActive()); // label of the party is active
			test.context.handleKeyEvent(0, 0, 'a'); //Entering a valid label starts here
			test.context.handleKeyEvent(0,0, ':');
			test.context.handleKeyEvent(0, 0,'L');
			test.context.handleKeyEvent(KeyEvent.VK_ENTER, 10, ' '); // Entered for verification of label
			// verfiction of label setting off
			assertFalse(test.context.getAllWindows().get(0).getDiagram().getShapeList().get(0).getLabel().isActive());
			

			//Create a second Party
			
			test.context.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 300, 200, 1); //create party event.
			assertEquals(test.context.getAllWindows().get(0).getDiagram().getShapeList().size(),2); //  A NEW partyShape associated with the party has been created.
			assertTrue(test.context.getAllWindows().get(0).getDiagram().getShapeList().get(1).getLabel().isActive()); // label of the party is active
			test.context.handleKeyEvent(0, 0, 'a'); //Entering a valid label starts here
			test.context.handleKeyEvent(0,0, ':');
			test.context.handleKeyEvent(0, 0,'L');
			test.context.handleKeyEvent(KeyEvent.VK_ENTER, 10, ' '); // Entered for verification of label
			assertFalse(test.context.getAllWindows().get(0).getDiagram().getShapeList().get(1).getLabel().isActive()); // Label of the second party has been deactivated.
		    
			//create Message
			PartyShape first = test.context.getAllWindows().get(0).getDiagram().getShapeList().get(0);
			PartyShape second = test.context.getAllWindows().get(0).getDiagram().getShapeList().get(1);
			//Message creation starts here
			
			
			test.context.handleMouseEvent(MouseEvent.MOUSE_DRAGGED, first.getLine().getX()+first.getLine().getWidth()-2, first.getLine().getY()+2, 1);
		    test.context.handleMouseEvent(MouseEvent.MOUSE_RELEASED, second.getLine().getX(), second.getLine().getY()+2, 1); // released mouse on second objects life line             
		    assertEquals(first.getSource().getSenderList().size(),1); // Message added to the second's reciever's list.
		       
		}
		
		
	

}
