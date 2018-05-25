package diagramViews;

import domainObjects.Interaction;
import domainObjects.Message;
import domainObjects.Party;

public enum DomainEventEnum  {
	
	LabelEnabled{

		@Override
		protected void performAction(DiagramView view,Object source, Object oldValue, Object newValue) {
			  view.enabledLabel((Party)newValue,(boolean)newValue);
		}
		
	},
	CreatedParty{

		@Override
		protected void performAction(DiagramView view,Object source, Object oldValue, Object newValue) {
			view.createPartyShape((Interaction)source,(Party)newValue);
		}
		
	},
	
	RemoveMessage{

		@Override
		protected void performAction(DiagramView view,Object source, Object oldValue, Object newValue) {
			     view.removeMessage((Party)source,(Message)oldValue);
		}
		
	},
	RemoveParty{

		@Override
		protected void performAction(DiagramView view,Object source, Object oldValue, Object newValue) {
			view.removePartyShape((Party) oldValue);
		}
		
	},
	ConvertedParty{

		@Override
		protected void performAction(DiagramView view,Object source, Object oldValue, Object newValue) {
			  view.convertPartyType((Party)oldValue,(Party)newValue);
		}
		
	},
	MessageFinished{
		@Override
		protected void performAction(DiagramView view,Object source, Object oldValue, Object newValue) {
			  view.finishMessageCreation((Party)newValue,(Party)source);
		}
		
	},
	LabelVerified{

		@Override
		protected void performAction(DiagramView view,Object source, Object oldValue, Object newValue) {
			  view.setLabelOff((Party) newValue,(String) oldValue);
		}
		
	},
	MessageCreated{

		@Override
		protected void performAction(DiagramView view,Object source, Object oldValue, Object newValue) {
                       view.createMessageShape((Party)source, (Message)newValue);			
		}
		
	}, 
	NullEvent{

		@Override
		protected void performAction(DiagramView view, Object source, Object oldValue, Object newValue) {
               //I'm dummy variable			
		}
		
	}, MessagelabelVerfied {
		@Override
		protected void performAction(DiagramView view, Object source, Object oldValue, Object newValue) {
             view.verifyMessageLabel((Message) source,(String)newValue);			
		}
	}, MessageLabelEdited {
		@Override
		protected void performAction(DiagramView view, Object source, Object oldValue, Object newValue) {
               view.populateBox((Message)source);			
		}
	};

	protected abstract void performAction(DiagramView view,Object source, Object oldValue, Object newValue);
	
}
