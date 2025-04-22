    /**
     * Returns whether any of the given infos have tips and tricks.
     *
     * @param infos the infos
     * @return <code>true</code> if tips and tricks were found, <code>false</code> if not
     */
    private boolean hasTipsAndTricks(AboutInfo[] infos) {
        for (AboutInfo info : infos) {
            if (info.getTipsAndTricksHref() != null) {
            	return true;
            }
        }
        return false;
