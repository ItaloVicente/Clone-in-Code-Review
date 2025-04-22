
				RevObject newObj;
				try {
					newObj = walk.parseAny(cmd.getNewId());
				} catch (IOException e) {
					cmd.setResult(Result.REJECTED_MISSING_OBJECT
							cmd.getNewId().name());
					continue;
				}

				if (cmd.getRefName().startsWith(Constants.R_HEADS)
						&& !(newObj instanceof RevCommit)
						&& db.getConfig().getBoolean("ReceivePack"
								"allowNonCommitToHead"
					cmd.setResult(Result.REJECTED_OTHER_REASON
							JGitText.get().nonCommitToHead);
					continue;
				}
