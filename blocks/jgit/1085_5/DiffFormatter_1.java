		writeDiffHeader(out

		if (ent.getOldMode() == GITLINK || ent.getNewMode() == GITLINK) {
			writeGitLinkDiffText(out
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

	private void writeGitLinkDiffText(OutputStream o
			throws IOException {
		if (ent.getOldMode() == GITLINK) {
			o.write(encodeASCII("-Subproject commit " + ent.getOldId().name()
					+ "\n"));
		}
		if (ent.getNewMode() == GITLINK) {
			o.write(encodeASCII("+Subproject commit " + ent.getNewId().name()
					+ "\n"));
		}
	}

	private void writeDiffHeader(OutputStream o
			throws IOException {
