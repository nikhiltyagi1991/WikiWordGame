package com.nikhil.wikiwordgame;

import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

/**
 * Created by nikhil on 25/6/16.
 * To create blank spaces here.
 */
public class BlankClickedSpan extends ClickableSpan {
    public static int selectedIndex = -1;
    private int index;

    public BlankClickedSpan(int index){
        this.index = index;
    }

    @Override
    public void onClick(View widget) {
        selectedIndex = index;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        ds.setUnderlineText(false);
    }
}
