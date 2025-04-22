		final int nTree = walk.getTreeCount();
		final int myTree = nTree - 1;

		switch (nTree) {
		case 1:
			while (walk.next()) {
				final FileDiff d = new FileDiff(commit, walk.getPathString());
				d.blobs = new ObjectId[] { walk.getObjectId(0) };
				d.modes = new FileMode[] { walk.getFileMode(0) };
				r.add(d);
			}
			break;
		case 2:
			while (walk.next()) {
				final FileDiff d = new FileDiff(commit, walk.getPathString());
				final ObjectId id0 = walk.getObjectId(0);
				final ObjectId id1 = walk.getObjectId(1);
				final FileMode fm0 = walk.getFileMode(0);
				final FileMode fm1 = walk.getFileMode(1);
				d.blobs = new ObjectId[] { id0, id1 };
				d.modes = new FileMode[] { fm0, fm1 };

				final int m0 = walk.getRawMode(0);
				final int m1 = walk.getRawMode(1);
				if (m0 == 0 && m1 != 0)
				else if (m0 != 0 && m1 == 0)
				else if (m0 != m1 && walk.idEqual(0, 1))
				r.add(d);
			}
			break;
		default:
			while (walk.next()) {
				if (matchAnyParent(walk, myTree))
					continue;

				final FileDiff d = new FileDiff(commit, walk.getPathString());
				int m0 = 0;
				for (int i = 0; i < myTree; i++)
					m0 |= walk.getRawMode(i);
				final int m1 = walk.getRawMode(myTree);
				if (m0 == 0 && m1 != 0)
				else if (m0 != 0 && m1 == 0)
				else if (m0 != m1 && walk.idEqual(0, myTree))
				d.blobs = new ObjectId[nTree];
				d.modes = new FileMode[nTree];
				for (int i = 0; i < nTree; i++) {
					d.blobs[i] = walk.getObjectId(i);
					d.modes[i] = walk.getFileMode(i);
				}
				r.add(d);
			}
			break;
