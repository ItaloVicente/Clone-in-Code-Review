						if (file != null) {
							item = new LocalResourceTypedElement(file);
						} else {
							item = new LocalNonWorkspaceTypedElement(repository,
									location);
						}
						item.setSharedDocumentListener(
								new LocalResourceSaver(item));
