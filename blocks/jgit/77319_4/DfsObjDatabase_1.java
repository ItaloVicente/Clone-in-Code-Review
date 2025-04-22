
		abstract boolean dirty();

		abstract void markDirty();
	}

	private static final class PackListImpl extends PackList {
		private volatile boolean dirty;

		PackListImpl(DfsPackFile[] packs) {
			super(packs);
		}

		@Override
		boolean dirty() {
			return dirty;
		}

		@Override
		void markDirty() {
			dirty = true;
		}
