	public FileHeader createFileHeader(DiffEntry ent) throws IOException
			CorruptObjectException
		ByteArrayOutputStream buf = new ByteArrayOutputStream();
		final EditList editList;
		final FileHeader.PatchType type;

		writeDiffHeader(buf

		if (ent.getOldMode() == GITLINK || ent.getNewMode() == GITLINK) {
			writeGitLinkDiffText(buf
			editList = new EditList();
			type = PatchType.UNIFIED;
		} else {
			byte[] aRaw = open(ent.getOldMode()
			byte[] bRaw = open(ent.getNewMode()

			if (RawText.isBinary(aRaw) || RawText.isBinary(bRaw)) {
				buf.write(encodeASCII("Binary files differ\n"));
				editList = new EditList();
				type = PatchType.BINARY;
			} else {
				RawText a = rawTextFactory.create(aRaw);
				RawText b = rawTextFactory.create(bRaw);
				editList = new MyersDiff(a
				type = PatchType.UNIFIED;
			}
		}

		return new FileHeader(buf.toByteArray()
	}

