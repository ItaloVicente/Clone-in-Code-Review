		try {
			getConfig().load();
		} catch (ConfigInvalidException e1) {
			IOException e2 = new IOException("Unknown repository format");
			e2.initCause(e1);
			throw e2;
		}

		if (workDir == null) {
			if (d != null) {
				String workTreeConfig = getConfig().getString("core"
				if (workTreeConfig != null) {
					workDir = FS.resolve(d
				} else {
					workDir = gitDir.getParentFile();
				}
