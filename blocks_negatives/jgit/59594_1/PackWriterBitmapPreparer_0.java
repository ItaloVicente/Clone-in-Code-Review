			if (ro instanceof RevCommit) {
				RevCommit rc = (RevCommit) ro;
				reuseCommits.add(new BitmapCommit(rc, false, entry.getFlags()));
				rw.markUninteresting(rc);
				bitmapRemapper.ofObjectType(bitmapRemapper.getBitmap(rc),
						Constants.OBJ_COMMIT).trim();
				reuse.add(rc, Constants.OBJ_COMMIT);
