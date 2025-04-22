
			RevCommit rc = (RevCommit) ro;
			peeledWant.add(rc);
			rw.markStart(rc);

			BitmapBuilder bitmap = commitBitmapIndex.newBitmapBuilder();
			bitmap.or(reuse);
			bitmap.add(rc
			tipCommitBitmaps.add(new BitmapBuilderEntry(rc
