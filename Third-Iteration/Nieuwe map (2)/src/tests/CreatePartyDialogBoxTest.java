package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import org.junit.jupiter.api.Test;

import diagramViews.DiagramView;

class CreatePartyDialogBoxTest {
    
	createNewInteractionTest test;

	@Test
	public void CreatePartyDialogBox_LegalCase() {
		//Created New Interaction
		test = new createNewInteractionTest();
		test.addNewInteraction_LegalCase();
		//Verify that the new interaction is effectively created
		assertEquals(test.context.getAllWindows().size() ,1);
		//Coordinates of the party which will be created.
		int xCoordinateOfParty = 50;
		int yCoordinateOfParty = 150;
		//Create new Party at given coordinates.
		test.context.handleMouseEvent(MouseEvent.MOUSE_CLICKED, xCoordinateOfParty, yCoordinateOfParty, 1); //created a party
		//Verify that the new Party has been created.
		assertEquals(test.context.getAllWindows().get(0).getDiagram().getShapeList().size(),1);
		//Save temporay reference to diagram for simplicity.
		DiagramView diagram = test.context.getAllWindows().get(0).getDiagram();
		//Turn the label of the paryt off, by inserting a valid label.
		test.context.handleKeyEvent(0, 0, 'a');
		test.context.handleKeyEvent(0,0, ':');
		test.context.handleKeyEvent(0, 0,'L');
		//Enter the label
		test.context.handleKeyEvent(KeyEvent.VK_ENTER, 10, ' ');
		//Click in the middle of the party's shape to select the party's shape.
		test.context.handleMouseEvent(MouseEvent.MOUSE_CLICKED,xCoordinateOfParty+2 , yCoordinateOfParty+ diagram.getShapeList().get(0).getHeight()/2,1);
		//Verify that the shape is selected.
		assertTrue(diagram.getShapeList().get(0).isSetIsSelected());
		//Events for creating the dialogBox for the selected party.
		test.context.handleKeyEvent(KeyEvent.KEY_PRESSED,17, ' ');
		test.context.handleKeyEvent(KeyEvent.KEY_PRESSED, 10, ' ');
		//Verify that the size of the dialogBOxList is 1.
		assertEquals(diagram.getDialogBoxList().size(),1);
	}

}
