    }

    /**
     * @param text  a string which contains no wildcard
     * @param start  the starting index in the text for search, inclusive
     * @param end  the stopping point of search, exclusive
     * @return the starting index in the text of the pattern , or -1 if not found
     */
    protected int posIn(String text, int start, int end) {//no wild card in pattern
        int max = end - fLength;

        if (!fIgnoreCase) {
            int i = text.indexOf(fPattern, start);
            if (i == -1 || i > max) {
