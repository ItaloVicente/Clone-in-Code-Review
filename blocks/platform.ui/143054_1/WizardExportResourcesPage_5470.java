			if (o instanceof IContainer) {
				IContainer container = (IContainer) o;
				if (!showLinkedResources && container.isLinked(IResource.CHECK_ANCESTORS)) {
					return EMPTY;
				}
				IResource[] members = null;
				try {
					members = container.members();
				} catch (CoreException e) {
					return EMPTY;
				}

				List<IResource> results = new ArrayList<>();
				for (IResource resource : members) {
					if (!showLinkedResources && resource.isLinked()) {
						continue;
					}
					if ((resource.getType() & resourceType) > 0) {
						results.add(resource);
					}
				}
				return results.toArray();
			}
			if (o instanceof ArrayList) {
				return ((List<?>) o).toArray();
			}
			return EMPTY;
		}
	}

	private ITreeContentProvider getResourceProvider(int resourceType, boolean showLinkedResources) {
		return new ResourceProvider(resourceType, showLinkedResources);
	}

	protected boolean getShowLinkedResources() {
		return showLinkedResources;
	}

	protected void updateContentProviders(boolean showLinked) {
		showLinkedResources = showLinked;
		resourceGroup.setTreeProviders(getResourceProvider(IResource.FOLDER | IResource.PROJECT, showLinkedResources),
				WorkbenchLabelProvider.getDecoratingWorkbenchLabelProvider());

		resourceGroup.setListProviders(getResourceProvider(IResource.FILE, showLinkedResources),
				WorkbenchLabelProvider.getDecoratingWorkbenchLabelProvider());
	}

	protected List getSelectedResources() {
		Iterator resourcesToExportIterator = this.getSelectedResourcesIterator();
		List resourcesToExport = new ArrayList();
		while (resourcesToExportIterator.hasNext()) {
