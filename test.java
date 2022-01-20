//--Systems Programming Project 3: Cachelab--//
//----AUTHORS----//
//Engin Bektaş, 150118501//
//Mahmut Salman, 150118506//
//Kardelen Kubat, //

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
class test {
    //region static variables
    static Line[][] L1I;
    static Line[][] L1D;
    static Line[][] L2;

    static int SystemTime;
    static int tempSystemTime;

    static int L1s;
    static int L1b;
    static int L1E;
    static int L1S;
    static int L2B;


    static int L2s;
    static int L2b;
    static int L2E;
    static int L2S;
    static int L1B;


    static int L1TagSize;
    static int L2TagSize;

    static int L1I_hits;
    static int L1I_misses;
    static int L1I_evictions;

    static int L1D_hits;
    static int L1D_misses;
    static int L1D_evictions;

    static int L2_hits;
    static int L2_misses;
    static int L2_evictions;
    //endregion
    static ArrayList<String> RAM = new ArrayList<>();
public static void main(String[] args) throws Exception {

    //region CREATE RAM
    DataInputStream input = new DataInputStream(new FileInputStream("RAM.dat"));
            while (input.available() > 0) {
                String data = Integer.toHexString(Integer.parseInt(String.valueOf(input.read())));
                RAM.add(data);
            }
            input.close();
    //endregion

    //region Get Input & Set caches and variables
    //-----------------TAKE INPUT AS ARGS
    //        int s1 = Integer.parseInt(args[1]);
    //		int E1 = Integer.parseInt(args[3]);
    //		int b1 = Integer.parseInt(args[5]);
    //
    //		int s2 = Integer.parseInt(args[7]);
    //		int E2 = Integer.parseInt(args[9]);
    //		int b2 = Integer.parseInt(args[11]);
    //      String trace = args[13];
    //      int L1S = (int)Math.pow(2,s1);
    //      int L2S = (int)Math.pow(2,s2);
    //      int blockSizeOfs1 = (int)Math.pow(2,b1);
    //      int blockSizeOfs2 = (int)Math.pow(2,b2);

        L1s=1;
        L1b=3;
        L1E=1;
        L2s=1;
        L2b=3;
        L2E=1;
        L1S = (int)Math.pow(2,L1s);
        L2S = (int)Math.pow(2,L2s);
        L1B = (int)Math.pow(2,L1b);
        L2B = (int)Math.pow(2,L2b);
        L1TagSize = 32 - L1s - L1s;
        L2TagSize = 32 - L2s - L2s;

        //Set tag sizes
    //    L1TagSize = 32 - L1s - L1b;
    //    L2TagSize = 32 - L2s - L2b;

        //Creating caches
        L1I = new Line[L1S][L1E];
        L1D = new Line[L1S][L1E];
        L2 = new Line[L2S][L2E];

        for (int i = 0; i < L1S; i++) {
            for (int j = 0; j < L1E; j++) {
                L1I[i][j] = new Line();
                L1D[i][j] = new Line();
            }
        }
        for (int i = 0; i < L2S; i++) {
            for (int j = 0; j < L2E; j++) {
                L2[i][j] = new Line();
            }
        }
    //endregion

//        String trace1= "L 001da310, 6"; // Example trace

    //region Read Trace File
    File file = new File("traces\\test_large.trace");    //creates a new file instance
        FileReader fr = new FileReader(file);   //reads the file
        BufferedReader br = new BufferedReader(fr);  //creates a buffering character input stream
        StringBuffer sb = new StringBuffer();    //constructs a string buffer with no characters
        String line;
    //endregion

        while((line = br.readLine()) != null) {

            String[] tokenized = tokenizeInput(line);
            String instruction = tokenized[0];

            //region Set currentCache
            Line[][] currentCache;
            if (instruction.equals("L")) {
                currentCache = L1D;
            } else if (instruction.equals("I")) {
                currentCache = L1I;
            }
            //endregion

            //region Invoke instructions "M", "L", "I", "S"
            if (instruction.equals("L")) {
                tokenized = tokenizeInput(line);
                String address = hexToBinary(tokenized[1]);
                int size = Integer.parseInt(tokenized[2]);

                // size --> 0 skip to the next instruction.
                if (size == 0) {
                    continue;
                }
                // Instruction, address and size info.
                System.out.println("L " + tokenized[1] + " " + size);
                // Execute the instruction with L1D cache.
                getInstructionAndData(L1I, L1D, L2, address, size, "L1D");
            }
            else if (instruction.equals("I")) {
                tokenized = tokenizeInput(line);
                String address = hexToBinary(tokenized[1]);
                int size = Integer.parseInt(tokenized[2]);

                // size --> 0 skip to the next instruction.
                if (size == 0) {
                    continue;
                }
                // Instruction, address and size info.
                System.out.println("L " + tokenized[1] + " " + size);
                // Execute the instruction with L1D cache.
                getInstructionAndData(L1I, L1D, L2, address, size, "L1I");
            }
            else if (instruction.equals("S")) {
                tokenized = tokenizeInput(line);
                String address = hexToBinary(tokenized[1]);
                int size = Integer.parseInt(tokenized[2]);
                String data = tokenized[3];
                // size --> 0 skip to the next instruction.
                if (size == 0) {

                    continue;
                }
                // Instruction, address, size and data info.
                System.out.println("S " + tokenized[1] + " " + size + " " + data);
                // Store the given data for L1D and L2 cache.
                StoreData(L1D, L2, address, size, data);
            }
            else if (instruction.equals("M")) {
                // Take address and size values from the line.
                tokenized = tokenizeInput(line);
                String address = hexToBinary(tokenized[1]);
                int size = Integer.parseInt(tokenized[2]);
                String data = tokenized[3];
                // size --> 0 skip to the next instruction.
                if (size == 0) {
                    continue;
                }
                // Instruction, address, size and data info.
                System.out.println("M " + tokenized[1] + " " + size + " " + data);
                // Modify the given data for L1I, L1D and L2 cache.
                ModifyData(L1I, L1D, L2, size, address, data);
            }
        System.out.println();
        }
    try {
        File myObj = new File("log.txt");
        if (myObj.createNewFile()) {
            System.out.println("File created: " + myObj.getName());
        } else {
            System.out.println("File already exists.");
        }
    } catch (IOException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
    }
    try {
        FileWriter myWriter = new FileWriter("filename.txt");
        myWriter.write("L1I Hits: " + L1I_hits + "\n");
        myWriter.write("L1D Hits: " + L1D_hits + "\n");
        myWriter.write("L2 Hits: " + L2_hits + "\n");
        myWriter.write("L1I Misses: " + L1I_misses + "\n");
        myWriter.write("L1D Misses: " + L1D_misses + "\n");
        myWriter.write("L2 Misses: " + L2_misses + "\n");
        myWriter.write("L1I Evictions: " + L1I_evictions + "\n");
        myWriter.write("L1D Evictions: " + L1D_evictions + "\n");
        myWriter.write("L2 Evictions: " + L2_evictions + "\n");
        myWriter.close();
        System.out.println("Successfully wrote to the file.");
    } catch (IOException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
    }
    //endregion

    }
    public static void getInstructionAndData(Line[][] L1I, Line[][] L1D, Line[][] L2, String address,
                                             int size, String cacheName) throws IOException {

        //region Settings
        Line[][] currentCache = null;
        //set Cache type
        switch (cacheName) { //Set cacheType to I or D
            case "L1I" -> currentCache = L1I;
            case "L1D" -> currentCache = L1D;
        }
        int AddressInDecimal = Integer.parseInt(address, 2);

        String L1TagBits = address.substring(0,address.length()-L1b-L1s);

        String L1SetBits = address.substring(address.length()-L1b-L1s,address.length()-L1b);
        String L1BlockBits= address.substring(address.length()-L1s,address.length());
        int L1Set = Integer.parseInt(L1SetBits,2);

        String L2TagBits = address.substring(0,address.length()-L2b-L2s);
        String L2SetBits = address.substring(address.length()-L2b-L2s,address.length()-L2b);
        String L2BlockBits= address.substring(address.length()-L2s,address.length());
        int L2Set = Integer.parseInt(L1SetBits,2);

        int L1PositionLine = GetAvailableLineFromCache(currentCache, L1E, L1Set, L1TagBits); // Returns valid line number.
        int L2PositionLine = GetAvailableLineFromCache(L2, L2E, L2Set, L2TagBits); // Returns valid line number.
        //endregion

        //region Check hits and misses
        //BEGINNING OF HITS MISSES
        String currentCacheName = "";
        if (L1PositionLine != -1) {
            //region Hit L1
            //Update Hit
            switch (cacheName) {
                case "L1I":
                    L1I_hits++;
                    currentCacheName = "L1I";
                    break;
                case "L1D":
                    L1D_hits++;
                    currentCacheName = "L1D";
                    break;
            }
            System.out.print(currentCacheName + " hit ");
            //Update time
            SystemTime++;
            tempSystemTime = SystemTime;
            assert currentCache != null;
            currentCache[L1Set][L1PositionLine].setTime(tempSystemTime);
            //endregion
            if (L2PositionLine != -1) {
                //region Hit L1, Hit L2
                System.out.print("L2" + " hit ");
                //Update Hit
                L2_hits++;
                //Update time
                SystemTime++;
                tempSystemTime = SystemTime;
                currentCache[L2Set][L2PositionLine].setTime(tempSystemTime);
                //endregion
            }
            else {
                //region Hit L1, Miss L2
                System.out.print("L2 miss ");
                L2_misses++;
                // Check eviction and write data to the cache.
                L2_evictions += checkEvictionAndWriteData(currentCache, L2, L1PositionLine, L1Set, L2Set, L2TagBits, L2E);
                //endregion
            }
        }
        else {
            //region Miss L1
            //Update Hit
            switch (cacheName) {
                case "L1I":
                    L1I_misses++;
                    currentCacheName = "L1I";
                    break;
                case "L1D":
                    L1D_misses++;
                    currentCacheName = "L1D";
                    break;
            }
            System.out.print(currentCacheName + " miss ");
            //endregion
            if (L2PositionLine != -1) {
                //region Miss L1, Hit L2
                System.out.print("L2" + " hit ");
                L2_hits++;

                // Update System time and L2 cache time.
                SystemTime++;
                tempSystemTime = SystemTime;
                L2[L2Set][L2PositionLine].setTime(tempSystemTime);

                // Check eviction.
                int L1Eviction = checkEvictionAndWriteData(L2, currentCache, L2PositionLine, L2Set, L1Set,
                        L1TagBits, L1E);

                if (cacheName.equals("L1I")) { // Check Cache Type.
                    L1I_evictions += L1Eviction;
                } else if (cacheName.equals("L1D")) {
                    L1D_evictions += L1Eviction;
                }
                //endregion
            }
            else {
                //region Miss L1, Miss L2
                System.out.print("L2" + " miss ");
                L2_misses++;

                // Fetch the ram and get the cache line.
                L2PositionLine = getLineNumberAndFetchRam(L2, L2Set, L2TagBits, AddressInDecimal);

                // Check eviction.
                int L1Eviction = checkEvictionAndWriteData(L2, currentCache, L2PositionLine, L2Set, L1Set,
                        L1TagBits, L1E);

                if (cacheName.equals("L1I")) { // Check Cache Type.

                    L1I_evictions += L1Eviction;

                } else if (cacheName.equals("L1D")){

                    L1D_evictions += L1Eviction;

                }
                //endregion
            }
        }
        //endregion

    }
    public static void StoreData(Line[][] L1D, Line[][] L2, String address, int size, String data) throws Exception {

        //region Settings
        Line[][] currentCache = L1D;
        int AddressInDecimal = Integer.parseInt(address, 2);

        String L1TagBits = address.substring(0,address.length()-L1b-L1s);
        String L1SetBits = address.substring(address.length()-L1b-L1s,address.length()-L1b);
        String L1BlockBits= address.substring(address.length()-L1s,address.length());
        int L1Set = Integer.parseInt(L1SetBits,2);

        String L2TagBits = address.substring(0,address.length()-L2b-L2s);
        String L2SetBits = address.substring(address.length()-L2b-L2s,address.length()-L2b);
        String L2BlockBits= address.substring(address.length()-L2s,address.length());
        int L2Set = Integer.parseInt(L1SetBits,2);

        int L1PositionLine = GetAvailableLineFromCache(currentCache, L1E, L1Set, L1TagBits); // Returns valid line number.
        int L2PositionLine = GetAvailableLineFromCache(L2, L2E, L2Set, L2TagBits); // Returns valid line number.

        //endregion

        //region Check hits and misses
        boolean L1Hit = false;
        boolean L2Hit = false;
        //when hit, get line data, remove first "size" bits, concatenate "data" + "modified line data" and put it in line data
        if (L1PositionLine != -1) { // Hit in L1D.

            //region L1 Hit
            L1Hit = true;
            System.out.print("L1D" + " hit ");
            L1D_hits++;

            // Update the system time and L1D cache time.
            SystemTime++;
            tempSystemTime = SystemTime;
            L1D[L1Set][L1PositionLine].setTime(tempSystemTime);

            // Update the L1D cache data.

            L1D[L1Set][L1PositionLine].setData(data + L1D[L1Set][L1PositionLine].getData().substring(size*2));
            //endregion

            if (L2PositionLine != -1) { // Hit in L2.

                //region L1 Hit, L2 Hit
                L2Hit = true;
                System.out.print("L2" + " hit ");
                L2_hits++;

                // Update the system time and L2 cache time.
                SystemTime++;
                tempSystemTime = SystemTime;
                L2[L2Set][L2PositionLine].setTime(tempSystemTime);
                // Update the L2 cache data.
                L2[L2Set][L2PositionLine].setData(data + L2[L2Set][L2PositionLine].getData().substring(size*2));
                //endregion

            } else { // Hit in L1, Miss in L2. Eviction for L2.

                //region L1 Hit, L2 Miss
                L2Hit = false;
                System.out.print("L2" + " miss ");
                L2_misses++;
                //endregion

            }

        } else { // Miss in L1D. Eviction for L1D.

            //region L1D miss
            L1Hit = false;
            System.out.print("L1D" + " miss ");
            L1D_misses++;
            //endregion

            if (L2PositionLine != -1) { // Miss in L1D, hit in L2.

                //region L1D miss, L2 hit
                L2Hit = true;
                System.out.print("L2" + " hit ");
                L2_hits++;
                // Take eviction for L1D and load the data to the L1D cache.
                L2[L2Set][L2PositionLine].setData(data + L2[L2Set][L2PositionLine].getData().substring(size*2));
                //endregion

            } else { // Miss L1 and L2 load the data from RAM.

                //region L1D miss, L2 miss
                L2Hit = false;
                System.out.print("L2" + " miss ");
                L2_misses++;
                //endregion

            }

        }
        //endregion

        writeDatatoRAM(AddressInDecimal, size, data);
        System.out.print("- Stored in ");
        if (L1Hit)
            System.out.print("L1D, ");
        if (L2Hit)
            System.out.print("L2, ");
        System.out.print("RAM.");

    }


