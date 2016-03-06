package com.lib.hashtag;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.BackgroundColorSpan;
import android.text.style.CharacterStyle;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.text.style.TypefaceSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;

import com.lib.hashtag.callbacks.OnHashTagClickListener;
import com.lib.hashtag.views.HashTagView;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Abhishek on 3/6/2016.
 */
public final class HashTag {
    private final HashTagView view;
    private int hashColor = Color.BLACK;
    private int hashBackgroundColor = Color.TRANSPARENT;
    private int atColor = Color.BLACK;
    private int atBackgroundColor = Color.TRANSPARENT;
    private float hashSize = 1f;
    private float atSize = 1f;
    private boolean trackHash = true;
    private boolean trackAt;
    private boolean boldHash;
    private boolean boldAt;
    private boolean italicHash;
    private boolean italicAt;
    private boolean underlineHash;
    private boolean underlineAt;
    private Typeface hashTagFont;
    private Typeface atFont;
    private OnHashTagClickListener hashTagListener;

    public HashTag() {
        this(null);
    }

    public HashTag(HashTagView view) {
        this.view = view;
    }

    public void createHashTags(CharSequence text) {
        Spannable spannable = (Spannable) text;
        CharacterStyle[] spans = spannable.getSpans(0, text.length(), CharacterStyle.class);
        for (CharacterStyle span : spans)
            spannable.removeSpan(span);
        createTags(text);
    }

    public List<String> getHashTags(CharSequence text) {
        return getTags(text, '#');
    }

    public List<String> getAts(CharSequence text) {
        return getTags(text, '@');
    }

    private List<String> getTags(CharSequence text, char c) {
        Set<String> tags = new LinkedHashSet<>();
        Spannable spannable = (Spannable) text;
        for (CharacterStyle span : spannable.getSpans(0, text.length(), CharacterStyle.class)) {
            if (text.charAt(spannable.getSpanStart(span)) == c)
                tags.add(text.toString().substring(spannable.getSpanStart(span) + 1, spannable.getSpanEnd(span)));
        }
        return new ArrayList<>(tags);
    }

    private void createTags(CharSequence text) {
        int itemIndex;
        int index = 0;
        while (index < text.length() - 1) {
            char c = text.charAt(index);
            int nextItemIndex = index + 1;
            if (isUpdateRequired(c)) {
                itemIndex = index;
                nextItemIndex = getIndex(text, itemIndex);
                updateSpans(text, itemIndex, nextItemIndex, c == '#');
            }
            index = nextItemIndex;
        }
    }

    private boolean isUpdateRequired(char c) {
        if (trackHash && c == '#')
            return true;
        else if (trackAt && c == '@')
            return true;
        return false;
    }

    private int getIndex(CharSequence text, int itemIndex) {
        int index = -1;
        for (int i = itemIndex + 1; i < text.length(); i++) {
            char c = text.charAt(i);
            if (!Character.isLetterOrDigit(c)) {
                index = i;
                break;
            }
        }
        return (index == -1 ? text.length() : index);
    }

