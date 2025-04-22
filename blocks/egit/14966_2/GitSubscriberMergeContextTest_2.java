	}

	@Test
	public void mergeNoConflict() throws Exception {
		String fileName = "src/Main.java";
		File file = testRepo.createFile(iProject, fileName);
		final String initialContent = "class Main {}\n";
		testRepo.appendContentAndCommit(iProject, file, initialContent,
				"some file");
		testRepo.addToIndex(iProject.getFile(".classpath"));
		testRepo.addToIndex(iProject.getFile(".project"));
		testRepo.commit("project files");

		IFile workspaceFile = testRepo.getIFile(iProject, file);
		String repoRelativePath = testRepo.getRepoRelativePath(workspaceFile
				.getLocation().toPortableString());

		testRepo.createAndCheckoutBranch(MASTER, BRANCH);

		final String branchChanges = "branch changes\n";
		setContentsAndCommit(repoRelativePath, workspaceFile, branchChanges
				+ initialContent,
				"branch commit");

		testRepo.checkoutBranch(MASTER);

		final String masterChanges = "some changes\n";
		setContentsAndCommit(repoRelativePath, workspaceFile, initialContent
				+ masterChanges,
				"master commit");

		IMergeContext mergeContext = prepareContext(workspaceFile, MASTER,
				BRANCH);
		IDiff node = mergeContext.getDiffTree().getDiff(workspaceFile);
		assertNotNull(node);

		IStatus mergeStatus = mergeContext.merge(node, false,
				new NullProgressMonitor());
		assertEquals(IStatus.OK, mergeStatus.getSeverity());
		assertContentEquals(workspaceFile, branchChanges + initialContent
				+ masterChanges);

		Status status = new Git(repo).status().call();
		assertEquals(1, status.getChanged().size());
		assertEquals(0, status.getModified().size());
		assertTrue(status.getChanged().contains(repoRelativePath));
	}

	@Test
	public void mergeModelNoConflict() throws Exception {
		File file1 = testRepo.createFile(iProject, "file1."
				+ SAMPLE_FILE_EXTENSION);
		File file2 = testRepo.createFile(iProject, "file2."
				+ SAMPLE_FILE_EXTENSION);

		String initialContent1 = "some content for the first file";
		String initialContent2 = "some content for the second file";
		testRepo.appendContentAndCommit(iProject, file1, initialContent1,
				"first file - initial commit");
		testRepo.appendContentAndCommit(iProject, file2, initialContent2,
				"second file - initial commit");

		IFile iFile1 = testRepo.getIFile(iProject, file1);
		IFile iFile2 = testRepo.getIFile(iProject, file2);
		String repoRelativePath1 = testRepo.getRepoRelativePath(iFile1
				.getLocation().toPortableString());
		String repoRelativePath2 = testRepo.getRepoRelativePath(iFile2
				.getLocation().toPortableString());

		testRepo.createAndCheckoutBranch(MASTER, BRANCH);

		final String branchChanges = "branch changes\n";
		setContentsAndCommit(repoRelativePath1, iFile1, branchChanges
				+ initialContent1, "branch commit");
		setContentsAndCommit(repoRelativePath2, iFile2, branchChanges
				+ initialContent2, "branch commit");

		testRepo.checkoutBranch(MASTER);

		final String masterChanges = "some changes\n";
		setContentsAndCommit(repoRelativePath1, iFile1, initialContent1
				+ masterChanges, "master commit");
		setContentsAndCommit(repoRelativePath2, iFile2, initialContent2
				+ masterChanges, "master commit");

		IMergeContext mergeContext = prepareModelContext(iFile1, MASTER, BRANCH);
		IDiff node = mergeContext.getDiffTree().getDiff(iFile1);
		assertNotNull(node);
		node = mergeContext.getDiffTree().getDiff(iFile2);
		assertNotNull(node);

		IResourceMappingMerger merger = new SampleModelMerger(
				SAMPLE_PROVIDER_ID);
		IStatus mergeStatus = merger.merge(mergeContext,
				new NullProgressMonitor());
		assertEquals(IStatus.OK, mergeStatus.getSeverity());
		assertContentEquals(iFile1, branchChanges + initialContent1
				+ masterChanges);
		assertContentEquals(iFile2, branchChanges + initialContent2
				+ masterChanges);

		Status status = new Git(repo).status().call();
		assertEquals(2, status.getChanged().size());
		assertEquals(0, status.getModified().size());
		assertTrue(status.getChanged().contains(repoRelativePath1));
		assertTrue(status.getChanged().contains(repoRelativePath2));
	}

	@Test
	public void mergeWithConflict() throws Exception {
		String fileName = "src/Main.java";
		File file = testRepo.createFile(iProject, fileName);
		final String initialContent = "class Main {}\n";
		testRepo.appendContentAndCommit(iProject, file, initialContent,
				"some file");
		testRepo.addToIndex(iProject.getFile(".classpath"));
		testRepo.addToIndex(iProject.getFile(".project"));
		testRepo.commit("project files");

		IFile workspaceFile = testRepo.getIFile(iProject, file);
		String repoRelativePath = testRepo.getRepoRelativePath(workspaceFile
				.getLocation().toPortableString());

		testRepo.createAndCheckoutBranch(MASTER, BRANCH);

		final String branchChanges = "branch changes\n";
		setContentsAndCommit(repoRelativePath, workspaceFile, initialContent
				+ branchChanges, "branch commit");

		testRepo.checkoutBranch(MASTER);

		final String masterChanges = "some changes\n";
		setContentsAndCommit(repoRelativePath, workspaceFile, initialContent
				+ masterChanges, "master commit");

		IMergeContext mergeContext = prepareContext(workspaceFile, MASTER,
				BRANCH);
		IDiff node = mergeContext.getDiffTree().getDiff(workspaceFile);
		assertNotNull(node);

		IStatus mergeStatus = mergeContext.merge(node, false,
				new NullProgressMonitor());
		assertEquals(IStatus.ERROR, mergeStatus.getSeverity());
		assertContentEquals(workspaceFile, initialContent + masterChanges);

		Status status = new Git(repo).status().call();
		assertEquals(0, status.getChanged().size());
		assertEquals(0, status.getModified().size());
		assertFalse(status.getChanged().contains(repoRelativePath));
	}

	@Test
	public void mergeModelWithConflict() throws Exception {
		File file1 = testRepo.createFile(iProject, "file1."
				+ SAMPLE_FILE_EXTENSION);
		File file2 = testRepo.createFile(iProject, "file2."
				+ SAMPLE_FILE_EXTENSION);

		String initialContent1 = "some content for the first file";
		String initialContent2 = "some content for the second file";
		testRepo.appendContentAndCommit(iProject, file1, initialContent1,
				"first file - initial commit");
		testRepo.appendContentAndCommit(iProject, file2, initialContent2,
				"second file - initial commit");

		IFile iFile1 = testRepo.getIFile(iProject, file1);
		IFile iFile2 = testRepo.getIFile(iProject, file2);
		String repoRelativePath1 = testRepo.getRepoRelativePath(iFile1
				.getLocation().toPortableString());
		String repoRelativePath2 = testRepo.getRepoRelativePath(iFile2
				.getLocation().toPortableString());

		testRepo.createAndCheckoutBranch(MASTER, BRANCH);

		final String branchChanges = "branch changes\n";
		setContentsAndCommit(repoRelativePath1, iFile1, initialContent1
				+ branchChanges, "branch commit");
		setContentsAndCommit(repoRelativePath2, iFile2, initialContent2
				+ branchChanges, "branch commit");

		testRepo.checkoutBranch(MASTER);

		final String masterChanges = "some changes\n";
		setContentsAndCommit(repoRelativePath1, iFile1, initialContent1
				+ masterChanges, "master commit");
		setContentsAndCommit(repoRelativePath2, iFile2, initialContent2
				+ masterChanges, "master commit");

		IMergeContext mergeContext = prepareModelContext(iFile1, MASTER, BRANCH);
		IDiff node = mergeContext.getDiffTree().getDiff(iFile1);
		assertNotNull(node);
		node = mergeContext.getDiffTree().getDiff(iFile2);
		assertNotNull(node);

		IResourceMappingMerger merger = new SampleModelMerger(
				SAMPLE_PROVIDER_ID);
		IStatus mergeStatus = merger.merge(mergeContext,
				new NullProgressMonitor());
		assertEquals(IStatus.ERROR, mergeStatus.getSeverity());

		assertTrue(mergeStatus instanceof IMergeStatus);
		assertEquals(2,
				((IMergeStatus) mergeStatus).getConflictingMappings().length);
		Set<IFile> conflictingFiles = new LinkedHashSet<IFile>();
		for (ResourceMapping conflictingMapping : ((IMergeStatus) mergeStatus)
				.getConflictingMappings()) {
			assertTrue(conflictingMapping instanceof SampleResourceMapping
					&& conflictingMapping.getModelObject() instanceof IFile);
			conflictingFiles.add((IFile) conflictingMapping.getModelObject());
		}
		assertTrue(conflictingFiles.contains(iFile1));
		assertTrue(conflictingFiles.contains(iFile2));

		assertContentEquals(iFile1, initialContent1 + masterChanges);
		assertContentEquals(iFile2, initialContent2 + masterChanges);

		Status status = new Git(repo).status().call();
		assertEquals(0, status.getChanged().size());
		assertEquals(0, status.getModified().size());
	}

	private RevCommit setContentsAndCommit(String repoRelativePath,
			IFile targetFile,
			String newContents, String commitMessage) throws Exception {
		targetFile.setContents(
				new ByteArrayInputStream(newContents.getBytes()),
				IResource.FORCE, new NullProgressMonitor());
		new Git(testRepo.getRepository()).add()
				.addFilepattern(repoRelativePath).call();
		testRepo.addToIndex(targetFile);
		return testRepo.commit(commitMessage);
	}

	private void assertContentEquals(IFile file, String expectedContents)
			throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				file.getContents()));
		StringBuilder contentsBuilder = new StringBuilder();
		String line = reader.readLine();
		while (line != null) {
			contentsBuilder.append(line);
			contentsBuilder.append('\n');
			line = reader.readLine();
		}
		reader.close();
		assertEquals(expectedContents, contentsBuilder.toString());
	}

	private IMergeContext prepareContext(IFile workspaceFile, String srcRev,
			String dstRev) throws Exception {
		GitSynchronizeData gsd = new GitSynchronizeData(repo, srcRev, dstRev,
				true);
		GitSynchronizeDataSet gsds = new GitSynchronizeDataSet(gsd);
		GitResourceVariantTreeSubscriber subscriber = new GitResourceVariantTreeSubscriber(
				gsds);
		subscriber.init(new NullProgressMonitor());

		ResourceMapping mapping = AdapterUtils.adapt(workspaceFile,
				ResourceMapping.class);
		SubscriberScopeManager manager = new SubscriberScopeManager(
				subscriber.getName(), new ResourceMapping[] { mapping, },
				subscriber, true);
		try {
			manager.initialize(new NullProgressMonitor());
		} catch (CoreException e) {
			fail("Couldn't expand the subscriber scope");
		}
		GitSubscriberMergeContext mergeContext = new GitSubscriberMergeContext(
				subscriber, manager, gsds);
		Job.getJobManager().join(mergeContext, new NullProgressMonitor());
		return mergeContext;
	}

	private IMergeContext prepareModelContext(IFile workspaceFile,
			String srcRev,
			String dstRev) throws Exception {
		Set<IResource> includedResources = new HashSet<IResource>(
				Arrays.asList(workspaceFile));
		Set<IResource> newResources = new HashSet<IResource>(includedResources);
		Set<ResourceMapping> allMappings = new HashSet<ResourceMapping>();
		ResourceMappingContext mappingContext = ResourceMappingContext.LOCAL_CONTEXT;
		ModelProvider provider = new SampleProvider();
		do {
			Set<IResource> copy = newResources;
			newResources = new HashSet<IResource>();
			for (IResource resource : copy) {
				try {
					ResourceMapping[] mappings = provider.getMappings(resource,
							mappingContext, new NullProgressMonitor());
					allMappings.addAll(Arrays.asList(mappings));

					newResources.addAll(collectResources(mappings,
							mappingContext));
				} catch (CoreException e) {
					fail("Could not expand scope to the model of " + resource);
				}
			}
		} while (includedResources.addAll(newResources));
		ResourceMapping[] mappings = allMappings
				.toArray(new ResourceMapping[allMappings.size()]);

		GitSynchronizeData gsd = new GitSynchronizeData(repo, srcRev, dstRev,
				true);
		GitSynchronizeDataSet gsds = new GitSynchronizeDataSet(gsd);
		GitResourceVariantTreeSubscriber subscriber = new GitResourceVariantTreeSubscriber(
				gsds);
		subscriber.init(new NullProgressMonitor());

		GitSubscriberResourceMappingContext resourceMappingContext = new GitSubscriberResourceMappingContext(
				subscriber, gsds);
		SubscriberScopeManager manager = new SubscriberScopeManager(
				subscriber.getName(), mappings, subscriber,
				resourceMappingContext, true);
		try {
			manager.initialize(new NullProgressMonitor());
		} catch (CoreException e) {
			fail("Couldn't expand the subscriber scope");
		}
		GitSubscriberMergeContext mergeContext = new GitSubscriberMergeContext(
				subscriber, manager, gsds);
		Job.getJobManager().join(mergeContext, new NullProgressMonitor());
		return mergeContext;
	}

	private static Set<IResource> collectResources(ResourceMapping[] mappings,
			ResourceMappingContext mappingContext) {
		final Set<IResource> resources = new HashSet<IResource>();
		for (ResourceMapping mapping : mappings) {
			try {
				ResourceTraversal[] traversals = mapping.getTraversals(
						mappingContext,
						new NullProgressMonitor());
				for (ResourceTraversal traversal : traversals)
					resources.addAll(Arrays.asList(traversal.getResources()));
			} catch (CoreException e) {
				fail("Could not obtain traversal of the mapping for "
						+ mapping.getModelObject());
			}
		}
		return resources;
	}

	private static class SampleProvider extends ModelProvider {
		@Override
		public ResourceMapping[] getMappings(IResource resource,
				ResourceMappingContext context, IProgressMonitor monitor)
				throws CoreException {
			if (resource instanceof IFile
					&& SAMPLE_FILE_EXTENSION
							.equals(resource.getFileExtension())) {
				return new ResourceMapping[] { new SampleResourceMapping(
						(IFile) resource, SAMPLE_PROVIDER_ID), };
			}
			return super.getMappings(resource, context, monitor);
		}
	}

	private static class SampleResourceMapping extends ResourceMapping {
		private final IFile file;

		private final String providerId;

		public SampleResourceMapping(IFile file, String providerId) {
			this.file = file;
			this.providerId = providerId;
		}

		@Override
		public Object getModelObject() {
			return file;
		}

		@Override
		public String getModelProviderId() {
			return providerId;
		}

		@Override
		public ResourceTraversal[] getTraversals(
				ResourceMappingContext context, IProgressMonitor monitor)
				throws CoreException {
			Set<IFile> sampleSiblings = new LinkedHashSet<IFile>();
			for (IResource res : file.getParent().members()) {
				if (res instanceof IFile
						&& SAMPLE_FILE_EXTENSION.equals(res.getFileExtension())) {
					sampleSiblings.add((IFile) res);
				}
			}
			final IResource[] resourceArray = sampleSiblings
					.toArray(new IResource[sampleSiblings.size()]);
			return new ResourceTraversal[] { new ResourceTraversal(
					resourceArray, IResource.DEPTH_ONE, IResource.NONE), };
		}

		@Override
		public IProject[] getProjects() {
			return new IProject[] { file.getProject(), };
		}
	}

	private static class SampleModelMerger extends ResourceMappingMerger {
		private final String providerID;

		public SampleModelMerger(String providerID) {
			this.providerID = providerID;
		}

		@Override
		protected ModelProvider getModelProvider() {
			return null;
		}

		@Override
		public IStatus merge(IMergeContext mergeContext,
				IProgressMonitor monitor) throws CoreException {
			IDiff[] deltas = getSetToMerge(mergeContext);
			IStatus status = mergeContext.merge(deltas, false /* don't force */,
					monitor);
			if (status.getCode() == IMergeStatus.CONFLICTS) {
				return new MergeStatus(status.getPlugin(), status.getMessage(),
						mergeContext.getScope().getMappings(providerID));
			}
			return status;
		}
