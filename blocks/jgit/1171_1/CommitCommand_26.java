					RevWalk revWalk = new RevWalk(repo);
					try {
						RevCommit revCommit = revWalk.parseCommit(commitId);
						RefUpdate ru = repo.updateRef(Constants.HEAD);
						ru.setNewObjectId(commitId);
						ru.setRefLogMessage("commit : "
								+ revCommit.getShortMessage()
