
				RevObject newObj = walk.parseAny(cmd.getNewId());

				if (cmd.getRefName().startsWith(Constants.R_HEADS)
						&& !(newObj instanceof RevCommit)) {
					cmd.setResult(Result.REJECTED_OTHER_REASON
							JGitText.get().nonCommitToHeads);
					continue;
				}
