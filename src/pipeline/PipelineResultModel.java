package pipeline;

/**
 * Created by Niall McShane on 23/03/2016.
 */
public class PipelineResultModel {

    private int totalAttempts = 0;
    private int correctAttempts = 0;

    public void incrementTotalAttempts(){
        this.totalAttempts++;
    }

    public void incrementCorrectAttempts(){
        this.correctAttempts++;
    }

    public int getTotalAttempts() {
        return totalAttempts;
    }

    public void setTotalAttempts(int totalAttempts) {
        this.totalAttempts = totalAttempts;
    }

    public int getCorrectAttempts() {
        return correctAttempts;
    }

    public void setCorrectAttempts(int correctAttempts) {
        this.correctAttempts = correctAttempts;
    }

    public double computeResult(){
        return  (((double)correctAttempts / (double)totalAttempts) * 100);
    }
}
