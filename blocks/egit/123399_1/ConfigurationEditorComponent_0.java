	private void reveal(String sectionName, String subSectionName,
			String entryName) {
		Entry entry = ((GitConfig) tv.getInput()).getEntry(sectionName,
				subSectionName, entryName);
		if (entry != null) {
			tv.reveal(entry);
		}
	}

