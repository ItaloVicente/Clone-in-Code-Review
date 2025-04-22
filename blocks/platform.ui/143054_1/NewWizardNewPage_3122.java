			updateWizardSelection((IWizardDescriptor) selectedObject);
		} else {
			selectedElement = null;
			page.setHasPages(false);
			page.setCanFinishEarly(false);
			page.selectWizardNode(null);
			updateDescription(null);
		}
	}

	protected void selectPreviouslySelected() {
		String selectedId = settings.get(STORE_SELECTED_ID);
		if (selectedId == null) {
