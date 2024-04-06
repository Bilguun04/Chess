package chess;

public class Tile {
    private int x;
    private int y;
    private int value;
    private boolean merged;

    public Tile(int x, int y, int value) {
        this.x = x;
        this.y = y;
        this.value = value;
        this.merged = false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isMerged() {
        return merged;
    }

    public void setMerged(boolean merged) {
        this.merged = merged;
    }
}