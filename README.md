# FlexiTags

Wellcome to FlexiTags for Android. This custom view shows tags which can be configured easily. You give a string list and FlexiTags convert them to tag views. It adds them side by side and if there isn't enough width to add next tag, it continues with next line. Flexi word comes from this feature. You can CRUD tags at runtime and you can group them. All visual features are customizable including group appearances. You can either set a background and icon for all tags in FLexiTags or set different backgrounds and icons for different groups of tag. Tags can be clickable. Set a tag click listener to flexitags and when a tag clicked you can catch it.(Your minimum sdk should be 15 and higher)

![alt tag](https://github.com/mskurt/FlexiTags/blob/master/demo/src/main/res/preview/preview.jpg?raw=true)

## Usage

1)First of all, add below in your **build.gradle** at the end of repositories:
    
    repositories {
        // ...
        maven { url "https://jitpack.io" }
    }
    
Add below dependency also.

    compile 'com.github.mskurt:NeverEmptyListView:v1.0.1' 

2)Add the FlexiTags to your layout xml. You must use features which are belong to FlexiTags with `custom` namespace. It is recommended to use **square size image** for **tagIcon**. 

    <?xml version="1.0" encoding="utf-8"?>
    <RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

        <net.mskurt.flexitagslibrary.FlexiTags
            xmlns:custom="http://schemas.android.com/apk/res-auto"
            android:id="@+id/flexitags"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            custom:tagIconSize="15dp"
            custom:tagIcon="@drawable/hashtag_icon"
            custom:tagTextColor="#000000"
            custom:tagBackground="@drawable/tag_selector"/>

    </RelativeLayout>
    
3)Bind your FlexiTags and configure it. You can import a string array. **You should call showTime() method after all configuration** and let the FlexiTags works. (You can import a string ArrayList also.)

``` java
FlexiTags flexiTags=(FlexiTags)findViewById(R.id.flexitags);
        flexiTags.addTagsFromStringArray(new String[]{"Android", "Java", "Go","Objective-C", "Php", 
                "JavaScript", "Mysql", "Css", "Html", "Json","Ruby","Xml","Linux","Node.js","Eclipse",
                "Android Studio","Multithreading","Oracle",
                "This tag is very long for testing, it should overflow. Lorem ipsum dolor sit amet, consectetur adipiscing elit."});
flexiTags.showTime();        
```


4)If you think that you don't have enough height for FlexiTags, you can wrap it with ScrollView and make it scrollable.

    <?xml version="1.0" encoding="utf-8"?>
    <RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <ScrollView
            android:layout_width="match_parent"
            android:layout_height="match_parent">

            <net.mskurt.flexitagslibrary.FlexiTags
                xmlns:custom="http://schemas.android.com/apk/res-auto"
                android:id="@+id/flexitags"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                custom:tagIconSize="15dp"
                custom:tagIcon="@drawable/hashtag_icon"
                custom:tagTextColor="#000000"
                custom:tagBackground="@drawable/tag_selector"/>
        
        </ScrollView>
        
    </RelativeLayout>

5)You can set a string array to your FlexiTags in layout xml.

    <net.mskurt.flexitagslibrary.FlexiTags
        xmlns:custom="http://schemas.android.com/apk/res-auto"
        android:id="@+id/flexitags"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        custom:tagIconSize="15dp"
        custom:tagIcon="@drawable/hashtag_icon"
        custom:tagTextColor="#000000"
        custom:tagBackground="@drawable/tag_selector"
        custom:tags="@array/some_string_array"/>

6)You can configure all visual attributes at runtime.
``` java
FlexiTags flexiTags=(FlexiTags)findViewById(R.id.flexitags);
        flexiTags.addTagsFromStringArray(new String[]{"Android", "Java", "Go","Objective-C", "Php", 
                "JavaScript", "Mysql", "Css", "Html", "Json","Ruby","Xml","Linux","Node.js","Eclipse",
                "Android Studio","Multithreading","Oracle",
                "This tag is very long for testing, it should overflow. Lorem ipsum dolor sit amet, consectetur adipiscing elit."});
flexiTags.setTagIcon(R.drawable.other_tag_icon);
flexiTags.setTagIconSizeInDp(20);
flexiTags.setTagBackground(R.drawable.other_tag_background);
flexiTags.setTagTextColor(Color.parseColor("#f4f4f4"));
flexiTags.setTagVerticalMarginInDp(20);
flexiTags.showTime();
```

