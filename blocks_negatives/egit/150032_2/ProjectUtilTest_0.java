	@Test
	public void testRefreshValidProjects() throws Exception {
		IProject p = spy(project.getProject());
		IProject[] projects = { p };
		ProjectUtil.refreshValidProjects(projects, new NullProgressMonitor());
		verify(p).refreshLocal(eq(IResource.DEPTH_INFINITE),
				any(IProgressMonitor.class));
	}

