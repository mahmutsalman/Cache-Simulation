import java.util.ArrayList;

public class Line {

    private String validBit;
    private String tag;
    private int time;
    String data;

    public Line() {
        validBit = "0";
    }


    public String getValidBit() {
        return validBit;
    }

    public void setValidBit(String validBit) {
        this.validBit = validBit;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
