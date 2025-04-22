		if (property.equals("canCommit")) { //$NON-NLS-1$
			Repository rep = node.getRepository();
			return rep.getRepositoryState().canCommit();
		}
		if (property.equals("canAmend")) { //$NON-NLS-1$
			Repository rep = node.getRepository();
			return rep.getRepositoryState().canAmend();
		}
		if (property.equals("canStash")) { //$NON-NLS-1$
			Repository rep = node.getRepository();
			return rep.getRepositoryState().canCommit();
		}
