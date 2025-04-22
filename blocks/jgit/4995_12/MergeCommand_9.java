						mergeStatus = MergeStatus.MERGED;
					} else {
						msg = JGitText.get().squashCommitNotUpdatingHEAD;
						newHead = headCommit;
						mergeStatus = MergeStatus.MERGED_SQUASHED;
					}
					return new MergeResult(newHead.getId()
							new ObjectId[] { headCommit.getId()
									srcCommit.getId() }
							mergeStrategy
