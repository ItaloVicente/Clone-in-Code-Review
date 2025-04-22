
	private String ignoreRepositoryChangesDecorator(String rootText) {
		if (rootText.startsWith("> ")) {
			return rootText.substring(2);
		}
		return rootText;
	}
