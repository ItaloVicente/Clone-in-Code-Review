				if (presentation == PRESENTATION_COMPRESSED_FOLDERS) {
					StagingViewContentProvider contentProvider = (StagingViewContentProvider) unstagedViewer
							.getContentProvider();
					StagingFolderEntry folder = (StagingFolderEntry) element;
					StagingEntry[] entries = contentProvider
							.getChildResources(folder);
					for (StagingEntry entry : entries) {
						if (isUnfiltered(entry))
							rm = selectEntryForStaging(git, rm, addPaths, entry);
					}
				} else {
					IResource resource = AdapterUtils.adapt(element,
							IResource.class);
					if (resource != null) {
						RepositoryMapping mapping = RepositoryMapping
								.getMapping(resource);
						if (mapping != null
								&& mapping.getRepository() == currentRepository) {
							String path = mapping.getRepoRelativePath(resource);
							if ("".equals(path)) //$NON-NLS-1$
								addPaths.add("."); //$NON-NLS-1$
							else
								addPaths.add(path);
						}
