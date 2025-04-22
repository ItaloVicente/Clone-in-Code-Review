		return cascadingTheme;
	}

	private void updateForDialogFontChange(Font newFont) {
		Iterator iterator = dialogFontWidgets.iterator();
		while (iterator.hasNext()) {
			((Control) iterator.next()).setFont(newFont);
		}

		labelProvider.clearFontCacheAndUpdate();
	}

	private void updateTreeSelection(ISelection selection) {
		ThemeElementCategory category = null;
		Object element = ((IStructuredSelection) selection).getFirstElement();
		if (element instanceof ThemeElementCategory) {
			category = (ThemeElementCategory) element;
		} else if (element instanceof ColorDefinition) {
			String categoryID = ((ColorDefinition) element).getCategoryId();
			category = WorkbenchPlugin.getDefault().getThemeRegistry().findCategory(categoryID);
		} else if (element instanceof FontDefinition) {
			String categoryID = ((FontDefinition) element).getCategoryId();
			category = WorkbenchPlugin.getDefault().getThemeRegistry().findCategory(categoryID);
		}
