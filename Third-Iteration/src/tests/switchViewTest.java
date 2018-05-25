package tests;

import static org.junit.Assert.*;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import org.junit.Test;

import diagramViews.CommView;
import diagramViews.SequenceView;
import diagramViews.View;
import diagramViews.WindowContext;

public class switchViewTest {

	WindowContext context = new WindowContext("");

	@Test
	public void switchView_LegalCase() {
		
		assertEquals(context.getAllWindows().size(),0);
		context.handleKeyEvent(0,17, ' ');
		context.handleKeyEvent(0, 78, ' ');// New interaction created
		assertEquals(context.getAllWindows().size(),1);
		context.handleKeyEvent(0, 17, ' ');
		context.handleKeyEvent(0, 68, ' ');// New diagram created
		assertEquals(context.getAllWindows().size(),2);
		assertTrue(context.getAllWindows().get(1).getView().equals(View.SEQUENCE_View));
		context.handleKeyEvent(0, 9, ' ');
       assertTrue(context.getAllWindows().get(1).getView().equals(View.COMM_View));
	}
	
	
	@Test
	public void switchView_IllegalCase() {
		assertEquals(context.getAllWindows().size(),0);
		context.handleKeyEvent(0,17, ' ');
		context.handleKeyEvent(0, 78, ' ');
		assertEquals(context.getAllWindows().size(),1);
		assertTrue(context.getAllWindows().get(0).getView().equals(View.SEQUENCE_View));
		context.handleMouseEvent(MouseEvent.MOUSE_CLICKED, 50, 150, 1); //created a party
		context.handleKeyEvent(0, 9, ' ');
		assertTrue(context.getAllWindows().get(0).getView().equals(View.SEQUENCE_View));
	}

	
	createMessageTest messageTest;
	@Test
	public void switchViewWithPartiesWithMessage_LegalCase() {
		messageTest = new createMessageTest();
		messageTest.createMessageTest_LegalCase();
		//Verify that the initial view is sequence view
		assertTrue(messageTest.test.context.getAllWindows().get(0).getDiagram() instanceof SequenceView);
		messageTest.test.context.handleKeyEvent(KeyEvent.KEY_PRESSED, 9, ' ');
		//Verify that the view has been switched.
		assertTrue(messageTest.test.context.getAllWindows().get(0).getDiagram() instanceof CommView);
		//Verify that the message is also copied.
		assertEquals(messageTest.test.context.getAllWindows().get(0).getDiagram().getShapeList().get(0).getSource().getSenderList().size(),1);
	}
	
	
	
	
	
	
	
	
	
	
	
	

}
