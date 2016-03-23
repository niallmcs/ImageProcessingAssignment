package classification;

import imagewrappers.ImageModel;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class NearestNeighbour2D  extends BaseClassifier implements Classifier {
    private final int k;

    public NearestNeighbour2D(int k) {
        this.k = k;
    }

    @Override
    public String classify(List<FeatureClassificationItem> classificationItems, ImageModel imageToClassify, int... features) {
        int featureA = features[0];
        int featureB = features[1];

        List<DoubleFeatureClassificationItem> newItems = (List<DoubleFeatureClassificationItem>)(List<?>) classificationItems;

        for(DoubleFeatureClassificationItem item : newItems) {

            item.setDistance((int) Math.round(Math.sqrt(Math.pow(featureA - item.getFeatureA(), 2) + Math.pow((featureB - item.getFeatureB()), 2))));

        }

        Collections.sort(newItems, new Comparator<DoubleFeatureClassificationItem>() {
            @Override
            public int compare(DoubleFeatureClassificationItem o1, DoubleFeatureClassificationItem o2) {
                return o1.getDistance() - o2.getDistance();
            }
        });

        if(showMessage) {
            System.out.println("New image 2D: ");
            for (DoubleFeatureClassificationItem item : newItems) {
                System.out.println("Class: " + item.getClassification() + " Distance: " + item.getDistance());
            }
        }

        //Choose the K nearest neighbours
        String[] votes = new String[k];
        for(int i = 0; i < k; i++) {
            votes[i] = newItems.get(i).getClassification();
        }

        return BaseClassifier.getMajorityVote(votes);

    }

}
