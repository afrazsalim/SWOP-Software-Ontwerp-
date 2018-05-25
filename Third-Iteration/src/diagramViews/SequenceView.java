package diagramViews;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import Exceptions.IllegalOperationExcetion;
import domainObjects.Actor;
import domainObjects.Interaction;
import domainObjects.InvocationMessage;
import domainObjects.Message;
import domainObjects.Party;
import domainObjects.PartyObject;
import domainObjects.ResultingMessage;
import shapes.ActivationBarShape;
import shapes.ActorShapeSequenceView;
import shapes.MessageShape;
import shapes.PartyObjectSequenceView;
import shapes.PartyShape;
import shapes.RectangleLineShape;


/**
 * A class representing the sequence view of the diagram.
 * @author Afraz Salim
 *
 */
public class SequenceView extends DiagramView {
	
	
	private int ActorShapeHeight = 70;
	private int actorShapeWidth = 70;
	
	/**
	 * A constructor to create the sequence view.
	 * @param view
	 *        The current view of the diagram.
	 * @param interaction
	 *        The current controller of the diagram with which it sends events to the domain.
	 * @param x
	 *        The x-coordinate of the view.
	 * @param y
	 *        The y-coordinate of they view.
	 * @param width
	 *       The width of the view.
	 * @param height
	 *        The height of the view.
	 */
	public SequenceView(View view, Interaction interaction, int x, int y, int width,int height) {
		super(view, interaction, x, y, width, height);
	}
	
	
	

	
	



	



	protected int getActorShapeWidth() {
		return actorShapeWidth;
	}




	






	
	protected int getActorHeight() {
		return this.ActorShapeHeight;
	}
	

	private int getPartyObjectheight() {
		return 60;
	}



	private int getFixedY() {
		return this.getY() + 100;
	}









	/**
	 * A checker to check whether an object can be created.
	 */
	@Override
	protected boolean canCreate(int x, int y) {
		return true;
	}







	/**
	 * A function to create the party's shape.
	 */
	@Override
	protected void createShape(Party party,int x, int y,PartyShape shape) {
     if(party instanceof Actor)
    	 this.createActorShape(party,x,y,shape);
     else if(party instanceof PartyObject)
    	 this.createPartyObjectShape(party,x,y,shape);
     else throw new IllegalOperationExcetion("Party type not supported");
	}







	private void createActorShape(Party party, int x, int y, PartyShape shapes) {
      PartyShape shape = new ActorShapeSequenceView(this,x, this.getFixedY(), this.getActorShapeWidth(), this.getActorHeight());		
	  shape.setSource(party);
      this.getShapeList().add(shape);
      this.getAssociationList().put(party, shape);
      if(shapes != null) {
        this.copyShapesElements(shape,shapes);
      }
	}






	private void copyShapesElements(PartyShape shape, PartyShape shapes) {
		    this.editLabelByCopy(shape,shapes);
	        shape.getLine().changeBarList(shapes.getLine().getBarList());
	        shape.getLine().updateOwnerShip(shape);
	        this.setSelectedBox(null);
	        this.getDialogBoxList().removeAll(shapes.getDialogBoxList());
	        if(shapes.getDialogBoxList().size() > 0)
	        	this.getDialogBoxList().add(shape.createNewDialogBox());		
	}




	/**
	 * A function which copies label from one party's shape to other.
	 */
	@Override
	protected void editLabelByCopy(PartyShape shape, PartyShape shapes) {
		 shape.getLabel().setText(shapes.getLabel().getText());
	     shape.setLabelActive(false);
	}




	/**
	 * A function to create the copy of the message.
	 */
	@Override
	protected void createCopyMessage(HashMap<Party, PartyShape> map) {
      		for(Party party : this.getPartyList()) {
      			PartyShape shape  = map.get(party);
                this.createInvocationShapes(this.getAssociationList().get(party),shape.getLine().getBarList(),shape);
 			    this.editLabelByCopy(this.getAssociationList().get(party), shape);
      		}
      		for(Party party : this.getPartyList()) {
      			PartyShape shape  = map.get(party);
                this.createResultingShape(this.getAssociationList().get(party),shape.getLine().getBarList(),shape);
      		}
      		this.updateMessageShapeText();
	}









private void createResultingShape(PartyShape shape, ArrayList<ActivationBarShape> list, PartyShape shapes) {
	 list.stream().forEach(e -> {
		   e.getSenderList().stream().forEach(message -> {
			   if(shapes.getDiagram().getShapeMessageMap().get(message) instanceof ResultingMessage) {
				   this.createMessageShape(shape, message, shapes);
			   }
		   });
	});		
}




private void createInvocationShapes(PartyShape shape, ArrayList<ActivationBarShape> list, PartyShape shapes) {
	  list.stream().forEach(e -> {
		   e.getSenderList().stream().forEach(message -> {
			   shapes.getDiagram().getShapeMessageMap().get(message).subscribe(this);
			if(shapes.getDiagram().getShapeMessageMap().get(message) instanceof InvocationMessage) {
			   this.createMessageShape(shape,message,shapes);
			}
		    });
		});		
	}



protected int getYcoordinateOfReciver(PartyShape recieverShape, MessageShape message, PartyShape shapes) {
	if(shapes.getDiagram().getShapeMessageMap().get(message) instanceof InvocationMessage) {
		return (recieverShape.getLine().getY()+Math.abs(message.getySecond()-message.getRecieverShape().getLine().getY()));
	}
	return this.getMessageShapeMap().get(getMessageAssociationMap().get(shapes.getDiagram().getShapeMessageMap().get(message))).getY()+10;
}








/**
 * A function which returns the first y-coordinate of the message'shape.
 */
protected int getYCoordinateOfSender(PartyShape senderShape, MessageShape message, PartyShape shapes) {
	if(shapes.getDiagram().getShapeMessageMap().get(message) instanceof InvocationMessage) {
		return  (senderShape.getLine().getY()+Math.abs(message.getY()-message.getSenderShape().getLine().getY()));
	}
	return this.getMessageShapeMap().get(getMessageAssociationMap().get(shapes.getDiagram().getShapeMessageMap().get(message))).getySecond()+10;
}




/**
	 * A function to move the party's shape.
	 */
	@Override
	public void move(int x, int y, Party source) {
		 if(!this.getInteraction().hasAnyPartyLabelEnabled()) {
		  PartyShape shape = this.getAssociationList().get(source);
		   if(this.canMove(x-getPreviousX(),shape)) {
	           shape.move(shape.getX()+(x-getPreviousX()), this.getFixedY());	
	        }
	    }
	}





	

	

