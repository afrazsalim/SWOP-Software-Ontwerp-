package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.event.MouseEvent;

import org.junit.Test;

public class closeInteractionTest {

	createNewInteractionTest test;
	@Test
	public void closeInteraction_LegalCase() {
          test = new createNewInteractionTest();
       // Initially there is no window
          assertEquals(test.context.getAllWindows().size(),0); 
       // first interaction is created
          test.addNewInteraction_LegalCase();
          // verify that the first window has been created.
          assertEquals(test.context.getAllWindows().size(),1); 
       // clicked on the closed button coordinates
          test.context.handleMouseEvent(MouseEvent.MOUSE_CLICKED,test.context.getInitialWidth(), test.context.getInitialYco()+10, 1);
          // Again size of list is zero , means window has been removed.
          assertEquals(test.context.getAllWindows().size(),0);
	}

}
