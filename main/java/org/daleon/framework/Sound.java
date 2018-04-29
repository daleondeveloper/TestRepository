package org.daleon.framework;

public interface Sound {
    public int play(float volume);

    public void stop(int i);
    public void setPriority(int priority);
    public void setLoop(int loop);
    public void setRate(int rate);
    public void dispose();
}
