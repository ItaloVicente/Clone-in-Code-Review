        }
        return -1;
    }

    /**
     *
     * @return boolean
     * @param <code>text</code>, a String to match
     * @param <code>start</code>, int that indicates the starting index of match, inclusive
     * @param <code>end</code> int that indicates the ending index of match, exclusive
     * @param <code>p</code>, String,  String, a simple regular expression that may contain '?'
     * @param <code>ignoreCase</code>, boolean indicating wether code>p</code> is case sensitive
     */
    protected boolean regExpRegionMatches(String text, int tStart, String p,
            int pStart, int plen) {
        while (plen-- > 0) {
            char tchar = text.charAt(tStart++);
            char pchar = p.charAt(pStart++);

            /* process wild cards */
            if (!fIgnoreWildCards) {
                /* skip single wild cards */
                if (pchar == fSingleWildCard) {
                    continue;
                }
            }
            if (pchar == tchar) {
