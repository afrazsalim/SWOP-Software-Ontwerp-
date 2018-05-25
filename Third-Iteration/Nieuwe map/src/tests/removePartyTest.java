package tests;

import static org.junit.Assert.*;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import org.junit.Test;

import shapes.ActorShapeSequenceView;
import shapes.PartyShape;

public class removePartyTest {

	
	createNewInteractionTest test;

	@Test
	public void removeParty_LegalCase() {
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
		PartyShape shape = test.context.getAllWindows().get(0).getDiagram().getShapeList().get(0);
		test.context.handleMouseEvent(MouseEvent.MOUSE_CLICKED, shape.getLabel().getX()+2, shape.getLabel().getY()+2, 1); // Turning on the label
		assertTrue(test.context.getAllWindows().get(0).getDiagram().getShapeList().get(0).getLabel().isActive());
		test.context.handleKeyEvent(KeyEvent.VK_DELETE, 127, ' ');
		assertEquals(test.context.getAllWindows().get(0).getDiagram().getShapeList().size(),0);
	}
	
	
	@Test
	public void removePartyWithMessage_LegalCase() {
		test = new createNewInteractionTest();
		test.addNewInteraction_LegalCase(); //created new Interaction
		assertEquals(test.context.getAllWindows().size() ,1); // Assertion that a new interaction has been added to the list.
		test.context.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 156, 91, 1); //create party event.
		assertEquals(test.context.getAllWindows().get(0).getDiagram().getShapeList().size(),1); //  A partyShape associated with the party has been created.
		assertTrue(test.context.getAllWindows().get(0).getDiagram().getShapeList().get(0).getLabel().isActive()); // label of the party is active
		test.context.handleKeyEvent(0, 0, 'a'); //Entering a valid label starts here
		test.context.handleKeyEvent(0,0, ':');
		test.context.handleKeyEvent(0, 0,'L');
		test.context.handleKeyEvent(KeyEvent.VK_ENTER, 10, ' ');
		// Entered for verification of label
		assertFalse(test.context.getAllWindows().get(0).getDiagram().getShapeList().get(0).getLabel().isActive()); //Label is no more active
		
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
	       //Message verification
	    assertEquals(first.getSource().getSenderList().size(),1);
	    //Message verification
	    assertEquals(second.getSource().getRecieverList().size(),1);
	    
	    //Removing party starts here
	    PartyShape shape = test.context.getAllWindows().get(0).getDiagram().getShapeList().get(0);
	    //indentifying the label's coordinates
	    int x = 0 ; 
	    int y  = 0;
	    
	    if(shape instanceof ActorShapeSequenceView) {
	    	x = shape.getX()+2;
	    	y = shape.getY()+shape.getHeight()+2;
	    }
	    else
	    {
	    	x = shape.getX()+2;
	    	y = shape.getY()-2;
	    }
	    
	    //verify tat the label is not active
	    assertFalse(shape.getLabel().isActive());
	    //Enable the party's label.
		shape.mouseClicked( x, y);
	    //verify that the label has been enabled.
		assertTrue(shape.getHasActiveLabel());
		//verify that there are two party shape's
		assertEquals(test.context.getAllWindows().get(0).getDiagram().getShapeList().size(),2);
        //remove party event
		test.context.handleKeyEvent(KeyEvent.VK_DELETE, 127, ' ');
		//verify that the shape has also been removed.
		assertEquals(test.context.getAllWindows().get(0).getDiagram().getShapeList().size(),1);

	    
	    
	    
	    
	    
	}
	

}
