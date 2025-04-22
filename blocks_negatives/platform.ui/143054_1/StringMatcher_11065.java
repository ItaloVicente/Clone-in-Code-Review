        }

        return -1;
    }

    /**
     * @param <code>text</code>, a simple regular expression that may only contain '?'(s)
     * @param <code>start</code>, the starting index in the text for search, inclusive
     * @param <code>end</code>, the stopping point of search, exclusive
     * @param <code>p</code>, a simple regular expression that may contains '?'
     * @param <code>caseIgnored</code>, wether the pattern is not casesensitive
     * @return the starting index in the text of the pattern , or -1 if not found
     */
    protected int regExpPosIn(String text, int start, int end, String p) {
        int plen = p.length();

        int max = end - plen;
        for (int i = start; i <= max; ++i) {
            if (regExpRegionMatches(text, i, p, 0, plen)) {
