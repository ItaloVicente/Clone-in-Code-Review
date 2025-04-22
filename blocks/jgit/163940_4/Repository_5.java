	public File getBaseDirectoryForChild(String child) {
		if (hasCommonDirectory() && !child.endsWith(Constants.HEAD)) {
			final String[] commondirConstantsArr = new String[] {
					Constants.BRANCHES
					Constants.LOGS
					Constants.CONFIG
					Constants.HOOKS
			String[] prefix = child.split(Constants.FILE_SEPARATOR
			if (Arrays.asList(commondirConstantsArr).contains(prefix[0])) {
				return getCommonDirectory();
			}
		}
		return getDirectory();
	}

