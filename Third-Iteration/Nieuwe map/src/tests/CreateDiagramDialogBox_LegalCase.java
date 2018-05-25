package tests;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import diagramViews.WindowContext;

class CreateDiagramDialogBox_LegalCase {

	createNewInteractionTest interaction = new createNewInteractionTest();
	
	
	@Test
	public void createNewDiagramDialogBox_LegalCase() {
		interaction.addNewInteraction_LegalCase();
		interaction.context.handleKeyEvent(0, 17, ' ');
		interaction.context.handleKeyEvent(0, 10, ' ');
		assertEquals(interaction.context.getAllWindows().get(0).getDialogBoxList().size(),1);
	}
	

}
