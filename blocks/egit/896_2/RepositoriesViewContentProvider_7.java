		RepositoryTreeNode node = (RepositoryTreeNode) element;
		Repository repo = node.getRepository();
		switch (node.getType()) {
		case BRANCHES:
			return true;
		case REPO:
			return true;
		case SYMBOLICREFS:
			try {
				for (Ref refEntry : repo.getRefDatabase().getRefs(
						RefDatabase.ALL).values()) {
					if (refEntry.isSymbolic())
						return true;
				}
			} catch (IOException e) {
				return true;
			}
			return false;
		case TAGS:
			try {
				return !repo.getRefDatabase().getRefs(Constants.R_TAGS)
						.isEmpty();
			} catch (IOException e) {
				return true;
			}
		case WORKINGDIR:
			if (node.getRepository().isBare())
				return false;
			File workingDir = repo.getWorkDir();
			if (workingDir == null || !workingDir.exists())
				return false;
			return workingDir.listFiles().length > 0;
		default:
			Object[] children = getChildren(element);
			return children != null && children.length > 0;
		}
