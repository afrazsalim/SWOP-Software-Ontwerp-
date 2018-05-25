package diagramViews;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import Exceptions.IllegalOperationExcetion;
import domainObjects.Actor;
import domainObjects.Interaction;
import domainObjects.InvocationMessage;
import domainObjects.Message;
import domainObjects.Party;
import domainObjects.PartyObject;
import domainObjects.ResultingMessage;
import shapes.ActivationBarShape;
import shapes.ActorShapeCommView;
import shapes.LineShape;
import shapes.MessageShape;
import shapes.PartyObjectCommView;
import shapes.PartyShape;
import shapes.RectangleLineShape;

/**
 * A class represeting the communication view.
 * @author Afraz Salim
 *
 */
public class CommView extends DiagramView {
	
	/**
	 * A private variable which stores the height of the actor in comm view.
	 */
	private int ActorShapeHeight = 90;
	/**
	 * A private variable which stores the width of the actor in comm view.
	 */
	private int actorShapeWidth = 80;
	/**
	 * A private variable which stores the height of the label.
	 */
	private int labelheight = 20;
	/**
	 * A constructor to initialize the view.
	 * @param view
	 *        The current view of the diagram.
	 * @param interaction
	 *        The controller of the diagram.
	 * @param x
	 *        The x coordinate of the diagram.
	 * @param y
	 *        The y-coordinate of the diagram.
	 * @param width
	 *       The width of the diagram.
	 * @param height
	 *       The height of the diagram.
	 */
	public CommView(View view,Interaction interaction,int x, int y, int width, int height) {
		super(view, interaction,x,y,width,height);
	}

	
	
	
	/**
	 * A getter which returns the width of the actor widht.
	 * @return
	 *       Returns the width of the actor.
	 */
	protected int getActorShapeWidth() {
		return actorShapeWidth;
	}

	/**
	 * A setter to set the width of the actor.
	 * @param actorShapeWidth
	 *        The new width of the actor.
	 */
	protected void setActorShapeWidth(int actorShapeWidth) {
		this.actorShapeWidth = actorShapeWidth;
	}

	/**
	 * A getter to get the height of the label.
	 * @return
	 *       Returns the height of the label.
	 */
	protected int getLabelheight() {
		return labelheight;
	}

	/**
	 * A setter to set the height of the label.
	 * @param labelheight
	 *        The new height of the label.
	 */
	protected void setLabelheight(int labelheight) {
		this.labelheight = labelheight;
	}

	/**
	 * A getter to get the height of the actor in comm view.
	 * @return
	 *       Returns the height of the actor.
	 */
	protected int getActorHeight() {
		return ActorShapeHeight;
	}
	

	
	/**
	 * A setter to set the height of the actor.
	 * @param actorShapeHeight
	 *         The height of the actor shape.
	 */
	protected void setActorShapeHeight(int actorShapeHeight) {
		ActorShapeHeight = actorShapeHeight;
	}





	/**
	 * A function to create the shape for the party.
	 */
	@Override
	protected void createShape(Party party, int x, int y, PartyShape shape) {
		 if(party instanceof Actor)
	    	 this.createActorShape(party,x,y,shape);
	     else if(party instanceof PartyObject)
	    	 this.createPartyObjectShape(party,x,y,shape);
	     else throw new IllegalOperationExcetion("Party type not supported");		
	}


	private void createPartyObjectShape(Party party, int x, int y, PartyShape shap) {
		  PartyShape shape = new PartyObjectCommView(this,x,y, this.getActorShapeWidth(), this.getActorHeight());		
	      shape.setSource(party);
	      this.getShapeList().add(shape);
	      this.getAssociationList().put(party, shape);
	      if(shap != null) {
	    	  this.copyOldElementsOfShape(shape,shap);
			 
	      }
	}


	private void copyOldElementsOfShape(PartyShape shape, PartyShape shap) {
	  shap.getLine().updateOwnerShip(shape);
      this.editLabelByCopy(shape, shap);
   	  ((RectangleLineShape) shape.getLine()).setSenderList(((RectangleLineShape) shap.getLine()).getSenderList());
   	  ((RectangleLineShape) shape.getLine()).setRecieverList(((RectangleLineShape) shap.getLine()).getRecieverList());
   	  this.getDialogBoxList().removeAll(shap.getDialogBoxList());		
	}


