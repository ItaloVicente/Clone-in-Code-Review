			if (cmd.getType() == ReceiveCommand.Type.DELETE) {
				ObjectId objectId = ref != null ? ref.getObjectId() : null;
				if (objectId != null
						&& !ObjectId.zeroId().equals(cmd.getOldId())
						&& !objectId.equals(cmd.getOldId())) {
					cmd.setResult(Result.REJECTED_OTHER_REASON
							JGitText.get().invalidOldIdSent);
					continue;
				}
