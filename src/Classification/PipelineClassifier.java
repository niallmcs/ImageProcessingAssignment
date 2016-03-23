package classification;

import java.util.List;

/**
 * Created by Cathan O'Donnell on 23/03/2016.
 */
public class PipelineClassifier {

    private final List<Classifier> classifierList;

    public PipelineClassifier(List<Classifier> classifierList) {
        this.classifierList = classifierList;
    }

}
