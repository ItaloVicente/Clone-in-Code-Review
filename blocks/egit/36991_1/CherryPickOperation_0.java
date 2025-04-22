				if (commits.size() == 1) {
					RevCommit commit = commits.get(0);
					CherryPickCommand command = new Git(repo).cherryPick()
							.include(commit.getId());
					try {
						CherryPickResult cbresult = command.call();
						switch (cbresult.getStatus()) {
						case CONFLICTING:
							result = RebaseResult.conflicts(Collections
									.<String> emptyList());
							break;
						case FAILED:
							result = RebaseResult.failed(cbresult
									.getFailingPaths());
							break;
						case OK:
							result = RebaseResult.result(Status.OK,
									cbresult.getNewHead());
							break;
