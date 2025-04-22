        }
        return -1;
    }

    /**
     *
     * @return boolean
     * @param  text  a String to match
     * @param tStart  int that indicates the starting index of match, inclusive
     * @param p  String,  String, a simple regular expression that may contain '&#63;'
     * @param pStart
     * @param plen
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
