						"/project1/link2project2" });
		Collection<IResource> resources2 = resourceDeltaTestHelper2
				.getChangedResources();
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IResource resourceInProject2 = root
				.findMember("/project2/project2folder1/project2folder1file2.txt");
		IResource resourceInLinkedFolder = root
				.findMember("/project1/link2project2/project2folder1/project2folder1file2.txt");
		assertTrue(
				"Expected resource changed event either for original path or for linked folder",
				resources2.contains(resourceInProject2)
						|| resources2.contains(resourceInLinkedFolder));
