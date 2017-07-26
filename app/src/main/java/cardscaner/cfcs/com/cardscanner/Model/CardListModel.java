package cardscaner.cfcs.com.cardscanner.Model;

/**
 * Created by Admin on 26-07-2017.
 */

public class CardListModel
{
    public String name;
    public String compName;

    public CardListModel(String name, String compName) {
        this.name = name;
        this.compName = compName;
    }

    public String getName() {
        return name;
    }

    public String getCompName() {
        return compName;
    }
}
