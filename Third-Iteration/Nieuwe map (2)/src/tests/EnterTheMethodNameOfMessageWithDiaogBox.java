package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import box.DialogBox;
import box.TextField;
import button.Button;
import diagramViews.DiagramView;
import diagramViews.WindowContext;

class EnterTheMethodNameOfMessageWithDiaogBox {
	 private createMessageDialogBox test;
	private WindowContext context;
	@Before
	void setup() {
	    // Initialize the reference variable
			test = new createMessageDialogBox();
			//Create a dialogbox of message to work with
		    test.createMessageDialogBox_LegaCase();
		    //Save the context reference to forward the event.
		    context = test.test.test.context;
	}
	
	
	@Test
	public void enterMethodNameOfMessageLabel_LegalCase() {
		//Set up a dialogbox by using previous test-case(for creating a dialogbox)
				setup();
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
				TextField field = (TextField) diagram.getSelectedBox().getChildList().get(7);
				assertTrue(field.getLabel().equals("methodName"));
				//get the addButton
				Button button = (Button) diagram.getSelectedBox().getChildList().get(8);
				assertTrue(button.getLabel().equals("Enter"));
				
				//Activate the textfield 
				context.handleMouseEvent(MouseEvent.MOUSE_CLICKED,field.getX()+4, field.getY()+2, 1);
				//verify that the field is activated.
				assertTrue(field.getIsActive());
				
				//Save the old label of the message.
				String label = diagram.getShapeList().get(0).getSource().getSenderList().get(0).getLabelText();
				//Verify that the character we are going to insert does not exist already./
				assertFalse(label.contains("w"));
				
				//Insert w as parameter then we again verify that the label contains this new character.
				context.handleKeyEvent(KeyEvent.KEY_PRESSED,0, 'p');
				//Press the add button to add it
				context.handleMouseEvent(MouseEvent.MOUSE_CLICKED, button.getX()+2, button.getY()+2,1);
				//Save the new label
				String newLabel = diagram.getShapeList().get(0).getSource().getSenderList().get(0).getLabelText();
				//Verify that the new label contains the new character
				assertTrue(newLabel.contains("p"));
				//deactivate now field
				context.handleMouseEvent(MouseEvent.MOUSE_CLICKED,field.getX(), field.getY()+field.getHeight()+2, 1);
				assertFalse(field.getIsActive());
		
		
		
		
	}

}