	private void createActorShape(Party party, int x, int y, PartyShape shape) {
		   PartyShape shap = new ActorShapeCommView(this,x, y, this.getActorShapeWidth(), this.getActorHeight());		
		   shap.setSource(party);
		   this.getShapeList().add(shap);
		   this.getAssociationList().put(party, shap);		
		   if(shape != null) {
			  this.copyOldElementsOfShape(shap, shape);
		   }

	}


	@Override
	protected boolean canCreate(int x, int y) {
		return false;
	}

	Random random = new Random();

	/**
	 * A function to move the party at the give location.
	 * @param x
	 *        The given x-coordinate of the point.
	 * @param y
	 *        The given y-coordinate of the point.
	 * @param party
	 *        The given party whose shape should be moved.
	 */
	@Override
	public void move(int x, int y, Party party) {
		 if(!this.getInteraction().hasAnyPartyLabelEnabled()) {
			  PartyShape shape = this.getAssociationList().get(party);
			  if(this.canMove(shape,(x-getPreviousX()),(y-getPreviousY()))) {
		           shape.move(shape.getX()+(x-getPreviousX()), shape.getY()+(y-getPreviousY()));
			  }
			}		
	     }


	private boolean canMove(PartyShape shape, int x, int y) {
        if(shape.getX()+x +this.getActorShapeWidth() > this.getX()+this.getWidht())
        	return false;
        if(shape.getX()+x < this.getX())
        	return false;
        if(shape.getY()+y+this.getActorHeight() > this.getY()+this.getHeight())
        	return false;
        if(shape.getY()+y < this.getY())
        	return false;
		return true;
	}





	/**
	 * A function which creates coppy messages.
	 */
	@Override
	protected void createCopyMessage(HashMap<Party, PartyShape> map) {
		for(Party party : this.getPartyList()) {
  			PartyShape shape  = map.get(party);
  			PartyShape shapes = map.get(party);
	        ((RectangleLineShape) shapes.getLine()).getSenderList().stream().forEach(message -> {
			   this.createMessageShape(shape,message,shapes);
			   this.editLabelByCopy(shape, shapes);
	     });
		}
	  this.setAllMessageNumberVisible();
	}
	
	



	private void setAllMessageNumberVisible() {
		this.getPartyList().stream().forEach(e -> {
			e.getSenderList().stream().forEach(m -> {
				this.getMessageShapeMap().get(m).setNumbersVisible(true);
			});
		});		
	}




	/**
	 * A function which copies label from one party's shape to other party's shape.
	 */
	@Override
	protected void editLabelByCopy(PartyShape shape, PartyShape shapes) {
       shape.getLabel().setText(shapes.getLabel().getText());
       shape.setLabelActive(false);
	}


	/**
	 * A function to transfer message from one party to other party's shape.
	 */
	@Override
	protected void transferMessage(Party party, PartyShape shape) {
		shape.getLine().getBarList().stream().forEach(e -> {
		   e.getSenderList().stream().forEach(message  -> {
			   PartyShape sender = this.getAssociationList().get(message.getSenderShape().getSource());
			   PartyShape reciever = this.getAssociationList().get(message.getRecieverShape().getSource());
			   MessageShape messageShape = sender.getLine().createMessageShape(sender, null, shape.getDiagram().getShapeMessageMap().get(message),sender.getX(),sender.getY(),getPreviousX(),getPreviousY());
		        messageShape.updateSecondPoint(this.getProperxOfReciever(sender, reciever),this.getProperYSender(messageShape, reciever));
		       reciever.getLine().finishMessage(reciever,messageShape, reciever.getX(), reciever.getY());
		       this.getShapeMessageMap().put(messageShape, shape.getDiagram().getShapeMessageMap().get(message));
		       this.getMessageShapeMap().put(shape.getDiagram().getShapeMessageMap().get(message), messageShape);
		       messageShape.setNumbersVisible(true);
			 });
		});
		
	}



	private int getProperxOfReciever(PartyShape sender, PartyShape reciever) {
		 if(reciever.getX() > sender.getX())
			 return reciever.getX();
		return reciever.getX()+reciever.getWidth();
	}







	private int getProperYSender(MessageShape message, PartyShape sender) {
		return (sender.getY() + (int)(Math.random() * ((sender.getY()+sender.getHeight() - sender.getY()) + 1)));
	}


	/**
	 * A function to get selected message from all parties.
	 */
	@Override
	public MessageShape partyWithActiveMessage() {
		for(PartyShape shape : this.getShapeList()) {
	       for(MessageShape message : ((RectangleLineShape) shape.getLine()).getSenderList()) {
	    	   if(message.isSelected())
					return message;
	       }
		}
		return null;
	}





	


	







}
