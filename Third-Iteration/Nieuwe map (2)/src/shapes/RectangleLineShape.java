package shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

import domainObjects.Message;

/**
 * A class representing the rectangle line shape.
 * @author Afraz Salim
 *
 */
public class RectangleLineShape extends LineShape {

	private ArrayList<MessageShape> senderList;
	private ArrayList<MessageShape> recieverList;
	/**
	 * A construtor to create the new instance of rectangle line shape.
	 * @param x
	 *        The given x-coordinate of the rectangle line shape.
	 * @param y
	 *        The given y-coordinate of the rectangle line shape.
	 * @param width
	 *        The given width of the rectangle line shape.
	 * @param height
	 *        The given height of the rectangle line shape.
	 */
	public RectangleLineShape(int x, int y, int width, int height) {
		super(x, y, width, height);
		this.setSenderList(new ArrayList<>());
		this.setRecieverList(new ArrayList<>());
	}

	
	/**
	 * A function to draw the graphics of the line.
	 */
	@Override
	public void draw(Graphics g) {
		  Graphics2D g2 = (Graphics2D) g;
          g2.setColor(Color.WHITE);
	}

	/**
	 * A function to finish the messsage creation process.
	 */
	@Override
	public void finishMessage(PartyShape shape,MessageShape message, int x, int y) {
      		this.getRecieverList().add(message);
      		if(message.getxSecond() > message.getX())
     			message.updateSecondPoint(this.getX(), this.getYOfMessage(y));
     		else
     			message.updateSecondPoint(this.getX()+this.getWidth(), this.getYOfMessage(y));
      		message.setRecieverShape(shape);
	}

	/**
	 * A function to update the point of the line.
	 */
	@Override
	public void updatePoints(int lineX, int lineY) {
		this.getSenderList().stream().forEach(e -> {
			if(e.getX() > e.getxSecond())
				e.setX(this.getX());
			else
				e.setX(this.getX()+this.getWidth());
			e.setY(e.getY()+(lineY-this.getY()));
		});
		this.getRecieverList().stream().forEach(e -> {
			if(e.getX() > e.getxSecond())
				e.setxSecond(this.getX()+this.getWidth());
			else
				e.setxSecond(this.getX());
			 e.setySecond(e.getySecond()+(lineY-this.getY()));
		});
        this.setX(lineX);
        this.setY(lineY);		
	}
	
	/**
	 * A function to remove the message shape.
	 */
	@Override
	public void removeMessage(MessageShape message) {
       	
	}

	/**
	 * A getter to get the list of all message shape .
	 * @return
	 *        Returns the list of all message shapes which are sent from this line.
	 */
	public ArrayList<MessageShape> getSenderList() {
		return senderList;
	}

	/**
	 * A setter to set the senderlist of this line.
	 * @param senderList
	 *        The senderlist which will be set.
	 */
	public void setSenderList(ArrayList<MessageShape> senderList) {
		this.senderList = senderList;
	}
	/**
	 * A getter to get the list of all message shape .
	 * @return
	 *        Returns the list of all message shapes which are recieved from this line.
	 */
	public ArrayList<MessageShape> getRecieverList() {
		return recieverList;
	}
	/**
	 * A setter to set the reciever of this line.
	 * @param recieverList
	 *        The reciever which will be set.
	 */
	public void setRecieverList(ArrayList<MessageShape> recieverList) {
		this.recieverList = recieverList;
	}
	/**
	 * A function to create the message's shape.
	 */
	@Override
	public MessageShape createMessageShape(PartyShape sender, PartyShape reciever, Message message, int x, int y,int previousX,int previousY) {
		    MessageShape shape = new MessageShape(sender, reciever, this.getXofMessage(x),this.getYOfMessage(y),x,y);
	        this.getSenderList().add(shape);
	        if(x > this.getX())
	        	shape.setX(this.getX()+this.getWidth());
	        else
	        	shape.setX(this.getX());
	        shape.setY(this.getYOfMessage(y));
	        shape.setSource(message);
	        return shape;
	}



	private int getYOfMessage(int y) {
		if(y> this.getY()+this.getHeight() || y < this.getY())
		  return getRandomGenerator().nextInt(((this.getY()+this.getHeight()) - this.getY()) + 1) + this.getY();
		return y;
	}



	private int getXofMessage(int x) {
        if(x > this.getX()+5)
        	return this.getX()+this.getWidth();
		return this.getX();
	}



	private Random getRandomGenerator() {
		return new Random();
	}
	/**
	 * A method to update the owner of all the message's shapes.
	 */
	@Override
	public void updateOwnerShip(PartyShape shape) {
		this.getSenderList().stream().forEach(e ->{
			e.setSenderShape(shape);
		});
		this.getRecieverList().stream().forEach(e->{
			e.setRecieverShape(shape);
		});
	}

	/**
	 * A getter to get the message"s shape whose label intersects the given points.
	 */
	@Override
	public MessageShape getIntersectingLabel(int x, int y) {
			for(MessageShape e : this.getSenderList()) {
				if(this.intersectsX(e,x) && this.intersectsY(e, y))
					return e;
		}
		return null;
	}



private boolean intersectsY(MessageShape e, int y) {
		return (y >= e.getY()) && (y <= e.getySecond());
	}



private boolean intersectsX(MessageShape e, int x) {
	return x >= (e.getX()+(e.getxSecond()-e.getX())/2)-5 && x <= (e.getX()+(e.getxSecond()-e.getX())/2)+10;
}


/**
 * A getter to get the message shap's whom's shape intersects the given coordinates.
 */
@Override
public MessageShape hasAnyIntersectingMessage(int x, int y) {
	for(MessageShape message : this.getSenderList()) {
		if(message.hasPoint(x,y))
			return message;
	}
	return null;
}


/**
 * A getter to get the selected message of sent by this line.
 */
@Override
public MessageShape getSelectedMessage() {
	 for(MessageShape shape : this.getSenderList()) {
		   if(shape.isSelected())
			   return shape;
	   }
	return null;
}



	


}
