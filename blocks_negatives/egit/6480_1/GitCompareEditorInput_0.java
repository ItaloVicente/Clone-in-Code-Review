					monitor.setTaskName(baseVersionIterator
							.getEntryPathString());
					GitFileRevision baseRev = GitFileRevision.inCommit(
							repository, baseCommit, baseVersionIterator
									.getEntryPathString(), tw
									.getObjectId(baseTreeIndex));
					GitFileRevision compareRev;
