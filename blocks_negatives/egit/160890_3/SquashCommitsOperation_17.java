					private boolean isRelevant(AbbreviatedObjectId id) {
						for (RevCommit commit : commits) {
							if (id.prefixCompare(commit) == 0)
								return true;
						}
						return false;
					}

					@Override
					public String modifyCommitMessage(String oldMessage) {
						return messageHandler.modifyCommitMessage(oldMessage);
