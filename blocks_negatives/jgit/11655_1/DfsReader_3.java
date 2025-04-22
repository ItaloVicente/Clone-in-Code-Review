		if (packList.length == 0) {
			Iterator<ObjectToPack> itr = objects.iterator();
			if (itr.hasNext())
				throw new MissingObjectException(itr.next(), "unknown");
			return;
		}

		int objectCount = 0;
		int updated = 0;
		int posted = 0;
		List<DfsObjectRepresentation> all = new BlockList<DfsObjectRepresentation>();
		for (ObjectToPack otp : objects) {
			boolean found = false;
			for (int packIndex = 0; packIndex < packList.length; packIndex++) {
				DfsPackFile pack = packList[packIndex];
				long p = pack.findOffset(this, otp);
				if (0 < p) {
					DfsObjectRepresentation r = new DfsObjectRepresentation(otp);
					r.pack = pack;
					r.packIndex = packIndex;
					r.offset = p;
					all.add(r);
					found = true;
