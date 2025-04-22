	public Pack openPack(File pack) throws IOException {
		PackFile pf;
		try {
			pf = new PackFile(pack);
		} catch (IllegalArgumentException e) {
			throw new IOException(
					MessageFormat.format(JGitText.get().notAValidPack
					e);
		}

		String p = pf.getName();
				|| !pf.getPackExt().equals(PACK)) {
			throw new IOException(
					MessageFormat.format(JGitText.get().notAValidPack
		}

		PackFile bitmapIdx = pf.create(BITMAP_INDEX);
		Pack res = new Pack(pack
