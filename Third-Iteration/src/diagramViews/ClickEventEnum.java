package diagramViews;

/**
 * A interface for Enums which contain different action events and their implementations.
 * @author Afraz Salim
 *
 */
public interface ClickEventEnum {
	/**
	 * A function which will be implemented by all action events .
	 * @param shape
	 *       The shape of the window.
	 * @param x
	 *        The x-coordinate of the event.
	 * @param y
	 *        The y-coordinate of the event.
	 */
    public abstract void performAction(WindowShape shape, int x, int y);

}
