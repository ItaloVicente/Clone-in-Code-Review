	private class NestedProjectsLabelProviderAccessor extends NestedProjectsLabelProvider {
		@Override
		public int getHighestProblemSeverity(IResource resource) {
			return super.getHighestProblemSeverity(resource);
		}
	}

	@Test
	public void testProblemDecoration() throws Exception {
		IProgressMonitor monitor = new NullProgressMonitor();
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IProject parentProject = root.getProject("parent");
		parentProject.create(monitor);
		parentProject.open(monitor);
		IFolder intermediaryFolder = parentProject.getFolder("folder");
		intermediaryFolder.create(true, false, monitor);
		IProject firstChildProject = root.getProject("child1");
		IProjectDescription description = root.getWorkspace().newProjectDescription(firstChildProject.getName());
		description.setLocation(intermediaryFolder.getLocation().append(firstChildProject.getName()));
		firstChildProject.create(description, monitor);
		firstChildProject.open(monitor);
		IProject secondChildProject = root.getProject("child2");
		description = root.getWorkspace().newProjectDescription(secondChildProject.getName());
		description.setLocation(intermediaryFolder.getLocation().append(secondChildProject.getName()));
		secondChildProject.create(description, monitor);
		secondChildProject.open(monitor);
		NestedProjectsLabelProviderAccessor labelProvider = new NestedProjectsLabelProviderAccessor();
		labelProvider.init(null);
		assertEquals(-1, labelProvider.getHighestProblemSeverity(parentProject));
		root.getWorkspace().run(aMonitor -> {
			IMarker marker = firstChildProject.createMarker(IMarker.PROBLEM);
			marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_WARNING);
		}, monitor);
		assertEquals(IMarker.SEVERITY_WARNING, labelProvider.getHighestProblemSeverity(parentProject));
		root.getWorkspace().run(aMonitor -> {
			IMarker marker = secondChildProject.createMarker(IMarker.PROBLEM);
			marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
		}, monitor);
		assertEquals(IMarker.SEVERITY_ERROR, labelProvider.getHighestProblemSeverity(parentProject));
		for (IMarker marker : secondChildProject.findMarkers(IMarker.PROBLEM, true, IResource.DEPTH_INFINITE)) {
			marker.delete();
		}
		assertEquals(IMarker.SEVERITY_WARNING, labelProvider.getHighestProblemSeverity(parentProject));
		root.getWorkspace().run(aMonitor -> {
			IMarker marker = secondChildProject.createMarker(IMarker.PROBLEM);
			marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
		}, monitor);
		assertEquals(IMarker.SEVERITY_ERROR, labelProvider.getHighestProblemSeverity(parentProject));
		secondChildProject.close(monitor);
		assertEquals(IMarker.SEVERITY_WARNING, labelProvider.getHighestProblemSeverity(parentProject));
		secondChildProject.open(monitor);
		assertEquals(IMarker.SEVERITY_ERROR, labelProvider.getHighestProblemSeverity(parentProject));
		root.getWorkspace().run(aMonitor -> {
			IMarker marker = parentProject.createMarker(IMarker.PROBLEM);
			marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
			secondChildProject.findMarkers(IMarker.PROBLEM, true, IResource.DEPTH_INFINITE)[0].delete();
		}, monitor);
		assertEquals(IMarker.SEVERITY_ERROR, labelProvider.getHighestProblemSeverity(parentProject));
	}

