				DiffEntry r;
				try {
					r = findRename(parent, n.sourceCommit, n.sourcePath);
					if (r == null) {
						continue;
					}
				} catch (CanceledException e) {
