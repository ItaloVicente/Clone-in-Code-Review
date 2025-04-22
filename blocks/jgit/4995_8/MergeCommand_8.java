				String mergeMessage = "";
				if (!squash) {
					mergeMessage = new MergeMessageFormatter().format(
							commits
					repo.writeMergeCommitMsg(mergeMessage);
					repo.writeMergeHeads(Arrays.asList(ref.getObjectId()));
				} else {
					List<RevCommit> squashedCommits = RevWalkUtils.find(
							revWalk
					String squashMessage = new SquashMessageFormatter().format(
							squashedCommits
					repo.writeSquashCommitMsg(squashMessage);
				}
