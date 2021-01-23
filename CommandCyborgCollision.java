package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class CommandCyborgCollision extends Command {
	private GameWorld gw;
	
	/**
	 * Constuctor for drone
	 * @param gw
	 */
	public CommandCyborgCollision(GameWorld gw) {
		super("Collided with NPC");
		this.gw = gw;
	}
	
	/**
	 * override for action
	 */
	@Override
	public void actionPerformed(ActionEvent ev) {
		gw.cyborgCollision('c');
	}
}