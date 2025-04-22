		return resourcesToExport;
	}

	protected Iterator getSelectedResourcesIterator() {
		return this.resourceGroup.getAllCheckedListItems().iterator();
	}

	protected List getTypesToExport() {

		return selectedTypes;
	}

	protected List getWhiteCheckedResources() {

		return this.resourceGroup.getAllWhiteCheckedItems();
	}

	protected void handleTypesEditButtonPressed() {
		Object[] newSelectedTypes = queryResourceTypesToExport();

		if (newSelectedTypes != null) { // ie.- did not press Cancel
			this.selectedTypes = new ArrayList(newSelectedTypes.length);
			for (Object newSelectedType : newSelectedTypes) {
				this.selectedTypes.add(newSelectedType);
			}
			setupSelectionsBasedOnSelectedTypes();
		}

	}

	protected boolean hasExportableExtension(String resourceName) {
		if (selectedTypes == null) {
