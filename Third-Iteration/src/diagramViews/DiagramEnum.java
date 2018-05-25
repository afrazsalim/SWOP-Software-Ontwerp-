package diagramViews;

import java.awt.event.MouseEvent;

import shapes.PartyShape;
/**
 * An enum class for suppoting different event in order to avoid the switch statements.
 * @author Afraz Salim
 *
 */
public enum DiagramEnum {
	
	
	Clicked{
		@Override
		public void performAction(DiagramView view, int x, int y) {
			if(view.canForwardRequestToBox(x,y)) 
				view.forwarRequestToDialogBox(x,y);
			else if(view.canActivateABox(x,y))
				view.activateBox(x,y);
			else if(view.canDeSelectedBox(x,y))
				view.deSelectBox();
			else if(view.getMessageUnderConstruction() != null) {}
			else if(view.canDeselectShape(x,y)) {view.deSelectShape();}
			else if(view.isValidPositionToCreateObject(x,y))
        	         view.createNewObject(x,y);
			else view.forwarRequestToShapes(x,y);
			}
			
		},

	DummyValue{
		@Override
		public void performAction(DiagramView view, int x, int y) {			
		}
		
	}, 
	
	doubleClicked{
		@Override
		public void performAction(DiagramView view, int x, int y) {
             view.forwarRequestToShapeDoubleClick(x,y);			
		}
		
	}, 
	
	
	dragged{

		@Override
		public void performAction(DiagramView view, int x, int y) {
			if(view.getSelectedBox() != null) 
				view.forwarDragRequestToBox(x,y);
			else if(view.getMessageUnderConstruction() != null) 
				view.updateMessageSecondPoint(x,y);
			else 
				view.forwardDragRequestToShapes(x,y);			
		}
		
	}, 
	
	released {

		@Override
		public void performAction(DiagramView view, int x, int y) {
           view.mouseReleased(x,y);			
		}
		
	};


	public abstract void performAction(DiagramView view, int x, int y);
}
