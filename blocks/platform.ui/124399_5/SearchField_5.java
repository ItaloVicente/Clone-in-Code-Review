			isLoadingPreviousElements = true;
			refreshQuickAccessContents = new UIJob("Finish restoring quick access elements") { //$NON-NLS-1$
				@Override
				public IStatus runInUIThread(IProgressMonitor monitor) {
					quickAccessContents.refresh(quickAccessContents.filterText.getText());
					return Status.OK_STATUS;
				}
			};
			refreshQuickAccessContents.setSystem(true);
			restoreDialogEntriesJob = new Job("Restore quick access elements") { //$NON-NLS-1$
				@Override
				protected IStatus run(IProgressMonitor monitor) {
					try {
						restoreDialogEntries(dialogSettings, monitor);
						return Status.OK_STATUS;
					} catch (OperationCanceledException e) {
						return Status.CANCEL_STATUS;
					} finally {
						isLoadingPreviousElements = false;
						refreshQuickAccessContents.schedule();
					}
				}
			};
			restoreDialogEntriesJob.schedule();
		}
	}

	private void restoreDialogEntries(IDialogSettings dialogSettings, IProgressMonitor monitor)
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

				try {
					Thread.sleep(2_000);
				} catch (InterruptedException e) {
				}

				QuickAccessProvider quickAccessProvider = providerMap.get(orderedProviders[i]);
				int numTexts = Integer.parseInt(textEntries[i]);
				subMonitor.split(1).setTaskName("Restoring quick access element \"" + orderedElements[i] + "\"."); //$NON-NLS-1$ //$NON-NLS-2$
				if (quickAccessProvider != null) {
					QuickAccessElement quickAccessElement = quickAccessProvider.getElementForId(orderedElements[i]);
					if (quickAccessElement != null) {
						ArrayList<String> arrayList = new ArrayList<>();
						for (int j = arrayIndex; j < arrayIndex + numTexts; j++) {
							String text = textArray[j];
							if (text.length() > 0) {
								arrayList.add(text);
								elementMap.put(text, quickAccessElement);
