		for (final Iterator<Map.Entry<File, Reference<Repository>>> i = repositoryCache
				.entrySet()
				.iterator(); i.hasNext();) {
			Map.Entry<File, Reference<Repository>> entry = i.next();
			Repository repository = entry.getValue().get();
			if (repository == null || !repository.getDirectory().exists()) {
				i.remove();
				Activator.getDefault().getIndexDiffCache()
						.remove(entry.getKey());
