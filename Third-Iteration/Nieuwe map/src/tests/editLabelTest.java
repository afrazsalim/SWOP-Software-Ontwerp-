package tests;

import static org.junit.Assert.*;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import org.junit.Test;

import domainObjects.Message;
import shapes.ActorShapeSequenceView;
import shapes.PartyShape;

public class editLabelTest {

	createNewInteractionTest test ;
	
	@Test
	public void editLabelLegal_Case() {
		test = new createNewInteractionTest();
		test.addNewInteraction_LegalCase();
		assertEquals(test.context.getAllWindows().size() ,1);
		test.context.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 50, 150, 1); //created a party
		assertEquals(test.context.getAllWindows().get(0).getDiagram().getShapeList().size(),1);
		test.context.handleKeyEvent(0, 0, 'a');
		test.context.handleKeyEvent(0,0, ':');
		test.context.handleKeyEvent(0, 0,'L');
		test.context.handleKeyEvent(KeyEvent.VK_ENTER, 10, ' ');
		test.context.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 200, 150, 1); //created a party
		assertEquals(test.context.getAllWindows().get(0).getDiagram().getShapeList().size(),2); //A new party has been created which increased the size of the shape
	}
	
	@Test
	public void removingLabelText_LegalCase() {
		test = new createNewInteractionTest();
		test.addNewInteraction_LegalCase();
		assertEquals(test.context.getAllWindows().size() ,1);
		test.context.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 50, 150, 1); //created a party
		assertEquals(test.context.getAllWindows().get(0).getDiagram().getShapeList().size(),1);
		test.context.handleKeyEvent(0, 0, 'a');
		test.context.handleKeyEvent(0,0, ':');
		test.context.handleKeyEvent(0, 0,'L');
		test.context.handleKeyEvent(0, 0,'M');
		test.context.handleKeyEvent(KeyEvent.VK_ENTER, 10, ' ');
		int x = 0;
		int y  = 0;
		PartyShape shape = test.context.getAllWindows().get(0).getDiagram().getShapeList().get(0);
		if(shape instanceof ActorShapeSequenceView) {
			x = shape.getX()+2;
			y = shape.getY()+shape.getHeight()+2;
		}
		else
		{
			x = shape.getX()+2;
			y = shape.getY()-2;
		}
		// Click on the label place
		test.context.handleMouseEvent(MouseEvent.MOUSE_CLICKED, x, y, 1);
	   // assertion that the label has been activated
		assertTrue(shape.getLabel().isActive());
		// Assertion before removing text
		assertTrue(shape.getLabel().getText().equals("a:LM"));
		//Event to remove the last character
		test.context.handleKeyEvent(KeyEvent.VK_BACK_SPACE, 8, ' ');
		//verify the new label text(by entering text in domain)
		test.context.handleKeyEvent(KeyEvent.VK_ENTER, 10, ' ');
        //verify the label
		assertFalse(shape.getLabel().isActive());
		// verify the text
		assertTrue(shape.getLabel().getText().equals("a:L"));
		
		
		
	}

	
	
	@Test
	public void editLabel_ILLegalCase() {
		test = new createNewInteractionTest();
		test.addNewInteraction_LegalCase();
		assertEquals(test.context.getAllWindows().size() ,1);
		test.context.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 50, 150, 1); //created a party
		assertEquals(test.context.getAllWindows().get(0).getDiagram().getShapeList().size(),1);
		test.context.handleKeyEvent(0, 0, 'a');
		test.context.handleKeyEvent(0,0, ':');
		test.context.handleKeyEvent(0, 0,'a');     //Invalid label
		test.context.handleKeyEvent(KeyEvent.VK_ENTER, 10, ' ');
		test.context.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 200, 150, 1); //Attempt to create a new party.
		assertEquals(test.context.getAllWindows().get(0).getDiagram().getShapeList().size(),1); //No new Party has been created as the size is same.
	}

	
	@Test
	public void removeTextFromPartyLabel_LegalCase() {
		test = new createNewInteractionTest();
		test.addNewInteraction_LegalCase();
		assertEquals(test.context.getAllWindows().size() ,1);
		test.context.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 50, 150, 1); //created a party
		assertEquals(test.context.getAllWindows().get(0).getDiagram().getShapeList().size(),1);
		test.context.handleKeyEvent(0, 0, 'a'); //Entering labale Starts here
		test.context.handleKeyEvent(0,0, ':');
		test.context.handleKeyEvent(0, 0,'L');     
		test.context.handleKeyEvent(0, 0, 'M');
		test.context.handleKeyEvent(0, 0, 'P'); // Entering label ends here
		assertTrue(test.context.getAllWindows().get(0).getDiagram().getShapeList().get(0).getLabel().getText().equals("a:LMP")); // verification of the entered text.
		test.context.handleKeyEvent(0, 8, ' '); // remove the last character 
		test.context.handleKeyEvent(0, 8, ' '); // removes the second last character and label should be equal to "a:L"
		assertTrue(test.context.getAllWindows().get(0).getDiagram().getShapeList().get(0).getLabel().getText().equals("a:L"));
	}
	
	

	@Test
	public void labelEnabled_LegalCase() {
		test = new createNewInteractionTest();
		test.addNewInteraction_LegalCase();
		assertEquals(test.context.getAllWindows().size() ,1);
		test.context.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 113,263, 1); //created a party
		assertEquals(test.context.getAllWindows().get(0).getDiagram().getShapeList().size(),1);
		test.context.handleKeyEvent(0, 0, 'a'); 
		//Entering labale Starts here
		
		test.context.handleKeyEvent(0,0, ':');
		test.context.handleKeyEvent(0, 0,'L');     
		test.context.handleKeyEvent(0, 0, 'M');
		test.context.handleKeyEvent(0, 0, 'P'); 
		// Entering label ends here
		// verification of the entered text.
		assertTrue(test.context.getAllWindows().get(0).getDiagram().getShapeList().get(0).getLabel().getText().equals("a:LMP")); 
		test.context.handleKeyEvent(0, KeyEvent.VK_ENTER, ' ');
		assertFalse(test.context.getAllWindows().get(0).getDiagram().getShapeList().get(0).getLabel().isActive());
		PartyShape shape = test.context.getAllWindows().get(0).getDiagram().getShapeList().get(0);
		int x = 0; 
		int y = 0;
		//Because location of label of actor and partyobject is different , i need to check what kind of shape it is.
		if(shape instanceof ActorShapeSequenceView) {
			x = shape.getX()+5;
			y = shape.getY()+shape.getHeight()+2;
		}
		else
		{
			x = shape.getX()+5;
			y = shape.getY()-5;
		}
		//test.context.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 139, 73, 1);
		test.context.handleMouseEvent(MouseEvent.MOUSE_CLICKED, x, y, 1); 
		assertTrue(test.context.getAllWindows().get(0).getDiagram().getShapeList().get(0).getLabel().isActive());
	}
	
	
	
	
	/**
	 * Message label editting case.
	 */
	createNewInteractionTest tests;

	/**
	 * A test-case to test the creation of the message in sequence diagram.
	 */
	@Test
	public void editMessageLabelTest_LegalCase() {
		tests = new createNewInteractionTest();
		tests.addNewInteraction_LegalCase(); //created new Interaction
		assertEquals(tests.context.getAllWindows().size() ,1); // Assertion that a new interaction has been added to the list.
		tests.context.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 156, 91, 1); //create party event.
		assertEquals(tests.context.getAllWindows().get(0).getDiagram().getShapeList().size(),1); //  A partyShape associated with the party has been created.
		assertTrue(tests.context.getAllWindows().get(0).getDiagram().getShapeList().get(0).getLabel().isActive()); // label of the party is active
		tests.context.handleKeyEvent(0, 0, 'a'); //Entering a valid label starts here
		tests.context.handleKeyEvent(0,0, ':');
		tests.context.handleKeyEvent(0, 0,'L');
		tests.context.handleKeyEvent(KeyEvent.VK_ENTER, 10, ' '); // Entered for verification of label
		assertFalse(tests.context.getAllWindows().get(0).getDiagram().getShapeList().get(0).getLabel().isActive());
		
		//Create a second Party
		
		tests.context.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 300, 91, 1); //create party event.
		assertEquals(tests.context.getAllWindows().get(0).getDiagram().getShapeList().size(),2); //  A NEW partyShape associated with the party has been created.
		assertTrue(tests.context.getAllWindows().get(0).getDiagram().getShapeList().get(1).getLabel().isActive()); // label of the party is active
		tests.context.handleKeyEvent(0, 0, 'a'); //Entering a valid label starts here
		tests.context.handleKeyEvent(0,0, ':');
		tests.context.handleKeyEvent(0, 0,'L');
		tests.context.handleKeyEvent(KeyEvent.VK_ENTER, 10, ' '); // Entered for verification of label
		assertFalse(tests.context.getAllWindows().get(0).getDiagram().getShapeList().get(1).getLabel().isActive()); // Label of the second party has been deactivated.
	    //create message
		
		PartyShape first = tests.context.getAllWindows().get(0).getDiagram().getShapeList().get(0);
		PartyShape second = tests.context.getAllWindows().get(0).getDiagram().getShapeList().get(1);
		tests.context.handleMouseEvent(MouseEvent.MOUSE_DRAGGED, first.getLine().getX(), first.getLine().getY()+10, 1); // drag event to create message on first party's line.
		tests.context.handleMouseEvent(MouseEvent.MOUSE_RELEASED, second.getLine().getX(), second.getLine().getY()+10, 1); // released mouse on second objects life line
	    assertEquals(first.getSource().getSenderList().size(),1); // Message created
	    assertEquals(second.getSource().getRecieverList().size(),1); // Message created
	    
	    //labelEditing starts here
	    Message message = first.getSource().getSenderList().get(0);
	    assertTrue(message.isActive());
	    tests.context.getAllWindows().get(0).getDiagram().enterMessageLabelText(message, 'a');
	    tests.context.getAllWindows().get(0).getDiagram().enterMessageLabelText(message, 'M');
	    tests.context.getAllWindows().get(0).getDiagram().enterMessageLabelText(message, 'S');
	    assertTrue(message.getLabelText().contains("aMS")); // Each message has sequence number also
	    tests.context.handleKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_ENTER, ' ');
	    //Label was false , so not verified
	    assertTrue(message.isActive());
	    //Entering valid label
	    tests.context.handleKeyEvent(KeyEvent.KEY_PRESSED,1, '(');
	    tests.context.handleKeyEvent(KeyEvent.KEY_PRESSED,1, ')');
	    //Verify label now
	    tests.context.handleKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_ENTER, ' ');
        //Check if label is active @Expected = label truned off
	    assertFalse(message.isActive());


	}
	
	
	
	private createNewInteractionTest temp;
	

	/**
	 * A test-case to test the creation of the message in sequence diagram.
	 */
	@Test
	public void  removingTextFromMessageLabel_LegalCase() {
		temp = new createNewInteractionTest();
		temp.addNewInteraction_LegalCase(); //created new Interaction
		assertEquals(temp.context.getAllWindows().size() ,1); // Assertion that a new interaction has been added to the list.
		temp.context.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 156, 91, 1); //create party event.
		assertEquals(temp.context.getAllWindows().get(0).getDiagram().getShapeList().size(),1); //  A partyShape associated with the party has been created.
		assertTrue(temp.context.getAllWindows().get(0).getDiagram().getShapeList().get(0).getLabel().isActive()); // label of the party is active
		temp.context.handleKeyEvent(0, 0, 'a'); //Entering a valid label starts here
		temp.context.handleKeyEvent(0,0, ':');
		temp.context.handleKeyEvent(0, 0,'L');
		temp.context.handleKeyEvent(KeyEvent.VK_ENTER, 10, ' '); // Entered for verification of label
		assertFalse(temp.context.getAllWindows().get(0).getDiagram().getShapeList().get(0).getLabel().isActive());
		
		//Create a second Party
		
		temp.context.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 300, 91, 1); //create party event.
		assertEquals(temp.context.getAllWindows().get(0).getDiagram().getShapeList().size(),2); //  A NEW partyShape associated with the party has been created.
		assertTrue(temp.context.getAllWindows().get(0).getDiagram().getShapeList().get(1).getLabel().isActive()); // label of the party is active
		temp.context.handleKeyEvent(0, 0, 'a'); //Entering a valid label starts here
		temp.context.handleKeyEvent(0,0, ':');
		temp.context.handleKeyEvent(0, 0,'L');
		temp.context.handleKeyEvent(KeyEvent.VK_ENTER, 10, ' '); // Entered for verification of label
		assertFalse(temp.context.getAllWindows().get(0).getDiagram().getShapeList().get(1).getLabel().isActive()); // Label of the second party has been deactivated.
	    //create message
		
		PartyShape first = temp.context.getAllWindows().get(0).getDiagram().getShapeList().get(0);
		PartyShape second = temp.context.getAllWindows().get(0).getDiagram().getShapeList().get(1);
		temp.context.handleMouseEvent(MouseEvent.MOUSE_DRAGGED, first.getLine().getX(), first.getLine().getY()+10, 1); // drag event to create message on first party's line.
		temp.context.handleMouseEvent(MouseEvent.MOUSE_RELEASED, second.getLine().getX(), second.getLine().getY()+10, 1); // released mouse on second objects life line
	    assertEquals(first.getSource().getSenderList().size(),1); // Message created
	    assertEquals(second.getSource().getRecieverList().size(),1); // Message created
	    
	    //labelEditing starts here
	    Message message = first.getSource().getSenderList().get(0);
	    assertTrue(message.isActive());
	    temp.context.getAllWindows().get(0).getDiagram().enterMessageLabelText(message, 'a');
	    temp.context.getAllWindows().get(0).getDiagram().enterMessageLabelText(message, 'M');
	    temp.context.getAllWindows().get(0).getDiagram().enterMessageLabelText(message, 'S');
	    assertTrue(message.getLabelText().contains("aMS"));
	    	    
	    //Text after entering verification
	    assertTrue(message.getLabelText().contains("aMS"));
	    //Removed last character by using function in diagramview which calls controller to remove text.
	    temp.context.getAllWindows().get(0).getDiagram().removeMessageLabelText(message);
	    // verification after removing last character
	    assertFalse(message.getLabelText().contains("aMS"));
	    assertTrue(message.getLabelText().contains("aM"));
	}
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
