			tw = new NameConflictTreeWalk(db);
			tw.addTree(mergeBase());
			tw.addTree(sourceTrees[0]);
			tw.addTree(sourceTrees[1]);
			tw.addTree(buildIt);
			if (workingTreeIterator != null)
				tw.addTree(workingTreeIterator);

			while (tw.next()) {
				if (!processEntry(
						tw.getTree(T_BASE, CanonicalTreeParser.class),
						tw.getTree(T_OURS, CanonicalTreeParser.class),
						tw.getTree(T_THEIRS, CanonicalTreeParser.class),
						tw.getTree(T_INDEX, DirCacheBuildIterator.class),
						(workingTreeIterator == null) ? null : tw.getTree(T_FILE, WorkingTreeIterator.class))) {
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
			if (implicitDirCache)
				dircache.unlock();