    public static int getLineNumberAndFetchRam(Line[][] L2, int setNumber, String tagBit,
                                               int address) throws IOException {

        String extractedData= "";
        for (int i = 0; i < L2B; i++) {
            if (RAM.get(address + i).length() == 1) {
                extractedData = extractedData + "0" + RAM.get(address + i);
            } else {
                extractedData = extractedData + RAM.get(address + i);
            }
        }

         // Extracted Data from RAM.

        //String dataAsString = RAM.get(address);
        // System.out.println(extractedData);

        // Store the data to the appropriate line of L2.

        boolean freeLine = false;
        int positionLine = 0;

        for (int index = 0; index < L2E; index++) { // No Eviction.

            if (L2[setNumber][index].getValidBit().equals("0")) {

                freeLine = true;
                positionLine = index;
                break;
            }
        }

        if (freeLine == false) { // Eviction here.

            L2_evictions++;

            // Update System time.
            SystemTime++;
            tempSystemTime = SystemTime;

            // Find appropriate line for cache to store the data.
            for (int index = 0; index < L2E; index++) {

                if (SystemTime > L2[setNumber][index].getTime()) {

                    positionLine = index;
                    tempSystemTime = L2[setNumber][index].getTime();

                }
            }
        }
        // Write to L2 cache process.
        SystemTime++;
        tempSystemTime = SystemTime;
        L2[setNumber][positionLine].setValidBit("1");
        L2[setNumber][positionLine].setTag(tagBit);
        L2[setNumber][positionLine].setTime(tempSystemTime);
        L2[setNumber][positionLine].data = extractedData;

        return positionLine;
    }


