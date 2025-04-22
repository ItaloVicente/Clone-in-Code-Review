			if (commit != null) {
				RevCommit[] parents = commit.getParents();
				if (parents.length > 1)
					throw new IllegalStateException(
							"Cannot create patch for merge commit"); //$NON-NLS-1$

				if (parents.length == 0)
					throw new IllegalStateException(
							"Cannot create patch for first commit"); //$NON-NLS-1$

				List<DiffEntry> diffs = diffFmt.scan(parents[0].getId(),commit.getId());
				for (DiffEntry ent : diffs) {
					String path;
					if (ChangeType.DELETE.equals(ent.getChangeType()))
						path = ent.getOldPath();
					else
						path = ent.getNewPath();
					currentEncoding = CompareCoreUtils.getResourceEncoding(repository, path);
					diffFmt.format(ent);
				}
			} else {
				diffFmt.format(
						new DirCacheIterator(repository.readDirCache()),
						new FileTreeIterator(repository));
