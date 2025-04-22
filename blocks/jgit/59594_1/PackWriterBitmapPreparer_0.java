
			RevCommit rc = (RevCommit) ro;
			reuseCommits.add(new BitmapCommit(rc
			rw.markUninteresting(rc);
			bitmapRemapper.ofObjectType(bitmapRemapper.getBitmap(rc)
					Constants.OBJ_COMMIT).trim();
			reuse.add(rc
