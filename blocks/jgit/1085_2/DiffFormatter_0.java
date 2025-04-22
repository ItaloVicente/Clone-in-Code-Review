		writeDiffHeader(out

		if (ent.getOldMode() == GITLINK || ent.getNewMode() == GITLINK) {
			if (ent.getOldMode() == GITLINK) {
				out.write(encodeASCII("-Subproject commit "
						+ ent.getOldId().name() + "\n"));
			}
			if (ent.getNewMode() == GITLINK) {
				out.write(encodeASCII("+Subproject commit "
						+ ent.getNewId().name() + "\n"));
			}
		} else {
			byte[] aRaw = open(ent.getOldMode()
			byte[] bRaw = open(ent.getNewMode()

			if (RawText.isBinary(aRaw) || RawText.isBinary(bRaw)) {
				out.write(encodeASCII("Binary files differ\n"));

			} else {
				RawText a = rawTextFactory.create(aRaw);
				RawText b = rawTextFactory.create(bRaw);
				formatEdits(a
			}
		}
	}

	private void writeDiffHeader(OutputStream o
