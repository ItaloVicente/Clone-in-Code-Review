		if (ent.getOldMode() == GITLINK || ent.getNewMode() == GITLINK) {
			formatOldNewPaths(buf, ent);
			writeGitLinkDiffText(buf, ent);
			editList = new EditList();
			type = PatchType.UNIFIED;

		} else if (ent.getOldId() == null || ent.getNewId() == null) {
