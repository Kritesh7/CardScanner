package cardscaner.cfcs.com.cardscanner.Model;

/**
 * Created by Admin on 16-08-2017.
 */

public class ZoneListModel
{
    public String zoneId;
    public String zoneName;

    public ZoneListModel(String zoneId, String zoneName) {
        this.zoneId = zoneId;
        this.zoneName = zoneName;
    }

    public String getZoneId() {
        return zoneId;
    }

    public String getZoneName() {
        return zoneName;
    }

    @Override
    public String toString() {
        return zoneName;
    }
}
