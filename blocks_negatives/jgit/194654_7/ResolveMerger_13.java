	 * @return <code>false</code> if the merge will fail because the index entry
	 *         didn't match ours or the working-dir file was dirty and a
	 *         conflict occurred
	 * @throws org.eclipse.jgit.errors.MissingObjectException
	 * @throws org.eclipse.jgit.errors.IncorrectObjectTypeException
	 * @throws org.eclipse.jgit.errors.CorruptObjectException
	 * @throws java.io.IOException
	 * @since 6.1
	 */
	protected boolean processEntry(CanonicalTreeParser base,
			CanonicalTreeParser ours, CanonicalTreeParser theirs,
			DirCacheBuildIterator index, WorkingTreeIterator work,
			boolean ignoreConflicts, Attributes[] attributes)
			throws MissingObjectException, IncorrectObjectTypeException,
			CorruptObjectException, IOException {
		enterSubtree = true;
		final int modeO = tw.getRawMode(T_OURS);
		final int modeT = tw.getRawMode(T_THEIRS);
		final int modeB = tw.getRawMode(T_BASE);
		boolean gitLinkMerging = isGitLink(modeO) || isGitLink(modeT)
				|| isGitLink(modeB);
		if (modeO == 0 && modeT == 0 && modeB == 0)
			return true;

		if (isIndexDirty())
			return false;

		DirCacheEntry ourDce = null;

		if (index == null || index.getDirCacheEntry() == null) {
			if (nonTree(modeO)) {
				ourDce = new DirCacheEntry(tw.getRawPath());
				ourDce.setObjectId(tw.getObjectId(T_OURS));
				ourDce.setFileMode(tw.getFileMode(T_OURS));
			}
		} else {
			ourDce = index.getDirCacheEntry();
		}

		if (nonTree(modeO) && nonTree(modeT) && tw.idEqual(T_OURS, T_THEIRS)) {
			if (modeO == modeT) {
				keep(ourDce);
				return true;
			}
			int newMode = mergeFileModes(modeB, modeO, modeT);
			if (newMode != FileMode.MISSING.getBits()) {
				if (newMode == modeO) {
					keep(ourDce);
				} else {
					if (isWorktreeDirty(work, ourDce)) {
						return false;
					}
					DirCacheEntry e = add(tw.getRawPath(), theirs,
							DirCacheEntry.STAGE_0, EPOCH, 0);
					addToCheckout(tw.getPathString(), e, attributes);
				}
				return true;
			}
			if (!ignoreConflicts) {
				add(tw.getRawPath(), base, DirCacheEntry.STAGE_1, EPOCH, 0);
				add(tw.getRawPath(), ours, DirCacheEntry.STAGE_2, EPOCH, 0);
				add(tw.getRawPath(), theirs, DirCacheEntry.STAGE_3, EPOCH, 0);
				unmergedPaths.add(tw.getPathString());
				mergeResults.put(tw.getPathString(),
						new MergeResult<>(Collections.emptyList()));
			}
			return true;
		}

		if (modeB == modeT && tw.idEqual(T_BASE, T_THEIRS)) {
			if (ourDce != null)
				keep(ourDce);
			return true;
		}

		if (modeB == modeO && tw.idEqual(T_BASE, T_OURS)) {

			if (isWorktreeDirty(work, ourDce))
				return false;
			if (nonTree(modeT)) {
				DirCacheEntry e = add(tw.getRawPath(), theirs,
						DirCacheEntry.STAGE_0, EPOCH, 0);
				if (e != null) {
					addToCheckout(tw.getPathString(), e, attributes);
				}
				return true;
			}
			if (tw.getTreeCount() > T_FILE && tw.getRawMode(T_FILE) == 0) {
				return true;
			}
			if (modeT != 0 && modeT == modeB) {
				return true;
			}
			addDeletion(tw.getPathString(), nonTree(modeO), attributes[T_OURS]);
			return true;
		}

		if (tw.isSubtree()) {
			if (nonTree(modeO) != nonTree(modeT)) {
				if (ignoreConflicts) {
					enterSubtree = false;
					return true;
				}
				if (nonTree(modeB))
					add(tw.getRawPath(), base, DirCacheEntry.STAGE_1, EPOCH, 0);
				if (nonTree(modeO))
					add(tw.getRawPath(), ours, DirCacheEntry.STAGE_2, EPOCH, 0);
				if (nonTree(modeT))
					add(tw.getRawPath(), theirs, DirCacheEntry.STAGE_3, EPOCH, 0);
				unmergedPaths.add(tw.getPathString());
				enterSubtree = false;
				return true;
			}

			if (!nonTree(modeO))
				return true;

		}

		if (nonTree(modeO) && nonTree(modeT)) {
			boolean worktreeDirty = isWorktreeDirty(work, ourDce);
			if (!attributes[T_OURS].canBeContentMerged() && worktreeDirty) {
				return false;
			}

			if (gitLinkMerging && ignoreConflicts) {
				add(tw.getRawPath(), ours, DirCacheEntry.STAGE_0, EPOCH, 0);
				return true;
			} else if (gitLinkMerging) {
				add(tw.getRawPath(), base, DirCacheEntry.STAGE_1, EPOCH, 0);
				add(tw.getRawPath(), ours, DirCacheEntry.STAGE_2, EPOCH, 0);
				add(tw.getRawPath(), theirs, DirCacheEntry.STAGE_3, EPOCH, 0);
				MergeResult<SubmoduleConflict> result = createGitLinksMergeResult(
						base, ours, theirs);
				result.setContainsConflicts(true);
				mergeResults.put(tw.getPathString(), result);
				unmergedPaths.add(tw.getPathString());
				return true;
			} else if (!attributes[T_OURS].canBeContentMerged()) {
				switch (getContentMergeStrategy()) {
				case OURS:
					keep(ourDce);
					return true;
				case THEIRS:
					DirCacheEntry theirEntry = add(tw.getRawPath(), theirs,
							DirCacheEntry.STAGE_0, EPOCH, 0);
					addToCheckout(tw.getPathString(), theirEntry, attributes);
					return true;
				default:
					break;
				}
				add(tw.getRawPath(), base, DirCacheEntry.STAGE_1, EPOCH, 0);
				add(tw.getRawPath(), ours, DirCacheEntry.STAGE_2, EPOCH, 0);
				add(tw.getRawPath(), theirs, DirCacheEntry.STAGE_3, EPOCH, 0);

				unmergedPaths.add(tw.getPathString());
				return true;
			}

			if (worktreeDirty) {
				return false;
			}
