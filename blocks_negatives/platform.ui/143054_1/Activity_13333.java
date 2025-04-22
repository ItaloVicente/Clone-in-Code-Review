    public boolean isMatch(String string) {
        if (isDefined()) {
			for (Iterator iterator = activityPatternBindings.iterator(); iterator
                    .hasNext();) {
                ActivityPatternBinding activityPatternBinding = (ActivityPatternBinding) iterator
                        .next();
