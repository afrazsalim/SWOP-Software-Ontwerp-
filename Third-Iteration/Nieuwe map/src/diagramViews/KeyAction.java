package diagramViews;
/**
 * An enum class for supporting differnt key event and to avoid switch statements.
 * @author Afraz Salim
 *
 */
public enum KeyAction implements ClickEventEnum {
	 
	Controrl{

		@Override
		public void performAction(WindowShape shape, int x, int y) {
			// TODO Auto-generated method stub
			
		}
	},
	
	Alt{

		@Override
		public void performAction(WindowShape shape, int x, int y) {
               			
		}
		
	},
	
	Enter{

		@Override
		public void performAction(WindowShape shape, int x, int y) {
			if(shape.canCreateNewDialogBox())
				shape.createNewDialogBox();			
		}
		
	},
	
	
	Tab{
		@Override
		public void performAction(WindowShape shape, int x, int y) {
			if(shape.getDiagram().getPartyWithLabelEnabled() != null) {}
			else if(shape.getDiagram().getSelectedBox() == null)
		           shape.switchView();
		}
		
	}, 
	
	D{

		@Override
		public void performAction(WindowShape shape, int x, int y) {
			if(shape.getPreviousCode() == 17)
			   shape.createNewDiagram();
		}
		
	},


}
