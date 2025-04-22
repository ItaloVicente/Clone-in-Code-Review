	private String[] getSelectedSetting() {
		Iterator<?> settingsIterator = selectedSettings.iterator();
		String[] selectionIDs = new String[selectedSettings.size()];
		int index = 0;
		while (settingsIterator.hasNext()) {
			IConfigurationElement elem = (IConfigurationElement) settingsIterator.next();
			selectionIDs[index++] = elem.getAttribute(ATT_ID);
		}
		return selectionIDs;
