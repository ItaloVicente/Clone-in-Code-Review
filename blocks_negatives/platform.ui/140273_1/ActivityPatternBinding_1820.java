    /**
     *
     * @param activityId The id
     * @param pattern This string will be used as plain string, or as pattern-
     * 		  matcher pattern. The use depends on parameter <code>nonRegExp</code>.
     * @param isEqualityPattern If true the <code>pattern</code> string will be
     * 	      interpreted as normal string, not as pattern.
     */
    public ActivityPatternBinding(String activityId, String pattern, boolean
    		isEqualityPattern) {
    	if (pattern == null) {
