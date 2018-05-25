package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import org.junit.jupiter.api.Test;

import box.TextField;
import diagramViews.DiagramView;
import shapes.PartyShape;

class AddInstanceNameOfPartyTest {

	CreatePartyDialogBoxTest instance;
	@Test
	public void changeInstanceNameOfPartyWithDialogBox_LegalCase() {
		instance = new CreatePartyDialogBoxTest(); // Creates a new WIndowShape with it's diagram.
		//Creates a new Party's Dialog Box.
		instance.CreatePartyDialogBox_LegalCase();
		//Save the diagram for avoiding long chaining
		DiagramView diagram = instance.test.context.getAllWindows().get(0).getDiagram();
		//Save the initial instancename to compare later.
	    String partyOldInstanceName = diagram.getShapeList().get(0).getSource().getInstaceName();
	    //Verify that a dialogBox was creatred
	    assertEquals(diagram.getDialogBoxList().size(),1);
	    //Activate the dialogBox.
	    instance.test.context.handleMouseEvent(MouseEvent.MOUSE_CLICKED, diagram.getDialogBoxList().get(0).getX()+2, diagram.getDialogBoxList().get(0).getY()+2, 1);
	    //Verify that the dialogBox effectively created.
	    assertTrue(diagram.getDialogBoxList().get(0).getIsActive());
	    // Click the field to activate it.
	    diagram.getDialogBoxList().get(0).getChildList().get(3).mouseClick(diagram.getDialogBoxList().get(0).getChildList().get(3).getX()+2, diagram.getDialogBoxList().get(0).getChildList().get(3).getY()+2);
	    // Verify that the field was activated.
	    assertTrue(diagram.getDialogBoxList().get(0).getChildList().get(3).getIsActive());
	    //Check that the field iwth instance name was enabled.
	    assertEquals(((TextField) diagram.getDialogBoxList().get(0).getChildList().get(3)).getLabel(),"instance");
	    //Now enter the text.
	    instance.test.context.handleKeyEvent(KeyEvent.KEY_PRESSED, 0, 'c');
	    instance.test.context.handleKeyEvent(KeyEvent.KEY_PRESSED, 0, 'd');
	    instance.test.context.handleKeyEvent(KeyEvent.KEY_PRESSED, 0, 'e');
	    //Set the field off.
	    instance.test.context.handleMouseEvent(MouseEvent.MOUSE_CLICKED, diagram.getDialogBoxList().get(0).getX(), diagram.getDialogBoxList().get(0).getY()+diagram.getDialogBoxList().get(0).getHeight()-2, 1);
	   // Verify that the field has been turned off.
	    assertFalse(diagram.getDialogBoxList().get(0).getChildList().get(3).getIsActive());
	    //Verify new text
	    assertEquals(diagram.getShapeList().get(0).getSource().getInstaceName(),partyOldInstanceName+"cde");
	}

}
