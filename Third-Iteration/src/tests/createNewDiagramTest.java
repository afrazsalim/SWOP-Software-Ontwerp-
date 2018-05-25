package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import diagramViews.WindowContext;

public class createNewDiagramTest {

	WindowContext context = new WindowContext("");

	@Test
	public void createNewDiagram_LegalCase() {
		assertEquals(context.getAllWindows().size(),0);
		context.handleKeyEvent(0,17, ' ');
		context.handleKeyEvent(0, 78, ' ');// New interaction created
		assertEquals(context.getAllWindows().size(),1);
		context.handleKeyEvent(0, 17, ' ');
		context.handleKeyEvent(0, 68, ' ');// New diagram created
		assertEquals(context.getAllWindows().size(),2);
	}

	
}
