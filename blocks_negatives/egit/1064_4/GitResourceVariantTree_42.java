		Set<IResourceVariant> members = new HashSet<IResourceVariant>();
		try {
			GitFolderResourceVariant folderVariant = (GitFolderResourceVariant) variant;
			IContainer container = folderVariant.getContainer();
			File resourceLocation = container.getLocation().toFile();
			IProject project = container.getProject();

			Repository repository = gsdData.getData(project).getRepository();

			monitor.beginTask(NLS.bind(
					CoreText.GitResourceVariantTree_fetchingMembers, container
							.getLocation()), updated.size());
			File root = repository.getWorkDir();

			for (Map.Entry<String, ObjectId> entry : updated.entrySet()) {
				String entryName = entry.getKey();
				File file = new File(root, entryName);

				if (file.getAbsolutePath().startsWith(
						resourceLocation.getAbsolutePath())) {
					members.add(getMember(container, repository, entryName));
				}

				monitor.worked(1);
			}
		} finally {
			monitor.done();
		}
		return members.toArray(new IResourceVariant[members.size()]);
	}

	private IResourceVariant getMember(IContainer container,
			Repository repository, String entryName) throws TeamException {
		String gitPath = RepositoryMapping.getMapping(container)
				.getRepoRelativePath(container);
		Tree merge = trees.get(repository);
		try {
			TreeEntry tree = merge.findBlobMember(entryName);
			GitBlobResourceVariant blobVariant = new GitBlobResourceVariant(
					container.getFile(new Path(entryName)), repository, tree
							.getId(), dates.get(entryName));
			return blobVariant;
		} catch (IOException e) {
			throw new TeamException(new Status(IStatus.ERROR, Activator
					.getPluginId(), NLS.bind(
					CoreText.GitResourceVariantTree_couldNotFindBlob, gitPath),
					e));
		}
	}
