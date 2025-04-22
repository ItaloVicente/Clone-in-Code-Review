			List<String> filterPaths = new ArrayList<String>();
			Repository repo = null;
			for (IResource resource : resources) {
				RepositoryMapping map = RepositoryMapping.getMapping(resource
						.getProject());
				if (repo != null && repo != map.getRepository())
					throw new InvocationTargetException(
							new IllegalStateException(
									UIText.RepositoryAction_multiRepoSelection));
				filterPaths.add(map.getRepoRelativePath(resource));
				repo = map.getRepository();
			}
