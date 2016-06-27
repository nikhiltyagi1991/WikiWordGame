package com.nikhil.wikiwordgame;

import android.util.Log;

import java.util.Collection;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by nikhil on 22/6/16.
 * Main Game class which controls the full game
 */
public class Game  {

    String wikiRead,dashedWikiText;

    private String[] removedWords = new String[10];

    public Game(String wikiRead){
        this.wikiRead = wikiRead;
        dashedWikiText = createDashedWiki();
    }

    private String createDashedWiki(){
        StringBuilder dashedPage = new StringBuilder();
        dashedPage.append(wikiRead);
        Log.d("initial: ",dashedPage.toString());
        String[] lines = wikiRead.split("[.?\n]");
        int x=0,lineIndex = 0,wordIndex = 0;

        for(int i=0;i<10;i++){
            lines[i] = lines[i].trim();
            if(lines[i].isEmpty()) {
                continue;
            }
            String[] words = lines[i].split("\\s+");

            Random r = new Random();
            int selected = r.nextInt(words.length);
            removedWords[x++] = words[selected];

            Pattern pattern = Pattern.compile("\\b"+words[selected]+"\\b");
            Matcher matcher = pattern.matcher(dashedPage);
            if(matcher.find(lineIndex))
                wordIndex = matcher.start();

            dashedPage.replace(wordIndex, wordIndex + words[selected].length(), "_______");
            lineIndex += lines[i].length()+1;
        }
        return dashedPage.toString();
    }

    public String getWikiRead() {
        return wikiRead;
    }

    public String getDashedWikiText() {
        return dashedWikiText;
    }

    public String[] getRemovedWords() {
        return removedWords;
    }

    public String[] getJumbledWords(){
        String[] jumbled = new String[10];
        System.arraycopy(removedWords,0,jumbled,0,removedWords.length);
        Random r = new Random();
        int i=jumbled.length-1;
        while(i>0){
            int tempIndex = r.nextInt(i+1);
            String temp = jumbled[i];
            jumbled[i] = jumbled[tempIndex];
            jumbled[tempIndex] = temp;
            i--;
        }
        return jumbled;
    }

    public int finalScore(Collection<String> answers){
        int score = 0,i=0;
        for(String answer : answers){
            if(answer.equals(removedWords[i])){
                score++;
            }
            i++;
        }
        return score;
    }
}