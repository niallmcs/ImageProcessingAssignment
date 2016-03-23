package classification;

/**
 * Created by Niall McShane on 23/03/2016.
 */
public abstract class FeatureClassificationItem {

    private String classification;
    private int distance;

    public FeatureClassificationItem(String classification) {
        this.classification = classification;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getClassification() {
        return classification;
    }
}