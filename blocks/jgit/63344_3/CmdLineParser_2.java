
	@Override
	public void printSingleLineUsage(Writer w
		List<OptionHandler> options = getOptions();
		if (options.isEmpty()) {
			super.printSingleLineUsage(w
			return;
		}
		List<OptionHandler> backup = new ArrayList<>(options);
		boolean changed = sortRestOfArgumentsHandlerToTheEnd(options);
		try {
			super.printSingleLineUsage(w
		} finally {
			if (changed) {
				options.clear();
				options.addAll(backup);
			}
		}
	}

	private boolean sortRestOfArgumentsHandlerToTheEnd(
			List<OptionHandler> options) {
		for (int i = 0; i < options.size(); i++) {
			OptionHandler handler = options.get(i);
			if (handler instanceof RestOfArgumentsHandler
					|| handler instanceof PathTreeFilterHandler) {
				options.remove(i);
				options.add(handler);
				return true;
			}
		}
		return false;
	}
