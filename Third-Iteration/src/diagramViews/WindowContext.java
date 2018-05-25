package diagramViews;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import Exceptions.IllegalOperationExcetion;
import domainObjects.Interaction;

/**
 * A class responsible for the context switches between the windows.
 * @author Afraz Salim
 *
 */
public class WindowContext extends CanvasWindow {

	private ArrayList<WindowShape> shape;
	private int previousKeyCode;
	private int initialXco = 10;
	private int initialYco = 10;
	private int initialWidth = 500;
	private int initialHeight = 450;
	private WindowShape activeWindow;

	
	/**
	 * A constructor to create the instance of window context.
	 * @param title
	 *         The title of the window.
	 */
	public WindowContext(String title) {
		super(title);
		this.setShape(new ArrayList<>());
	}
	
	
	
	

	/**
	 * The main function of the program.
	 * @param args
	 *        Given initial arguments.
	 */
	public static void main(String[] args) {
		      java.awt.EventQueue.invokeLater(() -> {
		          new WindowContext("My Canvas Window").show();
		      });
		  }
	
	
	
	
	
	/**
	 * A function to paint the different components.
	 * @param g
	 *        The given instance of graphics with which different components paint themselves.
	 */
	@Override
	protected void paint(Graphics g) {
	    this.initGui(g);
	    if(this.getActiveWindow() != null) {
	    	this.getAllWindows().stream().forEach(e -> e.paint(g));
		    this.getActiveWindow().paint(g);
	    }
	   
	}
	
	

	

	/**
	 * Initializes the initial window.
	 * @param g
	 *        The given graphics instance with which different components paint themselves.
	 */
	private void initGui(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(Color.darkGray);
		g2.fillRect(0, 0, super.panel.getWidth(),super.panel.getHeight());
	   }
	
	

	/**
	 * A function which handles the mouse events.
	 */
	@Override
	public void handleMouseEvent(int id, int x, int y, int clickCount) {
		try {
			  this.switchWindow(x, y);
		}catch (IllegalOperationExcetion exc)
		{
			System.out.println(exc.toString());
		}
          if(this.getActiveWindow() != null)
        	  this.getActiveWindow().performMouseAction(id,x,y,clickCount);
          this.repaint();
   }
	
	
	
	
	
	
	/**
	 * A function responsible for context switches.
	 * @param x
	 *        The x-coordinate where mouse was clicked.
	 * @param y
	 *        The y-coordinate where mouse was clicked.
	 */
	private void switchWindow(int x, int y) {
		if(this.getActiveWindow() == null)
			throw new IllegalOperationExcetion("No window is active");
		if(!this.getActiveWindow().contains(x, y) || !(this.getActiveWindow().getDiagram().contains(x, y))) {
			for(WindowShape shape : this.getAllWindows()) {
				if (shape.contains(x, y) && !(this.getActiveWindow().equals(shape))) {
					shape.setActive(true);
					this.getActiveWindow().setActive(false);
					this.setActiveWindow(shape);
				}
			}
		}
	}





	/**
	 * A function which handles the key events.
	 */
	@Override
	public void handleKeyEvent(int id, int keyCode, char keyChar) {
		if(this.getPreviousKeyCode() == 17 && keyCode == 78)
			this.createNewInteraction();
		else if(this.getActiveWindow() != null)
			this.getActiveWindow().handleKeyEvent(id,keyCode,keyChar);
		else 
			this.setPreviousKeyCode(keyCode);
		this.repaint();
	}

	
	
	
	/**
	 * A function which creates a new diagram.
	 */
	private void createNewInteraction() {
		Interaction interaction = new Interaction();
		WindowShape shape = new WindowShape(interaction,View.SEQUENCE_View,this.getInitialXco(),this.getInitialYco(),this.getInitialWidth(),this.getInitialHeight());
	    shape.setWindowContext(this);
		this.getAllWindows().add(shape);
		this.setActiveWindow(shape);
		this.adjustCounter(500, 450);
		this.repaint();
	}




	private int counter;
	private void adjustCounter(int width, int heigth) {
		this.setCounter(this.getCounter()+1);
		if(this.getCounter() == 1)
			this.setInitialXco(this.getInitialXco()+width+15);
		else if(this.getCounter() == 2) {
			this.setInitialYco(this.getInitialYco()+this.getInitialHeight());
			this.setInitialXco(this.getInitialXco()-width);
		}
		else if(this.getCounter() == 3) {
			this.setInitialXco(this.getInitialXco()+width);
		}
	}




	private void setCounter(int counter) {
		this.counter = counter;
	}




	private int getCounter() {
		return this.counter;
	}





	protected WindowShape getActiveWindow() {
		return this.activeWindow;
	}

	private void setActiveWindow(WindowShape shape) {
       this.activeWindow = shape;  		
	}




	/**
	 * Returns all the window along with their diagrams.
	 * @return
	 *        Returns all the windows.
	 */
	public ArrayList<WindowShape> getAllWindows() {
		return shape;
	}


	private void setShape(ArrayList<WindowShape> shape) {
		this.shape = shape;
	}




	private int getPreviousKeyCode() {
		return previousKeyCode;
	}




	private void setPreviousKeyCode(int previousKeyCode) {
		this.previousKeyCode = previousKeyCode;
	}

	
	
	



	private int getInitialXco() {
		return initialXco;
	}




	private void setInitialXco(int initialXco) {
		this.initialXco = initialXco;
	}




	/**
	 * A getter ot get the initial y-coordiante of the window.
	 * @return 
	 *      Returns the initial y-coordinate of the window.
	 */
	public int getInitialYco() {
		return initialYco;
	}




	private void setInitialYco(int initialYco) {
		this.initialYco = initialYco;
	}




	/**
	 * A getter to get the initial width of the window.
	 * @return
	 *      Returns the initial width of the window.
	 */
	public int getInitialWidth() {
		return initialWidth;
	}




	private void setInitialWidth(int initialWidth) {
		this.initialWidth = initialWidth;
	}




	private int getInitialHeight() {
		return initialHeight;
	}




	private void setInitialHeight(int initialHeight) {
		this.initialHeight = initialHeight;
	}





	/**
	 * Adds a new window to the list.
	 * @param window
	 *        The new window to be added to the list.
	 *        
	 */
	protected void add(WindowShape window) {
		this.getAllWindows().add(window);
		this.setActiveWindow(window);
	}



	


	/**
	 * Removes an interaction window from the interactions list.
	 * @param window
	 *        The interaction window which should be removed.
	 */
	public void remove(WindowShape window) {
       this.getAllWindows().remove(window);
       this.setCounter(this.getCounter()-1);
       if(this.getActiveWindow().equals(window)) {
    	   this.setActiveWindow(null);
       if(this.getAllWindows().size() > 0) {
    	   this.setActiveWindow(this.getAllWindows().get(0));
           this.getActiveWindow().setActive(true);
       }
       }
	}

	


	
}
