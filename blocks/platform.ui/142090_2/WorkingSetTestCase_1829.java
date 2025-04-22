	}

	private void assertMatches(ResourceMapping mapping, IResource[] resources) throws CoreException {
		assertTrue(mapping != null);
		ResourceTraversal[] traversals = mapping.getTraversals(null, null);
		assertTrue(traversals.length == resources.length);
		for (ResourceTraversal traversal : traversals) {
			boolean found = false;
			for (IResource element : resources) {
				if (element.equals(traversal.getResources()[0])) {
					found = true;
				}
			}
			assertTrue(found);
		}

	}

	private IProject createProject(String name) throws CoreException {
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(getName() + name);
		project.create(null);
		project.open(IResource.NONE, null);
		return project;
	}

	public void testWorkSetAdaptation() throws CoreException {

		IWorkingSetManager man = getWorkbench().getWorkingSetManager();
		IResource[] resources = new IResource[3];
		resources[0] = createProject("Project0");
		resources[1] = createProject("Project1");
		resources[2] = createProject("Project2");
		IWorkingSet set = man.createWorkingSet("test", resources);
		ResourceMapping mapping = getResourceMapping(set);
		assertMatches(mapping, resources);

		IWorkbenchAdapter adapter = getWorkbenchAdapter(set);
		String name = adapter.getLabel(set);
		assertEquals("test", name);

		QualifiedName key = new QualifiedName("org.eclipse.ui.test", "set");
		ResourceMappingPropertyTester tester = new ResourceMappingPropertyTester();

		assertFalse(tester.test(getResourceMapping(set), IResourceActionFilter.PROJECT_PERSISTENT_PROPERTY, new Object[] { "org.eclipse.ui.test.set", "one"}, null));

		resources[0].setPersistentProperty(key, "one");
		assertTrue(tester.test(getResourceMapping(set), IResourceActionFilter.PROJECT_PERSISTENT_PROPERTY, new Object[] { "org.eclipse.ui.test.set", "one"}, null));
		assertFalse(tester.test(getResourceMapping(set), IResourceActionFilter.PROJECT_PERSISTENT_PROPERTY, new Object[] { "org.eclipse.ui.test.set", "wrong"}, null));

		resources[1].setPersistentProperty(key, "two");
		assertFalse(tester.test(getResourceMapping(set), IResourceActionFilter.PROJECT_PERSISTENT_PROPERTY, new Object[] { "org.eclipse.ui.test.set", "one"}, null));
		assertFalse(tester.test(getResourceMapping(set), IResourceActionFilter.PROJECT_PERSISTENT_PROPERTY, new Object[] { "org.eclipse.ui.test.set", "two"}, null));

		resources[1].setPersistentProperty(key, "one");
		resources[2].setPersistentProperty(key, "one");
		assertTrue(tester.test(getResourceMapping(set), IResourceActionFilter.PROJECT_PERSISTENT_PROPERTY, new Object[] { "org.eclipse.ui.test.set", "one"}, null));
		assertFalse(tester.test(getResourceMapping(set), IResourceActionFilter.PROJECT_PERSISTENT_PROPERTY, new Object[] { "org.eclipse.ui.test.set", "two"}, null));

		((IProject)resources[0]).close(null);
		assertTrue(tester.test(getResourceMapping(set), IResourceActionFilter.PROJECT_PERSISTENT_PROPERTY, new Object[] { "org.eclipse.ui.test.set", "one"}, null));
	}
