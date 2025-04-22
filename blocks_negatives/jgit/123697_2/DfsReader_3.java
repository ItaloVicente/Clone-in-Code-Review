	private static final Comparator<DfsPackFile> PACK_SORT_FOR_REUSE = new Comparator<DfsPackFile>() {
		@Override
		public int compare(DfsPackFile af, DfsPackFile bf) {
			DfsPackDescription ad = af.getPackDescription();
			DfsPackDescription bd = bf.getPackDescription();
			PackSource as = ad.getPackSource();
			PackSource bs = bd.getPackSource();

			if (as == bs && DfsPackDescription.isGC(as)) {
				return Long.signum(bd.getFileSize(PACK) - ad.getFileSize(PACK));
			}

			return 0;
		}
	};
