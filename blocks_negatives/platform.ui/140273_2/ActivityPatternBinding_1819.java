    /**
     * @param activityId The id.
     * @param pattern A string that will be compiled to a pattern matcher.
     */
    public ActivityPatternBinding(String activityId, String pattern) {
    	this(activityId, Pattern.compile(pattern));
    }
