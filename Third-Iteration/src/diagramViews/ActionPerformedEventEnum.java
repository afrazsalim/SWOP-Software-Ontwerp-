package diagramViews;

import box.DialogBoxElement;
/**
 * A Enum class which contains different action Event and their function.
 * @author Afraz Salim
 *
 */
public enum ActionPerformedEventEnum  {

	/**
	 * A variable which indicates the close state of the dialogbox.
	 */
	X{

		/**
		 * A function which calls a close dialogbox function if the button is pressed.
		 */
		@Override
		protected void performAction(DiagramView view,DialogBoxElement element) {
          view.closeDialogBox(element);			
		}
		
	};
	/**
	 * A function which will be implemented by the different action events of this enum.
	 * @param view
	 *        The current view of the diagram.
	 * @param element
	 *        The Dialogbox which is selected.
	 */
	protected abstract void performAction(DiagramView view,DialogBoxElement element);


}
