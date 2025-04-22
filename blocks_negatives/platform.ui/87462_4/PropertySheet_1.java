        IPropertySheetPage page = (IPropertySheetPage) getCurrentPage();
        if (page != null) {
			page.selectionChanged(currentPart, currentSelection);
		}

        updateContentDescription();
