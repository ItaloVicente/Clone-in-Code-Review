		String logMsgPrefix;
		try {
		} catch (NotDefinedException e) {
		}

		try {
			File canonicalPath = getSystemExplorerPath(item);
			if (canonicalPath == null) {
				StatusManager
						.getManager()
						.handle(new Status(
