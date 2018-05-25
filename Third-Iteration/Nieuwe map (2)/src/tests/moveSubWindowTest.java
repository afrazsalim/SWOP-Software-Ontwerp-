package tests;

import static org.junit.Assert.*;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import org.junit.Test;

import shapes.PartyShape;

public class moveSubWindowTest {

	createNewInteractionTest test;

	
	/**
	 * Test-case for moving the window
	 */
	@Test
	public void moveSubWindowWithAPartyTest_LegalCase() {
		test = new createNewInteractionTest();
		test.addNewInteraction_LegalCase(); //created new Interaction
		assertEquals(test.context.getAllWindows().size() ,1); // Assertion that a new interaction has been added to the list.
	    int x = test.context.getAllWindows().get(0).getX();
	    assertEquals(test.context.getAllWindows().get(0).getX(),x); // verify the initial coordinate of the window
		test.context.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 50, 150, 1); //created a party
		assertEquals(test.context.getAllWindows().get(0).getDiagram().getShapeList().size(),1);
		test.context.handleKeyEvent(0, 0, 'a');
		test.context.handleKeyEvent(0,0, ':');
		test.context.handleKeyEvent(0, 0,'L');
		test.context.handleKeyEvent(KeyEvent.VK_ENTER, 10, ' ');
		test.context.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 200, 150, 1); //created a party
		PartyShape shape = test.context.getAllWindows().get(0).getDiagram().getShapeList().get(0);
		int partyX = shape.getX();
	    test.context.handleMouseEvent(MouseEvent.MOUSE_CLICKED, test.context.getAllWindows().get(0).getX()+10, test.context.getAllWindows().get(0).getY()+5, 1);
	    test.context.handleMouseEvent(MouseEvent.MOUSE_DRAGGED, test.context.getAllWindows().get(0).getX()+10, test.context.getAllWindows().get(0).getY()+5, 1);
	    test.context.handleMouseEvent(MouseEvent.MOUSE_DRAGGED, test.context.getAllWindows().get(0).getX()+20, test.context.getAllWindows().get(0).getY()+5, 1); // Move windw by 5 points
	    // verify New coordinate
	    assertEquals(test.context.getAllWindows().get(0).getX(),(x+10)); 
	    //verify the new coordinates of the party, party has been moved with same coordinates
	    assertEquals(shape.getX(),partyX+10);
	}
	

}
