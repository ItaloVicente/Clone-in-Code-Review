			switch (node.getRepository().getRepositoryState()) {
			case REBASING_INTERACTIVE:
				return true;
			case REBASING_REBASING:
				return true;
			default:
				return false;
			}

		if (property.equals("canContinueRebase")) //$NON-NLS-1$
