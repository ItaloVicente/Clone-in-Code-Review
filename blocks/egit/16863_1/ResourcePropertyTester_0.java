			RepositoryState state = repository.getRepositoryState();

			if ("canAbortRebase".equals(property)) //$NON-NLS-1$
				switch (state) {
				case REBASING_INTERACTIVE:
					return true;
				case REBASING_REBASING:
					return true;
				default:
					return false;
				}

			if ("canContinueRebase".equals(property)) //$NON-NLS-1$
				switch (state) {
				case REBASING_INTERACTIVE:
					return true;
				default:
					return false;
				}

