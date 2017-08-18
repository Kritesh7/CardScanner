package cardscaner.cfcs.com.cardscanner.Model;

/**
 * Created by Admin on 12-06-2017.
 */

public class CustomerDetailsModel
{
    public String customerName;
    public String CustomerId;
    public boolean isChecked;

    public CustomerDetailsModel(String customerName, String customerId) {
        this.customerName = customerName;
        CustomerId = customerId;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerId() {
        return CustomerId;
    }

    @Override
    public String toString() {
        return customerName;
    }
}
