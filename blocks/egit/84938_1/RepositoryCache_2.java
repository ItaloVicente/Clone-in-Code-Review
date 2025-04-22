		List<File> toRemove = new ArrayList<>();
		synchronized (repositoryCache) {
			for (Iterator<Map.Entry<File, Reference<Repository>>> i = repositoryCache
					.entrySet().iterator(); i.hasNext();) {
				Map.Entry<File, Reference<Repository>> entry = i.next();
				Repository repository = entry.getValue().get();
				if (repository == null || !repository.getDirectory().exists()) {
					i.remove();
					toRemove.add(entry.getKey());
				}
