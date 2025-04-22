	protected boolean isSubmodule() {
		if (diffEntry == null)
			return false;
		return diffEntry.getOldMode() == FileMode.GITLINK
				|| diffEntry.getNewMode() == FileMode.GITLINK;
	}

