				if (cmd.getRefName().startsWith(Constants.R_HEADS)
						&& !(newObj instanceof RevCommit)
						&& !isAllowNonCommitToHead()) {
					cmd.setResult(Result.REJECTED_OTHER_REASON
							JGitText.get().nonCommitToHead);
					continue;
				}

