    /**
     * Construct and return a fixed-size row. The row will always have the given
     * width, regardless of the size of the layout or the preferred sizes of its children.
     *
     * @param pixels size of the row
     * @return a fixed-size row with the given width (in pixels)
     */
    public static Row fixed(int pixels) {
        return new Row(pixels);
    }