	private boolean canMove(int x, PartyShape shape) {
        if(shape.getX() + x+this.getActorShapeWidth() > this.getX()+this.getWidht())
        	return false;
        if(!shape.contains(getPreviousX(), shape.getY()))
          return false;
        if(shape.getX()+x <this.getX())
        	return false;
		return true;
	}















	private void createPartyObjectShape(Party party, int x, int y, PartyShape shap) {
	      PartyShape shape = new PartyObjectSequenceView(this,x, this.getFixedY(), this.getActorShapeWidth(), this.getPartyObjectheight());		
	      shape.setSource(party);
	      this.getShapeList().add(shape);
	      this.getAssociationList().put(party, shape);
	      if(shap != null) {
	        this.copyShapesElements(shape, shap);
	      }
	}





















	





    /**
     * A function to resize different elements of the diagram.
     */
	@Override
	protected void resize(int width, int height) {
		
	}




	/**
	 * A function transfer messages after converting the party shape.
	 */
	@Override
	protected void transferMessage(Party party, PartyShape shape) {
		       ((RectangleLineShape) shape.getLine()).getSenderList().stream().forEach(message  -> {
		    	   if(shape.getDiagram().getShapeMessageMap().get(message) instanceof InvocationMessage)
		    	      this.drawMessage(message, shape);
		});
		       ((RectangleLineShape) shape.getLine()).getSenderList().stream().forEach(message  -> {
		    	   if(shape.getDiagram().getShapeMessageMap().get(message) instanceof ResultingMessage)
		    	      this.drawMessageResulting(message, shape);
		});
		       
	}

	private void drawMessageResulting(MessageShape message, PartyShape shape) {
		 PartyShape sender = this.getAssociationList().get(message.getSenderShape().getSource());
		 PartyShape reciever = this.getAssociationList().get(message.getRecieverShape().getSource());
		 MessageShape messageShape = sender.getLine().createMessageShape(sender, null, shape.getDiagram().getShapeMessageMap().get(message),this.getMessageShapeMap().get(getMessageAssociationMap().get(shape.getDiagram().getShapeMessageMap().get(message))).getxSecond(),this.getMessageShapeMap().get(getMessageAssociationMap().get(shape.getDiagram().getShapeMessageMap().get(message))).getySecond()+10,getPreviousX(),getPreviousY());
	     reciever.getLine().finishMessage(reciever,messageShape,this.getMessageShapeMap().get(getMessageAssociationMap().get(shape.getDiagram().getShapeMessageMap().get(message))).getX(),this.getMessageShapeMap().get(getMessageAssociationMap().get(shape.getDiagram().getShapeMessageMap().get(message))).getY()+10);		
         this.getMessageShapeMap().put(shape.getDiagram().getShapeMessageMap().get(message),messageShape);
         this.getShapeMessageMap().put(messageShape,shape.getDiagram().getShapeMessageMap().get(message));
	}




	private void drawMessage(MessageShape message, PartyShape shape) {
		   PartyShape sender = this.getAssociationList().get(message.getSenderShape().getSource());
		   PartyShape reciever = this.getAssociationList().get(message.getRecieverShape().getSource());
		   MessageShape messageShape = sender.getLine().createMessageShape(sender, null, shape.getDiagram().getShapeMessageMap().get(message),this.getProperX(sender),this.getProperY(sender),getPreviousX(),getPreviousY());
	       reciever.getLine().finishMessage(reciever,messageShape,this.getProperX(reciever),this.getProperY(reciever));		
           this.getMessageShapeMap().put(shape.getDiagram().getShapeMessageMap().get(message),messageShape);
           this.getShapeMessageMap().put(messageShape,shape.getDiagram().getShapeMessageMap().get(message));
	}
	



	private int getProperY(PartyShape shape) {
		return shape.getLine().getY() + (int)(Math.random() * (((shape.getLine().getY()+shape.getLine().getHeight()) - shape.getLine().getY()) + 1));
	}




	private int getProperX(PartyShape shape) {
		return shape.getLine().getX()-10;
	}




	/**
	 * A getter to get the selected message from partyShape.
	 */
	@Override
	public MessageShape partyWithActiveMessage() {
		for(PartyShape shape : this.getShapeList()) {
			for(ActivationBarShape bar : shape.getLine().getBarList()) {
				for(MessageShape message : bar.getSenderList())
					if(message.isSelected())
						return message;
			}
		}
		return null;
	}

		      
}











	
	




	