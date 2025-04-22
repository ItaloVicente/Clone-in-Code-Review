		final ArrayList<String> paths;
		if (in != null) {
			paths = new ArrayList<String>(in.length);
			for (final IResource r : in) {
				final RepositoryMapping map = RepositoryMapping.getMapping(r);
				if (map == null)
					continue;
