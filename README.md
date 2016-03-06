# HashTagView
![HashTagView](/HashTagView.png)

A widget that seperated words starting with **#** or **@**. HashTagView can be used as TextView and EditText.

To include ``HashTagView`` in your ``layout``, add the following code
```
<com.lib.hashtag.views.HashTagView
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:hashTagColor="#ff99cc00"
    app:trackHashTag="true" />
```
```
<com.lib.hashtag.views.HashTagEditText
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:hashTagColor="#ff99cc00"
    app:trackHashTag="true" />
```
HashTagView also provides with the following features
- Color for HashTag: **Text Color** & **Background Color**
- Text style for HashTag: **Bold**, *Italic* & Underline
- Text Size
- Fonts (Place the font in ``assets/font/font_name.ttf``)

Following is the code for the above features
```
app:hashTagColor="@color/hashtag_color"
app:hastTagBackground="@color/hashtag_background"
app:boldHashTag="true"
app:italicHashTag="true"
app:underlineHashTag="true"
app:hashTagSize="1.2"
app:fontName="font_name.ttf"
```
HashTagView also provides with the feature of ClickListener through ``OnHashTagClickListener``
### Note
``OnHashTagClickListener`` will only work on ``HashTagView``
```
HashTagView hashTag = (HashTagView) findViewById(R.id.hash_tag);
hashTag.setOnHashTagClickListener(new OnHashTagClickListener() {
    @Override
    public void onClick(HashTagView v, char c, String tag) {
        
    }
});
```
All the HashTags can be retrieved from ``HashTagView`` & ``HashTagEditText`` using the following code.
```
List<String> tags = hashTagView.getHashTags();
```
