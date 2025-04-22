	/**
	 * Locate the log file on disk for a single reference name.
	 *
	 * @param name
	 *            name of the ref, relative to the Git repository top level
	 *            directory (so typically starts with refs/).
	 * @return the log file location.
	 */
	public File logFor(String name) {
		if (name.startsWith(R_REFS)) {
			name = name.substring(R_REFS.length());
			return new File(logsRefsDir, name);
		}
		return new File(logsDir, name);
	}

