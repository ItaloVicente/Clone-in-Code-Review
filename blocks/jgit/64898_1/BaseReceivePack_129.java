			if (cmd.getType() == ReceiveCommand.Type.DELETE && ref != null) {
				ObjectId id = ref.getObjectId();
				if (id == null) {
					id = ObjectId.zeroId();
				}
				if (!ObjectId.zeroId().equals(cmd.getOldId())
						&& !id.equals(cmd.getOldId())) {
					cmd.setResult(Result.REJECTED_OTHER_REASON
							JGitText.get().invalidOldIdSent);
					continue;
				}
