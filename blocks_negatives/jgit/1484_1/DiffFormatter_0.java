		writeDiffHeader(out, ent);

		if (ent.getOldMode() == GITLINK || ent.getNewMode() == GITLINK) {
			writeGitLinkDiffText(out, ent);
		} else {
			if (db == null)
				throw new IllegalStateException(
						JGitText.get().repositoryIsRequired);

			ObjectReader reader = db.newObjectReader();
			byte[] aRaw, bRaw;
			try {
				aRaw = open(reader, ent.getOldMode(), ent.getOldId());
				bRaw = open(reader, ent.getNewMode(), ent.getNewId());
			} finally {
				reader.release();
			}

			if (RawText.isBinary(aRaw) || RawText.isBinary(bRaw)) {
				out.write(encodeASCII("Binary files differ\n"));

			} else {
				RawText a = rawTextFactory.create(aRaw);
				RawText b = rawTextFactory.create(bRaw);
				formatEdits(a, b, new MyersDiff(a, b).getEdits());
			}
		}
