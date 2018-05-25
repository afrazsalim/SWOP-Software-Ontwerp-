package shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import domainObjects.InvocationMessage;
import domainObjects.Message;

/**
 * A class representing the straight line shape.
 * @author Afraz Salim
 *
 */
public class StraightLineShape extends LineShape {

	/**
	 * A construtor to create the new instance of straight line shape.
	 * @param x
	 *        The given x-coordinate of the straight line shape.
	 * @param y
	 *        The given y-coordinate of the straight line shape.
	 * @param width
	 *        The given width of the straight line shape.
	 * @param height
	 *        The given height of the straight line shape.
	 */
	public StraightLineShape(int x, int y, int width, int height) {
		super(x, y, width, height);
	}
	
	/**
	 * A function which draw the lineShapes.
	 */
	@Override
	public void draw(Graphics g) {
		  Graphics2D g2 = (Graphics2D) g;
          g2.setColor(Color.WHITE);
	}

	/**
	 * A function to create the message's shape.
	 */
	@Override
	public MessageShape createMessageShape(PartyShape sender,PartyShape reciever,Message message, int x, int y,int previousX,int previousY) {
		if(this.getActivationBarAt(x, y) != null && message instanceof InvocationMessage) {
			ActivationBarShape bar = this.createNewActivationBarAt(this.getActivationBarAt(x, y).getX()+this.getIndex(previousX,x)*10,y);
			this.getBarList().add(bar);
       	    return bar.createMessageShape(message,bar.getX()-(10*this.getIndex(previousX, x)), y,sender,reciever);
		}
		else if(this.getActivationBarAt(x, y) != null) {
        	 return this.getActivationBarAt(x, y).createMessageShape(message,this.getActivationBarAt(x, y).getX()+this.getIndexOfMessage(previousX, x)*this.getActivationBarAt(x, y).getWidth(), this.getActivationBarAt(x, y).getY()+2,sender,reciever);
        }else {
        	ActivationBarShape bar = this.createNewActivationBarAt(this.getX()-8,y);
       	     this.getBarList().add(bar);
       	   return bar.createMessageShape(message,bar.getX()+this.getIndexOfMessage(previousX, x)*bar.getWidth(), bar.getY()+4,sender,reciever);
        }
	}
	


	private int getIndexOfMessage(int previousX, int x) {
		if(x < previousX)
			return -1;
		return 1;
	}

	private int getIndex(int previousX, int x) {
		if(x < this.getX())
			return -1;
		return 1;
	}

	/**
	 * A getter to get the activation bar which interscts at given coordinates.
	 * @param x
	 *        The given x-coordinate which will be checked.
	 * @param y
	 *        The given y-coordinate which will be checked.
	 * @return
	 *        Returns the activation bar shape which intersects the given points.
	 */
	protected ActivationBarShape getActivationBarAt(int x, int y) {
		for(ActivationBarShape shape : this.getBarList())
			if(shape.contains(x, y))
				return shape;
		return null;
	}






	/**
	 * A function to create new activationbar at given coordinates.
	 * @param x
	 *        The given x-coordinate on which bar's shape will be created.
	 * @param y
	 *        The y-coordinate on which bar's shape will be created.
	 * @return
	 *        Returns the newly created activation bar shape.
	 */
	private ActivationBarShape createNewActivationBarAt(int x, int y) {
		int height = 30;
		while(y+height > this.getY()+this.getHeight())
			height = height/2;
		ActivationBarShape shape  = new ActivationBarShape(x,y);
		shape.setHeight(height);
		return shape;
	}
	

	/**
	 * A function to finish the message creation process.
	 */
	@Override
	public void finishMessage(PartyShape shape,MessageShape message, int x, int y) {
           if(this.getActivationBarAt(x, y) != null) {
        	   this.getMostExtendBar(x, y, message).finishMessage(message,x,y);
           }
           else {
        	   ActivationBarShape bar = this.createNewActivationBarAt(this.getX()-5, y);
        	   this.getBarList().add(bar);
        	   bar.finishMessage(message, x, y);
           }
           message.setRecieverShape(shape);
        	   
	}
	



	

	
	private ActivationBarShape getMostExtendBar(int x, int y, MessageShape message) {
		if(message.getxSecond() < this.getX())
			return this.lookLeft(x,y,message);
		return this.lookRight(x,y,message);
	}

	private ActivationBarShape lookRight(int x, int y, MessageShape message) {
		ActivationBarShape temp = this.getActivationBarAt(x, y);
		assert(temp != null);
		for(int i = 0; i < this.getBarList().size() ;i++) {
			if(this.getBarList().get(i).equals(temp) && temp.getX() < this.getBarList().get(i).getX())
				temp = this.getBarList().get(i);
		}
		return temp;
	}

	private ActivationBarShape lookLeft(int x, int y, MessageShape message) {
		ActivationBarShape temp = this.getActivationBarAt(x, y);
		assert(temp != null);
		for(int i = 0; i < this.getBarList().size() ;i++) {
			if(!(this.getBarList().get(i).equals(temp)) && temp.getX() > this.getBarList().get(i).getX())
				temp = this.getBarList().get(i);
		}
		return temp;
	}

	/**
	 * A function to update the end points of the message shapes.
	 */
	@Override
	public void updatePoints(int lineX, int lineY) {
        this.getBarList().stream().forEach(e ->
        	e.moveTo(lineX-this.getX(), lineY-this.getY()));
        this.setX(lineX);
        this.setY(lineY);
	}

	/**
	 * A function to remove the message shape.
	 */
	@Override
	public void removeMessage(MessageShape message) {
		ArrayList<ActivationBarShape> temp = new ArrayList<>();
        for(ActivationBarShape bar : this.getBarList()) {
        	if(bar.hasSentThisMessage(message))
        		bar.remove(message);
        	if(bar.getRecieverList().contains(message))
        		bar.remove(message);
        	if(bar.getSenderList().size() == 0 )
        		temp.add(bar);
        }
        this.getBarList().removeAll(temp);
	}

	/**
	 * A method to update the owner of all the message's shapes.
	 */
	@Override
	public void updateOwnerShip(PartyShape shape) {
       this.getBarList().stream().forEach(e -> {
    	   e.getSenderList().stream().forEach(message -> {
    		   message.setSenderShape(shape);
    	   });
    	   e.getRecieverList().stream().forEach(message -> {
    		   message.setRecieverShape(shape);
    	   });
       });		
	}

	/**
	 * A getter to get the message"s shape whose label intersects the given points.
	 */
	@Override
	public MessageShape getIntersectingLabel(int x, int y) {
		for(ActivationBarShape e : this.getBarList()) {
			for(MessageShape message: e.getSenderList()) {
				if(message.hasLabelPlaceIntersecting(x,y)) {
					return message;
				}
			}
		}
		return null;
	}

	/**
	 * A getter to get the message shap's whom's shape intersects the given coordinates.
	 */
	@Override
	public MessageShape hasAnyIntersectingMessage(int x, int y) {
		for(ActivationBarShape bar : this.getBarList()) {
			for(MessageShape message : bar.getSenderList()) {
				if(message.hasPoint(x,y))
					return message;
			}
		}
		return null;
	}

	/**
	 * A getter to get the selected message of sent by this line.
	 */
	@Override
	public MessageShape getSelectedMessage() {
       for(ActivationBarShape bar : this.getBarList()) {
    	   for(MessageShape shape : bar.getSenderList()) {
    		   if(shape.isSelected())
    			   return shape;
    	   }
       }
		return null;
	}

	
	


	
	
	


	
	

}
