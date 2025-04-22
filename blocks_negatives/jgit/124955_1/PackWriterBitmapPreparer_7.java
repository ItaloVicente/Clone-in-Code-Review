			for (BitmapBuilderEntry entry : tipCommitBitmaps) {
				BitmapBuilder bitmap = entry.getBuilder();
				if (!bitmap.contains(rc)) {
					continue;
				}
				for (RevCommit c : rc.getParents()) {
					if (reuse.contains(c)) {
						continue;
					}
					bitmap.addObject(c, Constants.OBJ_COMMIT);
				}
			}
			pm.update(1);
