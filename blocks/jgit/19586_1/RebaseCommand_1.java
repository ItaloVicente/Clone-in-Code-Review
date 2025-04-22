				res = refUpdate.link(headName);
			} else {
				refUpdate.setNewObjectId(repo.readOrigHead());
				res = refUpdate.forceUpdate();

			}
			switch (res) {
			case FAST_FORWARD:
			case FORCED:
			case NO_CHANGE:
				break;
			default:
				throw new JGitInternalException(
						JGitText.get().abortingRebaseFailed);
