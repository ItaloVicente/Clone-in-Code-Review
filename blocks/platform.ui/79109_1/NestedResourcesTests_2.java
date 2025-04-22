	@Test
	public void testDashInProject() throws Exception {
		IProgressMonitor monitor = new NullProgressMonitor();
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IProject projectA = root.getProject("a");
		projectA.create(monitor);
		projectA.open(monitor);
		IProjectDescription projectAChildDesc = root.getWorkspace().newProjectDescription("child");
		projectAChildDesc.setLocation(projectA.getLocation().append(projectAChildDesc.getName()));
		IProject projectAChild = root.getProject(projectAChildDesc.getName());
		projectAChild.create(projectAChildDesc, monitor);
		projectAChild.open(monitor);
		IProject projectA_A = root.getProject("a-a");
		projectA_A.create(monitor);
		projectA_A.open(monitor);
		testProjects.add(projectA);
		testProjects.add(projectAChild);
		testProjects.add(projectA_A);

		Assert.assertTrue(NestedProjectManager.getInstance().hasDirectChildrenProjects(projectA));
		Assert.assertEquals(projectAChild, NestedProjectManager.getInstance().getDirectChildrenProjects(projectA)[0]);
	}

