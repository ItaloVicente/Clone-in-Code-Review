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
		List<QuickAccessElement> previousPicks = previousPicksList.stream().filter(Objects::nonNull)
				.collect(Collectors.toList());
		return previousPicks;
	}

	private IDialogSettings getDialogSettings() {
		final IDialogSettings workbenchDialogSettings = WorkbenchPlugin.getDefault().getDialogSettings();
		IDialogSettings result = workbenchDialogSettings.getSection(getId());
		if (result == null) {
			result = workbenchDialogSettings.addNewSection(getId());
		}
		return result;
	}

	private String getId() {
	}

	/**
	 * @param element
	 */
	private void addPreviousPick(String text, QuickAccessElement element) {

		previousPicksList.remove(element);
		if (previousPicksList.size() == MAXIMUM_NUMBER_OF_ELEMENTS) {
			Object removedElement = previousPicksList.remove(previousPicksList.size() - 1);
			ArrayList<String> removedList = textMap.remove(removedElement);
			for (int i = 0; i < removedList.size(); i++) {
				elementMap.remove(removedList.get(i));
			}
		}
		previousPicksList.add(0, element);

		ArrayList<String> textList = textMap.get(element);
		if (textList == null) {
			textList = new ArrayList<>();
			textMap.put(element, textList);
		}

		textList.remove(text);
		if (textList.size() == MAXIMUM_NUMBER_OF_TEXT_ENTRIES_PER_ELEMENT) {
			Object removedText = textList.remove(0);
			elementMap.remove(removedText);
		}

		if (text.length() > 0) {
			textList.add(text);

			QuickAccessElement replacedElement = elementMap.put(text, element);
			if (replacedElement != null && !replacedElement.equals(element)) {
				textList = textMap.get(replacedElement);
				if (textList != null) {
					textList.remove(text);
					if (textList.isEmpty()) {
						textMap.remove(replacedElement);
						previousPicksList.remove(replacedElement);
					}
				}
			}
		}
	}

	/**
	 * Returns the quick access shell for testing. Should not be referenced outside
	 * of the tests.
	 *
	 * @return the current quick access shell or <code>null</code>
	 */
	public Shell getQuickAccessShell() {
		createContentsAndTable();
		return shell;
	}

	/**
	 * Returns the quick access search text for testing. Should not be referenced
	 * outside of the tests.
	 *
	 * @return the search text in the workbench window or <code>null</code>
	 */
