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
