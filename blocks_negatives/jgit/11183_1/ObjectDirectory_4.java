	private static final class CachedPackList {
		final FileSnapshot snapshot;

		final Collection<LocalCachedPack> packs;

		final byte[] raw;

		CachedPackList(FileSnapshot sn, List<LocalCachedPack> list, byte[] buf) {
			snapshot = sn;
			packs = list;
			raw = buf;
		}

		@SuppressWarnings("unchecked")
		Collection<CachedPack> getCachedPacks() {
			Collection p = packs;
			return p;
		}
	}

