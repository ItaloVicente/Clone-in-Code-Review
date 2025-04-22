        return new Position(matchStart, curPos);
    }

    /**
     * match the given <code>text</code> with the pattern
     * @return true if matched otherwise false
     * @param text a String object
     */
    public boolean match(String text) {
    	if(text == null) {
