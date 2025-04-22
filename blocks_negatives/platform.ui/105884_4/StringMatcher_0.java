    protected String fPattern;


    protected boolean fIgnoreWildCards;

    protected boolean fIgnoreCase;

    protected boolean fHasLeadingStar;

    protected boolean fHasTrailingStar;


    /* boundary value beyond which we don't need to search in the text */
    protected int fBound = 0;

    protected static final char fSingleWildCard = '\u0000';

    public static class Position {


        public Position(int start, int end) {
            this.start = start;
            this.end = end;
        }

        public int getStart() {
            return start;
        }

        public int getEnd() {
            return end;
        }
    }

    /**
     * StringMatcher constructor takes in a String object that is a simple
     * pattern which may contain '*' for 0 and many characters and
     * '?' for exactly one character.
     *
     * Literal '*' and '?' characters must be escaped in the pattern
     * e.g., "\*" means literal "*", etc.
     *
     * Escaping any other character (including the escape character itself),
     * just results in that character in the pattern.
     * e.g., "\a" means "a" and "\\" means "\"
     *
     * If invoking the StringMatcher with string literals in Java, don't forget
     * escape characters are represented by "\\".
     *
     * @param pattern the pattern to match text against
     * @param ignoreCase if true, case is ignored
     * @param ignoreWildCards if true, wild cards and their escape sequences are ignored
     * 		  (everything is taken literally).
     */
    public StringMatcher(String pattern, boolean ignoreCase,
            boolean ignoreWildCards) {
        if (pattern == null) {
