	private static final Comparator<DfsPackFile> PACK_SORT_FOR_REUSE = new Comparator<DfsPackFile>() {
		public int compare(DfsPackFile af
			DfsPackDescription ad = af.getPackDescription();
			DfsPackDescription bd = bf.getPackDescription();
			PackSource as = ad.getPackSource();
			PackSource bs = bd.getPackSource();

			if (as != null && as == bs && DfsPackDescription.isGC(as)) {
				return Long.signum(bd.getFileSize(PACK) - ad.getFileSize(PACK));
			}

			return 0;
		}
	};

	private DfsPackFile[] sortPacksForSelectRepresentation()
			throws IOException {
		DfsPackFile[] packs = db.getPacks();
		DfsPackFile[] sorted = new DfsPackFile[packs.length];
		System.arraycopy(packs
		Arrays.sort(sorted
		return sorted;
	}

