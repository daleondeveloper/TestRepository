package org.daleon.framework.impl;

import org.daleon.framework.Game;
import org.daleon.framework.Screen;

public abstract class GLScreen extends Screen {
    protected  final GLGraphics glGraphics;
    protected final GLGame glGame;

    public GLScreen(Game game){
        super(game);
        glGame = (GLGame)game;
        glGraphics = ((GLGame)game).getGlGraphics();
    }

}

