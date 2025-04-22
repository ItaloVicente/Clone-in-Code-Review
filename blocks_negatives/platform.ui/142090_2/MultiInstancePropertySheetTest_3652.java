        contributingView.getSite().getSelectionProvider().setSelection(selection);

        if (standardPage) {
    		assertTrue("The 'Properties' view should be showing the content of the contributing view (" + contributingView.getTitle() + ") in a regular property page",
    				propertySheet.getCurrentPage() instanceof PropertySheetPage);
        } else {
    		assertFalse("The 'Properties' view should be showing the content of the contributing view (" + contributingView.getTitle() + ") in a non-standard customiezd page",
    				propertySheet.getCurrentPage() instanceof PropertySheetPage);
        }

