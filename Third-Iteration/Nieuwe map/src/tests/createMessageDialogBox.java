package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import org.junit.jupiter.api.Test;

import box.TextField;
import button.Button;
import diagramViews.DiagramView;
import shapes.ActivationBarShape;

class createMessageDialogBox {

	
	createMessageTest test = new createMessageTest();

	@Test
	public void createMessageDialogBox_LegaCase() {
		//Create new Message and verify that a message has been created.
		test.createMessageTest_LegalCase();
		//Save the diagram to avoid chaining
		DiagramView diagram = test.test.context.getAllWindows().get(0).getDiagram();
		//Check that diagram contains two shapes.
		assertEquals(diagram.getShapeList().size() , 2);
		//Initially there is no selected message.
		assertEquals(diagram.getShapeList().get(0).getLine().getSelectedMessage() ,null);
		//Assertion that the sender list contains one message.
		assertEquals(diagram.getShapeList().get(0).getLine().getBarList().get(0).getSenderList().size(),1);
		//Save the instance of teh activation bar.
		ActivationBarShape bar = diagram.getShapeList().get(0).getLine().getBarList().get(0);
		//Turn off the message's label first. 
		//Initially label is active
		assertTrue(diagram.getShapeList().get(0).getSource().getSenderList().get(0).isActive());
		//Insert a valid label
		diagram.enterMessageLabelText(diagram.getShapeList().get(0).getSource().getSenderList().get(0), 'm');
		diagram.enterMessageLabelText(diagram.getShapeList().get(0).getSource().getSenderList().get(0), '(');
		diagram.enterMessageLabelText(diagram.getShapeList().get(0).getSource().getSenderList().get(0), ')');
		//Enter the valid label
		test.test.context.handleKeyEvent(KeyEvent.KEY_PRESSED, 10, ' ');
		//Label is now verified and no more active
		assertFalse(diagram.getShapeList().get(0).getSource().getSenderList().get(0).isActive());
		//Activate a message.
        bar.getSenderList().get(0).setSelected(true);
        //Set it as activate in shape's line.
        diagram.getShapeList().get(0).setSelectedMessage(bar.getSenderList().get(0));
        //Verify that the selected message shape is same.
		assertEquals(diagram.getShapeList().get(0).getSelectedMessage(),bar.getSenderList().get(0));
		//Create a dialogbox here
		assertEquals(diagram.getDialogBoxList().size(),0); //First verify that there is no dialogbox
		test.test.context.handleKeyEvent(KeyEvent.KEY_PRESSED, 17, ' ');
		test.test.context.handleKeyEvent(KeyEvent.KEY_PRESSED, 10, ' ');
        assertEquals(diagram.getDialogBoxList().size(),1);
	}

}
