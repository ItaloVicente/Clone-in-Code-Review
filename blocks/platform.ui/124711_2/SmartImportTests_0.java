		assertEquals("Should have one project configured", 1, ImportMeProjectConfigurator.configuredProjects.size());
		Set<IProject> modules = new HashSet<>(Arrays.asList(projects));
		modules.remove(rootProject);
		assertEquals(modules.size(), ImportMeProjectConfigurator.configuredProjects.size());
		assertTrue("All modules should be configured",
				ImportMeProjectConfigurator.configuredProjects.containsAll(modules));
