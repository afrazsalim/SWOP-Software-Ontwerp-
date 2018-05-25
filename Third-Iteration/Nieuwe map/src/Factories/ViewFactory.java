package Factories;

import Exceptions.IllegalOperationExcetion;
import diagramViews.CommView;
import diagramViews.DiagramView;
import diagramViews.SequenceView;
import diagramViews.View;
import domainObjects.Interaction;

/**
 * A class representing the Factory to create the view.
 * @author Afraz Salim
 *
 */
public class ViewFactory {

	/**
	 * A private variabel which store the reference to instance of this factory.
	 */
	private static ViewFactory instance = new ViewFactory();

	/**
	 * Returns the instance of the this factory.
	 * @return
	 *       Returns the instance of this factory.
	 */
	public static ViewFactory getInstance() {
		return instance;
	}
	private ViewFactory() {}


	
	/**
	 * A function to create the new diagram.
	 * @param view
	 *        The given view with which the diagram will be created.
	 * @param interaction
	 *        The given controller for the new diagram.
	 * @param x
	 *        The given x-coordinate of the diagram.
	 * @param y
	 *        The given y-coordinate of the diagram.
	 * @param width
	 *        The given width of the diagram.
	 * @param height
	 *        The given height of the diagram.
	 * @return
	 *        Return the new diagram.
	 * @throws IllegalOperationExcetion
	 *        Throws the IllegalOperationExcetion if the view is not supported.
	 */
	public DiagramView createDiagramView(View view, Interaction interaction, int x, int y, int width, int height) {
		if(view == View.SEQUENCE_View)
			return new SequenceView(view,interaction,x,y,width,height);
		else if(view == View.COMM_View)
			return new CommView(view, interaction, x, y, width, height);
		else
			throw new IllegalOperationExcetion("Diagram not supported , unsolveable error");
	}


	
}
