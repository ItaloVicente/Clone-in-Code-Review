
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
				true, Collections.<IResource> singleton(workspaceFile));
		GitSynchronizeDataSet gsds = new GitSynchronizeDataSet(gsd);
		GitResourceVariantTreeSubscriber subscriber = new GitResourceVariantTreeSubscriber(
				gsds);
		subscriber.init(new NullProgressMonitor());

		ResourceMapping mapping = AdapterUtils.adapt(workspaceFile,
				ResourceMapping.class);
		SubscriberScopeManager manager = new SubscriberScopeManager(
				subscriber.getName(), new ResourceMapping[] { mapping, },
				subscriber, true);
		manager.initialize(new NullProgressMonitor());

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
				ResourceMapping[] mappings = provider.getMappings(resource,
						mappingContext, new NullProgressMonitor());
				allMappings.addAll(Arrays.asList(mappings));

				newResources.addAll(collectResources(mappings, mappingContext));
			}
		} while (includedResources.addAll(newResources));
		ResourceMapping[] mappings = allMappings
				.toArray(new ResourceMapping[allMappings.size()]);

		GitSynchronizeData gsd = new GitSynchronizeData(repo, srcRev, dstRev,
				true, includedResources);
		GitSynchronizeDataSet gsds = new GitSynchronizeDataSet(gsd);
		GitResourceVariantTreeSubscriber subscriber = new GitResourceVariantTreeSubscriber(
				gsds);
		subscriber.init(new NullProgressMonitor());

		GitSubscriberResourceMappingContext resourceMappingContext = new GitSubscriberResourceMappingContext(
				subscriber, gsds);
		SubscriberScopeManager manager = new SubscriberScopeManager(
				subscriber.getName(), mappings, subscriber,
				resourceMappingContext, true);
		manager.initialize(new NullProgressMonitor());

		GitSubscriberMergeContext mergeContext = new GitSubscriberMergeContext(
				subscriber, manager, gsds);
		Job.getJobManager().join(mergeContext, new NullProgressMonitor());
		return mergeContext;
	}

	private static Set<IResource> collectResources(ResourceMapping[] mappings,
			ResourceMappingContext mappingContext) throws Exception {
		final Set<IResource> resources = new HashSet<IResource>();
		for (ResourceMapping mapping : mappings) {
			ResourceTraversal[] traversals = mapping.getTraversals(
					mappingContext, new NullProgressMonitor());
			for (ResourceTraversal traversal : traversals)
				resources.addAll(Arrays.asList(traversal.getResources()));
		}
		return resources;
	}

	/**
	 * This model provider can be used to make all files of a given extension to
	 * be part of the same model. In this test, this will be used on files with
	 * extension {@link #SAMPLE_FILE_EXTENSION}.
	 */
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

	/**
	 * This resource mapping will consider all files of extension
	 * {@link #SAMPLE_FILE_EXTENSION} in a given folder to be part of the same
	 * model.
	 */
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

	/**
	 * Since we do not register our model provider the plugin way, we won't have
	 * a descriptor for it, and the default implementation would fail. This will
	 * avoid all the useless calls to provider.getDescriptor().
	 */
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

		private IDiff[] getSetToMerge(IMergeContext mergeContext) {
			ResourceMapping[] mappings = mergeContext.getScope().getMappings(
					providerID);
			Set<IDiff> deltas = new HashSet<IDiff>();
			for (int i = 0; i < mappings.length; i++) {
				ResourceTraversal[] traversals = mergeContext.getScope()
						.getTraversals(mappings[i]);
				deltas.addAll(Arrays.asList(mergeContext.getDiffTree()
						.getDiffs(traversals)));
			}
			return deltas.toArray(new IDiff[deltas.size()]);
		}
	}
