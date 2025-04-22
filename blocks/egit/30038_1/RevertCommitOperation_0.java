						CoreText.RevertCommitOperation_reverting,
						Integer.valueOf(commits.size())));
				RevertCommand command = new Git(repo).revert();
				for (RevCommit commit : commits) {
					command.include(commit);
				}
