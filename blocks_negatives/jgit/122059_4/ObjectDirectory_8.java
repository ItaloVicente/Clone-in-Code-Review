	private static final class PackList {
		/** State just before reading the pack directory. */
		final FileSnapshot snapshot;

		/** All known packs, sorted by {@link PackFile#SORT}. */
		final PackFile[] packs;

		PackList(FileSnapshot monitor, PackFile[] packs) {
			this.snapshot = monitor;
			this.packs = packs;
		}
	}

