package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import diagramViews.WindowContext;

public class createNewInteractionTest {

	WindowContext context = new WindowContext("");
	
	@Test
	public void addNewInteraction_LegalCase() {
		assertEquals(context.getAllWindows().size(),0);
		context.handleKeyEvent(0,17, ' ');
		context.handleKeyEvent(0, 78, ' ');
		assertEquals(context.getAllWindows().size(),1);
	}
	
	
	@Test
	public void addNewInteraction_IllegalCase() {
		assertEquals(context.getAllWindows().size(),0);
		context.handleKeyEvent(0,17, ' ');
		context.handleKeyEvent(0, 7, ' ');
		assertEquals(context.getAllWindows().size(),0);
	}

}

