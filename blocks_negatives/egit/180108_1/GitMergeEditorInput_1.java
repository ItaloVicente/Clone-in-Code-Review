						left = new LocalNonWorkspaceTypedElement(repository,
								location);
					}
					if (left instanceof LocalResourceTypedElement) {
						((LocalResourceTypedElement) left)
								.setSharedDocumentListener(
										new LocalResourceSaver(
												(LocalResourceTypedElement) left));