    private void updateSpans(CharSequence text, int start, int end, boolean isHash) {
        Spannable spannable = (Spannable) text;
        spannable.setSpan(new ForegroundColorSpan(getTextColor(isHash)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(new BackgroundColorSpan(getBackgroundColor(isHash)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(new StyleSpan(getTextStyle(isHash)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        if (getFont(isHash) != null)
            spannable.setSpan(new FontSpan("", getFont(isHash)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        if (isUnderlineRequired(isHash))
            spannable.setSpan(new UnderlineSpan(), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(new RelativeSizeSpan(isHash ? getHashTextSize() : getAtTextSize()), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(new TagClickSpan(), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    private int getTextColor(boolean isHash) {
        return isHash ? hashColor : atColor;
    }

    private int getBackgroundColor(boolean isHash) {
        return isHash ? hashBackgroundColor : atBackgroundColor;
    }

    private int getTextStyle(boolean isHash) {
        if (isHash) {
            if (boldHash && italicHash)
                return Typeface.BOLD | Typeface.ITALIC;
            else if (boldHash && !italicHash)
                return Typeface.BOLD;
            else if (!boldHash && italicHash)
                return Typeface.ITALIC;
            else
                return Typeface.NORMAL;
        } else {
            if (boldAt && italicAt)
                return Typeface.BOLD | Typeface.ITALIC;
            else if (boldAt && !italicAt)
                return Typeface.BOLD;
            else if (!boldAt && italicAt)
                return Typeface.ITALIC;
            else
                return Typeface.NORMAL;
        }
    }

    private boolean isUnderlineRequired(boolean isHash) {
        return isHash ? underlineHash : underlineAt;
    }

    private Typeface getFont(boolean isHash) {
        return isHash ? hashTagFont : atFont;
    }

    private class TagClickSpan extends ClickableSpan {

        @Override
        public void onClick(View widget) {
            CharSequence text = ((TextView) widget).getText();
            Spanned s = (Spanned) text;
            if (hashTagListener != null)
                hashTagListener.onClick(view, text.charAt(s.getSpanStart(this)), text.subSequence(s.getSpanStart(this) + 1, s.getSpanEnd(this)).toString());
        }

        @Override
        public void updateDrawState(TextPaint ds) {

        }
    }

    private class FontSpan extends TypefaceSpan {
        private final Typeface tf;

        public FontSpan(String family, Typeface tf) {
            super(family);
            this.tf = tf;
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            updateFont(ds);
        }

        @Override
        public void updateMeasureState(TextPaint paint) {
            updateFont(paint);
        }

        private void updateFont(Paint paint) {
            Typeface old = paint.getTypeface();
            int oldStyle = (old == null) ? 0 : old.getStyle();
            int fake = oldStyle & ~tf.getStyle();
            if ((fake & Typeface.BOLD) != 0)
                paint.setFakeBoldText(true);
            if ((fake & Typeface.ITALIC) != 0)
                paint.setTextSkewX(-0.25f);
            paint.setTypeface(tf);
        }
    }

    public int getHashColor() {
        return hashColor;
    }

    public void setHashColor(int color) {
        this.hashColor = color;
    }

    public int getHashBackgroundColor() {
        return hashBackgroundColor;
    }

    public void setHashBackgroundColor(int color) {
        this.hashBackgroundColor = color;
    }

    public int getAtBackgroundColor() {
        return atBackgroundColor;
    }

    public void setAtBackgroundColor(int color) {
        this.atBackgroundColor = color;
    }

    public int getAtColor() {
        return atColor;
    }

    public void setAtColor(int color) {
        this.atColor = color;
    }

    public float getHashTextSize() {
        if (hashSize <= 0f)
            hashSize = 1f;
        return hashSize;
    }

    public void setHashTextSize(float size) {
        this.hashSize = size;
    }

    public float getAtTextSize() {
        if (atSize <= 0f)
            atSize = 1f;
        return atSize;
    }

    public void setAtTextSize(float size) {
        this.atSize = size;
    }

    public boolean isHashTracked() {
        return trackHash;
    }

    public void setTrackHash(boolean track) {
        this.trackHash = track;
    }

    public boolean isAtTracked() {
        return trackAt;
    }

    public void setTrackAt(boolean track) {
        this.trackAt = track;
    }

    public boolean isHashBold() {
        return boldHash;
    }

    public void setBoldHash(boolean bold) {
        this.boldHash = bold;
    }

    public boolean isAtBold() {
        return boldAt;
    }

    public void setBoldAt(boolean bold) {
        this.boldAt = bold;
    }

    public boolean isHashItalic() {
        return italicHash;
    }

    public void setItalicHash(boolean italic) {
        this.italicHash = italic;
    }

    public boolean isAtItalic() {
        return italicAt;
    }

    public void setItalicAt(boolean italic) {
        this.italicAt = italic;
    }

    public boolean isHashUnderlined() {
        return underlineHash;
    }

    public void setUnderlineHash(boolean underline) {
        this.underlineHash = underline;
    }

    public boolean isAtUnderlined() {
        return underlineAt;
    }

    public void setUnderlineAt(boolean underline) {
        this.underlineAt = underline;
    }

    public void setHashTagFont(Typeface font) {
        this.hashTagFont = font;
    }

    public void setAtFont(Typeface font) {
        this.atFont = font;
    }

    public void setOnHashTagClickListener(OnHashTagClickListener l) {
        this.hashTagListener = l;
    }
}