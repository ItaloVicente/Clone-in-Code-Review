            }
            return false;
        }
        return true;
    }

    /**
     * @param text  the string to match
     * @param start  the starting index in the text for search, inclusive
     * @param end  the stopping point of search, exclusive
     * @param p  a string that has no wildcard
     * @return the starting index in the text of the pattern , or -1 if not found
     */
    protected int textPosIn(String text, int start, int end, String p) {

        int plen = p.length();
        int max = end - plen;

        if (!fIgnoreCase) {
            int i = text.indexOf(p, start);
            if (i == -1 || i > max) {
