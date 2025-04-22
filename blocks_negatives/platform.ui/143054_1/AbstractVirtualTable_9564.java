    /**
     * Tells the receiver that the item at given row has changed. This may indicate
     * that a different element is now at this row, but does not necessarily indicate
     * that the element itself has changed. The receiver should request information for
     * this row the next time it becomes visibile.
     *
     * @param index row to clear
     */
    public abstract void clear(int index);
