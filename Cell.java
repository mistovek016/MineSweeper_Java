import java.util.Objects;
public class Cell {
    private final int x, y;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Cell(Cell c) {
        this.x = c.getX();
        this.y = c.getY();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        // the below line means that if obj is ANY Cell, then it is equal - not correct
        // if (obj instanceof Cell) return true;
        if (!(obj instanceof Cell c)) return false;

        // pattern variable
        return this.x == c.getX() && this.y == c.getY();
    }

    @Override
    public int hashCode() { return Objects.hash(x, y); }

    public int getX() { return x; }
    public int getY() { return y; }
}