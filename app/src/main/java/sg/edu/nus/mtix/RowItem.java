package sg.edu.nus.mtix;

public class RowItem {

    private String title;
    private String desc;
    private byte[] image;
    private String name;

    public RowItem(String title, String desc, byte[] image, String name) {
        this.title = title;
        this.desc = desc;
        this.image = image;
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return title;
    }
}
