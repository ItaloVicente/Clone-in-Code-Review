    /**
     * Reuse the file attributes set in the import.
     * @param targetResource
     * @param fileObject
     */
    private void setResourceAttributes(IFile targetResource, Object fileObject) {

    	long timeStamp = 0;
    	if(fileObject instanceof File) {
