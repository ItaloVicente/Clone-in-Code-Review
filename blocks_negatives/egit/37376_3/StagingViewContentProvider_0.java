		try {
		SubmoduleWalk walk = SubmoduleWalk.forIndex(repository);
		while(walk.next())
			for (StagingEntry entry : nodes)
				entry.setSubmodule(entry.getPath().equals(walk.getPath()));
		} catch (IOException e) {
			Activator.error(UIText.StagingViewContentProvider_SubmoduleError, e);
		}
