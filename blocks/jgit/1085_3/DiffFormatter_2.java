	public FileHeader createFileHeader(DiffEntry ent) throws IOException {
		ByteArrayOutputStream buf = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(buf
		final EditList editList;

		writeDiffHeader(ps

		if (ent.getOldMode() == GITLINK || ent.getNewMode() == GITLINK) {
			writeGitLinkDiffText(ps
			editList = new EditList();
		} else {
			byte[] aRaw = open(ent.getOldMode()
			byte[] bRaw = open(ent.getNewMode()

			if (RawText.isBinary(aRaw) || RawText.isBinary(bRaw)) {
				ps.write(encodeASCII("Binary files differ\n"));
				editList = new EditList();
			} else {
				RawText a = rawTextFactory.create(aRaw);
				RawText b = rawTextFactory.create(bRaw);
				editList = new MyersDiff(a
			}
		}

		ps.flush();
		return new FileHeader(buf.toByteArray()
	}

