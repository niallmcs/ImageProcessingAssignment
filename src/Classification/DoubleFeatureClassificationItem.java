package classification;

/**
 * Created by Cathan O'Donnell on 23/03/2016.
 */
public class DoubleFeatureClassificationItem extends FeatureClassificationItem {

    private int featureA;
    private int featureB;

    public DoubleFeatureClassificationItem(String classification, int featureA, int featureB) {
        super(classification);

        this.featureA = featureA;
        this.featureB = featureB;
    }

    public int getFeatureA() {
        return featureA;
    }

    public int getFeatureB() {
        return featureB;
    }

    @Override
    public String toString() {
        return "DoubleFeatureClassificationItem{" +
                "classification=" + getClassification() +
                ", featureA=" + featureA +
                ", featureB=" + featureB +
                '}';
    }

    @Override
    public String toCsv() {
        return getClassification() + "," + featureA + "," + featureB;
    }

}
