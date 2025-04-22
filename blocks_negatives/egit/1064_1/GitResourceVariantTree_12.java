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
