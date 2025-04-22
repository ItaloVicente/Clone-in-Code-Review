	public FileHeader createFileHeader(DiffEntry ent) throws IOException {
		ByteArrayOutputStream buf = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(buf
		EditList editList = new EditList();

		writeDiffHeader(ps

		if (ent.getOldMode() == GITLINK || ent.getNewMode() == GITLINK) {
			if (ent.getOldMode() == GITLINK) {
				ps.write(encodeASCII("-Subproject commit "
						+ ent.getOldId().name() + "\n"));
			}
			if (ent.getNewMode() == GITLINK) {
				ps.write(encodeASCII("+Subproject commit "
						+ ent.getNewId().name() + "\n"));
			}
		} else {
			byte[] aRaw = open(ent.getOldMode()
			byte[] bRaw = open(ent.getNewMode()

			if (RawText.isBinary(aRaw) || RawText.isBinary(bRaw)) {
				ps.write(encodeASCII("Binary files differ\n"));
			} else {
				RawText a = rawTextFactory.create(aRaw);
				RawText b = rawTextFactory.create(bRaw);
				editList = new MyersDiff(a
			}
		}

		ps.flush();
		return new FileHeader(buf.toByteArray()
	}

