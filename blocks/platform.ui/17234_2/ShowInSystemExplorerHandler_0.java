		StringTokenizer tokenizer = new StringTokenizer(command);
		while (tokenizer.hasMoreTokens()) {
			String token = tokenizer.nextToken();
			String replacedCommandPart = replaceVariables(token, path);
			commandParts.add(replacedCommandPart);
		}
		return (String[]) commandParts.toArray(new String[commandParts.size()]);
	}

	private String replaceVariables(String commandPart, File path) throws IOException {
		commandPart = Util.replaceAll(commandPart, VARIABLE_RESOURCE, path.getCanonicalPath());
