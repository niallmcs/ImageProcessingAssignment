package imagewrappers;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ryan McCleave1 on 22/03/2016.
 */
public class PropertyModel
{
    private int area, perimeter, compactness;
    private String classification;
    private Map<String, Integer> features = new HashMap<>();

    public PropertyModel()
    {
        area = 0;
        perimeter = 0;
        compactness = 0;
        classification = "";
    }

    public PropertyModel(int area, int perimeter, int compactness, String classification) {
        this.area = area;
        this.perimeter = perimeter;
        this.compactness = compactness;
        this.classification = classification;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public int getPerimeter() {
        return perimeter;
    }

    public void setPerimeter(int perimeter) {
        this.perimeter = perimeter;
    }

    public int getCompactness() {
        return compactness;
    }

    public void setCompactness(int compactness) {
        this.compactness = compactness;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public void setFeature(String featureName, int value){
        features.put(featureName, value);
    }

    public int getFeature(String featureName){
        if(features.containsKey(featureName)){
            return features.get(featureName);
        }

        throw new RuntimeException("Could not find feature: " + featureName);
    }
}
