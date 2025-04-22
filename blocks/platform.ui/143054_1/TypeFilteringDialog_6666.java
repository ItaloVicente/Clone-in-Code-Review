	private void addUserDefinedEntries(List result) {
		StringTokenizer tokenizer = new StringTokenizer(userDefinedText.getText(), TYPE_DELIMITER);
		while (tokenizer.hasMoreTokens()) {
			String currentExtension = tokenizer.nextToken().trim();
			if (!currentExtension.isEmpty()) {
				if (currentExtension.startsWith("*.")) { //$NON-NLS-1$
