					} else {
						msg = JGitText.get().squashCommitNotUpdatingHEAD;
						newHead = headCommit;
						status.add(MergeStatus.SQUASHED);
					}
					return new MergeResult(newHead.getId()
							new ObjectId[] { headCommit.getId()
									srcCommit.getId() }
							null
