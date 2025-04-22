				ResolveMerger merger = (ResolveMerger) strategy.newMerger(repo);
				merger.setWorkingTreeIterator(new FileTreeIterator(repo));
				merger.setBase(srcParent.getTree());
				merger.setCommitNames(new String[] { "BASE", ourName, //$NON-NLS-1$
						cherryPickName });
				if (merger.merge(newHead, srcCommit)) {
					if (!merger.getModifiedFiles().isEmpty()) {
