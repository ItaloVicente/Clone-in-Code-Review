					for (IResource resource : model) {
						final ResourceMapping[] modelMappings = modelProvider
								.getMappings(resource, mappingContext,
										new NullProgressMonitor());
						allMappings.addAll(Arrays.asList(modelMappings));
					}
