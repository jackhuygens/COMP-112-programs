// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP112 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP112 Assignment
 * Name:
 * Usercode:
 * ID:
 */

import ecs100.*;

import java.awt.*;

/** DodgemGame
 *  Game with two dodgem cars whose steering is controlled by the players
 *  (uses keys: player 1: S/D for left/right;  player 2: K/L for left/right)
 *  Cars run around at a constant speed in an arena with an enclosing wall and
 *  a round obstacle in the of the arena.
 *  If a car hits the wall or the obstacle, then it gets damaged
 *  If the cars collide then they bump apart and their directions are changed
 *  To win the game, a player needs to make the other car crash into the wall
 *  or obstacle enough times.
 *
 *  Controls:
 *  - key to start (space)
 *  - keys to turn the two cars  (s/d  and k/l)
 *    The simplest control is to change the direction of the car directly
 *    when a key is pressed; a better control is to change the direction of
 *    the "steering wheel", which will make the car change direction as it moves
 *
 *  Display:
 *   program constantly shows
 *   - the arena, obstacle, and the cars
 *   - the damage level of each player
 *
 *  Constants:
 *    This class should contain constants specifying the various parameters of
 *    the game, including the geometry of the arena and obstacle.
 *    - Colliding with a wall gives a little bit of damage
 *    - Colliding with the obstacle should give a lot of damage
 *    - Colliding with the other car may give a little damage, but needs to be very low
 *      to allow a player to try to push the other player into the obstacle.
 */

public class DodgemGame{
	
	public DodgemCar player1, player2;

	public static int gameState, winner, player1Score, player2Score;
    
    public Rectangle floorRect = new Rectangle(50,50,500,400); 
    public Rectangle[] colliders = {
    		new Rectangle(floorRect.x - 50, floorRect.y - 50, 50, floorRect.height + 100), 
    		new Rectangle(floorRect.x + floorRect.width, floorRect.y - 50, 50, floorRect.height + 100), 
    		new Rectangle(floorRect.x - 50, floorRect.y - 50, floorRect.width + 100, 50),
    		new Rectangle(floorRect.x - 50, floorRect.y + floorRect.height, floorRect.width + 100, 50)
    		};
    
    public double[] circleCollider = {300,250,80}; // x, y, radius
    

    public DodgemGame(){
    	
        UI.initialise();
        UI.setKeyListener(this::KeyEvent);
        UI.setImmediateRepaint(false);
        
        RestartGame();
        
        while (true){
        	
        	if(DodgemGame.gameState == 0){ //gameplay
		        player1.move();
		        player2.move();;
		        
		        player1.CheckCollisions(this, player2);
		        player2.CheckCollisions(this, player1);
		        
		        DrawColliders();
	
		        UI.setColor(Color.white);
		        UI.fillRect(100, 10, 100, 20);
		        UI.fillRect(400, 10, 100, 20);
		        
		        UI.setColor(Color.red);
		        UI.fillRect(100, 10, player1.health, 20);
		        UI.setColor(Color.blue);
		        UI.fillRect(400, 10, player2.health, 20);
		        
		        UI.setColor(Color.white);
 		        UI.setFontSize(22);
 		    	UI.drawString("" + DodgemGame.player1Score, 240, 30);
 		    	UI.drawString("" + DodgemGame.player2Score, 360, 30);
        	}
        	else if(DodgemGame.gameState == 2){ // ready screen
        		
        		for(int i = 3; i > 0; i--){
	        		UI.setColor(Color.white);
	 		        UI.fillRect(0, 0, 1000, 1000);
	 		        UI.setColor(Color.black);
	 		        UI.setFontSize(29);
	 		    	UI.drawString("Ready: " + i, 260, 240);
	 		    	UI.repaintGraphics();
	 		    	UI.sleep(500);
        		}
        		UI.setColor(Color.white);
 		        UI.fillRect(0, 0, 1000, 1000);
        		DodgemGame.gameState = 0;
	
        	}
        	else{ //winner screen
        		UI.setColor(Color.white);
 		        UI.fillRect(0, 0, 1000, 1000);
 		        UI.setColor(Color.black);
 		        UI.setFontSize(22);
 		    	UI.drawString("player " + (DodgemGame.winner+1) + " wins! - press SPACE to restart", 80, 150);
        	}
        	
	        UI.sleep(10);
	        UI.repaintGraphics();
        }
    }
    
    void DrawColliders(){
    	UI.setColor(new Color (160, 160, 160));
    	for(int i = 0; i < colliders.length; i ++)
    		 UI.fillRect(colliders[i].x, colliders[i].y, colliders[i].width, colliders[i].height);
    	
    	UI.fillOval(circleCollider[0] - circleCollider[2], 
        		circleCollider[1] - circleCollider[2], 
        		circleCollider[2]*2, 
        		circleCollider[2]*2);
    }


    private void RestartGame(){
    	UI.setColor(Color.white);
    	UI.fillRect(0, 0, 1000, 1000);
    	DodgemGame.gameState = 2;
    	player1 = new DodgemCar(100,200,0, 0);
        player2 = new DodgemCar(500,200,180, 1);
    }

 
    public void KeyEvent(String e) {
    	player1.ApplyKey(e);
    	player2.ApplyKey(e); 	
    	
    	if(e == "Space" && DodgemGame.gameState == 1)
    		RestartGame();
    }
    
    public boolean CheckSphereCollision(double[] c1, double[] c2) {
    	  double x = c1[0] - c2[0];
    	  double y = c1[1] - c2[1];
    	  double dist = c1[2] + c2[2];  
    	  boolean collision = (x * x + y * y) < dist * dist;

    	  return(collision); 
    }

    public static void main(String[] arguments){
        new DodgemGame();
    }   
}
