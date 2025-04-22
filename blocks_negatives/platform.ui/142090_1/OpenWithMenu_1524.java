        menuItem.addListener(SWT.Selection, listener);
    }

    /**
     * Creates the Other... menu item
     *
     * @param menu the menu to add the item to
     */
    private void createOtherMenuItem(final Menu menu) {
    	final IFile fileResource = getFileResource();
