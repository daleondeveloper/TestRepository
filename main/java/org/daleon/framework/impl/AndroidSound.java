package org.daleon.framework.impl;

import android.media.SoundPool;
import org.daleon.framework.Sound;

public class AndroidSound implements Sound{
    int soundId;
    SoundPool soundPool;
    int priority;
    int loop;
    int rate;

    public AndroidSound(SoundPool soundPool, int soundId){
        this.soundId = soundId;
        this.soundPool = soundPool;
        priority =0;
        loop =0;
        rate =0;
    }

    @Override
    public int play(float volume) {

        int i = soundPool.play(soundId,volume,volume,priority,loop,rate);
        return i;
    }

    public void stop(int i){
        soundPool.stop(i);
    }

    @Override
    public void dispose() {
        soundPool.unload(soundId);
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setLoop(int loop) {
        this.loop = loop;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
}
