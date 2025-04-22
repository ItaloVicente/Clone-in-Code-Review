			switch (node.getRepository().getRepositoryState()) {
			case REBASING_INTERACTIVE:
				return true;
			case REBASING_REBASING:
				return true;
			default:
				return false;
			}

			switch (node.getRepository().getRepositoryState()) {
			case REBASING_INTERACTIVE:
				return true;
			default:
				return false;
			}

