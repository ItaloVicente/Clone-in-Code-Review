						updateRef = repo.updateRef(newRefName);
						ObjectId  startAt = new RevWalk(repo).parseCommit(repo.resolve(refName));

						updateRef.setNewObjectId(startAt);
						updateRef.setRefLogMessage(
								"branch: Created from " + refName, false); //$NON-NLS-1$
						updateRef.update();