7)You can set a tag click listener to FlexiTags. Clicked tag comes to onTagClick() method.

``` java
flexiTags.setOnTagClickListener(new FlexiTags.OnTagClickListener() {
    @Override
    public void onTagClick(Tag tag) {
        Toast.makeText(MainActivity.this,("Clicked on"+tag.getTagname()),Toast.LENGTH_LONG).show();        
    }
});
```

8)You can group your tags and set a different looking for different groups.

``` java
FlexiTags flexiTags=(FlexiTags)findViewById(R.id.flexitags);
flexiTags.addGroup(new Group("id_of_group1", R.drawable.some_background_for_group1, R.drawable.some_icon_for_group1));
flexiTags.addGroup(new Group("id_of_group2", R.drawable.some_background_for_group2, R.drawable.some_icon_for_group2));
flexiTags.addGroup(new Group("id_of_group3", R.drawable.some_background_for_group3, R.drawable.some_icon_for_group3));
flexiTags.addTagsFromStringArrayWithGroup("id_of_group1", new String[]{"NY", "Lisbon", "Los Angeles", "Istanbul", "Tokyo","Munchen","Moscova"});
flexiTags.addTagsFromStringArrayWithGroup("id_of_group2", new String[]{"Finance","Health","Technology","Banking","Tourism"});
flexiTags.addTagsFromStringArrayWithGroup("id_of_group3", new String[]{"Engineering","Law","Education","Medicine","Human Resources"});
flexiTags.showTime();
```

9)You can apply all crud (create-read-update-delete) methods for tag or group.

``` java
//Create new tag. Just with a name or with name and group
flexiTags.addTag(new Tag("new tag"));
flexiTags.addTag(new Tag("group of new tag ", "new tag"));

//Get a tag with its index or its name
flexiTags.getTag(index);
flexiTags.getTag("tag name");

//Update your tag
flexiTags.getTag(index).setTagname("new tag name");
flexiTags.getTag("tag name").setGroup(flexiTags.getGroup("group name"));

//Remove your tag with its index or its name
flexiTags.removeTag(index);
flexiTags.removeTag("tag name");

//You can do all with group objects of FlexiTags
```

10)Here is one of the most important things. Assume that you called **showTime()** method once and your FlexiTags is initialized. But you want to update your tags or change your FlexiTags looking at runtime. **You should call  refresh() method after all changings.**

``` java
flexiTags.setTagIconSizeInDp(new_value_for_icon_size);
flexiTags.setTagBackground(R.drawable.new_background_for_tags);
flexiTags.setTagTextColor(new_color_for_text_of_tags);
flexiTags.clearAllTags();
flexiTags.clearAllGroups();
flexiTags.addTagsFromStringArray(new_string_array_or_arrayList);
flexiTags.refresh();
```
 
## FlexiTags Customizing
FlexiTags is fully customizable. You can use below feature list.

![alt tag](https://github.com/mskurt/FlexiTags/blob/master/demo/src/main/res/preview/features.jpg?raw=true)

    tagBackground: color or drawable reference
    tagIcon : color or drawable reference
    tagIconSize : dimension in px or dp
    tagVerticalMargin : dimension in px or dp
    tagHorizontalMargin : dimension in px or dp
    tagPadding : dimension in px or dp
    tagMarginBetweenTextAndIcon : dimension in px or dp
    tagTextSize : dimension in sp
    tagTextColor : hardcoded color or color reference
    tagTextStyle : enum normal - bold - italic
    tagTextTypeface : enum normal - sansserif - monospace
    tags : string array reference

## Developed By
Sedat Kurt - www.mskurt.net - sedat@4pps.co

    Copyright 2015 Sedat Kurt.
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
       http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

