    }

    /**
     * Returns the time at which the last Show In was performed
     * for the given target part, or 0 if unknown.
     */
    public long getShowInTime(String partId) {
        Long t = (Long) showInTimes.get(partId);
        return t == null ? 0L : t.longValue();
    }

    /**
     * Returns the show view shortcuts associated with this perspective.
     * 
     * @return an array of view identifiers
     */
    public String[] getShowViewShortcuts() {
