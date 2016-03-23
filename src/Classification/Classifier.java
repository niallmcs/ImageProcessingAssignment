package classification;

import imagewrappers.ImageModel;

import java.util.List;

/**
 * Created by Cathan O'Donnell on 22/03/2016.
 */
public interface Classifier {

    String classify(List<FeatureClassificationItem> classificationItems, ImageModel imageToClassify, int... features);

}
