package com.lib.hashtag.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

import com.lib.hashtag.HashTag;
import com.lib.hashtag.R;

import java.util.List;

/**
 * Created by Abhishek on 3/6/2016.
 */
public class HashTagEditText extends EditText {
    private HashTag tag;

    public HashTagEditText(Context context) {
        super(context);
        init(context, null);
    }

    public HashTagEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public HashTagEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setFocusableInTouchMode(true);
        String fontName = null;
        tag = new HashTag();
        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.HashTagView, 0, 0);
            try {
                tag.setHashColor(array.getColor(R.styleable.HashTagEditText_hashTagColor, Color.BLACK));
                tag.setHashBackgroundColor(array.getColor(R.styleable.HashTagEditText_hastTagBackground, Color.TRANSPARENT));
                tag.setAtColor(array.getColor(R.styleable.HashTagEditText_atColor, Color.BLACK));
                tag.setAtBackgroundColor(array.getColor(R.styleable.HashTagEditText_atBackground, Color.TRANSPARENT));
                tag.setHashTextSize(array.getFloat(R.styleable.HashTagEditText_hashTagSize, 1f));
                tag.setAtTextSize(array.getFloat(R.styleable.HashTagEditText_atSize, 1f));
                tag.setTrackHash(array.getBoolean(R.styleable.HashTagEditText_trackHashTag, true));
                tag.setTrackAt(array.getBoolean(R.styleable.HashTagEditText_trackAt, false));
                tag.setBoldHash(array.getBoolean(R.styleable.HashTagEditText_boldHashTag, false));
                tag.setBoldAt(array.getBoolean(R.styleable.HashTagEditText_boldAt, false));
                tag.setItalicHash(array.getBoolean(R.styleable.HashTagEditText_italicHashTag, false));
                tag.setItalicAt(array.getBoolean(R.styleable.HashTagEditText_italicAt, false));
                tag.setUnderlineHash(array.getBoolean(R.styleable.HashTagEditText_underlineHashTag, false));
                tag.setUnderlineAt(array.getBoolean(R.styleable.HashTagEditText_underlineAt, false));
                String font = array.getString(R.styleable.HashTagEditText_hashTagFont);
                if (font != null)
                    tag.setHashTagFont(Typeface.createFromAsset(context.getAssets(), "font/" + font));
                font = array.getString(R.styleable.HashTagEditText_atFont);
                if (font != null)
                    tag.setAtFont(Typeface.createFromAsset(context.getAssets(), "font/" + font));
                fontName = array.getString(R.styleable.HashTagView_fontName);
            } finally {
                array.recycle();
            }
        }
        if (fontName != null)
            setTypeface(Typeface.createFromAsset(context.getAssets(), "font/" + fontName));
        tag.createHashTags(getText());
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        if (tag != null)
            tag.createHashTags(text);
    }

    public List<String> getHashTags() {
        return tag.getHashTags(getText());
    }

    public List<String> getAts() {
        return tag.getAts(getText());
    }

    public int getHashColor() {
        return tag.getHashColor();
    }

    public void setHashColor(int color) {
        tag.setHashColor(color);
    }

    public int getHashBackgroundColor() {
        return tag.getHashBackgroundColor();
    }

    public void setHashBackgroundColor(int color) {
        tag.setHashBackgroundColor(color);
    }

    public int getAtBackgroundColor() {
        return tag.getAtBackgroundColor();
    }

    public void setAtBackgroundColor(int color) {
        tag.setAtBackgroundColor(color);
    }

    public int getAtColor() {
        return tag.getAtColor();
    }

    public void setAtColor(int color) {
        tag.setAtColor(color);
    }

    public float getHashTextSize() {
        return tag.getHashTextSize();
    }

    public void setHashTextSize(float size) {
        tag.setHashTextSize(size);
    }

    public float getAtTextSize() {
        return tag.getAtTextSize();
    }

    public void setAtTextSize(float size) {
        tag.setAtTextSize(size);
    }

    public boolean isHashTracked() {
        return tag.isHashTracked();
    }

    public void setTrackHash(boolean track) {
        tag.setTrackHash(track);
    }

    public boolean isAtTracked() {
        return tag.isAtTracked();
    }

    public void setTrackAt(boolean track) {
        tag.setTrackAt(track);
    }

    public boolean isHashBold() {
        return tag.isHashBold();
    }

    public void setBoldHash(boolean bold) {
        tag.setBoldHash(bold);
    }

    public boolean isAtBold() {
        return tag.isAtBold();
    }

    public void setBoldAt(boolean bold) {
        tag.setBoldAt(bold);
    }

    public boolean isHashItalic() {
        return tag.isHashItalic();
    }

    public void setItalicHash(boolean italic) {
        tag.setItalicHash(italic);
    }

    public boolean isAtItalic() {
        return tag.isAtItalic();
    }

    public void setItalicAt(boolean italic) {
        tag.setItalicAt(italic);
    }

    public boolean isHashUnderlined() {
        return tag.isHashUnderlined();
    }

    public void setUnderlineHash(boolean underline) {
        tag.setUnderlineHash(underline);
    }

    public boolean isAtUnderlined() {
        return tag.isAtUnderlined();
    }

    public void setUnderlineAt(boolean underline) {
        tag.setUnderlineAt(underline);
    }
}