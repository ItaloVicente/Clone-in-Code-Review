    /**
     * Returns an array of FontData containing copies of the FontData
     * from the original.
     *
     * @param original array to copy
     * @return a deep copy of the original array
     * @since 3.3
     */
    public static FontData[] copy(FontData[] original) {
    	FontData[] result = new FontData[original.length];
    	for (int i = 0; i < original.length; i++) {
