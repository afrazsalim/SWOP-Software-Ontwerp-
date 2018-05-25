package diagramViews;

public enum KeyEventDiagram {
	
	Enter{

		@Override
		public void performAction(DiagramView view,char keyChar) {
			if(view.canCreateMessageDialogBox())
				view.createMessageBox();
			else if(view.canCreatePartyDialogBox())
					view.createDialogBoxForParty();	
			else if(view.canEditMessage())
				view.verifyMessageLabel();
			else if(view.canEditPartyLabel())
				view.verifyPartyLabel();
		}			
		
	},
	
	
   DummyValue{

	@Override
	public void performAction(DiagramView view,char keyChar) {
		
	}
	
	},
     EnterTextParty{

		@Override
		public void performAction(DiagramView view,char keyChar) {
          if(view.canEditPartyLabel())
        	  view.addTextToParty(keyChar);
		}
		}, 
       RemoveParty {
			@Override
			public void performAction(DiagramView view, char keyChar) {
               if(view.canEditPartyLabel())
            	   view.removeParty();
			}
		}, 
       RemoveText {
			@Override
			public void performAction(DiagramView view, char keyChar) {
             	 if(view.canEditPartyLabel())
             		 view.removePartyLabelText();
             	 else if(view.canEditMessage())
             		 view.removeMessageLabelText(view.messageWithLabelActive());
             	
			}
		}, EnterMessageText {
			@Override
			public void performAction(DiagramView view, char keyChar) {
				   if(view.canEditMessage())
		        	  view.addTextToMessage(keyChar);
			}
		};
		
		    

	public abstract void performAction(DiagramView view,char keyChar);

	
}
