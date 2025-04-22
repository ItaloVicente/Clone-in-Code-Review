	@Test
	public void shouldNotReturnNullOnSameResouceVariant() throws Exception {
		String changingFileName = "changingFile."
				+ SampleModelProvider.SAMPLE_FILE_EXTENSION;
		String notChangingFileName = "notChangingFile."
				+ SampleModelProvider.SAMPLE_FILE_EXTENSION;
		String toBeRemovedFileName = "toBeRemovedFile."
				+ SampleModelProvider.SAMPLE_FILE_EXTENSION;

		File changingFile = testRepo.createFile(iProject, changingFileName);
		File notChangingFile = testRepo.createFile(iProject,
				notChangingFileName);
		File toBeRemovedFile = testRepo.createFile(iProject,
				toBeRemovedFileName);

		testRepo.appendFileContent(changingFile, "My content is changing");
		testRepo.appendFileContent(notChangingFile, "My content is constant");
		testRepo.appendFileContent(toBeRemovedFile, "I will be removed");

		IFile iChangingFile = testRepo.getIFile(iProject, changingFile);
		IFile iNotChangingFile = testRepo.getIFile(iProject, notChangingFile);
		IFile iToBeRemovedFile = testRepo.getIFile(iProject, toBeRemovedFile);

		testRepo.trackAllFiles(iProject);

		RevCommit firstCommit = testRepo.commit("C1");

		testRepo.appendFileContent(changingFile, " My content has changed");
		testRepo.track(changingFile);
		testRepo.rm(toBeRemovedFile);

		RevCommit secondCommit = testRepo.commit("C2");


		testRepo.checkoutBranch(firstCommit.getName());

		iProject.refreshLocal(IResource.DEPTH_INFINITE,
				new NullProgressMonitor());

		SampleModelProvider provider = new SampleModelProvider();
		ResourceMapping[] mappings = provider
				.getMappings(iChangingFile,
						ResourceMappingContext.LOCAL_CONTEXT,
						new NullProgressMonitor());

		Set<IResource> includedResource = collectResources(mappings);
		Set<IResource> expectedIncludedResources = new HashSet<IResource>();
		expectedIncludedResources.add(iChangingFile);
		expectedIncludedResources.add(iNotChangingFile);
		expectedIncludedResources.add(iToBeRemovedFile);

		assertEquals(expectedIncludedResources, includedResource);

		final GitSynchronizeData data = new GitSynchronizeData(
				testRepo.getRepository(), firstCommit.getName(),
				secondCommit.getName(), true, includedResource);
		GitSynchronizeDataSet gitSynchDataSet = new GitSynchronizeDataSet(data);
		final GitResourceVariantTreeSubscriber subscriber = new GitResourceVariantTreeSubscriber(
				gitSynchDataSet);
		subscriber.init(new NullProgressMonitor());


		IResourceVariantTree sourceVariantTree = subscriber.getSourceTree();
		assertNotNull(sourceVariantTree);

		IResourceVariantTree remoteVariantTree = subscriber.getRemoteTree();
		assertNotNull(remoteVariantTree);

		IResourceVariant notChangingSourceVariant = sourceVariantTree
				.getResourceVariant(iNotChangingFile);

		IResourceVariant notChangingRemoteVariant = remoteVariantTree
				.getResourceVariant(iNotChangingFile);

		IResourceVariant toBeRemovedSourceVariant = sourceVariantTree
				.getResourceVariant(iToBeRemovedFile);

		IResourceVariant toBeRemovedRemoteVariant = remoteVariantTree
				.getResourceVariant(iToBeRemovedFile);


		assertNotNull(toBeRemovedSourceVariant);
		assertNull(toBeRemovedRemoteVariant);
		assertNotNull(notChangingSourceVariant);
		assertNotNull(notChangingRemoteVariant);

		GitSubscriberResourceMappingContext context = new GitSubscriberResourceMappingContext(subscriber, gitSynchDataSet);
		assertFalse(context.hasLocalChange(iNotChangingFile, new NullProgressMonitor()));
		assertFalse(context.hasRemoteChange(iNotChangingFile, new NullProgressMonitor()));

		assertFalse(context.hasLocalChange(iChangingFile, new NullProgressMonitor()));
		assertTrue(context.hasRemoteChange(iChangingFile, new NullProgressMonitor()));

		assertFalse(context.hasLocalChange(iToBeRemovedFile, new NullProgressMonitor()));
		assertTrue(context.hasRemoteChange(iToBeRemovedFile, new NullProgressMonitor()));
	}

	private static Set<IResource> collectResources(ResourceMapping[] mappings)
			throws CoreException {
		final Set<IResource> resources = new HashSet<IResource>();
		ResourceMappingContext context = ResourceMappingContext.LOCAL_CONTEXT;
		for (ResourceMapping mapping : mappings) {
			ResourceTraversal[] traversals = mapping.getTraversals(context,
					new NullProgressMonitor());
			for (ResourceTraversal traversal : traversals)
				resources.addAll(Arrays.asList(traversal.getResources()));
		}
		return resources;
	}

