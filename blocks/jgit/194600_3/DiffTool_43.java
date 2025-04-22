		for (String name : predefTools.keySet()) {
			if (predefTools.get(name).isAvailable()) {
				availableToolNames.append(MessageFormat.format("\t\t{0}\n"
			} else {
				notAvailableToolNames.append(MessageFormat.format("\t\t{0}\n"
			}
