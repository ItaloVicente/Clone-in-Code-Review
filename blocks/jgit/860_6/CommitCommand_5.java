					RevCommit revCommit = new RevWalk(repo)
							.parseCommit(commitId);
					RefUpdate ru = repo.updateRef(Constants.HEAD);
					ru.setNewObjectId(commitId);
					ru.setRefLogMessage("commit : "
							+ revCommit.getShortMessage()
