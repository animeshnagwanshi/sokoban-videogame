package com.animesh;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;

import javax.swing.JPanel;
public class Board extends JPanel {
     private final int Offset=30;
     private final int Space=20;
     private final int Left_Collision=1;
     private final int Right_Collision=2;
     private final int Top_Collision=3;
     private final int Bottom_Collision=4;
     
     private ArrayList<Wall> walls;
     private ArrayList<Baggage> baggs;
     private ArrayList<Area> areas;
     
     private Player soko;
     private int h=0;
     private int w=0;
     public boolean isCompleted=false;
     
     private String level=
    		 "     ######\n"
    		+"     ##   #\n"
    		+"     ##$  ###\n"
    		+"   ####   $ ##\n"
    		+"   ##   $ $  #\n"
    		+"####    # ## #      #####\n"
    		+"##     #  ## ######## ..#\n"
    		+"## $  $               ..#\n"
    		+"######   ###  #@##    ..#\n"
    		+"     ##         #########\n"
    		+"      ###########\n";
     
     public Board()
     {
    	 initBoard();
     }
     
     private void initBoard() {
    	 addKeyListener(new TAdapter());
    	 setFocusable(true);
    	 initWorld();
     }
     
     public int getBoardWidth() {
    	 return this.w;
     }
     
     public int getBoardHeight() {
    	 return this.h;
     }
     
     public void initWorld() {
    	 walls= new ArrayList<Wall>();
    	 baggs = new ArrayList<Baggage>();
    	 areas= new ArrayList<Area>();
    	 int x= Offset;
    	 int y= Offset;
    	 Wall wall;
    	 Baggage b;
    	 Area a;
    	 for(int i=0;i<level.length();i++)
    	 {
    		 char item=level.charAt(i);
    		 switch(item) {
    		 
    		 case '\n':
    			 y+=Space;
    			 if(this.w<x) {
    				 this.w=x;
    			 }
    			 x=Offset;
    			 break;
    			 
    		 case '#':
    			 wall =new Wall(x,y);
    			 walls.add(wall);
    			 x+=Space;
    			 break;
    			 
    		 case '$':
    			 b=new Baggage(x,y);
    			 baggs.add(b);
    			 x+=Space;
    			 break;
    			 
    		 case '.':
    			 a= new Area(x,y);
    			 areas.add(a);
    			 x+=Space;
    			 break;
    			 
    		 case '@':
    			 soko=new Player(x,y);
    			 x+=Space;
    			 break;
    			 
    		 case ' ':
    			 x+=Space;
    			 break;
    			 
    		 default:
    			 break;
    		 }
    		 h=y;
    	 }
     }
     private void buildWorld(Graphics g)
     {
    	 g.setColor(new Color(255,255,254));
    	 g.fillRect(0, 0,this.getWidth(),this.getHeight());
    	 ArrayList<Actor> world= new ArrayList<Actor>();
    	 world.addAll(walls);
    	 world.addAll(areas);
    	 world.addAll(baggs);
    	 world.add(soko);
        
    	 for(int i=0;i<world.size();i++) {
    		 Actor item= world.get(i);
    		 if(item instanceof Player || item instanceof Baggage)
    		 {
    			 g.drawImage(item.getImage(),item.x(),item.y(),this);
    		 }
    		 else
    		 {
    			 g.drawImage(item.getImage(),item.x(),item.y(),this);
    		 }
    		 if(isCompleted)
    		 {
    			 g.setColor(new Color(0,0,0));
    			 g.drawString("Completed",25,20);
    		 }
    	 }

     }
     public void paintComponent(Graphics g)
     {
    	 super.paintComponent(g);
    	 buildWorld(g);
     }
     
     private class TAdapter extends KeyAdapter{
    	 public void keyPressed(KeyEvent e) {
    		 if (isCompleted) {
    			 return;
    		 }
    		 int key = e.getKeyCode();
    		 switch(key) {
    		 
    		 case KeyEvent.VK_LEFT:
    			 if(checkWallCollision(soko,Left_Collision)) {
    				 return;
    			 }
    			 if(checkBagCollision(Left_Collision)) {
    				 return;
    			 }
    			 soko.move(-Space,0);
    			 break;
    			 
    		 case KeyEvent.VK_RIGHT:
    			 if(checkWallCollision(soko,Right_Collision)) {
    				 return;
    			 }
    			 if(checkBagCollision(Right_Collision)) {
    				 return;
    			 }
    			 soko.move(Space,0);
    			 break;
    			 
    		 case KeyEvent.VK_UP:
    			 if(checkWallCollision(soko,Top_Collision)) {
    				 return;
    			 }
    			 if(checkBagCollision(Top_Collision)) {
    				 return;
    			 }
    			 soko.move(0,-Space);
    			 break;
    			 
    		 case KeyEvent.VK_DOWN:
    			 if(checkWallCollision(soko,Bottom_Collision)) {
    				 return;
    			 }
    			 if(checkBagCollision(Bottom_Collision)) {
    				 return;
    			 }
    			 soko.move(0,Space);
    			 break;
    			 
    		 case KeyEvent.VK_R:
    			 restartLevel();
    			 break;
    			 
    		default:
    			break;
    		 }
    		 repaint();
    	 }

