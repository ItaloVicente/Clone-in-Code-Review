        super.setAllSelections(value);
    }

    /**
     * Sets the source name of the import to be the supplied path.
     * Adds the name of the path to the list of items in the
     * source combo and selects it.
     *
     * @param path the path to be added
     */
    protected void setSourceName(String path) {

        if (path.length() > 0) {

        	this.currentSourceName = path;
            String[] currentItems = this.sourceNameField.getItems();
            int selectionIndex = -1;
            for (int i = 0; i < currentItems.length; i++) {
                if (currentItems[i].equals(path)) {
