		if (objectDatabase.exists()) {
			try {
				getConfig().load();
			} catch (ConfigInvalidException e1) {
				IOException e2 = new IOException("Unknown repository format");
				e2.initCause(e1);
				throw e2;
