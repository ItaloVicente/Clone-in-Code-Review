    /**
     * Creates a fixed-size row with the given width (pixels).
     * The preferred sizes of child controls are ignored.
     *
     * @param size
     */
    public Row(int size) {
        largerThanChildren = false;
        this.size = size;
        grows = false;
    }
