			if (cmd.getType() == ReceiveCommand.Type.DELETE && ref != null
					&& !ObjectId.zeroId().equals(cmd.getOldId())
					&& !ref.getObjectId().equals(cmd.getOldId())) {
				cmd.setResult(Result.REJECTED_OTHER_REASON,
						JGitText.get().invalidOldIdSent);
				continue;
