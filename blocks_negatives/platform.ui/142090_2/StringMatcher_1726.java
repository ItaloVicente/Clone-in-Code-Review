        return new Position(matchStart, curPos);
    }

    /**
     * match the given <code>text</code> with the pattern
     * @return true if matched eitherwise false
     * @param text  a String object
     */
    public boolean match(String text) {
        return match(text, 0, text.length());
    }

    /**
     * Given the starting (inclusive) and the ending (exclusive) positions in the
     * <code>text</code>, determine if the given substring matches with aPattern
     * @return true if the specified portion of the text matches the pattern
     * @param text  a <code>String</code> object that contains the substring to match
     * @param start  marks the starting position (inclusive) of the substring
     * @param end  marks the ending index (exclusive) of the substring
     */
    public boolean match(String text, int start, int end) {
        if (null == text) {
