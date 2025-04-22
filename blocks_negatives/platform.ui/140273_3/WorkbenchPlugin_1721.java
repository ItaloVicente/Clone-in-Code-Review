    }

    /**
     * Returns the shared images for the workbench.
     *
     * @return the shared image manager
     */
    public ISharedImages getSharedImages() {
    	if(sharedImages == null) {
    		sharedImages = new SharedImages();
    	}
    	if(e4Context == null) {
    		return sharedImages;
    	}
