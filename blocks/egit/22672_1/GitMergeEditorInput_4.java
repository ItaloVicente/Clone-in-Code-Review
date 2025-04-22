			checkCanceled(monitor);

			if (!resourcesInOperation.isEmpty() && outOfWS) {
				throw new InvocationTargetException(new IllegalStateException(
						UIText.GitMergeEditorInput_OutOfWSResources));
			} else if (resourcesInOperation.isEmpty()) {
			} else {
				final RemoteResourceMappingContext remoteMappingContext = new SubscriberResourceMappingContext(
						subscriber, true);
				final IResource firstResource = resourcesInOperation.iterator()
						.next();
				final Set<IResource> model = LogicalModels.discoverModel(
						firstResource, remoteMappingContext);

				if (!model.containsAll(resourcesInOperation)) {
					throw new RuntimeException(
							UIText.GitMergeEditorInput_MultipleModels);
				}

				final ISynchronizationCompareAdapter compareAdapter = LogicalModels
						.findAdapter(model,
								ISynchronizationCompareAdapter.class);
				if (compareAdapter != null) {
					final Set<ResourceMapping> allMappings = LogicalModels
							.getResourceMappings(model, remoteMappingContext);

					checkCanceled(monitor);

					final ISynchronizationContext synchronizationContext = prepareSynchronizationContext(
							repository, subscriber, allMappings,
							remoteMappingContext);
					final Object modelObject = allMappings.iterator().next()
							.getModelObject();
					if (compareAdapter.hasCompareInput(synchronizationContext,
							modelObject))
						return compareAdapter.asCompareInput(
								synchronizationContext, modelObject);
					else {
					}
				} else {
				}
