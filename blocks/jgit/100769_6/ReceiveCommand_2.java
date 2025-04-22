		switch (type) {
		case CREATE:
			if (!ObjectId.zeroId().equals(oldId)) {
				throw new IllegalArgumentException(
						JGitText.get().createRequiresZeroOldId);
			}
			break;
		case DELETE:
			if (!ObjectId.zeroId().equals(newId)) {
				throw new IllegalArgumentException(
						JGitText.get().deleteRequiresZeroNewId);
			}
			break;
		case UPDATE:
		case UPDATE_NONFASTFORWARD:
			if (ObjectId.zeroId().equals(newId)
					|| ObjectId.zeroId().equals(oldId)) {
				throw new IllegalArgumentException(
						JGitText.get().updateRequiresOldIdAndNewId);
			}
			break;
		default:
			throw new IllegalStateException(JGitText.get().enumValueNotSupported0);
		}
