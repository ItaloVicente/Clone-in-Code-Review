	@Test
	public void testImportWhereRepoAlreadyExistsAtDifferentLocation() throws Exception {
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IPath reposPath = root.getLocation().append("existingbutdifferent");
		pathsToClean.add(reposPath.toFile());

		IPath repoPath = reposPath.append("repo");
		IProject project = createProject(repoPath, "project");
		project.delete(false, true, null);
		String url = createUrl(repoPath);
		File repoDir = createRepository(repoPath, url, "master");

		IPath otherRepoPath = reposPath.append("other");
		File otherRepoDir = createRepository(otherRepoPath, "other-url", "master");

		RepositoryUtil util = Activator.getDefault().getRepositoryUtil();
		util.addConfiguredRepository(repoDir);
		util.addConfiguredRepository(otherRepoDir);

		String reference = createProjectReference(repoPath, "master", "project");

		addToWorkspace(new String[] { reference });

		IProject imported = root.getProject("project");
		assertEquals("Expected imported project to be from already existing repository",
				root.getLocation().append("existingbutdifferent/repo/project"), imported.getLocation());
	}

