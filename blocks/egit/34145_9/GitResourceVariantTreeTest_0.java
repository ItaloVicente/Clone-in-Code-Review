	@Test
	public void shouldNotReturnNullOnSameResouceVariant() throws Exception {
		String modifiedFileName = "changingFile."
				+ SampleModelProvider.SAMPLE_FILE_EXTENSION;
		String unchangedFileName = "notChangingFile."
				+ SampleModelProvider.SAMPLE_FILE_EXTENSION;
		String removedFileName = "toBeRemovedFile."
				+ SampleModelProvider.SAMPLE_FILE_EXTENSION;

		File modifiedFile = testRepo.createFile(iProject, modifiedFileName);
		File unchangedFile = testRepo.createFile(iProject, unchangedFileName);
		File removedFile = testRepo.createFile(iProject, removedFileName);

		testRepo.appendFileContent(modifiedFile, "My content is changing");
		testRepo.appendFileContent(unchangedFile, "My content is constant");
		testRepo.appendFileContent(removedFile, "I will be removed");

		IFile iModifiedFile = testRepo.getIFile(iProject, modifiedFile);
		IFile iUnchangedFile = testRepo.getIFile(iProject, unchangedFile);
		IFile iRemovedFile = testRepo.getIFile(iProject, removedFile);

		testRepo.trackAllFiles(iProject);

		RevCommit firstCommit = testRepo.commit("C1");

		testRepo.appendFileContent(modifiedFile, " My content has changed");
		testRepo.track(modifiedFile);
		testRepo.removeFromIndex(removedFile);

		RevCommit secondCommit = testRepo.commit("C2");


		testRepo.checkoutBranch(firstCommit.getName());

		iProject.refreshLocal(IResource.DEPTH_INFINITE,
				new NullProgressMonitor());

		SampleModelProvider provider = new SampleModelProvider();
		ResourceMapping[] mappings = provider
				.getMappings(iModifiedFile,
						ResourceMappingContext.LOCAL_CONTEXT,
						new NullProgressMonitor());

		Set<IResource> includedResource = collectResources(mappings);
		Set<IResource> expectedIncludedResources = new HashSet<IResource>();
		expectedIncludedResources.add(iModifiedFile);
		expectedIncludedResources.add(iUnchangedFile);
		expectedIncludedResources.add(iRemovedFile);

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


		IResourceVariant unchangedSourceVariant = sourceVariantTree
				.getResourceVariant(iUnchangedFile);
		IResourceVariant unchangedRemoteVariant = remoteVariantTree
				.getResourceVariant(iUnchangedFile);

		assertNotNull(unchangedSourceVariant);
		assertNotNull(unchangedRemoteVariant);

		IResourceVariant removedSourceVariant = sourceVariantTree
				.getResourceVariant(iRemovedFile);
		IResourceVariant removedRemoteVariant = remoteVariantTree
				.getResourceVariant(iRemovedFile);

		assertNotNull(removedSourceVariant);
		assertNull(removedRemoteVariant);

		GitSubscriberResourceMappingContext context = new GitSubscriberResourceMappingContext(subscriber, gitSynchDataSet);
		assertFalse(context.hasLocalChange(iUnchangedFile,
				new NullProgressMonitor()));
		assertFalse(context.hasRemoteChange(iUnchangedFile,
				new NullProgressMonitor()));

		assertFalse(context.hasLocalChange(iModifiedFile,
				new NullProgressMonitor()));
		assertTrue(context.hasRemoteChange(iModifiedFile,
				new NullProgressMonitor()));

		assertFalse(context.hasLocalChange(iRemovedFile,
				new NullProgressMonitor()));
		assertTrue(context.hasRemoteChange(iRemovedFile,
				new NullProgressMonitor()));
	}

	private static Set<IResource> collectResources(ResourceMapping[] mappings)
			throws CoreException {
		final Set<IResource> resources = new HashSet<IResource>();
		ResourceMappingContext context = ResourceMappingContext.LOCAL_CONTEXT;
		for (ResourceMapping mapping : mappings) {
			ResourceTraversal[] traversals = mapping.getTraversals(context,
					new NullProgressMonitor());
			for (ResourceTraversal traversal : traversals) {
				resources.addAll(Arrays.asList(traversal.getResources()));
			}
		}
		return resources;
	}

