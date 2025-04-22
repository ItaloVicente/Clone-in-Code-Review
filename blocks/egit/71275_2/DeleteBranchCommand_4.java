					monitor.beginTask(
							UIText.DeleteBranchCommand_DeletingBranchesProgress,
							refs.size());
					for (Entry<Ref, Repository> entry : refs.entrySet()) {
						Repository repository = entry.getValue();
						Ref ref = entry.getKey();
						int result = deleteBranch(repository, ref,
								forceDeletionOfUnmergedBranches);
