			Object element = iterator.next();
			if (element instanceof StagingEntry) {
				StagingEntry entry = (StagingEntry) element;
				switch (entry.getState()) {
				case ADDED:
				case CHANGED:
				case REMOVED:
					break;
				case CONFLICTING:
				case MODIFIED:
				case PARTIALLY_MODIFIED:
				case UNTRACKED:
					addPaths.add(entry.getPath());
					break;
				case MISSING:
					if (rm == null)
						rm = git.rm();
					rm.addFilepattern(entry.getPath());
					break;
				}
			} else {
				IResource resource = AdapterUtils.adapt(element, IResource.class);
				if (resource != null) {
					RepositoryMapping mapping = RepositoryMapping.getMapping(resource);
					if (mapping != null && mapping.getRepository() == currentRepository) {
						String path = mapping.getRepoRelativePath(resource);
						if ("".equals(path)) //$NON-NLS-1$
							addPaths.add("."); //$NON-NLS-1$
						else
							addPaths.add(path);
					}
				}
