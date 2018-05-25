package tests;

import static org.junit.Assert.*;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import org.junit.Test;

import diagramViews.View;

public class movePartyTestCommView {
	
	createNewInteractionTest test;


	@Test
	public void movePartyTest_LegalCase() {
		test = new createNewInteractionTest();
		test.addNewInteraction_LegalCase(); //created new Interaction
		assertEquals(test.context.getAllWindows().size() ,1); // Assertion that a new interaction has been added to the list.
		// switching view
		test.context.handleKeyEvent(KeyEvent.VK_TAB, 9, ' ');
		assertEquals(test.context.getAllWindows().get(0).getDiagram().getView(),View.COMM_View);
		test.context.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 156, 200, 1); //create party event
		assertEquals(test.context.getAllWindows().get(0).getDiagram().getShapeList().size(),1); //  A partyShape associated with the party has been created.
		assertTrue(test.context.getAllWindows().get(0).getDiagram().getShapeList().get(0).getLabel().isActive()); // label of the party is active
		int xCoordinateOfShape  = test.context.getAllWindows().get(0).getDiagram().getShapeList().get(0).getX()+1;
		int yCoordianteOfShape = test.context.getAllWindows().get(0).getDiagram().getShapeList().get(0).getY()+1;
		test.context.handleKeyEvent(0, 0, 'a'); //Entering a valid label starts here
		test.context.handleKeyEvent(0,0, ':');
		test.context.handleKeyEvent(0, 0,'L');
		test.context.handleKeyEvent(KeyEvent.VK_ENTER, 10, ' '); // Entered for verification of label
		assertFalse((test.context.getAllWindows().get(0).getDiagram().getShapeList().get(0).getLabel().isActive()));
	    test.context.handleMouseEvent(MouseEvent.MOUSE_CLICKED, xCoordinateOfShape, yCoordianteOfShape, 1);
	    assertTrue(test.context.getAllWindows().get(0).getDiagram().getShapeList().get(0).isSetIsSelected());
		test.context.handleMouseEvent(MouseEvent.MOUSE_CLICKED, xCoordinateOfShape, yCoordianteOfShape,1);
		//Move the shape with 4 units
		test.context.handleMouseEvent(MouseEvent.MOUSE_DRAGGED,xCoordinateOfShape+4,yCoordianteOfShape+4, 1); 
	   int changedX = test.context.getAllWindows().get(0).getDiagram().getShapeList().get(0).getX();
	   int changedY = test.context.getAllWindows().get(0).getDiagram().getShapeList().get(0).getY();
	   assertTrue(test.context.getAllWindows().get(0).getDiagram().getShapeList().get(0).isSetIsSelected());
	   //Verify new Coordinates @expected = 3 units away/ Because we clicked on shape.GetX()+1
	   assertEquals(changedX,(xCoordinateOfShape+3));
	   assertEquals(changedY,(yCoordianteOfShape+3));
	}

}
