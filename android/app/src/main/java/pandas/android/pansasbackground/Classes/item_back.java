package pandas.android.pansasbackground.Classes;

public class item_back {
    String label="";
    String descripe="";
    int itemIcon;

    public item_back(String label, String descripe, int itemIcon) {
        this.label = label;
        this.descripe = descripe;
        this.itemIcon = itemIcon;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescripe() {
        return descripe;
    }

    public void setDescripe(String descripe) {
        this.descripe = descripe;
    }

    public int getItemIcon() {
        return itemIcon;
    }

    public void setItemIcon(int itemIcon) {
        this.itemIcon = itemIcon;
    }

    @Override
    public String toString() {
        return label;
    }
}
