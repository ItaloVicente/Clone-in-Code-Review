    }

    /**
     * The Image to use to repesent this editor
     */
    /* package */void setImageDescriptor(ImageDescriptor desc) {
    	synchronized (imageDescLock) {
	        imageDesc = desc;
	        testImage = true;
    	}
    }

    /**
     * The name of the image describing this editor.
     *
     * @return the image file name
     */
    public String getImageFilename() {
    	if (configurationElement == null) {
