        return section;
    }

    /**
     * Returns the descriptors for the selected views.
     * 
     * @return the descriptors for the selected views
     */
    public IViewDescriptor[] getSelection() {
        return viewDescs;
    }

    /**
     * Layout the top control.
     * 
     * @param control the control.
     */
    private void layoutTopControl(Control control) {
        GridData spec = new GridData(GridData.FILL_BOTH);
        spec.widthHint = LIST_WIDTH;
        spec.heightHint = LIST_HEIGHT;
        control.setLayoutData(spec);
    }

    /**
     * Use the dialog store to restore widget values to the values that they
     * held last time this dialog was used to completion.
     */
    protected void restoreWidgetValues() {
		Object selection = null;

        IDialogSettings settings = getDialogSettings();
        String[] expandedCategoryIds = settings
                .getArray(STORE_EXPANDED_CATEGORIES_ID);
		if (expandedCategoryIds != null) {
			ViewRegistry reg = (ViewRegistry) viewReg;
			ArrayList categoriesToExpand = new ArrayList(expandedCategoryIds.length);
			for (int i = 0; i < expandedCategoryIds.length; i++) {
				IViewCategory category = reg.findCategory(expandedCategoryIds[i]);
				if (category != null) {
					categoriesToExpand.add(category);
				}
			}
