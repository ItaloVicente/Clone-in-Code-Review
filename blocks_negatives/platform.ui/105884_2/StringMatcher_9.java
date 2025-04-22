        }

        return -1;
    }

    /**
     * @param text a simple regular expression that may only contain '?'(s)
     * @param start the starting index in the text for search, inclusive
     * @param end the stopping point of search, exclusive
     * @param p a simple regular expression that may contains '?'
     * @return the starting index in the text of the pattern , or -1 if not found
     */
    protected int regExpPosIn(String text, int start, int end, String p) {
        int plen = p.length();

        int max = end - plen;
        for (int i = start; i <= max; ++i) {
            if (regExpRegionMatches(text, i, p, 0, plen)) {
