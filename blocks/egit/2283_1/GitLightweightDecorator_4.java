		}
	}

	public static void processDecoration(final Object[] elements) {
		final GitLightweightDecorator decorator = (GitLightweightDecorator) Activator
				.getDefault().getWorkbench().getDecoratorManager()
				.getBaseLabelProvider(DECORATOR_ID);
		if (decorator != null)
			decorator.prepareDecoration(elements);
	}

	public void prepareDecoration(final Object[] elements) {
		if (elements == null)
			return;

		final IResource[] resources = new IResource[elements.length];
		for (int i = 0; i < elements.length; i++) {
			if (elements[i] != null)
				resources[i] = getResource(elements[i]);
		}

		final IDecoratableResource[] decoratableResources = DecoratableResourceHelper
				.createDecoratableResources(resources);

		final ArrayList<Object> elementList = new ArrayList<Object>();
		for (int i = 0; i < decoratableResources.length; i++) {
			try {
				if (decoratableResources[i] != null) {
					resources[i].setSessionProperty(DECORATABLE_RESOURCE_KEY,
							decoratableResources[i]);
					elementList.add(elements[i]);
				} else {
					if (resources[i] != null)
						handleException(resources[i],
								new CoreException(new Status(IStatus.ERROR,
										Activator.getPluginId(),
										"Resource could not be decorated"))); //$NON-NLS-1$ // TODO externalize string
				}
			} catch (CoreException e) {
				handleException(resources[i], new CoreException(new Status(
						IStatus.ERROR, Activator.getPluginId(), e.getMessage(),
						e)));
			}
