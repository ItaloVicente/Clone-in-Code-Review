    /**
     * Given the starting (inclusive) and the ending (exclusive) positions in the
     * <code>text</code>, determine if the given substring matches with aPattern
     * @return true if the specified portion of the text matches the pattern
     * @param text a String object that contains the substring to match
     * @param start marks the starting position (inclusive) of the substring
     * @param end marks the ending index (exclusive) of the substring
     */
    public boolean match(String text, int start, int end) {
        boolean found = true;
        String[] segments = null;
        if (null == text) {
