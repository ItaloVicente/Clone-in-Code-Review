				Map<String, Set<ProjectReference>> repositoryBranches = repositories
						.get(projectReference.getRepository());
				if (repositoryBranches == null) {
					repositoryBranches = new HashMap<>();
					repositories.put(projectReference.getRepository(),
							repositoryBranches);
				}
				Set<ProjectReference> projectReferences = repositoryBranches
						.get(projectReference.getBranch());
				if (projectReferences == null) {
					projectReferences = new LinkedHashSet<>();
					repositoryBranches.put(projectReference.getBranch(),
							projectReferences);
				}

