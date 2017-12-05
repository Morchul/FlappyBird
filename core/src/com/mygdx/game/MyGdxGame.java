package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.mygdx.game.enums.Screens;
import com.mygdx.game.screen.EndScreen;
import com.mygdx.game.screen.FirstScreen;
import com.mygdx.game.stage.FirstScreenStage;

public class MyGdxGame extends Game {

	private FirstScreen fs;

	public MyGdxGame(){
		//fs = new FirstScreen(this);
	}

	@Override
	public void create() {
		setScreen(Screens.FIRST_SCREEN);
	}

	public void setScreen(Screens screen){
		switch (screen){
			case FIRST_SCREEN:
				setScreen(new FirstScreen(this)); break;
			case ENDSREEN:
				setScreen(new EndScreen(FirstScreenStage.getScore(),this));
		}
	}
}
