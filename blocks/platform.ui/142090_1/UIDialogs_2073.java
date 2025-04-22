		DialogCheck.assertDialog(dialog);
	}

	public void testCopyMoveProject() {
		IProject dummyProject = ResourcesPlugin.getWorkspace().getRoot()
				.getProject("DummyProject");
		Dialog dialog = new ProjectLocationSelectionDialog(getShell(),
				dummyProject);
		DialogCheck.assertDialog(dialog);
	}

	public void testCopyMoveResource() {
		Dialog dialog = new ContainerSelectionDialog(getShell(), null, true,
				"Select Destination");
		DialogCheck.assertDialog(dialog);
	}

	public void testEditActionSetsDialog() {
		fail("CustomizePerspectiveDialog not implemented");
