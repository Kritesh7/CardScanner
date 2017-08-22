package cardscaner.cfcs.com.cardscanner.Model;

/**
 * Created by Admin on 22-08-2017.
 */

public class ContactTypeListModel
{
    public String contactTypeId;
    public String contactType;

    public ContactTypeListModel(String contactTypeId, String contactType) {
        this.contactTypeId = contactTypeId;
        this.contactType = contactType;
    }

    public String getContactTypeId() {
        return contactTypeId;
    }

    public String getContactType() {
        return contactType;
    }

    @Override
    public String toString() {
        return contactType;
    }
}
