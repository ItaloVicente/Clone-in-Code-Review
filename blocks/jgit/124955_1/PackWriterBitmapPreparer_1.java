			pm.beginTask(JGitText.get().selectingCommits
			int totalWants = want.size();
			BitmapBuilder seen = commitBitmapIndex.newBitmapBuilder();
			seen.or(selectionHelper.reusedCommitsBitmap);
			rw2.setRetainBody(false);
			rw2.setRevFilter(new NotInBitmapFilter(seen));

			for (RevCommit rc : selectionHelper.newWantsByNewest) {
				BitmapBuilder tipBitmap = commitBitmapIndex.newBitmapBuilder();
				rw2.markStart((RevCommit) rw2.peel(rw2.parseAny(rc)));
				RevCommit rc2;
				while ((rc2 = rw2.next()) != null) {
					tipBitmap.addObject(rc2
