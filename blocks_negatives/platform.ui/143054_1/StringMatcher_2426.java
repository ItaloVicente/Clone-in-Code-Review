        fIgnoreCase = ignoreCase;
        fIgnoreWildCards = ignoreWildCards;
        fPattern = pattern;
        fLength = pattern.length();

        if (fIgnoreWildCards) {
            parseNoWildCards();
        } else {
            parseWildCards();
        }
    }

    /**
     * Find the first occurrence of the pattern between <code>start</code)(inclusive)
     * and <code>end</code>(exclusive).
     * @param text  the String object to search in
     * @param start  the starting index of the search range, inclusive
     * @param end  the ending index of the search range, exclusive
     * @return an <code>StringMatcher.Position</code> object that keeps the starting
     * (inclusive) and ending positions (exclusive) of the first occurrence of the
     * pattern in the specified range of the text; return null if not found or subtext
     * is empty (start==end). A pair of zeros is returned if pattern is empty string
     * Note that for pattern like &quot;*abc*&quot; with leading and trailing stars, position of &quot;abc&quot;
     * is returned. For a pattern like&quot;*&#63;&#63;*&quot; in text &quot;abcdf&quot;, (1,3) is returned
     */
    public StringMatcher.Position find(String text, int start, int end) {
        if (text == null) {
