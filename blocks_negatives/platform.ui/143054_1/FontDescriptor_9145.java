    /**
     * <p>Returns a FontDescriptor that is equivalent to the reciever, but whose height
     * is larger by the given number of points.</p>
     *
     * <p>Does not modify the reciever.</p>
     *
     * @param heightDelta a change in height, in points. Negative values will return smaller
     * fonts.
     * @return a FontDescriptor whose height differs from the reciever by the given number
     * of points.
     * @since 3.3
     */
    public final FontDescriptor increaseHeight(int heightDelta) {
    	if (heightDelta == 0) {
    		return this;
    	}
    	FontData[] data = getFontData();
