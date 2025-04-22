	private static final PackList NO_PACKS = new PackList(new DfsPackFile[0]) {
		@Override
		boolean dirty() {
			return true;
		}

		@Override
		void markDirty() {
		}
	};
