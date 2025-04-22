	public Pack openPack(File pack)
			throws IOException {
		final String p = pack.getName();
			throw new IOException(MessageFormat.format(JGitText.get().notAValidPack, pack));

		int extensions = PACK.getBit() | INDEX.getBit();
		final String base = p.substring(0, p.length() - 4);
		for (PackExt ext : PackExt.values()) {
			if ((extensions & ext.getBit()) == 0) {
				final String name = base + ext.getExtension();
				if (new File(pack.getParentFile(), name).exists())
					extensions |= ext.getBit();
			}
		}

		Pack res = new Pack(pack, extensions);
