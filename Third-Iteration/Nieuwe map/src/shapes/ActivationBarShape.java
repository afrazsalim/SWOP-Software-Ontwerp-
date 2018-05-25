package shapes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.ArrayList;

import diagramViews.DiagramView;
import domainObjects.Message;

/**
 * A class representing the shape of the activationBar.
 * @author Afraz Salim
 *
 */
public class ActivationBarShape implements Serializable {


	private static final long serialVersionUID = 8053623062668992854L;
	private int x;
	private int y;
	private int width = 16;
	private int height = 30;
	private ArrayList<MessageShape> senderList;
	private ArrayList<MessageShape> recieverList;
	
	/***
	 * A constructor to create the shape of the activationbar.
	 * @param x
	 *        The x-coordinat of the activaitnbar's shape.
	 * @param y
	 *       The y-coordinate of the activation bar's shape.
	 */
	public ActivationBarShape(int x , int y) {
         this.setX(x);
         this.setY(y);
         this.setSenderList(new ArrayList<>());
         this.setRecieverList(new ArrayList<>());
	}
	
	/**
	 * A getter to get the shape of teh activaiton bar.
	 * @return
	 *        Returns the shape of the activationbar.
	 */
	public Shape getActivationBarShape() {
		Shape rect  = new Rectangle2D.Double(this.getX(),this.getY(),this.getWidth(),this.getHeight());
		return rect;
	}

	/**
	 * A function to check if the shape of the activationbar contains the given point.
	 * @param x
	 *        The x-cooordinate of the point.
	 * @param y
	 *        The y-coordinate of the point.
	 * @return
	 *        Returns true if the shape contains the point.
	 */
	public boolean contains(int x, int y) {
		if(x >= this.getX() && x <=this.getX()+this.getWidth() && y >= this.getY() && y <= this.getY()+this.getHeight())
			return true;
		return false;
	}

	protected int getX() {
		return x;
	}

	protected void setX(int x) {
		this.x = x;
	}

	protected int getY() {
		return y;
	}

	protected void setY(int y) {
		this.y = y;
	}

	protected int getWidth() {
		return width;
	}

	protected void setWidth(int width) {
		this.width = width;
	}

	protected int getHeight() {
		return height;
	}

	protected void setHeight(int height) {
		this.height = height;
	}

	protected MessageShape createMessageShape(Message message, int x, int y,PartyShape sender,PartyShape reciever) {
        MessageShape shape = new MessageShape(sender,reciever,this.getSecondXOfMessage(x),y+2,x,y);
        this.getSenderList().add(shape);
        shape.setSource(message);
        return shape;
	}

	private int getSecondXOfMessage(int x) {
		if(DiagramView.getPreviousX() > x)
			return this.getX();
		return this.getX()+this.getWidth();
	}


	/**
	 * A getter to get the list of all message shape which depart from here.
	 * @return
	 *       Returns the list of all message shape which depart from here.
	 */
	public ArrayList<MessageShape> getSenderList() {
		return senderList;
	}
	
	protected int getSize(){
		return this.senderList.size();
	}

	protected void setSenderList(ArrayList<MessageShape> senderList) {
		this.senderList = senderList;
	}

	protected ArrayList<MessageShape> getRecieverList() {
		return recieverList;
	}

	protected void setRecieverList(ArrayList<MessageShape> recieverList) {
		this.recieverList = recieverList;
	}


	protected void draw(Graphics2D g) {
		g.draw(this.getActivationBarShape());		
		g.setColor(Color.GREEN);
		g.fill(getActivationBarShape());
		g.setColor(Color.WHITE);
	}


	
	protected void finishMessage(MessageShape message, int x, int y) {
		this.getRecieverList().add(message);
 		if(message.getxSecond() > message.getX())
 			message.updateSecondPoint(this.getX(), y);
 		else
 			message.updateSecondPoint(this.getX()+this.getWidth(), y);
	}


	protected void moveTo(int x, int y) {
		this.getSenderList().stream().forEach(e -> {
			if(e.getX() > e.getxSecond())
				e.setX(this.getX()+x);
			else
				e.setX(this.getX()+this.getWidth()+x);
			e.setY(e.getY()+y);
		});
		this.getRecieverList().stream().forEach(e -> {
			e.setxSecond(e.getxSecond()+x);
			e.setySecond(e.getySecond()+y);
		});
		this.setX(this.getX()+x);
		this.setY(this.getY()+y);
	}

	

	protected boolean hasSentThisMessage(MessageShape message) {
		return this.getSenderList().contains(message);
	}


	protected void remove(MessageShape message) {
        this.getSenderList().remove(message);		
	}


	protected void addToSenderList(MessageShape messageShape) {
        this.getSenderList().add(messageShape);		
	}


}
