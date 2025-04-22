			File gitDir = repository.getDirectory();
			if (gitDir != null) {
				String name = repositoryNameCache.get(gitDir.getPath()
						.toString());
				if (name != null)
					return name;
				name = gitDir.getParentFile().getName();
				repositoryNameCache.put(gitDir.getPath().toString(), name);
