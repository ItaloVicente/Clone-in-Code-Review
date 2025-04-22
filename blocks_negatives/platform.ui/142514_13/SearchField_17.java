		if (restoreDialogEntriesJob != null) {
			restoreDialogEntriesJob.cancel();
		}
		if (refreshQuickAccessContents != null) {
			refreshQuickAccessContents.cancel();
		}
		storeDialog();
		elementMap.clear();
		previousPicksList.clear();
		textMap.clear();
		partService = null;
	}

	private void storeDialog() {
		List<QuickAccessElement> previousPicks = getLoadedPreviousPicks();
		String[] orderedElements = new String[previousPicks.size()];
		String[] orderedProviders = new String[previousPicks.size()];
		String[] textEntries = new String[previousPicks.size()];
		ArrayList<String> arrayList = new ArrayList<>();
		for (int i = 0; i < orderedElements.length; i++) {
			QuickAccessElement quickAccessElement = previousPicks.get(i);
			ArrayList<String> elementText = textMap.get(quickAccessElement);
			Assert.isNotNull(elementText);
			orderedElements[i] = quickAccessElement.getId();
			orderedProviders[i] = quickAccessContents.getProviderFor(quickAccessElement).getId();
			arrayList.addAll(elementText);
		}
		String[] textArray = arrayList.toArray(new String[arrayList.size()]);
		IDialogSettings dialogSettings = getDialogSettings();
		dialogSettings.put(ORDERED_ELEMENTS, orderedElements);
		dialogSettings.put(ORDERED_PROVIDERS, orderedProviders);
		dialogSettings.put(TEXT_ENTRIES, textEntries);
		dialogSettings.put(TEXT_ARRAY, textArray);
		dialogSettings.put(DIALOG_HEIGHT, dialogHeight);
		dialogSettings.put(DIALOG_WIDTH, dialogWidth);
	}

	/**
	 * If the original list was not fully restored yet, it may contain null
	 * elements, so we return here only already resolved, non null elements
	 */
	private List<QuickAccessElement> getLoadedPreviousPicks() {
		return previousPicksList.stream().filter(Objects::nonNull)
				.collect(Collectors.toList());
	}

	private IDialogSettings getDialogSettings() {
		final IDialogSettings workbenchDialogSettings = WorkbenchPlugin.getDefault().getDialogSettings();
		IDialogSettings result = workbenchDialogSettings.getSection(getId());
		if (result == null) {
			result = workbenchDialogSettings.addNewSection(getId());
		}
		return result;
