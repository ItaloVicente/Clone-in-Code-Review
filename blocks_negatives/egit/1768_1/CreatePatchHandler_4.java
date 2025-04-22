			Object input = getInput(event);
			if (!(input instanceof IResource))
				return null;
			RepositoryMapping mapping = RepositoryMapping
					.getMapping((IResource) getInput(event));

			TreeWalk fileWalker = new TreeWalk(mapping.getRepository());
