
	private boolean canExport(final File d) {
		if (isExportAll()) {
			return true;
		}
		return new File(d, "git-daemon-export-ok").exists();
	}
