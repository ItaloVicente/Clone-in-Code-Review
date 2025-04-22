    /**
     * Get the extension.
     * 
     * @return the extension
     */
    public String getExtension() {

        int index = filename.lastIndexOf('.');
        if (index == -1) {
		}
        if (index == filename.length()) {
		}
        return filename.substring(index + 1, filename.length());
    }

    /**
     * Get the name.
     * 
     * @return the name
     */
    public String getName() {

        int index = filename.lastIndexOf('.');
        if (index == -1) {
			return filename;
		}
        if (index == 0) {
		}
        return filename.substring(0, index);
    }
