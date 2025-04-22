		BitmapBuilder reuseBitmap = commitBitmapIndex.newBitmapBuilder();
		List<BitmapCommit> reuse = new ArrayList<BitmapCommit>();
		for (PackBitmapIndexRemapper.Entry entry : bitmapRemapper) {
			if ((entry.getFlags() & FLAG_REUSE) != FLAG_REUSE)
				continue;

			RevObject ro = rw.peel(rw.parseAny(entry));
			if (ro instanceof RevCommit) {
				RevCommit rc = (RevCommit) ro;
				reuse.add(new BitmapCommit(rc
				rw.markUninteresting(rc);

				EWAHCompressedBitmap bitmap = bitmapRemapper.ofObjectType(
						bitmapRemapper.getBitmap(rc)
				writeBitmaps.addBitmap(rc
				reuseBitmap.add(rc
			}
		}

