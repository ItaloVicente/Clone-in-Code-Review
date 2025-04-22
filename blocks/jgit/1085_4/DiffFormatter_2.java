	public FileHeader createFileHeader(DiffEntry ent) throws IOException
			CorruptObjectException
		ByteArrayOutputStream buf = new ByteArrayOutputStream();
		final EditList editList;

		writeDiffHeader(buf

		if (ent.getOldMode() == GITLINK || ent.getNewMode() == GITLINK) {
			writeGitLinkDiffText(buf
			editList = new EditList();
		} else {
			byte[] aRaw = open(ent.getOldMode()
			byte[] bRaw = open(ent.getNewMode()

			if (RawText.isBinary(aRaw) || RawText.isBinary(bRaw)) {
				buf.write(encodeASCII("Binary files differ\n"));
				editList = new EditList();
			} else {
				RawText a = rawTextFactory.create(aRaw);
				RawText b = rawTextFactory.create(bRaw);
				editList = new MyersDiff(a
			}
		}

		buf.flush();
		return new FileHeader(buf.toByteArray()
	}

