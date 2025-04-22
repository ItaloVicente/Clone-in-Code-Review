			boolean found = false;
			for (int packIndex = 0; packIndex < packList.length; packIndex++) {
				DfsPackFile pack = packList[packIndex];
				long p = pack.findOffset(this
				if (0 < p) {
					DfsObjectRepresentation r = new DfsObjectRepresentation(otp);
					r.pack = pack;
					r.packIndex = packIndex;
					r.offset = p;
					all.add(r);
					found = true;
