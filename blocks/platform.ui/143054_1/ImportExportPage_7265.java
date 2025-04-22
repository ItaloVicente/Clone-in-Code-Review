				return wizardElement.createWizard();
			}
		};
	}

	protected void restoreWidgetValues() {
		updateMessage();
	}

	protected void expandPreviouslyExpandedCategories(String setting, IWizardCategory wizardCategories,
			TreeViewer viewer) {
		String[] expandedCategoryPaths = getDialogSettings().getArray(setting);
		if (expandedCategoryPaths == null || expandedCategoryPaths.length == 0) {
