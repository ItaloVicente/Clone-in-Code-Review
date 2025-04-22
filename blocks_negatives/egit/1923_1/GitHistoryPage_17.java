		if (o instanceof IResource) {
			RepositoryMapping mapping = RepositoryMapping
					.getMapping((IResource) o);
			if (mapping != null) {
				Repository repo = mapping.getRepository();
				input = new HistoryPageInput(repo,
						new IResource[] { (IResource) o });
