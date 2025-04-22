	public void cleanTemporaries() {
		if (tempFile != null && tempFile.exists()) {
			tempFile.delete();
		}
		tempFile = null;
	}

	public String replaceVariable(String input) throws IOException {
		return input.replace("$" + type.name()
	}

	public void addToEnv(Map<String
		env.put(type.name()
