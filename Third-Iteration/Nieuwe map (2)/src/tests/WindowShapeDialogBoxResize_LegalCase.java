package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.event.MouseEvent;

import org.junit.jupiter.api.Test;

import box.DialogBox;

class WindowShapeDialogBoxResize_LegalCase {
    
	
    createNewInteractionTest interaction = new createNewInteractionTest();

	
	@Test
	public void ResizeDialogBox_LegalCase() {
		//Creating a new Interaction
		interaction.addNewInteraction_LegalCase(); 
		//Adding a new DialogBox by entering the keycombination of following keys.
		interaction.context.handleKeyEvent(0, 17, ' ');
		interaction.context.handleKeyEvent(0, 10, ' ');
		//Verify that the dialogbOx has been created, because the size of the dialogbox is now 1.
		assertEquals(interaction.context.getAllWindows().get(0).getDialogBoxList().size(),1);
		//Take out the dialogBox on which the operations will be performed(for simplicity)
		DialogBox box = interaction.context.getAllWindows().get(0).getDialogBoxList().get(0);
		//Selected the dialogBox
		interaction.context.handleMouseEvent(MouseEvent.MOUSE_CLICKED, box.getX()+2, box.getY()+2,1);
		//Verify that the dialogBox has been selected
		assertEquals(interaction.context.getAllWindows().get(0).getSelectedBox(),box);
		//Save the current width of the box , before chaning it.
		int width = box.getWidth();
		//Resize events for the dialogBox .
		interaction.context.handleMouseEvent(MouseEvent.MOUSE_CLICKED, box.getX()+box.getWidth()-2, box.getY()+box.getHeight()/2,1);
		interaction.context.handleMouseEvent(MouseEvent.MOUSE_DRAGGED, box.getX()+box.getWidth()+2, box.getY()+box.getHeight()/2, 1);
	   //New width is: old width + difference between two drag events. (-2 _ +2 ) -> (-2 -1 0 1 2) -> 4 pixels, if we leave zero out.
		assertEquals(box.getWidth(),width+4);
				
	}

}
