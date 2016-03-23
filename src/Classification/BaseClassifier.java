package classification;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Niall McShane on 23/03/2016.
 */
public abstract class BaseClassifier{

    public boolean showMessage = false;

    public static String getMajorityVote(String[] votes) {
        Map<String, Integer> voteMap = new HashMap<String, Integer>();

        for(String vote : votes) {
            if(voteMap.containsKey(vote)) {
                int newValue = voteMap.get(vote) + 1;
                voteMap.put(vote, newValue);
            } else {
                voteMap.put(vote, 1);
            }
        }

        Map.Entry<String, Integer> majorityVote = null;
        for(Map.Entry<String, Integer> entry : voteMap.entrySet()) {
            if (majorityVote == null || majorityVote.getValue().compareTo(entry.getValue()) < 0) {
                majorityVote = entry;
            }

        }
        return majorityVote.getKey();
    }

}