	@Test
	public void testImportWithMultipleCallsForSameDestinationRepo() throws Exception {
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IPath reposPath = root.getLocation().append("repos");
		pathsToClean.add(reposPath.toFile());

		IPath xPath = reposPath.append("x");
		IProject xaProject = createProject(xPath, "xa");
		IProject xbProject = createProject(xPath, "xb");
		String url = createUrl(xPath);
		createRepository(xPath, url, "stable");
		xaProject.delete(false, true, null);
		xbProject.delete(false, true, null);

		String xaReference = createProjectReference(xPath, "master", "xa");
		String xbReference = createProjectReference(xPath, "master", "xb");

		capability.addToWorkspace(new String[] { xaReference },
				new ProjectSetSerializationContext(),
				new NullProgressMonitor());

		capability.addToWorkspace(new String[] { xbReference },
				new ProjectSetSerializationContext(),
				new NullProgressMonitor());

		pathsToClean.add(root.getLocation().append("x").toFile());

		IPath otherPathWithX = reposPath.append("other").append("x");
		String otherReferenceWithDifferentUrl = createProjectReference(otherPathWithX, "master", "xb");

		try {
			capability.addToWorkspace(new String[] { otherReferenceWithDifferentUrl },
					new ProjectSetSerializationContext(),
					new NullProgressMonitor());
			fail("Should throw TeamException when a repo exists at the place but doesn't have the same URL.");
		} catch (TeamException e) {
		}
	}

