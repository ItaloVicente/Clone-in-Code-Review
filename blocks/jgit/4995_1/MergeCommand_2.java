					} else {
						msg = JGitText.get().squashCommitNotUpdatingHEAD;
						newHead = headCommit;
					}
					return new MergeResult(newHead.getId()
							new ObjectId[] { headCommit.getId()
									srcCommit.getId() }
							mergeStrategy
