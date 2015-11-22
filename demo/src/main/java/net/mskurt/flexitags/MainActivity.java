package net.mskurt.flexitags;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import net.mskurt.flexitagslibrary.FlexiTags;
import net.mskurt.flexitagslibrary.Tag;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final FlexiTags flexiTags=(FlexiTags)findViewById(R.id.flexitags);
        flexiTags.addTagsFromStringArray(new String[]{"Android", "Java", "Go", "Objective-C", "Php",
                "JavaScript", "Mysql", "Css", "Html", "Json", "Ruby", "Xml", "Linux", "Node.js", "Eclipse",
                "Android Studio", "Multithreading", "Oracle",
                "This tag is very long for testing, it should overflow. Lorem ipsum dolor sit amet, consectetur adipiscing elit."});
        flexiTags.setOnTagClickListener(new FlexiTags.OnTagClickListener() {
            @Override
            public void onTagClick(Tag tag) {
                Toast.makeText(MainActivity.this, ("Clicked on " + tag.getTagname()), Toast.LENGTH_LONG).show();
            }
        });

        flexiTags.showTime();
    }
}
