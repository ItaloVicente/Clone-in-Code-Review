			byte[] aRaw

			if (ent.getOldMode() == GITLINK || ent.getNewMode() == GITLINK) {
				aRaw = writeGitLinkText(ent.getOldId());
				bRaw = writeGitLinkText(ent.getNewId());
			} else {
				aRaw = open(OLD
				bRaw = open(NEW
			}
