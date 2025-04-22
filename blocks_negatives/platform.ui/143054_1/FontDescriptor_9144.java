    /**
     * <p>Returns a new FontDescriptor that is equivalent to the reciever, but
     * has the given height.</p>
     *
     * <p>Does not modify the reciever.</p>
     *
     * @param height a height, in points
     * @return a new FontDescriptor with the height, in points
     * @since 3.3
     */
    public final FontDescriptor setHeight(int height) {
    	FontData[] data = getFontData();
