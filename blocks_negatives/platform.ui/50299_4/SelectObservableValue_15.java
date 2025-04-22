	private void addOption(Option option) {
		Option[] newOptions = new Option[options.length + 1];
		System.arraycopy(options, 0, newOptions, 0, options.length);
		newOptions[options.length] = option;
		options = newOptions;
	}

	@Override
	protected Object doGetValue() {
