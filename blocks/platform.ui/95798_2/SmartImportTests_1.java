	@Test
	public void testConfigurationIgnoreNestedProjects()
			throws IOException, OperationCanceledException, InterruptedException {
		URL url = FileLocator
				.toFileURL(getClass().getResource("/data/org.eclipse.datatransferArchives/projectSingleModule"));
		File file = new File(url.getFile());
		runSmartImport(file);

		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		assertEquals(2, projects.length);

		IProject rootProject = ResourcesPlugin.getWorkspace().getRoot().getProject("projectSingleModule");
		assertTrue("Missing root project", rootProject.exists());
		assertFalse("Root project shouldn't have been configured",
				ImportMeProjectConfigurator.configuredProjects.contains(rootProject));
		Set<IProject> modules = new HashSet<>(Arrays.asList(projects));
		modules.remove(rootProject);
		assertTrue("All modules should be configured",
				modules.size() == ImportMeProjectConfigurator.configuredProjects.size()
						&& ImportMeProjectConfigurator.configuredProjects.containsAll(modules));
	}

