
    private ResourceMapping[] getSelectedResourceMappings(ExecutionEvent event) throws ExecutionException {
        Object[] elements = getSelectedResources(event);
        ArrayList providerMappings = new ArrayList();
        for (int i = 0; i < elements.length; i++) {
            Object object = elements[i];
            Object adapted = getResourceMapping(object);
            if (adapted instanceof ResourceMapping) {
                ResourceMapping mapping = (ResourceMapping) adapted;
                if (isMappedToProvider(mapping, "org.eclipse.egit.core.GitProvider")) { //$NON-NLS-1$
                    providerMappings.add(mapping);
                }
            }
        }
        return (ResourceMapping[]) providerMappings.toArray(new ResourceMapping[providerMappings.size()]);
    }

    private Object getResourceMapping(Object object) {
        if (object instanceof ResourceMapping)
            return object;

        if (object instanceof IAdaptable)
        	return ((IAdaptable) object).getAdapter(ResourceMapping.class);

        return Utils.getResourceMapping(object);
    }

    private boolean isMappedToProvider(ResourceMapping element, String providerId) {
        IProject[] projects = element.getProjects();
        for (int k = 0; k < projects.length; k++) {
            IProject project = projects[k];
            RepositoryProvider provider = RepositoryProvider.getProvider(project);
            if (provider != null && provider.getID().equals(providerId)) {
                return true;
            }
        }
        return false;
    }

	private IWorkbenchPart getTargetPart() {
		IWorkbenchPart targetPart = null;
		IWorkbenchPage page = TeamUIPlugin.getActivePage();
		if (page != null) {
			targetPart = page.getActivePart();
		}
		return targetPart;
	}

