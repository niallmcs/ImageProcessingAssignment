package classification;

/**
 * Created by Cathan O'Donnell on 23/03/2016.
 */
public class SingleFeatureClassificationItem extends FeatureClassificationItem {

    private int feature;

    public SingleFeatureClassificationItem(String classification, int feature) {
        super(classification);
        this.feature = feature;
    }

    public int getFeature() {
        return feature;
    }

    @Override
    public String toString() {
        return "SingleFeatureClassificationItem{" +
                "classification=" + getClassification() +
                ", feature=" + feature +
                '}';
    }

    @Override
    public String toCsv() {
        return getClassification() + "," + feature;
    }
}
