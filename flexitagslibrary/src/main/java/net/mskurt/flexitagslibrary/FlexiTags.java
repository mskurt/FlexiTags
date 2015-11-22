/**
 * Wellcome to FlexiTags. This custom view shows tags which are configured easily by you.
 * You give a string list and FlexiTags convert them to tag views.
 * It adds them side by side and if there isn't enough width to add next tag, it continues with next line.
 * Flexi word comes from this feature. You can CRUD tags at runtime and you can group them.
 * All visual features are customizable including group appearances.
 * You can set different backgrounds or tag icons for different groups of tag.
 * Tags can be clickable. Set a tag click listener to flexitags and when a tag clicked you can catch it.
 *
 * @author  Sedat Kurt
 * @since   2015-11-21
 * @company 4pps Mobile Software
 */

package net.mskurt.flexitagslibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class FlexiTags extends LinearLayout implements View.OnClickListener{

    private Context context;
    private ArrayList<Tag> tags;
    private ArrayList<Group> groups;
    private ArrayList<View> tagViews;
    private LayoutInflater inflater;
    private OnTagClickListener onTagClickListener;

    /**
     * Default values for visual stuffs if relevant feature isn't set.
     */
    private final int DEFAULT_VERTICAL_MARGIN_BETWEEN_TAGS_IN_DP=5;
    private final int DEFAULT_HORIZONTAL_MARGIN_BETWEEN_TAGS_IN_DP=5;
    private final int DEFAULT_PADDING_IN_TAG_IN_DP=10;
    private final int DEFAULT_MARGIN_BETWEEN_TEXT_AND_ICON_IN_DP=5;
    private final int DEFAULT_TAG_FONT_SIZE_IN_PX=14;
    private final int DEFAULT_TAG_TEXT_COLOR = Color.parseColor("#000000");


    /**
     * Variables for customizable features.
     */
    private int TAG_ICON_SIZE_IN_PX;
    private int VERTICAL_MARGIN_BETWEEN_TAGS_IN_PX;
    private int HORIZONTAL_MARGIN_BETWEEN_TAGS_IN_PX;
    private int PADDING_IN_TAG_IN_PX;
    private int MARGIN_BETWEEN_TEXT_AND_ICON_IN_PX;
    private Drawable TAG_ICON_DRAWABLE;
    private Drawable TAG_BACKGROUND_DRAWABLE;
    private int TAG_FONT_SIZE_IN_PX;
    private int TAG_TEXT_COLOR;
    private Typeface TAG_TEXT_CUSTOM_TYPEFACE;
    private int TAG_TEXT_DEFINED_TYPEFACE;
    private int TAG_TEXT_STYLE;


    /**
     * Some dimens for flexitags.
     */
    private int FLEXITAGS_WIDTH;
    private int FLEXITAGS_LEFT_PADDING;
    private int FLEXITAGS_RIGHT_PADDING;


    public FlexiTags(Context context) {
        super(context);
        this.context=context;
        setOrientation(LinearLayout.VERTICAL);
        Util.setUtilStuff(context);
        initializeTagAndGroupList();
        inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        TAG_ICON_SIZE_IN_PX=0;
        VERTICAL_MARGIN_BETWEEN_TAGS_IN_PX=(int)(DEFAULT_VERTICAL_MARGIN_BETWEEN_TAGS_IN_DP*Util.DENSITY);
        HORIZONTAL_MARGIN_BETWEEN_TAGS_IN_PX=(int)(DEFAULT_HORIZONTAL_MARGIN_BETWEEN_TAGS_IN_DP*Util.DENSITY);
        PADDING_IN_TAG_IN_PX=(int)(DEFAULT_PADDING_IN_TAG_IN_DP*Util.DENSITY);
        MARGIN_BETWEEN_TEXT_AND_ICON_IN_PX=(int)(DEFAULT_MARGIN_BETWEEN_TEXT_AND_ICON_IN_DP*Util.DENSITY);
        TAG_ICON_DRAWABLE=null;
        TAG_BACKGROUND_DRAWABLE=null;
        TAG_FONT_SIZE_IN_PX=DEFAULT_TAG_FONT_SIZE_IN_PX;
        TAG_TEXT_CUSTOM_TYPEFACE=null;
        TAG_TEXT_DEFINED_TYPEFACE=0;
        TAG_TEXT_STYLE=0;
        TAG_TEXT_COLOR=DEFAULT_TAG_TEXT_COLOR;
    }

    public FlexiTags(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        setOrientation(LinearLayout.VERTICAL);
        Util.setUtilStuff(context);
        initializeTagAndGroupList();
        inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        getAllAttributes(context.getTheme().obtainStyledAttributes(attrs, R.styleable.FlexiTags, 0, 0));
    }

    public FlexiTags(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        setOrientation(LinearLayout.VERTICAL);
        Util.setUtilStuff(context);
        initializeTagAndGroupList();
        inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        getAllAttributes(context.getTheme().obtainStyledAttributes(attrs, R.styleable.FlexiTags, 0, 0));
    }

    public FlexiTags(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context=context;
        setOrientation(LinearLayout.VERTICAL);
        Util.setUtilStuff(context);
        initializeTagAndGroupList();
        inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        getAllAttributes(context.getTheme().obtainStyledAttributes(attrs, R.styleable.FlexiTags, 0, 0));
    }

    /**
     * Creates lists for model classes - tag and group
     */
    private void initializeTagAndGroupList(){
        tags=new ArrayList<>();
        groups=new ArrayList<>();
    }

    /**
     * Matches tags and groups.
     * If a group name equals a tag's group name, that group is assigned to tag's group reference.
     */
    private void matchTagsAndGroups(){
        for(Group group:groups){
            for(Tag tag:tags){
                if(tag.getGroupName().equals(group.getName())){
                    tag.setGroup(group);
                }
            }
        }
    }


    /**
     * Sets a layout param object to inflated tag view.
     * @param tagView
     */
    private void setLayoutParamsOfTag(View tagView){
        tagView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
    }

    /**
     * Sets a background to tag view. Firstly, checks if the tag is member of a group then apply that group background.
     * @param tagView
     * @param tag
     */
    private void setBackgroundOfTag(View tagView,Tag tag){
        Drawable groupBackground=null;
        try{groupBackground=Util.getDrawableResource(context,tag.getGroup().getBackgroundDrawableId());}catch (Exception e){groupBackground=null;}
        if(tag.getGroup()!=null && groupBackground!=null){
            Util.setViewBackground(tagView, groupBackground);
        }
        else if(TAG_BACKGROUND_DRAWABLE!=null){
            Util.setViewBackground(tagView, TAG_BACKGROUND_DRAWABLE.getConstantState().newDrawable());
        }
    }

    /**
     * Sets padding (left-top-right-bottom) to tag view.
     * @param tagView
     */
    private void setPaddingInTag(View tagView) {
        tagView.setPadding(PADDING_IN_TAG_IN_PX, PADDING_IN_TAG_IN_PX, PADDING_IN_TAG_IN_PX, PADDING_IN_TAG_IN_PX);
    }


    /**
     * Sets margin left and right of tag view.
     * @param tagView
     */
    private void setMarginRightAndLeftOfTag(View tagView) {
        LinearLayout.LayoutParams params = (LayoutParams)tagView.getLayoutParams();
        params.setMargins(HORIZONTAL_MARGIN_BETWEEN_TAGS_IN_PX, 0, HORIZONTAL_MARGIN_BETWEEN_TAGS_IN_PX,0);
        tagView.setLayoutParams(params);
    }

    /**
     * Sets width and height to tag view.
     * @param tagIcon
     */
    private void setDimensOfTagIcon(ImageView tagIcon){
        LinearLayout.LayoutParams params=(LinearLayout.LayoutParams) tagIcon.getLayoutParams();
        params.setMargins(0, 0, MARGIN_BETWEEN_TEXT_AND_ICON_IN_PX, 0);
        params.width=TAG_ICON_SIZE_IN_PX;
        params.height=TAG_ICON_SIZE_IN_PX;
        tagIcon.setLayoutParams(params);
    }

    /**
     * Sets a background and give a width and height to icon of tag view. Firstly, checks if the tag is member of a group then apply that group tag icon.
     * @param tagView
     * @param tag
     */
    private void setTagIconProperties(View tagView,Tag tag){
        Drawable groupIcon=null;
        try{groupIcon=Util.getDrawableResource(context,tag.getGroup().getIconDrawableId());}catch (Exception e){groupIcon=null;}
        ImageView tagIcon=(ImageView)tagView.findViewById(R.id.icon);
        if(tag.getGroup()!=null && groupIcon!=null){
            Util.setViewBackground(tagIcon, groupIcon);
            setDimensOfTagIcon(tagIcon);
        }
        else if(TAG_ICON_DRAWABLE!=null){
            Util.setViewBackground(tagIcon, TAG_ICON_DRAWABLE.getConstantState().newDrawable());
            setDimensOfTagIcon(tagIcon);
        }
        else{
            tagIcon.setVisibility(View.GONE);
        }
    }

    /**
     * Sets visual features and text to textview of tag.
     * @param tagView
     * @param tag
     */
    private void setTagTextProperties(View tagView,Tag tag){
        TextView tagText=(TextView)tagView.findViewById(R.id.text);
        tagText.setText(tag.getTagname());
        if(TAG_TEXT_CUSTOM_TYPEFACE!=null){
            tagText.setTypeface(TAG_TEXT_CUSTOM_TYPEFACE);
        }
        else{
            switch (TAG_TEXT_DEFINED_TYPEFACE){
                case 0: tagText.setTypeface(Typeface.DEFAULT,TAG_TEXT_STYLE);break;
                case 1: tagText.setTypeface(Typeface.SANS_SERIF,TAG_TEXT_STYLE);break;
                case 2: tagText.setTypeface(Typeface.MONOSPACE,TAG_TEXT_STYLE);break;
            }
        }
        tagText.setTextSize(TAG_FONT_SIZE_IN_PX);
        tagText.setTextColor(TAG_TEXT_COLOR);
    }

    /**
     * Forces to measure widths of all tag views.
     */
    private void findOutTagViewsWidth(){
        for(int i=0;i<tagViews.size();i++){
            tagViews.get(i).measure(MeasureSpec.UNSPECIFIED,MeasureSpec.UNSPECIFIED);
            Tag tag=(Tag)tagViews.get(i).getTag();
            tag.setWidth(tagViews.get(i).getMeasuredWidth());
        }
    }

    /**
     * Inflates all tag views, configures their visual features and place them into container.
     * You should call this function after you configured all you want.
     */
    public void showTime(){
        matchTagsAndGroups();
        tagViews=new ArrayList<>();
        int size=tags.size();
        for(int i=0;i<size;i++){
            View tagView=inflater.inflate(R.layout.tag,null,false);
            setLayoutParamsOfTag(tagView);
            setBackgroundOfTag(tagView,tags.get(i));
            setPaddingInTag(tagView);
            setTagIconProperties(tagView, tags.get(i));
            setTagTextProperties(tagView, tags.get(i));
            tagView.setTag(tags.get(i));
            tagView.setOnClickListener(this);
            tagViews.add(tagView);
        }
        post(new Runnable() {
            @Override
            public void run() {
                FLEXITAGS_LEFT_PADDING = getPaddingLeft();
                FLEXITAGS_RIGHT_PADDING = getPaddingLeft();
                FLEXITAGS_WIDTH = getMeasuredWidth();
                int availableSpace = FLEXITAGS_WIDTH - (FLEXITAGS_RIGHT_PADDING + FLEXITAGS_LEFT_PADDING);
                findOutTagViewsWidth();
                int size = tagViews.size();
                addView(getANewHorizontalContainer());
                for (int i = 0; i < size; i++) {
                    int tagWidth = ((Tag) tagViews.get(i).getTag()).getWidth();
                    if ((FLEXITAGS_WIDTH - (FLEXITAGS_RIGHT_PADDING + FLEXITAGS_LEFT_PADDING)) <= (tagWidth + (HORIZONTAL_MARGIN_BETWEEN_TAGS_IN_PX * 2))) {
                        if (availableSpace < (FLEXITAGS_WIDTH - (FLEXITAGS_RIGHT_PADDING + FLEXITAGS_LEFT_PADDING))) {
                            addView(getANewHorizontalContainer());
                        }
                        ((LinearLayout) getChildAt(getChildCount() - 1)).addView(tagViews.get(i));
                        setMarginRightAndLeftOfTag(tagViews.get(i));
                        addView(getANewHorizontalContainer());
                        availableSpace = FLEXITAGS_WIDTH - (FLEXITAGS_RIGHT_PADDING + FLEXITAGS_LEFT_PADDING);
                    } else if (availableSpace == (tagWidth + (HORIZONTAL_MARGIN_BETWEEN_TAGS_IN_PX * 2))) {
                        ((LinearLayout) getChildAt(getChildCount() - 1)).addView(tagViews.get(i));
                        setMarginRightAndLeftOfTag(tagViews.get(i));
                        addView(getANewHorizontalContainer());
                        availableSpace = FLEXITAGS_WIDTH - (FLEXITAGS_RIGHT_PADDING + FLEXITAGS_LEFT_PADDING);
                    } else if (availableSpace > (tagWidth + (HORIZONTAL_MARGIN_BETWEEN_TAGS_IN_PX * 2))) {
                        ((LinearLayout) getChildAt(getChildCount() - 1)).addView(tagViews.get(i));
                        setMarginRightAndLeftOfTag(tagViews.get(i));
                        availableSpace = availableSpace - (tagWidth + (HORIZONTAL_MARGIN_BETWEEN_TAGS_IN_PX * 2));
                    } else if (availableSpace < (tagWidth + (HORIZONTAL_MARGIN_BETWEEN_TAGS_IN_PX * 2))) {
                        availableSpace = FLEXITAGS_WIDTH - (FLEXITAGS_RIGHT_PADDING + FLEXITAGS_LEFT_PADDING);
                        addView(getANewHorizontalContainer());
                        ((LinearLayout) getChildAt(getChildCount() - 1)).addView(tagViews.get(i));
                        setMarginRightAndLeftOfTag(tagViews.get(i));
                        availableSpace = availableSpace - (tagWidth + (HORIZONTAL_MARGIN_BETWEEN_TAGS_IN_PX * 2));
                    }
                }
            }
        });
    }

    /**
     * Returns a container for a row of tags.
     * @return
     */
    private LinearLayout getANewHorizontalContainer(){
        LinearLayout linearLayout = new LinearLayout(context);
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, VERTICAL_MARGIN_BETWEEN_TAGS_IN_PX, 0, VERTICAL_MARGIN_BETWEEN_TAGS_IN_PX);
        linearLayout.setLayoutParams(params);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        return linearLayout;
    }

    /**
     * Adds tag object to the tag list.
     * @param tag
     */
    public void addTag(Tag tag){
        tags.add(tag);
    }

    /**
     * Adds tag object to the tag list with index.
     * @param index
     * @param tag
     */
    public void addTag(int index,Tag tag){
        tags.add(index, tag);
    }

    /**
     * Returns a tag at specified index.
     * @param index
     * @return
     */
    public Tag getTag(int index){
        return tags.get(index);
    }

    /**
     * Returns a tag in specified name.
     * @param tag
     * @return
     */
    public Tag getTag(String tag){
        Tag tagObj=null;
        int size=tags.size();
        for(int i=0;i<size;i++){
            if(tags.get(i).getTagname().equals(tag)){
                tagObj=tags.get(i);
                break;
            }
        }
        return tagObj;
    }

    /**
     * Removes a tag at specified index.
     * @param index
     */
    public void removeTag(int index){
        tags.remove(index);
    }

    /**
     * Removes a tag in specified name.
     * @param tag
     */
    public void removeTag(String tag){
        int size=tags.size();
        for(int i=0;i<size;i++){
            if(tags.get(i).getTagname().equals(tag)){
                tags.remove(i);
                break;
            }
        }
    }

    /**
     * Adds a group.
     * @param group
     */
    public void addGroup(Group group){
        groups.add(group);
    }


    /**
     * Get a group in specified name.
     * @param group
     * @return
     */
    public Group getGroup(String group){
        Group groupObj=null;
        int size=groups.size();
        for(int i=0;i<size;i++){
            if(groups.get(i).getName().equals(group)){
                groupObj=groups.get(i);
                break;
            }
        }
        return groupObj;
    }

    /**
     * Removes a group at specified index.
     * @param index
     */
    public void removeGroup(int index){
        groups.remove(index);
    }

    /**
     * Removes a group in specified name.
     * @param group
     */
    public void removeGroup(String group){
        int size=groups.size();
        for(int i=0;i<size;i++){
            if(groups.get(i).getName().equals(group)){
                groups.remove(i);
                break;
            }
        }
    }

    /**
     * Removes all tags.
     */
    public void clearAllTags(){
        tags.clear();
    }


    /**
     * Removes all groups.
     */
    public void clearAllGroups(){
        groups.clear();
    }

    /**
     * Converts all items in coming string array into tags and adds them all to tag list.
     * @param tagArray
     */
    public void addTagsFromStringArray(String[] tagArray){
        for(String s:tagArray){
            tags.add(new Tag(s));
        }
    }

    /**
     * Converts all items in coming string arraylist into tags and adds them all to tag list.
     * @param tagArrayList
     */
    public void addTagsFromStringArrayList(ArrayList<String> tagArrayList){
        for(String s:tagArrayList){
            tags.add(new Tag(s));
        }
    }

    /**
     * Converts all items in coming string array into tags specifying their group and adds them all to tag list.
     * @param group
     * @param tagArray
     */
    public void addTagsFromStringArrayWithGroup(String group,String[] tagArray){
        for(String s:tagArray){
            tags.add(new Tag(group,s));
        }
    }

    /**
     * Converts all items in coming string arraylist into tags specifying their group and adds them all to tag list.
     * @param group
     * @param tagArrayList
     */
    public void addTagsFromStringArrayListWithGroup(String group,ArrayList<String> tagArrayList){
        for(String s:tagArrayList){
            tags.add(new Tag(group,s));
        }
    }

    /**
     * Refreshes FlexiTags appearance.
     */
    public void refresh(){
        removeAllViews();
        showTime();
    }

    /**
     * Sets a drawable for all tags.
     * @param drawableId
     */
    public void setTagBackground(int drawableId){
        TAG_BACKGROUND_DRAWABLE=Util.getDrawableResource(context, drawableId);
    }

    /**
     * Sets a drawable for all tag icons.
     * @param drawableId
     */
    public void setTagIcon(int drawableId){
        TAG_ICON_DRAWABLE=Util.getDrawableResource(context, drawableId);
    }

    /**
     * Sets padding (left-top-right-bottom) of tag view in px.
     * @param sizePixel
     */
    public void setTagPaddingInPx(int sizePixel){
        PADDING_IN_TAG_IN_PX=sizePixel<=0 ? 0 : sizePixel;
    }

    /**
     * Sets padding (left-top-right-bottom) of tag view in dp.
     * @param sizeInDp
     */
    public void setTagPaddingInDp(int sizeInDp){
        PADDING_IN_TAG_IN_PX=sizeInDp<=0 ? 0 : (int)(Util.DENSITY*sizeInDp);
    }

    /**
     * Sets top and bottom margin for a tags row in px.
     * @param sizePixel
     */
    public void setTagVerticalMarginInPx(int sizePixel){
        VERTICAL_MARGIN_BETWEEN_TAGS_IN_PX=sizePixel<=0 ? 0 : sizePixel;
    }

    /**
     * Sets top and bottom margin for a tags row in dp.
     * @param sizeInDp
     */
    public void setTagVerticalMarginInDp(int sizeInDp){
        VERTICAL_MARGIN_BETWEEN_TAGS_IN_PX=sizeInDp<=0 ? 0 : (int)(Util.DENSITY*sizeInDp);
    }

    /**
     * Sets right and left margin for a tag view in px.
     * @param sizePixel
     */
    public void setTagHorizontalMarginInPx(int sizePixel){
        HORIZONTAL_MARGIN_BETWEEN_TAGS_IN_PX=sizePixel<=0 ? 0 : sizePixel;
    }

    /**
     * Sets right and left margin for a tag view in dp.
     * @param sizeInDp
     */
    public void setTagHorizontalMarginInDp(int sizeInDp){
        HORIZONTAL_MARGIN_BETWEEN_TAGS_IN_PX=sizeInDp<=0 ? 0 : (int)(Util.DENSITY*sizeInDp);
    }

    /**
     * Sets margin between icon and text in a tag view in px.
     * @param sizePixel
     */
    public void setMarginBetweenIconAndTextInPx(int sizePixel){
        MARGIN_BETWEEN_TEXT_AND_ICON_IN_PX=sizePixel<=0 ? 0 : sizePixel;
    }

    /**
     * Sets margin between icon and text in a tag view in dp.
     * @param sizeInDp
     */
    public void setMarginBetweenIconAndTextInDp(int sizeInDp){
        MARGIN_BETWEEN_TEXT_AND_ICON_IN_PX=sizeInDp<=0 ? 0 : (int)(Util.DENSITY*sizeInDp);
    }

    /**
     * Sets width and height size of all tag icons in px.
     * @param sizePixel
     */
    public void setTagIconSizeInPx(int sizePixel){
        TAG_ICON_SIZE_IN_PX=sizePixel<=0 ? 0 : sizePixel;
    }

    /**
     * Sets width and height size of all tag icons in dp.
     * @param sizeInDp
     */
    public void setTagIconSizeInDp(int sizeInDp){
        TAG_ICON_SIZE_IN_PX=sizeInDp<=0 ? 0 : (int)(Util.DENSITY*sizeInDp);
    }

    /**
     * Sets a color for all texts of tags.
     * @param color
     */
    public void setTagTextColor(int color){
        TAG_TEXT_COLOR=color;
    }

    /**
     * Sets font size of all texts of tags.
     * @param unit
     * @param size
     */
    public void setTagTextFontSize(int unit,int size){
        //Units:
        //TypedValue.COMPLEX_UNIT_PX   //Pixels
        //TypedValue.COMPLEX_UNIT_SP   //Scaled Pixels
        //TypedValue.COMPLEX_UNIT_DIP  //Device Independent Pixels
        switch (unit){
            case TypedValue.COMPLEX_UNIT_PX : TAG_FONT_SIZE_IN_PX=size;break;
            case TypedValue.COMPLEX_UNIT_SP : TAG_FONT_SIZE_IN_PX=(int)(Util.SCALED_DENSITY*size);break;
            case TypedValue.COMPLEX_UNIT_DIP : TAG_FONT_SIZE_IN_PX=(int)(Util.DENSITY*size);break;
        }
    }

    /**
     * Sets a tag click listener to catch click events of tags.
     * @param onTagClickListener
     */
    public void setOnTagClickListener(OnTagClickListener onTagClickListener){
        this.onTagClickListener=onTagClickListener;
    }

    /**
     * Sets a typeface for all texts of tags.
     * @param typeFace
     */
    public void setTagTextTypeFace(Typeface typeFace){
        TAG_TEXT_CUSTOM_TYPEFACE=typeFace;
    }

    /**
     * Gets all attributes specified by developer.
     * @param a
     */
    private void getAllAttributes(TypedArray a){
        TAG_BACKGROUND_DRAWABLE = a.getDrawable(R.styleable.FlexiTags_tagBackground);
        TAG_ICON_DRAWABLE = a.getDrawable(R.styleable.FlexiTags_tagIcon);
        TAG_ICON_SIZE_IN_PX = a.getDimensionPixelSize(R.styleable.FlexiTags_tagIconSize, 0);
        VERTICAL_MARGIN_BETWEEN_TAGS_IN_PX = a.getDimensionPixelSize(R.styleable.FlexiTags_tagVerticalMargin, (int) (DEFAULT_VERTICAL_MARGIN_BETWEEN_TAGS_IN_DP * Util.DENSITY));
        HORIZONTAL_MARGIN_BETWEEN_TAGS_IN_PX = a.getDimensionPixelSize(R.styleable.FlexiTags_tagHorizontalMargin, (int) (DEFAULT_HORIZONTAL_MARGIN_BETWEEN_TAGS_IN_DP * Util.DENSITY));
        PADDING_IN_TAG_IN_PX = a.getDimensionPixelSize(R.styleable.FlexiTags_tagPadding, (int)(DEFAULT_PADDING_IN_TAG_IN_DP*Util.DENSITY));
        MARGIN_BETWEEN_TEXT_AND_ICON_IN_PX = a.getDimensionPixelSize(R.styleable.FlexiTags_tagMarginBetweenTextAndIcon,(int)(DEFAULT_MARGIN_BETWEEN_TEXT_AND_ICON_IN_DP*Util.DENSITY));
        TAG_FONT_SIZE_IN_PX = a.getDimensionPixelSize(R.styleable.FlexiTags_tagTextSize, DEFAULT_TAG_FONT_SIZE_IN_PX);
        TAG_TEXT_DEFINED_TYPEFACE = a.getInt(R.styleable.FlexiTags_tagTextTypeface, 0);
        TAG_TEXT_STYLE = a.getInt(R.styleable.FlexiTags_tagTextStyle, 0);
        TAG_TEXT_COLOR = a.getColor(R.styleable.FlexiTags_tagTextColor, DEFAULT_TAG_TEXT_COLOR);
        CharSequence[] tagArray = a.getTextArray(R.styleable.FlexiTags_tags);
        if(tagArray!=null){
            for(CharSequence cs:tagArray){
                tags.add(new Tag(cs.toString()));
            }
        }
    }


    /**
     * Catches click event of a tag.
     * @param v
     */
    @Override
    public void onClick(View v) {
        this.onTagClickListener.onTagClick((Tag)v.getTag());
    }

    /**
     * Interface class for click of tag.
     */
    public interface OnTagClickListener {
        void onTagClick(Tag tag);
    }


}
