/**
 * A model class for Tag.
 *
 * @author  Sedat Kurt
 * @since   2015-11-21
 * @company 4pps Mobile Software
 */

package net.mskurt.flexitagslibrary;

public class Tag {
    private String tagname=null;
    private String groupName=null;
    private Group group=null;
    private int width;

    public Tag(String tagname) {
        this.tagname = tagname;
    }

    public Tag(String groupName, String tagname) {
        this.groupName = groupName;
        this.tagname = tagname;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getTagname() {
        return tagname;
    }

    public void setTagname(String tagname) {
        this.tagname = tagname;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
        this.groupName=group.getName();
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
