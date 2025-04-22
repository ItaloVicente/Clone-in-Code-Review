		if (wasHidden && part == this) {
			wasHidden = false;
			if (currentPart != null) {
				IPropertySheetPage page = (IPropertySheetPage) getCurrentPage();
				if (page != null) {
					page.selectionChanged(currentPart, currentSelection);
				}
				updateContentDescription();
			}
			return;
		}

