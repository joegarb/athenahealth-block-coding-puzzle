import java.util.ArrayList;
import java.util.List;

/**
 * GarberBlockCalc: Provides the ability to calculate the number of structurally sound panels that can be
 * constructed from two known sizes of blocks within a given width and height.
 * 
 * @author Joe Garber
 */
public class GarberBlockCalc {
    
    private final double block1 = 3;
    private final double block2 = 4.5;
    
    private double width;
    private int height;
    private List<Row> rows = new ArrayList<Row>();

    /**
     * Calculate and print the number of unique panels that can be constructed.
     * 
     * @param args the command line arguments, in order: width, height
     */
    public static void main(String[] args) {
        
        GarberBlockCalc calc = new GarberBlockCalc();
        
        if (!calc.commandLineIsValid(args)) {
            System.exit(1);
        }
        
        System.out.println(calc.getNumberOfPanels());
    }
    
    private long getNumberOfPanels() {
        
        long panelCount = 0;
        
        // Step 1: Build a list of all possible rows, i.e. each permutation of blocks that fits within the width
        placeBlock(new Row(), 0);
        
        // Step 2: Determine which rows are compatible with each other based on the structural integrity constraint
        // of not having any edges lined up over one another.
        for (int i = 0; i < rows.size(); i++) {
            Row row1 = rows.get(i);
            
            for (int j = 0; j < rows.size(); j++) {
                Row row2 = rows.get(j);
				
                if (rowsAreCompatible(row1, row2))
                    row1.compatibleRows.add(j);
            }
        }
        
        // Step 3: Count how many times each row could occur at each level, which equals the number of panels
        // possible within that height.
        
        // The array indices correspond to row indices from the main rows list, and the values are the number of
        // unique panels in which a row can occur.
        int numRows = rows.size();
        long[] currentLevel = new long[numRows];
        long[] nextLevel = new long[numRows];

        // The first level is just once each of the possible rows
        for (int i = 0; i < numRows; i++)
            currentLevel[i] = 1;

        for (int i = 1; i < height; i++) {

            // For each possible row configuration at this level, each of its compatible rows can occur at the next
            // higher level once per the number of times that the current row occurs in this level.
            for (int j = 0; j < numRows; j++) {

                // Number of unique panels in which this row occurs at the current level
                long times = currentLevel[j];

                List<Integer> compatibleRows = rows.get(j).compatibleRows;
                for (int k = 0; k < compatibleRows.size(); k++) {
                    int compatRow = compatibleRows.get(k);

                    // Increment a counter of the occurrences of this row that there will be at the next level
                    nextLevel[compatRow] += times;
                }
            }

            currentLevel = nextLevel.clone();
            nextLevel = new long[numRows];
        }

        // Finally, loop once more to sum up the occurrences of each row at the highest level
        for (int i = 0; i < numRows; i++)
            panelCount += currentLevel[i];
        
        return panelCount;
    }
    
    private void placeBlock(Row row, double sum) {
        
        if (sum == width) {
            rows.add(row);
            return;
        } else if (sum > width) {
            return;
        }
        
        // Try block1 in the next position
        Row newRow1 = (Row) row.clone();
        newRow1.add(block1);
        placeBlock(newRow1, sum + block1);
        
        // Try block2 in the next position
        Row newRow2 = (Row) row.clone();
        newRow2.add(block2);
        placeBlock(newRow2, sum + block2);
    }
    
    private boolean rowsAreCompatible(Row row1, Row row2) {
        
        int row1Pos = 0;
        int row2Pos = 0;
        double row1Sum = row1.get(0);
        double row2Sum = row2.get(0);
        
        // Walk the two rows, adding as we go to compare the block positions
        do {
            // If the sums match at any point before the end of the row, then the blocks are lined up where they shouldn't be
            if (row1Sum == row2Sum && row1Pos != row1.size() - 1)
                return false;
            
            // Advance our position within the row containing the smaller block in the current position
            if (row1Sum < row2Sum) {
                row1Pos++;
                row1Sum += row1.get(row1Pos);
            } else if (row2Sum < row1Sum) {
                row2Pos++;
                row2Sum += row2.get(row2Pos);
            } else {
                if (row1Pos < row1.size() - 1) {
                    row1Pos++;
                    row1Sum += row1.get(row1Pos);
                }
                if (row2Pos < row2.size() - 1) {
                    row2Pos++;
                    row2Sum += row2.get(row2Pos);
                }
            }
        } while (row1Sum < width && row2Sum < width);
        
        return true;
    }
    
    private boolean commandLineIsValid(String[] args) {
        
        boolean valid = false;
        
        // Verify that the width and height are specified in the correct format and within the bounds
        
        if (args.length > 1) {
            width = Double.parseDouble(args[0]);
            height = Integer.parseInt(args[1]);
            
            if (width < 3 || width > 48)
                System.err.println("Width is not within the valid range: 3-48.");
            else if (width % 0.5 != 0)
                System.err.println("Width is not a multiple of 0.5.");
            else if (height < 1 || height > 10)
                System.err.println("Height is not within the valid range: 1-10.");
            else
                valid = true;
        } else {
            System.err.println("Width and height are required arguments.");
        }
        
        return valid;
    }
    
    /**
     * Row extends ArrayList in order to maintain a row's association with its compatible rows, as well as enhance readability.
     */
    private class Row extends ArrayList<Double> {
        
        public List<Integer> compatibleRows = new ArrayList<Integer>();
        
        @Override
        public Object clone() {
            Row newRow = (Row) super.clone();
            newRow.compatibleRows = new ArrayList<Integer>();
            return newRow;
        }
        
    }
    
}
