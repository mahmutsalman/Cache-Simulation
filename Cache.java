import java.util.ArrayList;

public class Cache {

    private int s;
    private int E;
    private int b;
    private int numberOfSets;
    private int numberOfLines;
    private int blockSize;

    

    
    public Cache(int s,int E,int b){
        this.s=s;
        this.E=E;
        this.b=b;
        this.numberOfSets=(int) Math.pow(2,s);
        this.numberOfLines=E;
    }


    public Cache() {
    }

    public Cache(int s, int E, int b, int numberOfSets, int numberOfLines, int blockSize) {
        this.s = s;
        this.E = E;
        this.b = b;
        this.numberOfSets = numberOfSets;
        this.numberOfLines = numberOfLines;
        this.blockSize = blockSize;
       
    }

    public int getS() {
        return this.s;
    }

    public void setS(int s) {
        this.s = s;
    }

    public int getE() {
        return this.E;
    }

    public void setE(int E) {
        this.E = E;
    }

    public int getB() {
        return this.b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public int getNumberOfSets() {
        return this.numberOfSets;
    }

    public void setNumberOfSets(int numberOfSets) {
        this.numberOfSets = numberOfSets;
    }

    public int getNumberOfLines() {
        return this.numberOfLines;
    }

    public void setNumberOfLines(int numberOfLines) {
        this.numberOfLines = numberOfLines;
    }

    public int getBlockSize() {
        return this.blockSize;
    }

    public void setBlockSize(int blockSize) {
        this.blockSize = blockSize;
    }

    public ArrayList<Set> getSets() {
        return this.sets;
    }

    public void setSets(ArrayList<Set> sets) {
        this.sets = sets;
    }


    public Cache numberOfSets(int numberOfSets) {
        setNumberOfSets(numberOfSets);
        return this;
    }

    public Cache numberOfLines(int numberOfLines) {
        setNumberOfLines(numberOfLines);
        return this;
    }

    public Cache blockSize(int blockSize) {
        setBlockSize(blockSize);
        return this;
    }

    public Cache sets(ArrayList<Set> sets) {
        setSets(sets);
        return this;
    }

   

   

   
    



    
}
