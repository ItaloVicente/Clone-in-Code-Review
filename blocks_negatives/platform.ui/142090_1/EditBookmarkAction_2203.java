    private void editBookmark() {
    	BookmarkPropertiesDialog dialog = new BookmarkPropertiesDialog(
    			getView().getSite().getShell());
    	dialog.setMarker(marker);
    	dialog.open();
    }
