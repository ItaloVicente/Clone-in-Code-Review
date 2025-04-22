		compareBetween(repository, gitPath, null, leftRev, rightRev, page);
	}

	private static class DeleteView extends DiffEntry {

		private static final AbbreviatedObjectId A_ZERO = AbbreviatedObjectId
				.fromObjectId(ObjectId.zeroId());

		public DeleteView(DiffEntry modify) {
			this.changeType = ChangeType.DELETE;
			this.diffAttribute = modify.getDiffAttribute();
			this.newId = A_ZERO;
			this.newPath = DiffEntry.DEV_NULL;
			this.newMode = FileMode.MISSING;
			this.oldId = modify.getOldId();
			this.oldPath = modify.getOldPath();
			this.oldMode = modify.getOldMode();
		}
	}

	private static String findRename(Repository repository, String newRev,
			String oldRev, String gitPath, IProgressMonitor progress) {
		try {
			AbstractTreeIterator newTree = null;
			RevCommit newCommit = null;
			DirCache index = null;
			if (GitFileRevision.INDEX.equals(newRev)) {
				index = repository.readDirCache();
				newTree = new DirCacheIterator(index);
			} else {
				ObjectId newOid = repository.resolve(newRev);
				if (newOid == null) {
					return gitPath;
				}
				newCommit = repository.parseCommit(newOid);
			}
			AbstractTreeIterator oldTree = null;
			RevCommit oldCommit = null;
			if (GitFileRevision.INDEX.equals(oldRev)) {
				if (index == null) {
					index = repository.readDirCache();
				}
				oldTree = new DirCacheIterator(index);
			} else {
				ObjectId oldId = repository.resolve(oldRev);
				if (oldId == null) {
					return gitPath;
				}
				oldCommit = repository.parseCommit(oldId);
			}
			if ((GitFileRevision.INDEX.equals(newRev)
					&& Constants.HEAD.equals(oldRev))
					|| isParent(oldCommit, newCommit)) {
				try (TreeWalk walk = new TreeWalk(repository)) {
					walk.setFilter(PathFilterGroup.createFromStrings(gitPath));
					walk.setRecursive(true);
					if (oldCommit != null) {
						walk.addTree(oldCommit.getTree());
					} else {
						walk.addTree(oldTree);
					}
					if (walk.next()) {
						return gitPath;
					}
					walk.reset();
					walk.setFilter(TreeFilter.ANY_DIFF);
					if (oldCommit != null) {
						walk.addTree(oldCommit.getTree());
					} else {
						walk.addTree(oldTree);
					}
					if (newCommit != null) {
						walk.addTree(newCommit.getTree());
					} else {
						walk.addTree(newTree);
					}
					walk.setRecursive(true);
					List<DiffEntry> diffs = DiffEntry.scan(walk);
					RenameDetector renameDetector = new RenameDetector(
							walk.getObjectReader(),
							repository.getConfig().get(DiffConfig.KEY));
					List<DiffEntry> filtered = diffs.stream()
							.map(d -> {
								switch (d.getChangeType()) {
								case ADD:
									if (d.getNewPath().equals(gitPath)) {
										return d;
									}
									break;
								case DELETE:
									return d;
								case MODIFY:
									return new DeleteView(d);
								default:
									break;
								}
								return null;
							}).filter(Objects::nonNull)
							.collect(Collectors.toList());
					renameDetector.reset();
					renameDetector.addAll(filtered);
					EclipseGitProgressTransformer monitor = new EclipseGitProgressTransformer(
							progress);
					List<DiffEntry> renamed = renameDetector.compute(monitor);
					for (DiffEntry ent : renamed) {
						if (isRename(ent)
								&& ent.getNewPath().equals(gitPath)) {
							return ent.getOldPath();
						}
					}
				}
			}
		} catch (CanceledException e) {
		} catch (IOException e) {
			Activator.logError(
					NLS.bind(UIText.CompareUtils_errorRenameDetection, gitPath),
					e);
		}
		return gitPath;
	}

	private static boolean isParent(RevCommit old, RevCommit newCommit) {
		if (old == null || newCommit == null) {
			return false;
		}
		return Stream.of(newCommit.getParents()).anyMatch(p -> p.equals(old));
	}

	private static boolean isRename(DiffEntry ent) {
		return ChangeType.RENAME.equals(ent.getChangeType())
				|| ChangeType.COPY.equals(ent.getChangeType());
