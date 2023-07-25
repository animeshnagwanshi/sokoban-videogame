package com.animesh;

import java.awt.Image;

public class Actor {
	private final int Space=20;
	private int x;
	private int y;
	private Image image;
	
	public Actor(int x,int y) {
		this.x=x;
		this.y=y;
	}
	public Image getImage() {
		return image;
	}
	public void setImage(Image img) {
		image=img;
	}
	public int x() {
		return x;
	}
	
	public int y() {
		return y;
	}
	
	public void setX(int x) {
		this.x=x;
	}
	public void setY(int y) {
		this.y=y;
	}
	public boolean isLeftCollision(Actor actor) {
		return x()-Space==actor.x()&&y()==actor.y();
	}
	public boolean isRightCollision(Actor actor) {
		return x()+Space==actor.x()&&y()==actor.y();
	}
	public boolean  isTopCollision(Actor actor) {
		return y()-Space==actor.y()&&x()==actor.x;
	}
	public boolean isBottomCollision(Actor actor) {
		return y()+Space==actor.y()&&x()==actor.x();
	}
	
	
	

}
