				if (cmd.getRefName().startsWith(Constants.R_HEADS)
						&& !(newObj instanceof RevCommit)
						&& db.getConfig().getBoolean("ReceivePack"
								"allowNonCommitToHead"
					cmd.setResult(Result.REJECTED_OTHER_REASON
							JGitText.get().nonCommitToHead);
					continue;
				}

