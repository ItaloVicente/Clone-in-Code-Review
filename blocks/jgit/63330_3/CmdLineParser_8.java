
	@SuppressWarnings("unchecked")
	private List<OptionHandler> getOptions() {
		List<OptionHandler> options = null;
		try {
			Field field = org.kohsuke.args4j.CmdLineParser.class
			field.setAccessible(true);
			options = (List<OptionHandler>) field.get(this);
		} catch (NoSuchFieldException | SecurityException
				| IllegalArgumentException | IllegalAccessException e) {
		}
		if (options == null) {
			return Collections.emptyList();
		}
		return options;
	}
