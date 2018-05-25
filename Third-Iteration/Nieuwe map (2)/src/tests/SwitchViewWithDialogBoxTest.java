package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import button.Button;
import button.RadioButton;
import diagramViews.View;

class SwitchViewWithDialogBoxTest {

    createNewInteractionTest interaction = new createNewInteractionTest();
	
	
	@Test
	public void SwitchDiagramViewWIthDialogBox_LegalCase() {
		//Creating a new Interaction
		interaction.addNewInteraction_LegalCase(); 
		//Adding a new DialogBox by entering the keycombination of following keys.
		interaction.context.handleKeyEvent(0, 17, ' ');
		interaction.context.handleKeyEvent(0, 10, ' ');
		//Verify that the dialogbOx has been created, because the size of the dialogbox is now 1.
		assertEquals(interaction.context.getAllWindows().get(0).getDialogBoxList().size(),1);
		
		//Verify the current view of the window.
		assertEquals(interaction.context.getAllWindows().get(0).getDiagram().getView(),View.SEQUENCE_View);
		//Save the coordinates of the button.
		int xCoordianteOfButton = interaction.context.getAllWindows().get(0).getDialogBoxList().get(0).getChildList().get(1).getX();
		int yCoordinateOfButton = interaction.context.getAllWindows().get(0).getDialogBoxList().get(0).getChildList().get(1).getY();
		//Press the button
		interaction.context.getAllWindows().get(0).getDialogBoxList().get(0).getChildList().get(1).mouseClick(xCoordianteOfButton+2, yCoordinateOfButton+2);
		
		//Verify the new View
		assertEquals(interaction.context.getAllWindows().get(0).getDiagram().getView(),View.COMM_View);
	}
	

}
