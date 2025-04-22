    /**
     * Return whether or not we are debugging. Check the
     * system settings unless we are overiding them.
     * @return boolean <code>true</code> if debug
     * (system) jobs are being shown.
     */
    public boolean debug(){
    	if(!canShowDebug) {
