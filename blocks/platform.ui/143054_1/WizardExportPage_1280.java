		return typesToExportField.getText();
	}

	protected List getTypesToExport() {
		List result = new ArrayList();
		StringTokenizer tokenizer = new StringTokenizer(typesToExportField.getText(), TYPE_DELIMITER);

		while (tokenizer.hasMoreTokens()) {
			String currentExtension = tokenizer.nextToken().trim();
			if (!currentExtension.isEmpty()) {
