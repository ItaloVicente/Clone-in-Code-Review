    /**
     * Returns whether any of the given infos have tips and tricks.
     * 
     * @param infos the infos
     * @return <code>true</code> if tips and tricks were found, <code>false</code> if not
     */
    private boolean hasTipsAndTricks(AboutInfo[] infos) {
        for (int i = 0; i < infos.length; i++) {
            if (infos[i].getTipsAndTricksHref() != null) {
            	return true;
            }
        }
        return false;
