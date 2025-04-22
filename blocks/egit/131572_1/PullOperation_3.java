				
				Set<IProject> projectsToRefresh = results.entrySet().stream()
						.filter(e -> e.getValue() instanceof PullResult && refreshNeeded((PullResult)e.getValue()))
						.flatMap(e -> {
							try {
								return Arrays.stream(ProjectUtil.getValidOpenProjects(e.getKey()));
							} catch (CoreException ex) {
								throw new RuntimeException(ex);
							}
						})
						.collect(Collectors.toSet());
				
				ProjectUtil.refreshValidProjects(projectsToRefresh.toArray(new IProject[projectsToRefresh.size()]), progress);
				progress.worked(1);
