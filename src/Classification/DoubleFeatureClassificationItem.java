package classification;

import imagewrappers.ImageModel;

import java.util.List;

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
}
