		if (walk.getTreeCount() <= 2) {
			List<DiffEntry> entries = DiffEntry.scan(walk);
			for (DiffEntry entry : entries) {
				final FileDiff d = new FileDiff(commit, entry);
				r.add(d);
			}
		}
		else { // DiffEntry does not support walks with more than two trees
			final int nTree = walk.getTreeCount();
			final int myTree = nTree - 1;
			while (walk.next()) {
				if (matchAnyParent(walk, myTree))
					continue;

				final FileDiffForMerges d = new FileDiffForMerges(commit);
				d.path = walk.getPathString();
				int m0 = 0;
				for (int i = 0; i < myTree; i++)
					m0 |= walk.getRawMode(i);
				final int m1 = walk.getRawMode(myTree);
				d.change = ChangeType.MODIFY;
				if (m0 == 0 && m1 != 0)
					d.change = ChangeType.ADD;
				else if (m0 != 0 && m1 == 0)
					d.change = ChangeType.DELETE;
				else if (m0 != m1 && walk.idEqual(0, myTree))
					d.change = ChangeType.MODIFY; // there is no ChangeType.TypeChanged
				d.blobs = new ObjectId[nTree];
				d.modes = new FileMode[nTree];
				for (int i = 0; i < nTree; i++) {
					d.blobs[i] = walk.getObjectId(i);
					d.modes[i] = walk.getFileMode(i);
				}
				r.add(d);
			}

