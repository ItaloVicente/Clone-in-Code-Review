	public File logFor(String name) {
		if (name.startsWith(R_REFS)) {
			name = name.substring(R_REFS.length());
			return new File(logsRefsDir
		}
		return new File(logsDir
	}

