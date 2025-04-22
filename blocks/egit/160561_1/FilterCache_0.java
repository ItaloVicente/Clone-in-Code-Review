		Map<RepositoryTreeNodeType, String> map = cache.computeIfAbsent(
				node.getRepository().getDirectory(),
				k -> new ConcurrentHashMap<>());
		if (filter == null) {
			map.remove(node.getType());
		} else {
			map.put(node.getType(), filter);
		}
