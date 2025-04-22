
		if (property.equals("isRebasing")) //$NON-NLS-1$
			switch (node.getRepository().getRepositoryState()) {
			case REBASING_MERGE:
				return true;
			default:
				return false;
			}
