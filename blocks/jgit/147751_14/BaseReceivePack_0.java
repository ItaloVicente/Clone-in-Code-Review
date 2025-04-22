			RevObject newObj = null;
			if (cmd.getType() == ReceiveCommand.Type.CREATE
					|| cmd.getType() == ReceiveCommand.Type.UPDATE) {
				try {
					newObj = walk.parseAny(cmd.getNewId());
				} catch (IOException e) {
					cmd.setResult(Result.REJECTED_MISSING_OBJECT
							cmd.getNewId().name());
					continue;
				}
				if (cmd.getRefName().startsWith(Constants.R_HEADS)
						&& !(newObj instanceof RevCommit)) {
					cmd.setResult(Result.REJECTED_OTHER_REASON
							JGitText.get().nonCommitToHeads);
					continue;
				}
			}

