// NPC class that allows non-player cyborgs in the game that have 1 of 2 strategies
package com.mycompany.a3;

import java.util.Random;
import com.codename1.ui.geom.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;

public class NonPlayerCyborg extends Cyborg{
	
	IStrategy currentStrategy;
	private int size;
	private double x, y;

	public NonPlayerCyborg(int size, int heading, double x, double y,GameWorld gw) {
		super(gw, size, heading, x, y);
		this.x =x;
		this.y=y;
		this.size=size;
		Random rand = new Random();
		int xx = rand.nextInt(1);
		if(xx==0)
			currentStrategy = new NextBaseStrategy(this, gw);
		else
			currentStrategy = new CyborgAttackStrategy(this, gw.getCyborg());
	}
	@Override
	public void move(GameWorld temp,int elapsedTime) {
		currentStrategy.invokeStrategy();
		super.move(temp,elapsedTime);
	}
	
	
	public IStrategy getStrategy() {
		return currentStrategy;
	}

	public void collision(char with) {//Called when a collision with a drone or cyborg is detected. sets damage and color acordenly 
		if(super.getDamageLevel()!=100) {
		if(with=='r') {//cyborg collision
			super.setDamage(5);
			super.setColor(ColorUtil.rgb((super.getRed()-5), 0, 0));
			super.setRed(5);
			super.checkSpeed();
		}
		if(with=='d') {//drone collision
			super.setDamage(1);
			super.setColor(ColorUtil.rgb((super.getRed()-1), 0, 0));
			super.setRed(1);
			super.checkSpeed();
			
		}
		}
	}
	public void setStrategy(IStrategy s){
		currentStrategy = s;
	}
	@Override
	public String toString() {
		String parentDesc = super.toString();
		return "NPR:" + parentDesc;
	}
	@Override
	public void draw(Graphics g, Point pCmpRelPrnt) {
		// TODO Auto-generated method stub
		int locX = (int)getX() + (int)pCmpRelPrnt.getX();
        int locY = (int)getY() + (int)pCmpRelPrnt.getY();
        g.drawRect(locX, locY, size, size);
        
		}

}
