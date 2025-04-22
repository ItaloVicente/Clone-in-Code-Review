	private static Set<String> getCommitHistory() {
		String all = getPreferenceStore().getString(
				UIPreferences.COMMIT_DIALOG_HISTORY_MESSAGES);
		if (all.length() == 0)
			return Collections.emptySet();
		int max = getCommitHistorySize();
		if (max < 1)
			return Collections.emptySet();
		XMLMemento memento;
		try {
			memento = XMLMemento.createReadRoot(new StringReader(all));
		} catch (WorkbenchException e) {
			org.eclipse.egit.ui.Activator.logError(
					"Error reading commit message history", e); //$NON-NLS-1$
			return Collections.emptySet();
		}
		Set<String> messages = new LinkedHashSet<String>();
		for (IMemento child : memento.getChildren(KEY_MESSAGE)) {
			messages.add(child.getTextData());
			if (messages.size() == max)
				break;
		}
		return messages;
	}

	private static void saveCommitHistory(String message) {
		if (message == null || message.length() == 0)
			return;
		int size = getCommitHistorySize();
		if (size < 1)
			return;

		XMLMemento memento = XMLMemento.createWriteRoot(KEY_MESSAGES);
		memento.createChild(KEY_MESSAGE).putTextData(message);

		int count = 1;
		if (count < size) {
			Set<String> history = getCommitHistory();
			history.remove(message);
			for (String previous : history) {
				memento.createChild(KEY_MESSAGE).putTextData(previous);
				count++;
				if (count == size)
					break;
			}
		}
		StringWriter writer = new StringWriter();
		try {
			memento.save(writer);
			getPreferenceStore().setValue(
					UIPreferences.COMMIT_DIALOG_HISTORY_MESSAGES,
					writer.toString());
		} catch (IOException e) {
			org.eclipse.egit.ui.Activator.logError(
					"Error writing commit message history", e); //$NON-NLS-1$
		}
	}

	private static int getCommitHistorySize() {
		return getPreferenceStore().getInt(
				UIPreferences.COMMIT_DIALOG_HISTORY_SIZE);
	}

