			FileDiff[] diffs = FileDiff.compute(walker, commit);

			for (FileDiff diff : diffs) {
				if (diff.getBlobs().length == 2) {
					String path = diff.getPath();
					currentEncoding = CompareUtils
							.getResourceEncoding(db, path);
					diff.outputDiff(d, db, diffFmt, true);
