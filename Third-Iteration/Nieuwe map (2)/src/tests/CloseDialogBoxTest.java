package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CloseDialogBoxTest {

      createNewInteractionTest interaction = new createNewInteractionTest();
	
	
	@Test
	public void CloseWindowShapeDialogBox_LegalCase() {
		       //Creating a new Interaction
				interaction.addNewInteraction_LegalCase(); 
				//Adding a new DialogBox by entering the keycombination of following keys.
				interaction.context.handleKeyEvent(0, 17, ' ');
				interaction.context.handleKeyEvent(0, 10, ' ');
				//Verify that the dialogbOx has been created, because the size of the dialogbox is now 1.
				assertEquals(interaction.context.getAllWindows().get(0).getDialogBoxList().size(),1);
				//Save the coordinates of close button in vairables
				int xCoordianteOfCloseButton = interaction.context.getAllWindows().get(0).getDialogBoxList().get(0).getChildList().get(0).getX();
				int yCoordinateOfCloseButton = interaction.context.getAllWindows().get(0).getDialogBoxList().get(0).getChildList().get(0).getY();
	            //Click the close button
				interaction.context.getAllWindows().get(0).getDialogBoxList().get(0).getChildList().get(0).mouseClick(xCoordianteOfCloseButton, yCoordinateOfCloseButton);
	            //Verify that the dialogBox has been removed.
	            assertEquals(interaction.context.getAllWindows().get(0).getDialogBoxList().size(),0);
	}
}
