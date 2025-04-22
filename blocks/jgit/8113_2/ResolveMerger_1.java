

	protected boolean mergeTrees(AnyObjectId baseTree
			RevTree mergeTree) throws IOException {
		try {
			builder = dircache.builder();
			DirCacheBuildIterator buildIt = new DirCacheBuildIterator(builder);

			tw = new NameConflictTreeWalk(db);
			tw.addTree(baseTree);
			tw.addTree(headTree);
			tw.addTree(mergeTree);
			tw.addTree(buildIt);
			if (workingTreeIterator != null)
				tw.addTree(workingTreeIterator);

			while (tw.next()) {
				if (!processEntry(
						tw.getTree(T_BASE
						tw.getTree(T_OURS
						tw.getTree(T_THEIRS
						tw.getTree(T_INDEX
						(workingTreeIterator == null) ? null : tw.getTree(T_FILE
					cleanUp();
					return false;
				}
				if (tw.isSubtree() && enterSubtree)
					tw.enterSubtree();
			}

			if (!inCore) {
				checkout();

				if (!builder.commit()) {
					cleanUp();
					throw new IndexWriteException();
				}
				builder = null;

			} else {
				builder.finish();
				builder = null;
			}

			if (getUnmergedPaths().isEmpty() && !failed()) {
				resultTree = dircache.writeTree(getObjectInserter());
				return true;
			} else {
				resultTree = null;
				return false;
			}
		} finally {
		}
	}
