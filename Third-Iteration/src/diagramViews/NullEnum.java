package diagramViews;

/**
 * A enum class which introduces the nullEvent.
 * @author Afraz Salim
 *
 */
public enum NullEnum implements ClickEventEnum {
	
	/**
	 * A null Event for allowing polymorphism.
	 */
	NullEnum{
		/**
		 * A function which does nothing.
		 */
		@Override
		public void performAction(WindowShape shape, int x, int y) {
             //Help function for allowing polymorphism
		}
		
	};
	
	

}
