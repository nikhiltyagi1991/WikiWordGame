package com.nikhil.wikiwordgame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeMap;

/**
 * Main Page
 * **/
public class MainActivity extends AppCompatActivity {

    private Game game;
    private TreeMap<Integer,String> userAnswer = new TreeMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        game = new Game("Near the beginning of his career, Einstein thought that Newtonian mechanics was no longer enough to reconcile the laws of classical mechanics with the laws of the electromagnetic field. This led to the development of his special theory of relativity. He realized, however, that the principle of relativity could also be extended to gravitational fields, and with his subsequent theory of gravitation in 1916, he published a paper on general relativity. He continued to deal with problems of statistical mechanics and quantum theory, which led to his explanations of particle theory and the motion of molecules. He also investigated the thermal properties of light which laid the foundation of the photon theory of light. In 1917, Einstein applied the general theory of relativity to model the large-scale structure of the universe. He was visiting the United States when Adolf Hitler came to power in 1933 and, being Jewish, did not go back to Germany, where he had been a professor at the Berlin Academy of Sciences. He settled in the US, becoming an American citizen in 1940. On the eve of World War II, he endorsed a letter to President Franklin D Roosevelt alerting him to the potential development of \"extremely powerful bombs of a new type\" and recommending that the US begin similar research. This eventually led to what would become the Manhattan Project. Einstein supported defending the Allied forces, but largely denounced the idea of using the newly discovered nuclear fission as a weapon. Later, with the British philosopher Bertrand Russell, Einstein signed the Russell–Einstein Manifesto, which highlighted the danger of nuclear weapons. Einstein was affiliated with the Institute for Advanced Study in Princeton, New Jersey, until his death in 1955.");
        wikiTextSetup(game.getDashedWikiText());
        removedWordsSetup();
    }

    private void wikiTextSetup(String dashedWiki){
        Log.d("dashedText : ", dashedWiki);
        SpannableString ss = new SpannableString(dashedWiki);

        int index = dashedWiki.indexOf("_______");
        while (index >= 0) {
            ss.setSpan(new BlankClickedSpan(index),index,index+7,0);
            index = dashedWiki.indexOf("_______", index + 1);
        }
        TextView wiki = (TextView)findViewById(R.id.wikiparas);
        wiki.setText(ss);
        wiki.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void removedWordsSetup(){
        ListView words = (ListView)findViewById(R.id.words);
        final ArrayList<String> wordsList = new ArrayList<>(Arrays.asList(game.getJumbledWords()));
        final ArrayAdapter<String> wordsAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_activated_1,wordsList);
        words.setAdapter(wordsAdapter);
        words.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(BlankClickedSpan.selectedIndex>0){
                    TextView wiki = (TextView)findViewById(R.id.wikiparas);
                    StringBuilder string = new StringBuilder(wiki.getText());
                    string.replace(BlankClickedSpan.selectedIndex,BlankClickedSpan.selectedIndex+7,parent.getAdapter().getItem(position).toString());
                    userAnswer.put(BlankClickedSpan.selectedIndex,parent.getAdapter().getItem(position).toString());
                    wikiTextSetup(string.toString());
                    wordsList.remove(position);
                    wordsAdapter.notifyDataSetChanged();
                    BlankClickedSpan.selectedIndex = -1;
                    if(wordsList.isEmpty()){
                        Button button = (Button)findViewById(R.id.submitAnswer);
                        button.setVisibility(View.VISIBLE);
                    }
                }else {
                    Toast.makeText(view.getContext(),"Please select a blank first",Toast.LENGTH_SHORT).show();
                }
            }
        });

        StringBuilder str = new StringBuilder();
        for(String word : game.getRemovedWords()){
            str.append(word+" ");
        }
        Log.d("Removed Words : ", str.toString());
    }

    public void submitAnswer(View view){
        Intent intent = new Intent(this,HighScore.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("score",game.finalScore(userAnswer.values()));
        intent.putExtra("wiki",game.getWikiRead());
        startActivity(intent);
        finish();
    }
}