	@Test
	public void testImportFromRepoWithUrlOnlyDifferingInUserName()
			throws Exception {
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IPath reposPath = root.getLocation().append("repos");
		pathsToClean.add(reposPath.toFile());

		IPath repoPath = reposPath.append("repo");
		IProject project = createProject(repoPath, "project");
		project.delete(false, true, null);
		String url = createUrl(repoPath, "ssh", "userName");
		File repoDir = createRepository(repoPath, url, "master");

		RepositoryUtil util = Activator.getDefault().getRepositoryUtil();
		util.addConfiguredRepository(repoDir);

		String reference = createProjectReference(repoPath, "ssh", /* no user */
				null, "master", "project");

		addToWorkspace(new String[] { reference });

		IProject imported = root.getProject("project");
		assertEquals(
				"Expected imported project to be from already existing repository. User name must be ignored in URL.",
				root.getLocation().append("repos/repo/project"),
				imported.getLocation());
	}

