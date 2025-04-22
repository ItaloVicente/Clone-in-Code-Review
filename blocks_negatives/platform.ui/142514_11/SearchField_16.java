			restoreDialogEntriesJob.setRule(RESTORE_DIALOG_ENTRIES_SCHEDULING_RULE);
			restoreDialogEntriesJob.schedule();
		}
	}

	private void restoreDialogEntries(IDialogSettings dialogSettings, boolean restoreUiElements,
			IProgressMonitor monitor) throws OperationCanceledException {
		String[] orderedElements = dialogSettings.getArray(ORDERED_ELEMENTS);
		String[] orderedProviders = dialogSettings.getArray(ORDERED_PROVIDERS);
		String[] textEntries = dialogSettings.getArray(TEXT_ENTRIES);
		String[] textArray = dialogSettings.getArray(TEXT_ARRAY);

		if (orderedElements != null && orderedProviders != null && textEntries != null && textArray != null) {
			int arrayIndex = 0;
			int elementCount = orderedElements.length;
			SubMonitor subMonitor = SubMonitor.convert(monitor, "Restoring quick access elements.", elementCount); //$NON-NLS-1$

			for (int i = 0; i < elementCount; i++) {
				QuickAccessProvider quickAccessProvider = quickAccessContents.getProvider(orderedProviders[i]);
				int numTexts = Integer.parseInt(textEntries[i]);
				if (quickAccessProvider != null && restoreUiElements == quickAccessProvider.requiresUiAccess()) {
					QuickAccessElement quickAccessElement = quickAccessProvider.getElementForId(orderedElements[i]);

					if (quickAccessElement != null) {
						quickAccessContents.registerProviderFor(quickAccessElement, quickAccessProvider);
						ArrayList<String> arrayList = new ArrayList<>();
						for (int j = arrayIndex; j < arrayIndex + numTexts; j++) {
							String text = textArray[j];
							if (text.length() > 0) {
								arrayList.add(text);
								elementMap.put(text, quickAccessElement);
							}
						}
						textMap.put(quickAccessElement, arrayList);
						previousPicksList.set(i, quickAccessElement);
					}
				}
				arrayIndex += numTexts;
			}
