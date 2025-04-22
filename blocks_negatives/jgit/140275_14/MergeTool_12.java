		for (String name : userTools.keySet()) {
					+ userTools.get(name).getCommand());
		}
		outw.println(
				"The following tools are valid, but not currently available:"); //$NON-NLS-1$
		for (String name : predefTools.keySet()) {
			if (!predefTools.get(name).isAvailable()) {
