package cardscaner.cfcs.com.cardscanner.Model;

/**
 * Created by Admin on 26-07-2017.
 */

public class CardListModel
{
    public String name;
    public String compName;
    public String custId;
    public String photo;
    public String designation;
    public String phonenUmber;

    public CardListModel(String name, String compName, String custId, String photo, String designation, String phonenUmber) {
        this.name = name;
        this.compName = compName;
        this.custId = custId;
        this.photo = photo;
        this.designation = designation;
        this.phonenUmber = phonenUmber;
    }

    public String getCustId() {
        return custId;
    }

    public String getPhoto() {
        return photo;
    }

    public String getDesignation() {
        return designation;
    }

    public String getPhonenUmber() {
        return phonenUmber;
    }

    public String getName() {
        return name;
    }

    public String getCompName() {
        return compName;
    }
}
