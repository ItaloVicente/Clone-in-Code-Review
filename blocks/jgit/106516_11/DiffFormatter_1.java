		RawText aRaw = null;
		RawText bRaw = null;
		if (ent.getOldMode() == GITLINK || ent.getNewMode() == GITLINK) {
			aRaw = new RawText(writeGitLinkText(ent.getOldId()));
			bRaw = new RawText(writeGitLinkText(ent.getNewId()));
		} else {
			try {
