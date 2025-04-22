
			if (containsHelp(args)) {
				seenHelp = true;
				break;
			}
		}
		List<OptionHandler> backup = null;
		if (seenHelp) {
			backup = unsetRequiredOptions();
