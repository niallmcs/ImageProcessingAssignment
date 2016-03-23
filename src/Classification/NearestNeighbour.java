package classification;

import imagewrappers.ImageModel;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class NearestNeighbour extends BaseClassifier implements Classifier {

    private final int k;

    public NearestNeighbour(int k) {
        this.k = k;
    }

    @Override
    public String classify(List<FeatureClassificationItem> classificationItems, ImageModel imageToClassify, int... features) {


        int feature = features[0];

        //work out distance
        for(FeatureClassificationItem classificationItem : classificationItems) {
            classificationItem.setDistance(Math.abs(feature - ((SingleFeatureClassificationItem)classificationItem).getFeature()));
        }


        Collections.sort(classificationItems, new Comparator<FeatureClassificationItem>() {
            @Override
            public int compare(FeatureClassificationItem o1, FeatureClassificationItem o2) {
                return o1.getDistance() - o2.getDistance();
            }
        });

        if(showMessage) {
            System.out.println("New image 1D: ");
            for (FeatureClassificationItem item : classificationItems) {
                System.out.println("Class: " + item.getClassification() + " Distance: " + item.getDistance());
            }
        }


        //Choose the K nearest neighbours
        String[] votes = new String[k];
        for(int i = 0; i < k; i++) {
            votes[i] = classificationItems.get(i).getClassification();
        }

        return BaseClassifier.getMajorityVote(votes);

    }
}
