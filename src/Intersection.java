public class Intersection  {

    public int row;
    public int col;

    public Intersection(int row, int col) {
        this.row = row;
        this.col = col;
    }

    @Override
    public String toString() {
        return "[" + row + ","+ col + ']';
    }
}

