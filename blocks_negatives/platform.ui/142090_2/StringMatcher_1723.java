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
     * pattern which may contain &#39*&#39 for 0 and many characters and
     * &#39;&#63;&#39; for exactly one character.
     *
     * Literal &#39;*&#39; and &#39;*&#39; characters must be escaped in the pattern
     * e.g. &quot;&#92;*&quot; means literal &quot;*&quot;, etc.
     *
     * Escaping any other character (including the escape character itself),
     * just results in that character in the pattern.
     * e.g. &quot;&#92;a&quot; means &quot;a&quot; and &quot;&#92;&#92;&quot; means &quot;&#92;&quot;
     *
     * If invoking the StringMatcher with string literals in Java, don't forget
     * escape characters are represented by &quot;&#92;&#92;&quot;.
     *
     * @param pattern the pattern to match text against
     * @param ignoreCase if true, case is ignored
     * @param ignoreWildCards if true, wild cards and their escape sequences are ignored
     * 		  (everything is taken literally).
     */
    public StringMatcher(String pattern, boolean ignoreCase,
            boolean ignoreWildCards) {
        if (pattern == null) {
