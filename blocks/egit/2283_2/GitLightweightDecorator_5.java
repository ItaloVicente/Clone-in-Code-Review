
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
		}

		final LabelProviderChangedEvent event = new LabelProviderChangedEvent(
				this, elementList.toArray(new Object[elementList.size()]));
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				fireLabelProviderChanged(event);
			}
		});
