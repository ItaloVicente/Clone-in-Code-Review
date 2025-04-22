	private void populateCategoryDefinitions() {
		String stringToAppend = null;
		for (int index = 1; index <= 6; index++) {
			stringToAppend = Integer.toString(index);
			categoryDefinitions.add(new CategoryDefinition(
					"org.eclipse.category" + stringToAppend, "Category " //$NON-NLS-1$ //$NON-NLS-2$
							+ stringToAppend, sourceId, "description")); //$NON-NLS-1$
		}
	}
