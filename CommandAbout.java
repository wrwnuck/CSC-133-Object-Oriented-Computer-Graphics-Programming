package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.table.TableLayout;

public class CommandAbout extends Command{
	private GameWorld gw;
	
	/**
	 * Constructor for about
	 * @param gw
	 */
	
	public CommandAbout(GameWorld gw) {
		super("About");
		this.gw = gw;
	}
	
	/**
	 * @Override method for action
	 * updated for game 3!
	 */
	
	@Override
	public void actionPerformed(ActionEvent ev) {
		
		Dialog aboutBox = new Dialog("About", new TableLayout(4,1));
		Command okCommand = new Command("ok");
		
		aboutBox.add(new Label ("Sili-Challenge Game"));
		aboutBox.add(new Label ("v1.00"));
		aboutBox.add(new Label("By: Warren Wnuck"));
		aboutBox.add(new Label("CSC133 A3Prj"));
		
		Command c = Dialog.show("",  aboutBox, okCommand);
		if (c == okCommand) {
			return;
		}
		
	}
	

}
