package cardscaner.cfcs.com.cardscanner.Model;

/**
 * Created by Admin on 29-07-2017.
 */

public class BusinessVerticalCheckList
{
    public String name;
    public String id;

    public BusinessVerticalCheckList(String name, String id) {

        this.name = name;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
