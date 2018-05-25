package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import box.DialogBox;
import button.Button;
import diagramViews.DiagramView;
import domainObjects.Actor;
import shapes.PartyShape;

class ConvertPartyTypeWIthDialogBoxTest {

	CreatePartyDialogBoxTest partyBox;
	
	@Test
	public void ConvertPartyTypeWidthDialogBox_LegalCase() {
		partyBox = new CreatePartyDialogBoxTest();
		partyBox.CreatePartyDialogBox_LegalCase();
		DialogBox box = partyBox.test.context.getAllWindows().get(0).getDiagram().getDialogBoxList().get(0);
		PartyShape oldShape = partyBox.test.context.getAllWindows().get(0).getDiagram().getShapeList().get(0);
		Button button = null;
		if(oldShape.getSource() instanceof Actor)
			button = (Button) box.getChildList().get(1);
		else
			button = (Button) box.getChildList().get(2);
		assertTrue(button != null);
		partyBox.test.context.handleMouseEvent(MouseEvent.MOUSE_CLICKED, box.getX()+2, box.getY()+2,1);
		assertTrue(box.getIsActive());
		button.mouseClick(button.getX()+1, button.getY()+1);
		assertFalse(oldShape.equals(partyBox.test.context.getAllWindows().get(0).getDiagram().getShapeList().get(0)));
	}

}
