	private void informUserNoTool(List<String> tools) {
		try {
			StringBuilder toolNames = new StringBuilder();
			for (String name : tools) {
			}
			outw.println(MessageFormat
					.format(CLIText.get().mergeToolPromptToolName
			outw.flush();
		} catch (IOException e) {
			throw new IllegalStateException("Cannot output text"
		}
	}

	private void merge(Map<String
