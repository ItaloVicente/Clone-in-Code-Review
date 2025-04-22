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
	public Text getQuickAccessSearchText() {
		return txtQuickAccess;
	}

	/**
	 * Returns the table in the shell for testing. Should not be referenced outside
	 * of the tests.
	 *
	 * @return the table created in the shell or <code>null</code>
	 */
	public Table getQuickAccessTable() {
		createContentsAndTable();
		return table;
	}

	/**
	 * Bug 539510: in case of multiple workbench windows we must avoid loading
	 * commands simultaneously for each window
	 */
	private static final ISchedulingRule RESTORE_DIALOG_ENTRIES_SCHEDULING_RULE = new ISchedulingRule() {
		@Override
		public boolean isConflicting(ISchedulingRule rule) {
			return RESTORE_DIALOG_ENTRIES_SCHEDULING_RULE == rule;
		}

		@Override
		public boolean contains(ISchedulingRule rule) {
			return RESTORE_DIALOG_ENTRIES_SCHEDULING_RULE == rule;
		}
	};
