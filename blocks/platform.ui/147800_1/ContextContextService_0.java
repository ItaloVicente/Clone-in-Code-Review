		LinkedList<String> updates = (LinkedList<String>) eclipseContext.getLocal(DEFERRED_UPDATES);
		if (updates != null) {
			for (String update : updates) {
				if (update.startsWith("+")) {
					locals.add(update.substring(1));
				} else if (update.startsWith("-")) {
					locals.remove(update.substring(1));
				}
