package diagramViews;

import java.awt.event.MouseEvent;

import Exceptions.IllegalOperationExcetion;

/**
 * A class which has many click Events of the diagram.
 * @author Afarz Salim
 *
 */
public enum WindowShapeClickEvent implements ClickEventEnum {
		
	
    ClickEvent{
		@Override
		/**
		 * A function which performs different clickEvents.
		 */
		public void performAction(WindowShape shape, int x, int y) {
			if(shape.canActivateADialogBox(x,y))
				shape.selectBox(x,y);
			if(shape.canForwarRequestToDialogBox(x,y)) 
				 shape.forwarRequestToDialogBox(x,y);
			 else if(shape.closeButtonContains(x,y)) 
		    	  shape.closeWindow();
			else if(shape.canSetActiveToMove(x,y))
				shape.setActiveToMove(true);	
			else if(shape.sizeButtonContains(x,y))
		    	  shape.setDimensionMax(x,y);
			else if(shape.getSelectedBox() != null)
					shape.setOffSelectedBox();	
		 };
    },
    
    
    
    MouseDragged {
    	/**
    	 * A function which performs different drag events.
    	 */
		@Override
		public void performAction(WindowShape shape, int x, int y) {
			if(shape.canMove(x,y))
				shape.translate(x, y);    
		    else if(shape.getSelectedBox() != null) 
			  shape.getSelectedBox().drag(x, y, shape.getPreviousX(), shape.getPreviousY());
		    else if(shape.canResize(x,y)) {
		    	 shape.startResizing(x,y);
		    }
		}
    }, 
    
    
    
    MouseReleased{
    	/**
    	 * A function which performs different Released button events.
    	 */
		@Override
		public void performAction(WindowShape shape, int x, int y) {
			if(shape.canRemoveMovingAllowance(x, y))
				shape.setActiveToMove(false);	
		}
    	
    }
    
    
    
    
    
    
    
    
}
