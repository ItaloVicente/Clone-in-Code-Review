	private void addIncludedConfig(final List<ConfigLine> newEntries,
			ConfigLine line, int depth) throws ConfigInvalidException {
				line.value == null || line.value.equals(MAGIC_EMPTY_VALUE)) {
			throw new ConfigInvalidException(
					JGitText.get().invalidLineInConfigFile);
		}
		File path = new File(line.value);
