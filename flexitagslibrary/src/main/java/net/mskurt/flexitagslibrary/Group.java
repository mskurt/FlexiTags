/**
 * A model class for Group.
 *
 * @author  Sedat Kurt
 * @since   2015-11-21
 * @company 4pps Mobile Software
 */

package net.mskurt.flexitagslibrary;

public class Group {
    private String name;
    private int backgroundDrawableId;
    private int iconDrawableId;

    public Group(String name) {
        this.name = name;
    }

    public Group(String name, int backgroundDrawableId, int iconDrawableId) {
        this.name = name;
        this.backgroundDrawableId = backgroundDrawableId;
        this.iconDrawableId = iconDrawableId;
    }

    public int getBackgroundDrawableId() {
        return backgroundDrawableId;
    }

    public void setBackgroundDrawableId(int backgroundDrawableId) {
        this.backgroundDrawableId = backgroundDrawableId;
    }

    public int getIconDrawableId() {
        return iconDrawableId;
    }

    public void setIconDrawableId(int iconDrawableId) {
        this.iconDrawableId = iconDrawableId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
