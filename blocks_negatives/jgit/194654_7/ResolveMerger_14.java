			try {
				result = contentMerge(base, ours, theirs, attributes,
						getContentMergeStrategy());
			} catch (BinaryBlobException e) {
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
					result = new MergeResult<>(Collections.emptyList());
					result.setContainsConflicts(true);
					break;
				}
			}
			if (ignoreConflicts) {
				result.setContainsConflicts(false);
			}
			updateIndex(base, ours, theirs, result, attributes[T_OURS]);
			String currentPath = tw.getPathString();
			if (result.containsConflicts() && !ignoreConflicts) {
				unmergedPaths.add(currentPath);
			}
			modifiedFiles.add(currentPath);
			addCheckoutMetadata(cleanupMetadata, currentPath,
					attributes[T_OURS]);
			addCheckoutMetadata(checkoutMetadata, currentPath,
					attributes[T_THEIRS]);
		} else if (modeO != modeT) {
			if (((modeO != 0 && !tw.idEqual(T_BASE, T_OURS)) || (modeT != 0 && !tw
					.idEqual(T_BASE, T_THEIRS)))) {
				if (gitLinkMerging && ignoreConflicts) {
					add(tw.getRawPath(), ours, DirCacheEntry.STAGE_0, EPOCH, 0);
				} else if (gitLinkMerging) {
					add(tw.getRawPath(), base, DirCacheEntry.STAGE_1, EPOCH, 0);
					add(tw.getRawPath(), ours, DirCacheEntry.STAGE_2, EPOCH, 0);
					add(tw.getRawPath(), theirs, DirCacheEntry.STAGE_3, EPOCH, 0);
					MergeResult<SubmoduleConflict> result = createGitLinksMergeResult(
							base, ours, theirs);
					result.setContainsConflicts(true);
					mergeResults.put(tw.getPathString(), result);
					unmergedPaths.add(tw.getPathString());
				} else {
					MergeResult<RawText> result;
					try {
						result = contentMerge(base, ours, theirs, attributes,
								ContentMergeStrategy.CONFLICT);
					} catch (BinaryBlobException e) {
						result = new MergeResult<>(Collections.emptyList());
						result.setContainsConflicts(true);
					}
					if (ignoreConflicts) {
						result.setContainsConflicts(false);
						updateIndex(base, ours, theirs, result,
								attributes[T_OURS]);
					} else {
						add(tw.getRawPath(), base, DirCacheEntry.STAGE_1, EPOCH,
								0);
						add(tw.getRawPath(), ours, DirCacheEntry.STAGE_2, EPOCH,
								0);
						DirCacheEntry e = add(tw.getRawPath(), theirs,
								DirCacheEntry.STAGE_3, EPOCH, 0);

						if (modeO == 0) {
							if (isWorktreeDirty(work, ourDce)) {
								return false;
							}
							if (nonTree(modeT) && e != null) {
								addToCheckout(tw.getPathString(), e,
										attributes);
							}
						}

						unmergedPaths.add(tw.getPathString());

						mergeResults.put(tw.getPathString(), result);
					}
				}
			}
		}
		return true;
	}

	private static MergeResult<SubmoduleConflict> createGitLinksMergeResult(
			CanonicalTreeParser base, CanonicalTreeParser ours,
			CanonicalTreeParser theirs) {
		return new MergeResult<>(Arrays.asList(
				new SubmoduleConflict(
						base == null ? null : base.getEntryObjectId()),
				new SubmoduleConflict(
						ours == null ? null : ours.getEntryObjectId()),
				new SubmoduleConflict(
						theirs == null ? null : theirs.getEntryObjectId())));
	}

	/**
