package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class CommandCybCollision extends Command {

	private GameWorld gw;

	public CommandCybCollision(GameWorld gw) {
		super("Collide with Another Cyborg");
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