		private void restartLevel() {
			areas.clear();
			baggs.clear();
			walls.clear();
			
			initWorld();
			if(isCompleted) {
				isCompleted=false;
			}
			
		}

		private boolean checkBagCollision(int type) {
			switch(type) {
			case Left_Collision:
				for(int i=0;i<baggs.size();i++) {
					Baggage bag=baggs.get(i);
					if(soko.isLeftCollision(bag)) {
						for(int j=0;j<baggs.size();j++) {
							Baggage item=baggs.get(j);
							if(!bag.equals(item)) {
							if(bag.isLeftCollision(item)) {
								return true;
							}
						}
						if(checkWallCollision(bag,Left_Collision)) {
							return true;
						}
					}
					bag.move(-Space,0);
					isCompleted();
				}
				}
				return false;
				
			case Right_Collision:
				for(int i=0;i<baggs.size();i++) {
					Baggage bag=baggs.get(i);
					if(soko.isRightCollision(bag)) {
						for(int j=0;j<baggs.size();j++) {
							Baggage item=baggs.get(j);
							if(!bag.equals(item)) {
							if(bag.isRightCollision(item)) {
								return true;
							}
						}
						if(checkWallCollision(bag,Right_Collision)) {
							return true;
						}
					}
					bag.move(Space,0);
					isCompleted();
				}
				}
				return false;
				
			case Top_Collision:
				for(int i=0;i<baggs.size();i++) {
					Baggage bag=baggs.get(i);
					if(soko.isTopCollision(bag)) {
						for(int j=0;j<baggs.size();j++) {
							Baggage item=baggs.get(j);
							if(!bag.equals(item)) {
							if(bag.isTopCollision(item)) {
								return true;
							}
						}
						if(checkWallCollision(bag,Top_Collision)) {
							return true;
						}
					}
					bag.move(0,-Space);
					isCompleted();
				}
				}
				return false;
				
			case Bottom_Collision:
				for(int i=0;i<baggs.size();i++) {
					Baggage bag=baggs.get(i);
					if(soko.isBottomCollision(bag)) {
						for(int j=0;j<baggs.size();j++) {
							Baggage item=baggs.get(j);
							if(!bag.equals(item)) {
							if(bag.isBottomCollision(item)) {
								return true;
							}
						}
						if(checkWallCollision(bag,Bottom_Collision)) {
							return true;
						}
					}
					bag.move(0,Space);
					isCompleted();
				}
				
			}
			break;
			default:
				break;
			}
			return false;
		}

		private void isCompleted() {
			int nBags=baggs.size();
			int finishedBags=0;
			for(int i=0;i<nBags;i++) {
				Baggage bags=baggs.get(i);
				for(int j=0;j<nBags;j++) {
					Area area=areas.get(j);
					if(bags.x()==area.x()&&bags.y()==area.y()) {
						finishedBags+=1;
					}
				}
			}
			if(finishedBags==nBags) {
				isCompleted=true;
				repaint();
			}
		
			
		}

		private boolean checkWallCollision(Actor actor, int type) {
			switch(type) {
			case Left_Collision:
				for(int i=0;i<walls.size();i++)
				{
					Wall wall=walls.get(i);
					if(actor.isLeftCollision(wall)) {
						return true;
					}
				}
				return false;
				
			case Right_Collision:
				for(int i=0;i<walls.size();i++)
				{
					Wall wall=walls.get(i);
					if(actor.isRightCollision(wall)) {
						return true;
					}
				}
				return false;
				
			case Top_Collision:
				for(int i=0;i<walls.size();i++)
				{
					Wall wall=walls.get(i);
					if(actor.isTopCollision(wall)) {
						return true;
					}
				}
				return false;
				
			case Bottom_Collision:
				for(int i=0;i<walls.size();i++)
				{
					Wall wall=walls.get(i);
					if(actor.isBottomCollision(wall)) {
						return true;
					}
				}
				return false;
				
			default:
				break;
			}
			return false;
		}
     }
}