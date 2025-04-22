        return new Position(matchStart, curPos);
    }

    /**
     * match the given <code>text</code> with the pattern
     * @return true if matched eitherwise false
     * @param <code>text</code>, a String object
     */
    public boolean match(String text) {
        return match(text, 0, text.length());
    }

    /**
     * Given the starting (inclusive) and the ending (exclusive) poisitions in the
     * <code>text</code>, determine if the given substring matches with aPattern
     * @return true if the specified portion of the text matches the pattern
     * @param String <code>text</code>, a String object that contains the substring to match
     * @param int <code>start<code> marks the starting position (inclusive) of the substring
     * @param int <code>end<code> marks the ending index (exclusive) of the substring
     */
    public boolean match(String text, int start, int end) {
        if (null == text) {
