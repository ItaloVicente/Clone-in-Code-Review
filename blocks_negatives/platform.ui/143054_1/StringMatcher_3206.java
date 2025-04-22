            }
            return false;
        }
        return true;
    }

    /**
     * @param <code>text</code>, the string to match
     * @param <code>start</code>, the starting index in the text for search, inclusive
     * @param <code>end</code>, the stopping point of search, exclusive
     * @param code>p</code>, a string that has no wildcard
     * @param <code>ignoreCase</code>, boolean indicating wether code>p</code> is case sensitive
     * @return the starting index in the text of the pattern , or -1 if not found
     */
    protected int textPosIn(String text, int start, int end, String p) {

        int plen = p.length();
        int max = end - plen;

        if (!fIgnoreCase) {
            int i = text.indexOf(p, start);
            if (i == -1 || i > max) {
