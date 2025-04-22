            }
        });
    }

    /**
     * Update the status line based on the data of item.
     * @param item
     */
    protected void updateStatusLine(Widget item) {
    	setMessage(null);
    	setErrorMessage(null);

    	if (item != null) {
    		if (item.getData() instanceof PropertySheetEntry) {
    			PropertySheetEntry psEntry = (PropertySheetEntry) item.getData();

    			String desc = psEntry.getDescription();
    			if (desc != null && desc.length() > 0) {
