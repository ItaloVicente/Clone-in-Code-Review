			.getRepoRelativePath(file);
			Repository repository = repositoryMapping.getRepository();
			repositories.add(repository);
			Git git = new Git(repository);
			if(notIndexed.contains(file))
				continue;
