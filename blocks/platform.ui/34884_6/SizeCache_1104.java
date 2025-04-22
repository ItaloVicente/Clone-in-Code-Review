
package org.eclipse.ui.internal.layout;

public class Row {
    boolean grows = false;

    int size = 0;

    boolean largerThanChildren = true;

    public Row(int size) {
        largerThanChildren = false;
        this.size = size;
        grows = false;
    }

    public Row(boolean growing) {
        this.grows = growing;

        if (growing) {
            size = 100;
        }
    }

    public Row(int size, boolean largerThanChildren) {
        this.grows = true;
        this.size = size;
        this.largerThanChildren = largerThanChildren;
    }

    public static Row growing() {
        return new Row(100, true);
    }

    public static Row growing(int size, boolean largerThanChildren) {
        return new Row(size, largerThanChildren);
    }

    public static Row fixed() {
        return new Row(false);
    }

    public static Row fixed(int pixels) {
        return new Row(pixels);
    }
}
