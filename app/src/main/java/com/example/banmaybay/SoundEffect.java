package com.example.banmaybay;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

public class SoundEffect {
    private AudioAttributes audio;
    final int MAX = 2;
    private static int sndBattleTheme;
    private static int sndShooting;
    private static int sndEnemySpawn;
    private static int sndGameOver;
    private static int sndButtonClick;
    private static int sndEnemyDestroyed;
    private static int sndEnemyBreak;
    private static SoundPool sound;

    public SoundEffect(Context context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            audio = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();
            sound = new SoundPool.Builder()
                    .setAudioAttributes(audio)
                    .setMaxStreams(MAX)
                    .build();
        } else {
            sound = new SoundPool(MAX, AudioManager.STREAM_MUSIC, 0);
        }

        sndBattleTheme = sound.load(context, R.raw.battle_theme, 1);
        sndShooting = sound.load(context, R.raw.shoot_effect, 1);
        sndEnemySpawn = sound.load(context, R.raw.enemy_spawn, 1);
        sndGameOver = sound.load(context, R.raw.game_over, 1);
        sndButtonClick = sound.load(context, R.raw.button_click, 1);
        sndEnemyDestroyed = sound.load(context, R.raw.enemy_destroyed, 1);
        sndEnemyBreak = sound.load(context, R.raw.enemy_break, 1);
    }

    public void battleTheme() {
        sound.play(sndBattleTheme, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    public void shootingSound() {
        sound.play(sndShooting, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    public void enemySpawn() {
        sound.play(sndEnemySpawn, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    public void enemyDestroyed() {
        sound.play(sndEnemyDestroyed, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    public void enemyBreak() {
        sound.play(sndEnemyBreak, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    public void buttonClick() {
        sound.play(sndButtonClick, 1.0f, 1.0f, 1, 0, 1.0f);
    }


    public void gameOver() {
        sound.play(sndGameOver, 1.0f, 1.0f, 1, 0, 1.0f);
    }

}
