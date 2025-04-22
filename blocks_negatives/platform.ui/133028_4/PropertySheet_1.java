        if(isImportant(part)) {
        	currentPart = part;
        	currentSelection = null;
        }

        if (bootstrapSelection != null) {
            IPropertySheetPage page = (IPropertySheetPage) getCurrentPage();
            if (page != null) {
				page.selectionChanged(part, bootstrapSelection);
			}
            bootstrapSelection = null;
        }
    }
