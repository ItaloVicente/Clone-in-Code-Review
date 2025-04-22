			if (ro instanceof RevCommit && !reuse.contains(ro)) {
				RevCommit rc = (RevCommit) ro;
				peeledWant.add(rc);
				rw.markStart(rc);

				BitmapBuilder bitmap = commitBitmapIndex.newBitmapBuilder();
				bitmap.or(reuse);
				bitmap.add(rc, Constants.OBJ_COMMIT);
				tipCommitBitmaps.add(new BitmapBuilderEntry(rc, bitmap));