    public static int checkEvictionAndWriteData(Line[][] currentCache, Line[][] destinationCache,
                                                int currentCacheLineNumber, int currentCacheSetNumber,
                                                int destinationSetNumber, String cacheTag, int cacheLine) {

        int eviction = 0; // Eviction value.
        int positionLine = 0; // Id value during checking lines.
        boolean freeLine = false; // Free line for eviction checking.

        for (int index = 0; index < cacheLine; index++) {

            if (destinationCache[destinationSetNumber][index].getValidBit().equals("0")) { // No Eviction.

                positionLine = index;
                freeLine = true; // If it is free then there is no eviction for caches.
                break;
            }
        }

        //Determine which line of destination to write data
        if (freeLine == false) { // There is eviction

            eviction++;
            SystemTime++;
            tempSystemTime = SystemTime;

            // Checking caches lines to copy process. (Using FIFO procedure).
            for (int index = 0; index < cacheLine; index++) {
                //TODO ?
                if (tempSystemTime > destinationCache[destinationSetNumber][index].getTime()) {
                    tempSystemTime = destinationCache[destinationSetNumber][index].getTime();
                    positionLine = index;
                }
            }
        }

        // Copy process between caches.
        SystemTime++;
        tempSystemTime = SystemTime;
        destinationCache[destinationSetNumber][positionLine].setValidBit("1");
        destinationCache[destinationSetNumber][positionLine].setTag(cacheTag);
        destinationCache[destinationSetNumber][positionLine].setTime(tempSystemTime);
        String tempData = currentCache[currentCacheSetNumber][currentCacheLineNumber].getData();
        destinationCache[destinationSetNumber][positionLine].setData(tempData);

        return eviction;

        //L1 hit, L2 miss, free line in L2 ----> Load to L2 from L1, no eviction
        //L1 hit, L2 miss, no free line in L2 ----> Load to L2 from L1, no eviction
    }

