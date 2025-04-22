					.getMapping(project);
			Repository repository = repositoryMapping.getRepository();
			Tree projTree = treeMap.get(repository);
			if (projTree == null) {
				projTree = repository.mapTree(Constants.HEAD);
				if (projTree == null)
					projTree = new Tree(repository);
				treeMap.put(repository, projTree);
				if (GitTraceLocation.CORE.isActive())
					GitTraceLocation.getTrace().trace(
							GitTraceLocation.CORE.getLocation(),
			}
			GitIndex index = repository.getIndex();
