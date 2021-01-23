// Attack strategy for NPC's where the NPC chases the player

package com.mycompany.a3;

import com.codename1.util.MathUtil;

public class CyborgAttackStrategy implements IStrategy {
	
	
	
	private Cyborg cyborg1;
    private Cyborg targetCyborg;

    public CyborgAttackStrategy(Cyborg cyb1, Cyborg target){
        cyborg1 = cyb1;
        targetCyborg = target;
    }


    
    public void setStrategy(){
    	
        double dx, dy;

        dx = targetCyborg.getX() - cyborg1.getX();
        dy = targetCyborg.getY() - cyborg1.getY();


        if( dy == 0 )
            if( dx < 0 ){
                
                cyborg1.setHeading(270);
                return;
            }
            else if( dx > 0 ){
         
                cyborg1.setHeading(90);
                return;
            }

        if( dx == 0 )
            if( dy > 0 ){
             
                cyborg1.setHeading(0);
                return;
            }else if( dy < 0 ){
           
                cyborg1.setHeading(180);
                return;
            }

        int angle = (int)Math.toDegrees(MathUtil.atan(dx/dy) );

        if( dx > 0 ){ 
            if( dy > 0 ){
                cyborg1.setHeading( Math.abs(angle) );
                return;
            }else if( dy < 0){
                cyborg1.setHeading( 180 - angle );
                return;
            }
        }else if( dx < 0 ){
            if( dy > 0){
                cyborg1.setHeading( 360 - angle );
                return;
            }else if( dy < 0){
                cyborg1.setHeading( 180 + Math.abs(angle) );
                return;
            }
        }
    }
	
	
	@Override
	public void invokeStrategy() {
		setStrategy();
		
	}
	
    
	

}