    public static void ModifyData(Line[][] L1I, Line[][] L1D, Line[][] L2, int size, String address, String data)
            throws Exception {

        // Load the data and then store the data.
        getInstructionAndData(L1I, L1D, L2, address, size, "L1D");
        StoreData(L1D, L2, address, size, data);

    }

    public static int GetAvailableLineFromCache(Line[][] cache, int lineNumber, int set, String tag) {
        for (int index = 0; index < lineNumber; index++) {
            if (cache[set][index].getValidBit().equals("1")
                    && cache[set][index].getTag().equals(tag)) {

                return index;
            }
        }
        return -1;
    }
    public static String[] tokenizeInput(String input) {
        String[] tokens = input.split("[ ]" );
        for (int i=0; i < tokens.length; i++){
            if (tokens[i].contains(",")) {
                tokens[i]=tokens[i].substring(0,tokens[i].length() -1);
            }
        }

        return tokens;
    }
    public static String hexToBinary(String hex) {

        // variable to store the converted
        // Binary Sequence
        String binary = "";

        // converting the accepted Hexadecimal
        // string to upper case
        hex = hex.toUpperCase();

        // initializing the HashMap class
        HashMap<Character, String> hashMap
                = new HashMap<Character, String>();

        // storing the key value pairs
        hashMap.put('0', "0000");
        hashMap.put('1', "0001");
        hashMap.put('2', "0010");
        hashMap.put('3', "0011");
        hashMap.put('4', "0100");
        hashMap.put('5', "0101");
        hashMap.put('6', "0110");
        hashMap.put('7', "0111");
        hashMap.put('8', "1000");
        hashMap.put('9', "1001");
        hashMap.put('A', "1010");
        hashMap.put('B', "1011");
        hashMap.put('C', "1100");
        hashMap.put('D', "1101");
        hashMap.put('E', "1110");
        hashMap.put('F', "1111");

        int i;
        char ch;

        // loop to iterate through the length
        // of the Hexadecimal String
        for (i = 0; i < hex.length(); i++) {
            // extracting each character
            ch = hex.charAt(i);

            // checking if the character is
            // present in the keys
            if (hashMap.containsKey(ch))

                // adding to the Binary Sequence
                // the corresponding value of
                // the key
                binary += hashMap.get(ch);

                // returning Invalid Hexadecimal
                // String if the character is
                // not present in the keys
            else {
                binary = "Invalid Hexadecimal String";
                return binary;
            }
        }

        // returning the converted Binary
        return binary;
    }

    public static void writeDatatoRAM(int decimalAddress, int size, String data) throws Exception {

        for (int i = 0; i < size; i++)
            RAM.set(decimalAddress, data.substring(i*2,i*2+2));

    }
//    public static void updateHit(Line[][] currentCache, int cacheType) {
//        String cacheName;
//        //Update Hit
//        switch (cacheType) {
//            case 0:
//                L1I_hits++;
//                currentCacheName = "L1I";
//                break;
//            case 1:
//                L1D_hits++;
//                currentCacheName = "L1D";
//                break;
//        }
//        System.out.println(currentCacheName + " hit.");
//        //Update time
//        SystemTime++;
//        tempSystemTime = SystemTime;
//        assert currentCache != null;
//        currentCache[L1Set][L1PositionLine].setTime(tempSystemTime);
//    }
}
