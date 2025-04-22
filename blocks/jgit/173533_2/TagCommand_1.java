		case NO_CHANGE:
			if (forceUpdate) {
				return repo.exactRef(refName);
			}
			throw new RefAlreadyExistsException(MessageFormat
					.format(JGitText.get().tagAlreadyExists
