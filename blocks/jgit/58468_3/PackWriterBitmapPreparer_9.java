			for (BitmapBuilderEntry entry : tipCommitBitmaps) {
				BitmapBuilder bitmap = entry.getBuilder();
				if (bitmap.contains(rc)) {
					for (RevCommit c : rc.getParents()) {
						bitmap.add(c
					}
