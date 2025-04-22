			String[] orderedProviders = dialogSettings.getArray(ORDERED_PROVIDERS);
			previousPicksList.addAll(Arrays.asList(new QuickAccessElement[orderedProviders.length]));

			isLoadingPreviousElements = true;
			refreshQuickAccessContents = new UIJob("Finish restoring quick access elements") { //$NON-NLS-1$
				@Override
				public IStatus runInUIThread(IProgressMonitor monitor) {
					boolean restoreUiElements = true;
					restoreDialogEntries(dialogSettings, restoreUiElements, monitor);
					quickAccessContents.refresh(quickAccessContents.filterText.getText());
					List<QuickAccessElement> previousPicks = getLoadedPreviousPicks();
					previousPicksList.clear();
					previousPicksList.addAll(previousPicks);
					return Status.OK_STATUS;
				}
			};
			refreshQuickAccessContents.setSystem(true);
			restoreDialogEntriesJob = Job.createSystem("Restore quick access elements", (IProgressMonitor monitor) -> { //$NON-NLS-1$
				try {
					boolean restoreUiElements = false;
					restoreDialogEntries(dialogSettings, restoreUiElements, monitor);
					return Status.OK_STATUS;
				} catch (OperationCanceledException e) {
					return Status.CANCEL_STATUS;
				} finally {
					isLoadingPreviousElements = false;
					refreshQuickAccessContents.schedule();
				}
			});
			restoreDialogEntriesJob.schedule();
		}
	}

	private void restoreDialogEntries(IDialogSettings dialogSettings, boolean restoreUiElements,
			IProgressMonitor monitor)
			throws OperationCanceledException {
		String[] orderedElements = dialogSettings.getArray(ORDERED_ELEMENTS);
		String[] orderedProviders = dialogSettings.getArray(ORDERED_PROVIDERS);
		String[] textEntries = dialogSettings.getArray(TEXT_ENTRIES);
		String[] textArray = dialogSettings.getArray(TEXT_ARRAY);

		if (orderedElements != null && orderedProviders != null && textEntries != null && textArray != null) {
			int arrayIndex = 0;
			int elementCount = orderedElements.length;
			SubMonitor subMonitor = SubMonitor.convert(monitor, "Restoring quick access elements.", elementCount); //$NON-NLS-1$

			for (int i = 0; i < elementCount; i++) {
				QuickAccessProvider quickAccessProvider = providerMap.get(orderedProviders[i]);
				int numTexts = Integer.parseInt(textEntries[i]);
				subMonitor.split(1).setTaskName("Restoring quick access element \"" + orderedElements[i] + "\"."); //$NON-NLS-1$ //$NON-NLS-2$
				if (quickAccessProvider != null && restoreUiElements == quickAccessProvider.requiresUiAccess()) {
					QuickAccessElement quickAccessElement = quickAccessProvider.getElementForId(orderedElements[i]);

					if (quickAccessElement != null) {
						ArrayList<String> arrayList = new ArrayList<>();
						for (int j = arrayIndex; j < arrayIndex + numTexts; j++) {
							String text = textArray[j];
							if (text.length() > 0) {
								arrayList.add(text);
								elementMap.put(text, quickAccessElement);
