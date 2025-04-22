	}

	protected Object getSingleSelection(IStructuredSelection selection) {
		return selection.size() == 1 ? selection.getFirstElement() : null;
	}

	protected void restoreWidgetValues() {
		expandPreviouslyExpandedCategories();
		selectPreviouslySelected();
	}

	public void saveWidgetValues() {
		storeExpandedCategories();
		storeSelectedCategoryAndWizard();
	}

	@Override
