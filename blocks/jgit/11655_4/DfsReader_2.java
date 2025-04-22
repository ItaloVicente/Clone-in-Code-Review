	private List<DfsObjectToPack> findAllFromPack(DfsPackFile pack
			Iterable<ObjectToPack> objects) throws IOException {
		List<DfsObjectToPack> tmp = new BlockList<DfsObjectToPack>();
		PackIndex idx = pack.getPackIndex(this);
		for (ObjectToPack otp : objects) {
			long p = idx.findOffset(otp);
			if (0 < p) {
				otp.setOffset(p);
				tmp.add((DfsObjectToPack) otp);
