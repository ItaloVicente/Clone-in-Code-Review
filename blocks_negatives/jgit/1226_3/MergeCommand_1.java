					return new MergeResult(
							headCommit,
							null,
							new ObjectId[] { srcCommit, headCommit },
							MergeResult.MergeStatus.NOT_SUPPORTED,
							mergeStrategy,
							JGitText.get().onlyAlreadyUpToDateAndFastForwardMergesAreAvailable);
