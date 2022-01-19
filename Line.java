import java.util.ArrayList;

public class Line {

    private String validBit;
    private String tag;
    private String date;

    //Block 'a' data ekleme

    ArrayList<String> block = new ArrayList<String>();
    
    //Complex
    //111
    
    public void addDataToBlock(String data){

        block.add(date);
    }

    public String getValidBit() {
        return this.validBit;
    }

    public void setValidBit(String validBit) {
        this.validBit = validBit;
    }

    public String getTag() {
        return this.tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<String> getBlock() {
        return this.block;
    }

    public void setBlock(ArrayList<String> block) {
        this.block = block;
    }

    public Line validBit(String validBit) {
        setValidBit(validBit);
        return this;
    }

    public Line tag(String tag) {
        setTag(tag);
        return this;
    }

    public Line date(String date) {
        setDate(date);
        return this;
    }

    public Line block(ArrayList<String> block) {
        setBlock(block);
        return this;
    }

   

    

    
}
