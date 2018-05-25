package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import org.junit.jupiter.api.Test;

import box.DialogBox;
import box.ListBox;
import box.TextField;
import button.Button;
import diagramViews.DiagramView;
import diagramViews.WindowContext;

class RemoveArgumentFromMessageLabelWithDialogBox {

	
	private createMessageDialogBox test;
	private WindowContext context;
	
	@Test
	void setUp() {
		 // Initialize the reference variable
		test = new createMessageDialogBox();
		//Create a dialogbox of message to work with
	    test.createMessageDialogBox_LegaCase();
	    //Save the context reference to forward the event.
	    context = test.test.test.context;
	}

	

	@Test
	public void RemoveArgumentToMessageLabeWIthDialogBox_LegalCase() {
		//Set up a dialogbox by using previous test-case(for creating a dialogbox)
		setUp();
		//Verify that there exist one dialogbox.
		assertEquals(context.getAllWindows().get(0).getDiagram().getDialogBoxList().size(),1);
		//Save reference to diagram and dialogbox to avoid chaining.
		DiagramView diagram = context.getAllWindows().get(0).getDiagram();
		DialogBox box = context.getAllWindows().get(0).getDiagram().getDialogBoxList().get(0);
		//Activate the dialogbox
		context.handleMouseEvent(MouseEvent.MOUSE_CLICKED, box.getX(), box.getY()+2, 1);
		//Verify that a box is selected.
		assertTrue(diagram.getSelectedBox().equals(box));
		//get the argument field
		TextField field = (TextField) diagram.getSelectedBox().getChildList().get(2);
		assertTrue(field.getLabel().equals("new arg"));
		//get the addButton
		Button button = (Button) diagram.getSelectedBox().getChildList().get(1);
		assertTrue(button.getLabel().equals("Add"));
		
		//Activate the textfield 
		context.handleMouseEvent(MouseEvent.MOUSE_CLICKED,field.getX()+4, field.getY()+2, 1);
		//verify that the field is activated.
		assertTrue(field.getIsActive());
		
		//Save the old label of the message.
		String label = diagram.getShapeList().get(0).getSource().getSenderList().get(0).getLabelText();
		//Verify that the character we are going to insert does not exist already./
		assertFalse(label.contains("w"));
		
		//Insert w as parameter then we again verify that the label contains this new character.
		context.handleKeyEvent(KeyEvent.KEY_PRESSED,0, 'w');
		//Press the add button to add it
		context.handleMouseEvent(MouseEvent.MOUSE_CLICKED, button.getX()+2, button.getY()+2,1);
		//Save the new label
		String newLabel = diagram.getShapeList().get(0).getSource().getSenderList().get(0).getLabelText();
		//Verify that the new label contains the new character
		assertTrue(newLabel.contains("w"));
		//deactivate now field
		context.handleMouseEvent(MouseEvent.MOUSE_CLICKED,field.getX(), field.getY()+field.getHeight()+2, 1);
		assertFalse(field.getIsActive());
		
		
		
		
		
		
		//Removing argument starts here
		
		
		
		
		
		//Save the listBox
		ListBox listBox = (ListBox) diagram.getDialogBoxList().get(0).getChildList().get(3);
		//verify that it's the listBox
		assertTrue(listBox.getLabel().equals("arguments"));
		//activate the firs text field from listbox.
		context.handleMouseEvent(MouseEvent.MOUSE_CLICKED, listBox.getX()+2, listBox.getY()+2, 1);
		//Save the texfield
		TextField selectedField = listBox.getselectedField();
		//check that the label contains it's text
		assertTrue(diagram.getShapeList().get(0).getSource().getSenderList().get(0).getLabelText().contains(selectedField.getText()));
		// Now save the remove button for removing the text of selected textfield.
		Button deleteButton = (Button) diagram.getDialogBoxList().get(0).getChildList().get(6);
		//Verify that it's the correct button.
		assertTrue(deleteButton.getLabel().equals("delete"));
		//Now press the delete button.
		context.handleMouseEvent(MouseEvent.MOUSE_CLICKED, deleteButton.getX()+2, deleteButton.getY()+2, 1);
		//Now check if the label contains the text of selected field.
		assertFalse(diagram.getShapeList().get(0).getSource().getSenderList().get(0).getLabelText().contains(selectedField.getText()));
	}

}
