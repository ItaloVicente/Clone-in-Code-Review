			quickAccessContents.setShowAllMatches(!quickAccessContents.getShowAllMatches());
		}
	}

	/**
	 * Checks if the text or shell has focus. If not, closes the shell.
	 *
	 * @param table the shell's table
	 * @param text  the search text field
	 */
	protected void checkFocusLost(final Table table, final Text text) {
		if (shell == null) {
			return;
		}
		if (!shell.isDisposed() && !table.isDisposed() && !text.isDisposed()) {
			if (table.getDisplay().getActiveShell() == table.getShell()) {
				text.setFocus();
				return;
			}
			if (!shell.isFocusControl() && !table.isFocusControl() && !text.isFocusControl()) {
				quickAccessContents.doClose();
			}
		}
	}

	/**
	 * Adds a listener to the <code>org.eclipse.swt.accessibility.Accessible</code>
	 * object assigned to the Quick Access search box. The listener sets a name of a
	 * selected element in the search result list as a text to read for a screen
	 * reader.
	 */
	private void addAccessibleListener() {
		if (accessibleListener == null) {
			accessibleListener = new AccessibleAdapter() {
				@Override
				public void getName(AccessibleEvent e) {
					e.result = selectedString;
				}
			};
			txtQuickAccess.getAccessible().addAccessibleListener(accessibleListener);
		}
	}

	/**
	 * Removes a listener from the
	 * <code>org.eclipse.swt.accessibility.Accessible</code> object assigned to the
	 * Quick Access search box.
	 */
	private void removeAccessibleListener() {
		if (accessibleListener != null) {
			txtQuickAccess.getAccessible().removeAccessibleListener(accessibleListener);
			accessibleListener = null;
		}
	}

	/**
	 * Notifies <code>org.eclipse.swt.accessibility.Accessible<code> object that
	 * selected item has been changed.
	 */
	private void notifyAccessibleTextChanged() {
		if (table.getSelection().length == 0) {
			return;
		}
		TableItem item = table.getSelection()[0];
		selectedString = NLS.bind(QuickAccessMessages.QuickAccess_SelectedString, item.getText(0), item.getText(1));
		txtQuickAccess.getAccessible().sendEvent(ACC.EVENT_NAME_CHANGED, null);
	}

	private void restoreDialog() {
		IDialogSettings dialogSettings = getDialogSettings();
		if (dialogSettings != null) {
			try {
				dialogHeight = dialogSettings.getInt(DIALOG_HEIGHT);
				dialogWidth = dialogSettings.getInt(DIALOG_WIDTH);
			} catch (NumberFormatException e) {
				dialogHeight = -1;
				dialogWidth = -1;
			}

			/*
			 * add place holders, so that we don't change element order due to first
			 * restoring non-UI elements and then restoring UI elements
			 */
			String[] orderedProviders = dialogSettings.getArray(ORDERED_PROVIDERS);
			if (orderedProviders != null) {
				previousPicksList.addAll(Arrays.asList(new QuickAccessElement[orderedProviders.length]));
			}

			isLoadingPreviousElements = true;
				@Override
				public IStatus runInUIThread(IProgressMonitor monitor) {
					try {
						if (txtQuickAccess.isDisposed()) {
							return Status.OK_STATUS;
						}
						restoreDialogEntries(dialogSettings, true, monitor);
						quickAccessContents.refresh(txtQuickAccess.getText());
						List<QuickAccessElement> previousPicks = getLoadedPreviousPicks();
						previousPicksList.clear();
						previousPicksList.addAll(previousPicks);
					} finally {
						isLoadingPreviousElements = false;
					}
					return Status.OK_STATUS;
				}
			};
			refreshQuickAccessContents.setRule(RESTORE_DIALOG_ENTRIES_SCHEDULING_RULE);
			refreshQuickAccessContents.setSystem(true);
			restoreDialogEntriesJob = Job.createSystem("Restore quick access elements", (IProgressMonitor monitor) -> { //$NON-NLS-1$
				if (txtQuickAccess.isDisposed()) {
					isLoadingPreviousElements = false;
					return;
				}
				try {
					restoreDialogEntries(dialogSettings, false, monitor);
				} catch (OperationCanceledException e) {
				} finally {
					refreshQuickAccessContents.schedule();
				}
			});
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
