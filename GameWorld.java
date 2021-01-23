package com.mycompany.a3;


import java.util.ArrayList;

import java.util.Observable;

import java.util.Random;

import com.codename1.charts.util.ColorUtil;

	
	public class GameWorld  extends Observable{
		private int mapHeight;
		private boolean positionable, energyCol= false, crashCol= false, deathCol=false;
		private int mapWidth;
		private int contWidth,contHeight;
		private Random rand = new Random();
		private int counter = 0;
		private PlayerCyborg cyborg ;
		private double nonDisplayClock;
		private int lives;
		private GameObject lastCollsion;
		private int baseSize = 30;
		private boolean ispause = false;
		private int cyborgSize = 25;
		private boolean isExit = false;
		private ArrayList<GameObject> colliderList = new ArrayList<GameObject>();
		
		private boolean soundOn = false;
		GameObjectCollection gameObjectList;
		public GameWorld() {
			gameObjectList = new GameObjectCollection();
			 cyborg= new PlayerCyborg(this, cyborgSize, 0, 500, 500);
			 lastCollsion = cyborg;
		}
		public int getMapWidth() {
			return mapWidth;
		}
		public int getMapHeight() {
			return mapHeight;
		}
		//Exits for the game
		public void exit() {
			if (isExit)
				System.exit(0);
		}
		public void setMapWidth(int width) {
			mapWidth = width;
		
		}
		public void setMapHeight(int height) {
			this.mapHeight = height;
			
		}
		public void quitGame() {
			System.out.println("do you wish to exit? (y/n)");
			isExit=true;
		}
		public void dontQuit() {
			isExit=false;
		}
		public void setWidthHeight(int width, int height) {
			contWidth = width;
			contHeight=height;
		}
		public int getContWidth(){
			return contWidth;
		}
		public int getContheight(){
			return contHeight;
		}
		/**
		 * Starts the game and resets the gameObjects just in case
		 */
		public void init() {
		//clears the gameObjectList before play
			//gameObjectList.clear();
			
			
			//Creating Static objects
			gameObjectList.add(new Base(this,baseSize, 1,500, 500));
			gameObjectList.add(new Base(this,baseSize, 2,100, 800));
			gameObjectList.add(new Base(this,baseSize, 3,400, 400));
			gameObjectList.add(new Base(this,baseSize, 4,1000, 125));
			gameObjectList.add(new EnergyStation(this, randObjSize(),randX(),randY()));
			gameObjectList.add(new EnergyStation(this,randObjSize(),randX(),randY()));
			
			//addsplayer cyborg
			gameObjectList.add(cyborg);
		
			gameObjectList.add(new NonPlayerCyborg(cyborgSize, 0, 500, 800,this));
			gameObjectList.add(new NonPlayerCyborg(cyborgSize, 0, 500, 300,this));
			gameObjectList.add(new NonPlayerCyborg(cyborgSize, 0, 550, 100,this));
			
			
			//creates attack drones
			gameObjectList.add(new Drone(this, randObjSize(), randX(), randY()));
			gameObjectList.add(new Drone(this,randObjSize(), randX(), randY()));
			
			
		}
	
		public void display() {
			System.out.println("Lives left: "+cyborg.getLives()
			+	", Current clock time: "+counter+", Last base reached: "+cyborg.getLastBase()+", Energy Level: "
			+cyborg.getEnergyLevel()+", Damage: "+cyborg.getDamageLevel());
			
		}
		public void map() {
			IIterator elements = gameObjectList.getIterator();
			while (elements.hasNext()) {
				GameObject temp = ((GameObject) elements.getNext());
				System.out.println(temp.toString());
			}
		}
		public void toggleSound() {
			if(!soundOn)
				soundOn=true;
			else
				soundOn=false;
			this.setChanged();
			this.notifyObservers();
		}
		public void changeNPCStrategies(){
			System.out.println("changing strategy");
			IIterator anIterator = gameObjectList.getIterator();
			
			Object currentObj = new Object();
			
			while( anIterator.hasNext() ){
				currentObj = anIterator.getNext();
				if( currentObj instanceof NonPlayerCyborg){ 
					
					
					((NonPlayerCyborg)currentObj).baseCollision(((NonPlayerCyborg) currentObj).getLastBase() + 1);;
					
		 
					if( ((NonPlayerCyborg)currentObj).getStrategy() instanceof NextBaseStrategy ){
						((NonPlayerCyborg)currentObj).setStrategy(new CyborgAttackStrategy(((NonPlayerCyborg)currentObj), cyborg));
					} else if ( ((NonPlayerCyborg)currentObj).getStrategy() instanceof CyborgAttackStrategy ){
						((NonPlayerCyborg)currentObj).setStrategy(new NextBaseStrategy(((NonPlayerCyborg)currentObj), this));
					}
				}
			}
			notifyobs();
		}

		public void tick(int elapsedTime) {
			if(cyborg.getLastBase() == 4) {
				System.out.print("WINNER");
				System.exit(0);
			}
				
			if((cyborg.getDamageLevel()!=100 && cyborg.getDamageLevel() <=100) && cyborg.getEnergyLevel() !=0 && cyborg.getLives()!=0) {
				cyborg.setHeading(cyborg.getStearingDirection());
				cyborg.setEnergyLevel(300);
				counterTime();
			
				IIterator elements = gameObjectList.getIterator();
				while ( elements.hasNext()) {
					GameObject temp = ((GameObject) elements.getNext());
					if(temp instanceof Movable) {
						if(temp instanceof Drone) {
							((Movable) temp).setHeading(((Movable) temp).getHeading());
							((Movable) temp).move(this, elapsedTime);
						}
					else
						((Movable) temp).move(this, elapsedTime);
					}
				}
				IIterator theColliders = gameObjectList.getIterator();
				
		        while(theColliders.hasNext()){
		            GameObject curObj = (GameObject)theColliders.getNext(); // get a collidable object 
		            // check if this object collides with any OTHER object
		            if (cyborg.collidesWith(curObj)) {

		                if (!colliderList.contains((GameObject)curObj)) {
		                    colliderList.add((GameObject) curObj);
		                    cyborg.handleCollision(curObj);

		                }
		            } else {
		                // take it out of the array
		                colliderList.remove((GameObject) curObj);

		            }
		        }

				
		}
			else {
				if(cyborg.getLives()!=0) {
					deathCol =true;
					cyborg.setDamage(0);
					lifeReset();
				}
				else {
					System.out.println("Game Over");
				}
			}
			notifyobs();
		}
		
		public void cyborgCollision(char with) {
			
			cyborg.collision(with);
			IIterator iterator = gameObjectList.getIterator();
			while(iterator.hasNext()) {
				GameObject temp = (GameObject) iterator.getNext();
				if(temp instanceof NonPlayerCyborg) {
					((NonPlayerCyborg) temp).collision('r');
					break;
				}
			}
			notifyobs();
		}
		
		public void baseCollision(int baseNumber) {
			cyborg.baseCollision(baseNumber);
			notifyobs();
			
		}
		public void setCyborgSpeed(int x) {
			cyborg.setSpeed(cyborg.getSpeed()+x);
			notifyobs();
		}
		public void changeHeading(char change) {
			cyborg.changeHeading(change);
			notifyobs();
		}
		public int getLives() {
			return cyborg.getLives();
		}
		public int getClock() {
			return counter;
		}
		public int getCyborgBaseReached() {
			return cyborg.getLastBase();
		}
		public int getEnergyLevel() {
			return cyborg.getEnergyLevel();
		}
		public int getCyborgHealthLevel() {
			return cyborg.getDamageLevel();
		}
		public boolean isSound() {
			return soundOn;
		}
		public void energyStationCollision(GameObject temp) {
			energyCol = true;

					if(((EnergyStation) temp).getCapacity()!=0) {
						cyborg.setEnergyLevel(((EnergyStation) temp).getCapacity());
						((EnergyStation) temp).setCapacity();
						temp.setColor(ColorUtil.rgb(0, 255, 191));
						
					}
				
			
			gameObjectList.add(new EnergyStation(this,randObjSize(), randX(), randY()));
			notifyobs();
		}
		private void notifyobs() {
			this.setChanged();
			this.notifyObservers();
		}
		
		public void counterTime() {
			nonDisplayClock += (1/50.00);
			counter=(int) nonDisplayClock;
		}
		private void lifeReset() {//resets drone to start base to continue where left off
			cyborg.resetCyborg();
			setDeathCol(true);
		}
		//Creating randInts for the game
		private int randX() {
			return rand.nextInt((mapWidth-contWidth));
		}
		private int randY() {
		
			return rand.nextInt((mapHeight-contHeight));
		}
		private int randObjSize() {
			return 15+rand.nextInt(25);
		}
		public Cyborg getCyborg() {
			// TODO Auto-generated method stub
			return cyborg;
		}
		public boolean isIspause() {
			return ispause;
		}
		public void setIspause(boolean ispause) {
			this.ispause = ispause;
		}
		public boolean isPositionable() {
			return positionable;
		}
		public void setPositionable(boolean positionable) {
			this.positionable = positionable;
		}
		public boolean isEnergyCol() {
			return energyCol;
		}
		public void setEnergyCol(boolean energyCol) {
			this.energyCol = energyCol;
		}
		public boolean isCrashCol() {
			return crashCol;
		}
		public void setCrashCol(boolean crashCol) {
			this.crashCol = crashCol;
		}
		public boolean isDeathCol() {
			return deathCol;
		}
		public void setDeathCol(boolean deathCol) {
			this.deathCol = deathCol;

		}
	}
		
		
		
