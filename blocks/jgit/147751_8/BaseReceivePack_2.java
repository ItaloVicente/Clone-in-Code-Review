				if (cmd.getRefName().startsWith(Constants.R_HEADS)
						&& !(newObj instanceof RevCommit)
						&& !isAllowNonCommitToHeads()) {
					cmd.setResult(Result.REJECTED_OTHER_REASON
							JGitText.get().nonCommitToHeads);
					continue;
				}

